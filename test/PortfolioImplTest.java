import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

  private Portfolio createPortfolioObject(String name) {
    if (name == null) {
      name = this.portfolioName;
    }
    PortfolioImpl.PortfolioBuilder portfolioBuilder = PortfolioImpl.getBuilder();
    return portfolioBuilder.portfolioName(name)
            .setPortfolioFileName(null)
            .addStockToPortfolio(new StockObjectImpl("GOOG"), 87.69f)
            .addStockToPortfolio(new StockObjectImpl("TSLA"), 43.78f)
            .addStockToPortfolio(new StockObjectImpl("MAKE"), 87.98f)
            .build();
  }

  @Test
  public void testPortfolioImplObjectCreation() throws FileNotFoundException {

    Portfolio portfolio = createPortfolioObject(this.portfolioName);
    String filePath = portfolio.savePortfolioToFile();

    assertEquals(this.portfolioName, portfolio.getPortfolioName());

    File file = new File(portfolio.getPortfolioFilePath());
    assertTrue(file.exists());

    PortfolioItem[] portfolioItems = portfolio.getPortfolioComposition();

    Map<String, Float> stockMaps = new HashMap<>();
    stockMaps.put("GOOG", 87.69f);
    stockMaps.put("TSLA", 43.78f);
    stockMaps.put("MAKE", 87.98f);

    for (PortfolioItem portfolioItem : portfolioItems) {
      String ticker = portfolioItem.getStock().getTicker();
      assertTrue(stockMaps.containsKey(ticker));
      assertEquals(stockMaps.get(ticker), portfolioItem.getQuantity(), 0.0001);
    }
  }

  @Test
  public void testGetPortfolioValue() throws FileNotFoundException {
    Portfolio portfolio = createPortfolioObject("testPortfolioValue");
    Path filepath = Paths.get(portfolio.savePortfolioToFile());

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

  @Test
  public void testGetPortfolioValueAtADate() throws ParseException {
    String portfolioName = "portValueAtDate";
    Portfolio portfolio = createPortfolioObject(portfolioName);

    String dateString = "09/10/2021";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate date = LocalDate.parse(dateString, formatter);

    float valueAtDate = portfolio.getPortfolioValueAtDate(date);

    PortfolioItem[] portfolioItems = portfolio.getPortfolioComposition();

    float sumDate = 0;

    for (PortfolioItem portfolioItem : portfolioItems) {
      StockObject stock = portfolioItem.getStock();
      float value = portfolioItem.getQuantity() * stock.getCurrentPriceAtDate(date);
      sumDate += value;
    }

    assertEquals(sumDate, valueAtDate, 0.001);
  }

  @Test
  public void testPortfolioSaveToFile() throws FileNotFoundException {
    String name = "SaveFileTest";
    Portfolio portfolio = createPortfolioObject(name);

    String filePath = portfolio.savePortfolioToFile();

    File file = new File(filePath);

    Map<String, Float> stocksMap = new HashMap<>();

    stocksMap.put("GOOG", 87.69f);
    stocksMap.put("TSLA", 43.78f);
    stocksMap.put("MAKE", 87.98f);

    int linesCount = 0;

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String item = scanner.nextLine();
        String[] portfolioItems = item.split(",");

        assertTrue(stocksMap.containsKey(portfolioItems[0]));
        assertEquals(stocksMap.get(portfolioItems[0]),
                Float.parseFloat(portfolioItems[1]), 0.0001);
        linesCount++;
      }
    }

    assertEquals(linesCount, stocksMap.keySet().size());
  }

}