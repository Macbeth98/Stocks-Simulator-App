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
public class PortfolioImpl implements Portfolio {

  private final String portfolioName;
  private final Map<String, PortfolioItem> stocks;

  private final String portfolioFileName;
  private final String currentDirectory;

  private boolean fileStored;
  private String errMessage;

  public static PortfolioBuilder getBuilder() {
    return new PortfolioBuilder();
  }

  private PortfolioImpl(String portfolioName, Map<String, PortfolioItem> stocks) {
    this.currentDirectory = System.getProperty("user.dir") + "/portfolioCSVFiles/";
    this.portfolioName = portfolioName;
    this.portfolioFileName = portfolioName + "_" + new Date().getTime() + ".csv";

    this.stocks = new HashMap<>(stocks);
    try {
      this.savePortfolioToFile();
      fileStored = true;
    } catch (FileNotFoundException e) {
      fileStored = false;
      errMessage = e.getMessage();
    }
  }

  public static class PortfolioBuilder {

    private String portfolioName;
    private final Map<String, PortfolioItem> stocks;

    private PortfolioBuilder() {
      stocks = new HashMap<>();
    }

    public PortfolioBuilder portfolioName(String portfolioName) {
      // need to check that the portfolio name is unique.
      this.portfolioName = portfolioName;
      return this;
    }

    public PortfolioBuilder AddStockToPortfolio(StockObject stock, float quantity)
            throws IllegalArgumentException {

      if (quantity <= 0) {
        throw new IllegalArgumentException("The quantity value must be greater than zero.");
      }

      stocks.put(stock.getTicker(), new PortfolioItem(stock, quantity, stock.getCurrentPrice()));

      return this;
    }

    public Portfolio build() {
      return new PortfolioImpl(this.portfolioName, stocks);
    }


  }

  @Override
  public String getPortfolioName() {
    return this.portfolioName;
  }

  @Override
  public String getPortfolioFilePath() {
    return fileStored ?
            this.currentDirectory + this.portfolioFileName
            : "The Portfolio save file could not be created. Error Message: " + errMessage;
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

  private void savePortfolioToFile() throws FileNotFoundException {
    File outputFile = new File(currentDirectory + portfolioFileName);
    FileOutputStream fileOut = new FileOutputStream(outputFile);
    PrintStream out = new PrintStream(fileOut);

    stocks.keySet().forEach(stockTicker -> {
      String portfolioItemStr = stocks.get(stockTicker).toString();

      out.println(portfolioItemStr);
    });
  }
}
