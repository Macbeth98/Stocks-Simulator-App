import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FlexiblePortfolioImpl extends PortfolioImpl implements FlexiblePortfolio {

  private final List<PortfolioItemTransaction> portfolioItemTransactions;
  private final String transactionDirectory;

  protected FlexiblePortfolioImpl(String portfolioName, String portfolioFileName) {
    super(portfolioName, portfolioFileName);

    portfolioItemTransactions = new ArrayList<>();
    transactionDirectory = System.getProperty("user.dir") + "/portfolioTxnFiles/";

    // read the transactions from file and push into portfolioItemTransactions.
  }

  private void sortByDate() {
    this.portfolioItemTransactions.sort(Comparator.comparing(PortfolioItemTransaction::getDate));
  }

  private void saveTransactionToFile(PortfolioItemTransaction txn) {
    // call save to file.
  }

  @Override
  public FlexiblePortfolio addStock(String stockTicker, float quantity,
                                    Date purchaseDate, float commission)
          throws FileNotFoundException {

    StockObject  stock = new StockObjectImpl(stockTicker);

    PortfolioItemTransaction portfolioItemTransaction = new PortfolioItemTransaction(
            "buy", stock, quantity, purchaseDate, commission
    );

    float existingQuantity = 0;

    if (this.stocks.containsKey(stock.getTicker())) {
      existingQuantity = this.stocks.get(stock.getTicker()).getQuantity();
    }

    float cumulativeQuantity = existingQuantity + quantity;

    PortfolioItem portfolioItem = new PortfolioItem(stock, cumulativeQuantity,
            stock.getCurrentPriceAtDate(purchaseDate));

    this.stocks.put(stock.getTicker(), portfolioItem);

    this.savePortfolioToFile();

    portfolioItemTransactions.add(portfolioItemTransaction);

    this.saveTransactionToFile(portfolioItemTransaction);

    return this;
  }

  @Override
  public FlexiblePortfolio sellStock(String stockTicker, float quantity,
                                     Date saleDate, float commission) throws FileNotFoundException {

    StockObject stock = new StockObjectImpl(stockTicker);

    PortfolioItemTransaction portfolioItemTransaction = new PortfolioItemTransaction(
            "sell", stock, quantity, saleDate, commission
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

    this.savePortfolioToFile();

    this.saveTransactionToFile(portfolioItemTransaction);

    return this;
  }

  @Override
  public float getCostBasis(Date tillDate) {
    return portfolioItemTransactions
            .stream()
            .map(PortfolioItemTransaction::getTotalCost)
            .reduce((float) 0, Float::sum);
  }

  @Override
  public String getPortfolioName() {
    return null;
  }

  @Override
  public String getPortfolioFilePath() {
    return null;
  }

  @Override
  public PortfolioItem[] getPortfolioComposition() {
    return new PortfolioItem[0];
  }

  @Override
  public float getPortfolioValue() {
    return 0;
  }

  @Override
  public float getPortfolioValueAtDate(Date date) {
    return 0;
  }

  @Override
  public String savePortfolioToFile() throws FileNotFoundException {
    return null;
  }

}
