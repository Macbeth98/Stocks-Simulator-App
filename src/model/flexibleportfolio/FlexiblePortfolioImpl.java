package model.flexibleportfolio;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.flexibleportfolio.FlexiblePortfolio;

public class FlexiblePortfolioImpl extends PortfolioImpl implements FlexiblePortfolio {

  private final List<PortfolioItemTransaction> portfolioItemTransactions;

  public FlexiblePortfolioImpl(String portfolioName) throws IllegalArgumentException {
    super(portfolioName, null,
            System.getProperty("user.dir") + "/portfolioTxnFiles/");

    portfolioItemTransactions = new ArrayList<>();
    try {
      this.savePortfolioToFile();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File Saving error..." + e.getMessage());
    }
  }

  public FlexiblePortfolioImpl(String portfolioName, String fileName,
                               List<PortfolioItemTransaction> portfolioItemTransactions)
          throws IllegalArgumentException {
    super(portfolioName, fileName,
            System.getProperty("user.dir") + "/portfolioTxnFiles/");

    this.portfolioItemTransactions = new ArrayList<>(portfolioItemTransactions);

    this.constructPortfolio();

    try {
      this.saveTransactionToFile();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File Saving error..." + e.getMessage());
    }

  }

  private void sortByDate() {
    this.portfolioItemTransactions.sort(Comparator.comparing(PortfolioItemTransaction::getDate));
  }

  private void saveTransactionToFile() throws FileNotFoundException {
    // call save to file.
    this.sortByDate();
    PrintStream out = this.getFileOutStream(this.getSaveFilePath());

    portfolioItemTransactions.forEach(itemTxn -> {
      String itemTxnStr = itemTxn.toString();

      out.println(itemTxnStr);
    });

    this.fileSaved = true;
  }

  private float getUpdatedStockQuantity(String ticker, float quantity) {
    float existingQuantity = 0;

    if (this.stocks.containsKey(ticker)) {
      existingQuantity = this.stocks.get(ticker).getQuantity();
    }

    return existingQuantity + quantity;
  }

  private void constructPortfolio() {
    for (PortfolioItemTransaction item: portfolioItemTransactions) {
      float quantity = getUpdatedStockQuantity(item.getStock().getTicker(), item.getQuantity());

      PortfolioItem portfolioItem = new PortfolioItem(item.getStock(), quantity,
              item.getStock().getCurrentPriceAtDate(item.getDate()));

      this.stocks.put(item.getStock().getTicker(), portfolioItem);
    }
  }

  @Override
  public FlexiblePortfolio addStock(String stockTicker, float quantity,
                                    LocalDate purchaseDate, float commission)
          throws FileNotFoundException {

    StockObject stock = new StockObjectImpl(stockTicker);

    PortfolioItemTransaction portfolioItemTransaction = new PortfolioItemTransaction(
            TransactionType.BUY, stock, quantity, purchaseDate, commission
    );

    float cumulativeQuantity = getUpdatedStockQuantity(stockTicker, quantity);

    PortfolioItem portfolioItem = new PortfolioItem(stock, cumulativeQuantity,
            stock.getCurrentPriceAtDate(purchaseDate));

    this.stocks.put(stock.getTicker(), portfolioItem);

    // this.savePortfolioToFile();

    portfolioItemTransactions.add(portfolioItemTransaction);

    this.saveTransactionToFile();

    return this;
  }

  @Override
  public FlexiblePortfolio sellStock(String stockTicker, float quantity,
                                     LocalDate saleDate, float commission) throws FileNotFoundException {

    StockObject stock = new StockObjectImpl(stockTicker);

    PortfolioItemTransaction portfolioItemTransaction = new PortfolioItemTransaction(
            TransactionType.SELL, stock, quantity, saleDate, commission
    );

    float existingQuantity = 0;

    if(this.stocks.containsKey(stock.getTicker())) {
      existingQuantity = this.stocks.get(stock.getTicker()).getQuantity();
    } else {
      throw new IllegalArgumentException("There is not a previous buy transaction for this Stock.");
    }

    if (existingQuantity < quantity) {
      throw new IllegalArgumentException("Cannot input this sale. "
              + "The total transactions value do not match.");
    }

    this.sortByDate();

    float tillDateQuantity = 0;

    for (PortfolioItemTransaction txn : portfolioItemTransactions) {
      if (txn.getDate().compareTo(saleDate) > 0) {
        break;
      }

      tillDateQuantity += txn.getQuantity();
    }

    if(tillDateQuantity < quantity) {
      throw new IllegalArgumentException("Cannot input this sale. "
              + "The till date transactions value do not match.");
    }

    float resultQuantity = existingQuantity - quantity;

    if(resultQuantity == 0) {
      this.stocks.remove(stock.getTicker());
    } else {
      PortfolioItem portfolioItem = new PortfolioItem(stock, resultQuantity,
              stock.getCurrentPriceAtDate(saleDate));

      this.stocks.put(stock.getTicker(), portfolioItem);
    }

    portfolioItemTransactions.add(portfolioItemTransaction);

    // this.savePortfolioToFile();

    this.saveTransactionToFile();

    return this;
  }

  @Override
  public float getCostBasis(LocalDate tillDate) {
    return portfolioItemTransactions
            .stream()
            .map(PortfolioItemTransaction::getCostBasis)
            .reduce((float) 0, Float::sum);
  }

