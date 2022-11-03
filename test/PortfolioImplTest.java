import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is a test class for PortfolioImpl Class.
 * This class contains methods which test the methods of PortfolioImpl Class.
 */
public class PortfolioImplTest {

  private final String portfolioName = "PortfolioImplTest";

  @Test
  public void testPortfolioImplObjectCreation () {
    PortfolioImpl.PortfolioBuilder portfolioBuilder = PortfolioImpl.getBuilder();
    Portfolio portfolio = portfolioBuilder.portfolioName(this.portfolioName)
            .setPortfolioFileName(null)
            .AddStockToPortfolio(new StockObjectImpl("GOOG"), 87.69f)
            .AddStockToPortfolio(new StockObjectImpl("TSLA"), 43.78f)
            .AddStockToPortfolio(new StockObjectImpl("MAKE"), 87.98f)
            .build();

    assertEquals(this.portfolioName, portfolio.getPortfolioName());

    File file = new File(portfolio.getPortfolioFilePath());
    assertTrue(file.exists());

    PortfolioItem[] portfolioItems = portfolio.getPortfolioComposition();

    Map<String, Float> stockMaps = new HashMap<>();
    stockMaps.put("GOOG", 87.69f);
    stockMaps.put("TSLA", 43.78f);
    stockMaps.put("MAKE", 87.98f);

    for (PortfolioItem portfolioItem: portfolioItems) {
      String ticker = portfolioItem.getStock().getTicker();
      assertTrue(stockMaps.containsKey(ticker));
      assertEquals(stockMaps.get(ticker), portfolioItem.getQuantity(), 0.0001);
    }
  }

  @Test
  public void testGetPortfolioValue() throws FileNotFoundException {
    Path filepath = Paths.get("D:\\1. Northeastern University\\Semester "
            + "- I\\Courses\\PDP\\IntelliJ Projects\\Assignments\\Assignment4\\"
            + "portfolioCSVFiles\\PortfolioImplTest_1667445567057.csv");

    float sum = 0.00f;

    try (Scanner scanner = new Scanner(new File(filepath.toUri()))) {
      while (scanner.hasNextLine()) {
        String item = scanner.nextLine();
        String[] portfolioItems = item.split(",");

        sum = sum + Float.parseFloat(portfolioItems[3]);
      }
    }

    PortfolioList portfolioList = new PortfolioListImpl();

    float portfolioValue = portfolioList.getPortfolio(this.portfolioName).getPortfolioValue();

    assertEquals(sum, portfolioValue, 0.0001);
  }

}