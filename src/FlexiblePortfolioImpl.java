import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                                    Date purchaseDate, float commission)
          throws FileNotFoundException {

    StockObject  stock = new StockObjectImpl(stockTicker);

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
                                     Date saleDate, float commission) throws FileNotFoundException {

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
      if (txn.getDate().getTime() > saleDate.getTime()) {
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
  public float getCostBasis(Date tillDate) {
    return portfolioItemTransactions
            .stream()
            .map(PortfolioItemTransaction::getCostBasis)
            .reduce((float) 0, Float::sum);
  }

  private boolean isToday(Date date) {
    String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
    String givenDate = new SimpleDateFormat("MM/dd/yyyy").format(date);

    return currentDate.equals(givenDate);
  }

  private Map<StockObject, Float> getPortfolioTillDate(Date date) {
    this.sortByDate();

    Map<StockObject, Float> stocks = new HashMap<>();

    for (PortfolioItemTransaction item: portfolioItemTransactions) {
      if(item.getDate().getTime() <= date.getTime()) {
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
    PortfolioBuilder builder = PortfolioImpl.getBuilder();

    builder = builder.portfolioName(this.portfolioName)
            .setPortfolioFileName(Paths.get(this.getPortfolioFilePath()));

    StockObject[] stockObjects = stocks.keySet().toArray(new StockObject[0]);

    for (StockObject stockObject: stockObjects) {
      builder = builder.addStockToPortfolio(stockObject, stocks.get(stockObject));
    }

    return builder.build();
  }

  @Override
  public PortfolioItem[] getPortfolioCompositionAtDate(Date date) throws IllegalArgumentException {

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
  public float getPortfolioValueAtDate(Date date) throws IllegalArgumentException {

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

  @Override
  public List<Map<String, Float>> getPortfolioPerformance(LocalDate fromDate, LocalDate toDate)
          throws IllegalArgumentException  {

    if (fromDate.isEqual(toDate)) {
      throw new IllegalArgumentException("From date and To Date cannot be equal.");
    }

    if(fromDate.isAfter(toDate)) {
      throw new IllegalArgumentException("From Date and To Date given are not valid.");
    }

    Date date1 = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date date2 = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    if(date2.getTime() > new Date().getTime()) {
      throw new IllegalArgumentException("The To Date is in the future. "
              + "Cannot get performance for future.");
    }

    int timeHour = 60 * 60 * 1000;
    int timeDay = 24 * timeHour;
    int timeWeek = 7 * timeDay;
    int timeMonth = 30 * timeDay;
    int timeYear = 365 * timeDay;

    long spanTime = date2.getTime() - date1.getTime();

    long spanHours = (spanTime / timeHour);
    int spanDays = (int) (spanTime / timeDay);

    if(spanDays >= 5 && spanDays <= 30) {

    }


    return null;
  }
}