  private boolean isToday(LocalDate date) {
    return date.equals(LocalDate.now());
  }

  private Map<StockObject, Float> getPortfolioTillDate(LocalDate date) {
    this.sortByDate();

    Map<StockObject, Float> stocks = new HashMap<>();

    for (PortfolioItemTransaction item: portfolioItemTransactions) {
      if(item.getDate().compareTo(date) <= 0) {
        float stockAmount = 0;

        if(stocks.containsKey(item.getStock())) {
          stockAmount = stocks.get(item.getStock());
        }

        stockAmount += item.getQuantity();
        if(stockAmount <= 0) {
          stocks.remove(item.getStock());
        }
        stocks.put(item.getStock(), stockAmount);
      } else {
        break;
      }
    }

    return stocks;
  }

  private Portfolio buildPortfolio(Map<StockObject, Float> stocks) {
    PortfolioImpl.PortfolioBuilder builder = PortfolioImpl.getBuilder();

    builder = builder.portfolioName(this.portfolioName)
            .setPortfolioFileName(Paths.get(this.getPortfolioFilePath()));

    StockObject[] stockObjects = stocks.keySet().toArray(new StockObject[0]);

    for (StockObject stockObject: stockObjects) {
      builder = builder.addStockToPortfolio(stockObject, stocks.get(stockObject));
    }

    return builder.build();
  }

  @Override
  public PortfolioItem[] getPortfolioCompositionAtDate(LocalDate date) throws IllegalArgumentException {

    if(isToday(date)) {
      return super.getPortfolioComposition();
    }

    Map<StockObject, Float> stocks = this.getPortfolioTillDate(date);
    int txnCount = stocks.size();

    if (txnCount == 0) {
      throw new IllegalArgumentException("The Portfolio Has not been yet created at this date!");
    }

    Portfolio portfolio = this.buildPortfolio(stocks);

    return portfolio.getPortfolioComposition();
  }

  @Override
  public float getPortfolioValueAtDate(LocalDate date) throws IllegalArgumentException {

    if(isToday(date)) {
      return super.getPortfolioValueAtDate(date);
    }

    Map<StockObject, Float> stocks = this.getPortfolioTillDate(date);

    if(stocks.size() == 0) {
      return 0;
    }

    Portfolio portfolio = this.buildPortfolio(stocks);

    return portfolio.getPortfolioValueAtDate(date);
  }

  private Map<String, Float> performanceHelper (String period, int increment) {
    Map<String, Float> timeMaps = new HashMap<>();

    return timeMaps;
  }

  @Override
  public Map<String, Float> getPortfolioPerformance(LocalDate fromDate, LocalDate toDate)
          throws IllegalArgumentException  {

    if (fromDate.isEqual(toDate)) {
      throw new IllegalArgumentException("From date and To Date cannot be equal.");
    }

    if(fromDate.isAfter(toDate)) {
      throw new IllegalArgumentException("From Date and To Date given are not valid.");
    }

    if(toDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("The To Date is in the future. "
              + "Cannot get performance for future.");
    }

    this.sortByDate();

    int compareRes = fromDate.compareTo(portfolioItemTransactions.get(0).getDate());

    if(compareRes < 0) {
      throw new IllegalArgumentException("The From Date given is before the Portfolio's "
              + "first Purchase Date.");
    }

    long spanDays = fromDate.until(toDate, ChronoUnit.DAYS);
    long spanMonths = fromDate.until(toDate, ChronoUnit.MONTHS);
    long spanYears = fromDate.until(toDate, ChronoUnit.YEARS);

    int minRows = 5;
    int maxRows = 30;

    ChronoUnit period;
    int interval;

    if(spanDays <= 30) {
      period = ChronoUnit.DAYS;
      interval = 1;
    } else if(spanYears >= 5) {
      period = ChronoUnit.YEARS;
      interval = 1;
    } else if(spanMonths >=5 && spanMonths <= 30) {
      period = ChronoUnit.MONTHS;
      interval = 1;
    } else {
      long minSpan = spanDays / minRows;
      long maxSpan = spanDays / maxRows;

      long medianSpan = (minSpan + maxSpan)/2;

      period = ChronoUnit.DAYS;
      interval = (int) medianSpan;
    }

    Map<String, Float> timeMaps = new HashMap<>();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    LocalDate date = fromDate.plus(0, ChronoUnit.DAYS);

    float maxValue = 0;
    float minValue = 0;


    while (date.compareTo(toDate) <= 0) {
      float valueAtDate = this.getPortfolioValueAtDate(date);

      if(maxValue == 0 && minValue == 0) {
        maxValue = valueAtDate;
        minValue = valueAtDate;
      } else {
        if(valueAtDate > maxValue) {
          maxValue = valueAtDate;
        }
        if(valueAtDate < minValue) {
          minValue = valueAtDate;
        }
      }

      timeMaps.put(formatter.format(date), valueAtDate);

      date = date.plus(interval, period);
    }

    if(!timeMaps.containsKey(formatter.format(toDate))) {
      float toDateValue = this.getPortfolioValueAtDate(toDate);

      if(toDateValue > maxValue) {
        maxValue = toDateValue;
      }

      if(toDateValue < minValue) {
        minValue = toDateValue;
      }
    }

    float scaleValue = Math.round(maxValue / 50);

    timeMaps.put("scale", scaleValue);

    return timeMaps;
  }
}
