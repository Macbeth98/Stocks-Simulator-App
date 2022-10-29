import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

/**
 * This class implements the interface Portfolio.
 * This class has the list of stock objects.
 */
public class PortfolioImpl implements Portfolio{

  private final String portfolioName;
  private final HashSet<StockObject> stocks;

  private final String portfolioFileName;

  public PortfolioImpl (String portfolioName, StockObject[] stocksArr) {
    this.portfolioName = portfolioName;
    this.portfolioFileName = portfolioName + "_" + new Date().getTime() + ".csv";

    stocks = new HashSet<>();

    stocks.addAll(Arrays.asList(stocksArr));
  }

  @Override
  public String getPortfolioName() {
    return this.portfolioName;
  }

  @Override
  public StockObject[] getPortfolioComposition() {
    StockObject[] stocksArr = new StockObject[]{};
    return stocks.toArray(stocksArr);
  }

  @Override
  public float getPortfolioValue() {
    return stocks
            .stream()
            .map(StockObject::getCurrentValue)
            .reduce((float) 0, Float::sum);
  }

  @Override
  public float getPortfolioValueAtDate(Date date) {
    return stocks
            .stream()
            .map(stockObject -> stockObject.getCurrentValueAtDate(date))
            .reduce((float) 0, Float::sum);
  }

  public void savePortfolioToFile () throws FileNotFoundException {
    File outputFile = new File(portfolioFileName);
    FileOutputStream fileOut = new FileOutputStream(outputFile);
    PrintStream out = new PrintStream(fileOut);

    stocks.forEach(stock -> {
      String portfolioItem = stock.getTicker() + ","
              + stock.getQuantity() + ","
              + stock.getCurrentPrice() + ","
              + stock.getCurrentValue();

      out.println(portfolioItem);
    });
  }
}
