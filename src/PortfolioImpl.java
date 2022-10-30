import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the interface Portfolio.
 * This class has the list of stock objects.
 */
public class PortfolioImpl implements Portfolio{

  private final String portfolioName;
  private final Map<String, PortfolioItem> stocks;

  private final String portfolioFileName;

  public static PortfolioBuilder getBuilder () {
    return new PortfolioBuilder();
  }

  private PortfolioImpl (String portfolioName, Map<String, PortfolioItem> stocks) {
    this.portfolioName = portfolioName;
    this.portfolioFileName = portfolioName + "_" + new Date().getTime() + ".csv";

    this.stocks = new HashMap<>(stocks);
  }

  public static class PortfolioBuilder {

    private String portfolioName;
    private final Map<String, PortfolioItem> stocks;
    private PortfolioBuilder() {
      stocks = new HashMap<>();
    }

    public void portfolioName(String portfolioName) {
      // need to check that the portfolio name is unique.
      this.portfolioName = portfolioName;
    }

    public PortfolioBuilder AddStockToPortfolio (StockObject stock, float quantity, float costPerShare)
            throws IllegalArgumentException {

      if(quantity <= 0) {
        throw new IllegalArgumentException("The quantity value must be greater than zero.");
      }

      stocks.put(stock.getTicker(), new PortfolioItem(stock, quantity, costPerShare));

      return this;
    }

    public Portfolio build () {
      return new PortfolioImpl(this.portfolioName, stocks);
    }


  }

  @Override
  public String getPortfolioName() {
    return this.portfolioName;
  }

  @Override
  public PortfolioItem[] getPortfolioComposition() {
    PortfolioItem[] portfolioItems = new PortfolioItem[]{};
    return stocks.values().toArray(portfolioItems);
  }

  @Override
  public float getPortfolioValue() {
    return stocks
            .values()
            .stream()
            .map(PortfolioItem::getCurrentValue)
            .reduce((float) 0, Float::sum);
  }

  @Override
  public float getPortfolioValueAtDate(Date date) {
    return stocks
            .values()
            .stream()
            .map(portfolioItem -> portfolioItem
                    .getStock()
                    .getCurrentValueAtDate(date, portfolioItem.getQuantity()))
            .reduce((float) 0, Float::sum);
  }

  public void savePortfolioToFile () throws FileNotFoundException {
    File outputFile = new File(portfolioFileName);
    FileOutputStream fileOut = new FileOutputStream("portfolioCSVFiles/" + outputFile);
    PrintStream out = new PrintStream(fileOut);

    stocks.keySet().forEach(stockTicker -> {
      String portfolioItemStr = stocks.get(stockTicker).toString();

      out.println(portfolioItemStr);
    });
  }
}
