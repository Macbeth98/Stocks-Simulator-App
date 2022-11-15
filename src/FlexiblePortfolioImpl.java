import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlexiblePortfolioImpl extends PortfolioImpl implements FlexiblePortfolio {

  private final List<PortfolioItemTransaction> portfolioItemTransactions;

  public FlexiblePortfolioImpl(String portfolioName) {
    super(portfolioName, null,
            System.getProperty("user.dir") + "/portfolioTxnFiles/");

    portfolioItemTransactions = new ArrayList<>();
  }

  public FlexiblePortfolioImpl(String portfolioName, String fileName,
                               List<PortfolioItemTransaction> portfolioItemTransactions) {
    super(portfolioName, fileName,
            System.getProperty("user.dir") + "/portfolioTxnFiles/");

    this.portfolioItemTransactions = new ArrayList<>(portfolioItemTransactions);
  }

  private void sortByDate() {
    this.portfolioItemTransactions.sort(Comparator.comparing(PortfolioItemTransaction::getDate));
  }

  private void saveTransactionToFile(PortfolioItemTransaction txn) throws FileNotFoundException {
    // call save to file.
    this.sortByDate();
    PrintStream out = this.getFileOutStream(this.getSaveFilePath());

    portfolioItemTransactions.forEach(itemTxn -> {
      String itemTxnStr = itemTxn.toString();

      out.println(itemTxnStr);
    });

    this.fileSaved = true;
  }

  @Override
  public FlexiblePortfolio addStock(String stockTicker, float quantity,
                                    Date purchaseDate, float commission)
          throws FileNotFoundException {

    StockObject  stock = new StockObjectImpl(stockTicker);

    PortfolioItemTransaction portfolioItemTransaction = new PortfolioItemTransaction(
            TransactionType.BUY, stock, quantity, purchaseDate, commission
    );

    float existingQuantity = 0;

    if (this.stocks.containsKey(stock.getTicker())) {
      existingQuantity = this.stocks.get(stock.getTicker()).getQuantity();
    }

    float cumulativeQuantity = existingQuantity + quantity;

    PortfolioItem portfolioItem = new PortfolioItem(stock, cumulativeQuantity,
            stock.getCurrentPriceAtDate(purchaseDate));

    this.stocks.put(stock.getTicker(), portfolioItem);

    // this.savePortfolioToFile();

    portfolioItemTransactions.add(portfolioItemTransaction);

    this.saveTransactionToFile(portfolioItemTransaction);

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
    } else  {
      return  this;
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

    // this.savePortfolioToFile();

    this.saveTransactionToFile(portfolioItemTransaction);

    return this;
  }

  @Override
  public float getCostBasis(Date tillDate) {
    return portfolioItemTransactions
            .stream()
            .filter(PortfolioItemTransaction ->
                    PortfolioItemTransaction.getType() == TransactionType.BUY)
            .map(PortfolioItemTransaction::getTotalCost)
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
}
