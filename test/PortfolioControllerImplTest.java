import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is a test class for PortfolioControllerImpl class.
 * This class contains methods which will test the controller interaction with
 * Model and View.
 */
public class PortfolioControllerImplTest {

  private PortfolioController controller;

  private String menuString;

  //MockModel variables
  private final String uniqueString;
  private final Portfolio portfolio;

  public PortfolioControllerImplTest() throws FileNotFoundException {
    portfolio = new PortfolioListImpl().getPortfolio("sample");
    uniqueString = "mockString";
  }

  @Before
  public void setUp() {
    menuString = "\n\n"
            + "-------------------------------\n"
            + "Welcome!\n"
            + "What do you want to do? Press option key:\n"
            + "\n"
            + "1. Create new Portfolio manually\n"
            + "2. Create new Portfolio from file\n"
            + "3. Examine/View a portfolio\n"
            + "4. Get total value of a portfolio for a date\n"
            + "5. Exit\n"
            + "\n(Please press 0 at any time to return to main menu)\n"
            + "-------------------------------\n\n";
  }

  @Test
  public void testControllerExit() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());
    assertEquals(menuString, out.toString());
  }

  @Test
  public void testControllerCreatePortfolioManual() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 testPortfolio GOOG 2.55 y TSLA 4.55 n n 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());
    String resultPrompt = "Portfolio: testportfolio. Successfully Created!";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerCreatePortfolioManualExistingPortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 testPortfolio testPortfolioNew 0 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());
    String resultPrompt = "Portfolio Name: testportfolio, already exists! "
            + "Please use a different name!";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerCreatePortfolioManualInvalidStockSymbol() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 invalidstock 789yy 0 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());
    String resultPrompt = "Invalid Stock Ticker name! Enter a character "
            + "only string of maximum length 5!";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerCreatePortfolioManualInvalidStockQuantity() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 invalidquantity goog 56.65th 0 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());
    String resultPrompt = "Invalid float! Please enter correct float value!";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerCreatePortfolioFromFile() throws IOException {
    StringBuffer out = new StringBuffer();
    String pPath = System.getProperty("user.dir") + "/portfolioCSVFiles/sample_1667366700000.csv";
    Reader in = new StringReader("2 portfoliofromfile \n" + pPath + "\n n 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());
    String resultPrompt = "Portfolio: portfoliofromfile. Successfully Created!";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test(expected = FileNotFoundException.class)
  public void testControllerCreatePortfolioFromFileInvalidPath() throws IOException {
    StringBuffer out = new StringBuffer();
    String pPath = System.getProperty("user.dir") + "/portfolioCSVFiles/InvalidSample_1667366700000.csv";
    Reader in = new StringReader("2 portfoliofromfile \n" + pPath + "\n n 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());
    String resultPrompt = "Portfolio: portfoliofromfile. Successfully Created!";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerCreatePortfolioFromFilePortfolioNameExists() throws IOException {
    StringBuffer out = new StringBuffer();
    String pPath = System.getProperty("user.dir") + "/portfolioCSVFiles/sample_1667366700000.csv";
    Reader in = new StringReader("2 portfoliofromfile portfoliofromfilenew \n"
            + pPath + "\n n 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());
    String resultPrompt = "Portfolio Name: portfoliofromfile, already exists! "
            + "Please use a different name!";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerViewPortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    String pPath = System.getProperty("user.dir") + "/portfolioCSVFiles/sample_1667366700000.csv";
    Reader in = new StringReader("3 sample 0 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());

    File file = new File(pPath);

    try (Scanner sc = new Scanner(file)) {
      while (sc.hasNextLine()) {
        String[] item = sc.nextLine().split(",");
        assertTrue(out.toString().contains(item[0]+","+item[1]));
      }
    }
  }

  @Test
  public void testControllerViewPortfolioInvalidPortfolioName() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 sample123 0 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());

    String resultPrompt = "Please enter valid portfolio name!";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerGetPortfolioValueAtDate() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("4 sample 11/12/2019 n 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());

    String resultPrompt = "Value of portfolio: sample, at Date: 12 November 2019 IS :";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerGetPortfolioValueAtDateInvalidDate() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("4 sample foobardate 0 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());

    String resultPrompt = "Invalid Date String!: foobardate";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerGetPortfolioValueAtDateInvalidPortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("4 invalidSample 0 5");
    controller = new PortfolioControllerImpl(in, out);
    controller.go(new PortfolioListImpl());

    String resultPrompt = "Please enter valid portfolio name!";
    assertTrue(out.toString().contains(resultPrompt));
  }


  class MockModelPortfolioList implements PortfolioList {

    private final StringBuilder log;
    private final String uniqueString;
    private final Portfolio  portfolio;

    public MockModelPortfolioList (StringBuilder log, String uniqueString, Portfolio portfolio) {
      this.log = log;
      this.uniqueString = uniqueString;
      this.portfolio = portfolio;
    }

    @Override
    public String[] getPortfolioListNames() {
      return new String[]{uniqueString};
    }

    @Override
    public Portfolio getPortfolio(String portfolioName) throws FileNotFoundException {
      log.append(portfolioName).append("\n");
      return portfolio;
    }

    @Override
    public Portfolio createPortfolio(String portfolioName, Map<String, Float> stocksMap)
            throws FileNotFoundException {
      log.append(portfolioName).append("\n");
      stocksMap.keySet().forEach(stock -> {
        log.append(stock).append(",").append(stocksMap.get(stock)).append("\n");
      });
      return portfolio;
    }

    @Override
    public Portfolio createPortfolioFromFile(String portfolioName, String portfolioFilePath)
            throws FileNotFoundException {
      log.append(portfolioName).append("\n").append(portfolioFilePath);
      return portfolio;
    }
  }

  @Test
  public void testGoCreatePortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 mockportfolio goog 5.65 y tsla 7.89 n n 5");
    PortfolioController controller = new PortfolioControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    controller.go(new MockModelPortfolioList(log,uniqueString, portfolio));

    String resultPrompt = "mockportfolio\n"
            + "GOOG,5.65\n"
            + "TSLA,7.89\n";

    assertEquals(resultPrompt, log.toString());
    assertTrue(out.toString().contains(this.portfolio.getPortfolioName()));
    assertTrue(out.toString().contains(this.portfolio.getPortfolioFilePath()));
  }

  @Test
  public void testGoCreatePortfolioFromFile() throws IOException {
    StringBuffer out = new StringBuffer();
    String pPath = System.getProperty("user.dir") + "/portfolioCSVFiles/sample_1667366700000.csv";
    Reader in = new StringReader("2 mockportfoliofromfile \n" + pPath + "\n n 5");
    PortfolioController controller = new PortfolioControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    controller.go(new MockModelPortfolioList(log, uniqueString, portfolio));

    String resultPrompt = "mockportfoliofromfile\n" + pPath;

    assertEquals(resultPrompt, log.toString());
    assertTrue(out.toString().contains(this.portfolio.getPortfolioName()));
    assertTrue(out.toString().contains(this.portfolio.getPortfolioFilePath()));
  }

  @Test
  public void testGoGetPortfolios() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 0 5");
    PortfolioController controller = new PortfolioControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    controller.go(new MockModelPortfolioList(log, uniqueString, portfolio));

    assertTrue(out.toString().contains(this.uniqueString));
  }

  @Test
  public void testGoGetPortfolioObject() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("4 sample 11/12/2019 0 5");
    PortfolioController controller = new PortfolioControllerImpl(in, out);
    StringBuilder log = new StringBuilder();
    controller.go(new MockModelPortfolioList(log, "sample", portfolio));

    String resultPrompt = "Value of portfolio: sample";

    assertEquals("sample\n", log.toString());
    assertTrue(out.toString().contains(resultPrompt));
  }

}