import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * This class is a test class for PortfolioViewImpl.
 * This class contains methods which tests the methods and properties of PortfolioListImpl.
 */
public class PortfolioViewImplTest {

  private PortfolioView view_sample;
  private StringBuffer out;

  @Before
  public void setUp() {
    out = new StringBuffer();
    view_sample = new PortfolioViewImpl(out);
  }

  @Test
  public void testMenuView() throws IOException {
    view_sample.menuView();
    String menu = "\n\n"
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
    assertEquals(menu, out.toString());
  }

  @Test
  public void testPortfolioNamePrompt() throws IOException {
    view_sample.portfolioNamePrompt();
    assertEquals("\nEnter portfolio name (will be converted to lowercase): ",
            out.toString());
  }

  @Test
  public void testStockNamePrompt() throws IOException {
    view_sample.stockNamePrompt();
    assertEquals("\nEnter stock ticker (will be converted to uppercase, 5 char max): ",
            out.toString());
  }

  @Test
  public void testStockQuantityPrompt() throws IOException {
    view_sample.stockQuantityPrompt();
    assertEquals("\nEnter stock quantity: ", out.toString());
  }

  @Test
  public void testContinuePrompt() throws IOException {
    view_sample.continuePrompt();
    assertEquals("\nDo you wish to continue? ((y/Y) | (n/N)): ", out.toString());
  }

  @Test
  public void testDisplayListOfPortfolios() throws IOException {
    String[] pNames = new String[]{"PF1, PF2"};
    view_sample.displayListOfPortfolios(pNames);
    assertEquals("\nPortfolios present now: \n" + String.join("\n", pNames) + "\n",
            out.toString());
  }

  @Test
  public void testPortfolioFilePathPrompt() throws IOException {
    view_sample.portfolioFilePathPrompt();
    assertEquals("\nEnter portfolio file path (absolute path only): ", out.toString());
  }

  @Test
  public void testDatePrompt() throws IOException {
    view_sample.datePrompt();
    assertEquals("\nEnter particular date in (mm/dd/yyyy) format: ", out.toString());
  }

  @Test
  public void testDisplayValueAtDate() throws IOException {
    String pName = "testPortfolio";
    Date date = new Date();
    float value = 100.0f;
    view_sample.displayValueAtDate(pName, date, value);
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
    String result = "\nValue of portfolio: " + pName + ", at Date: "
            + formatter.format(date) + " IS : " + value + "\n";
    assertEquals(result, out.toString());
  }

  @Test
  public void testDisplayPortfolioSuccess() throws IOException {
    String pName = "testPortfolio";
    String pPath = "abc_1667319660187.csv";
    view_sample.displayPortfolioSuccess(pName, pPath);

    String res = "\nPortfolio: " + pName + ". Successfully Created! It is stored at: "
            + pPath + "\n";
    assertEquals(res, out.toString());
  }

  @Test
  public void testPortfolioExistsMessage() throws IOException {
    String pName = "testPortfolio";
    view_sample.portfolioExistsMessage(pName);
    assertEquals("\nPortfolio Name: " + pName + ", already exists! "
            + "Please use a different name!\n", out.toString());
  }

  @Test
  public void testNoPortfoliosMessage() throws IOException {
    view_sample.noPortfoliosMessage();
    assertEquals("\nNo portfolios present! Please create one first!\n", out.toString());
  }

  @Test
  public void testPortfolioNameErrorMessage() throws IOException {
    view_sample.portfolioNameErrorMessage();
    assertEquals("\nPlease enter valid portfolio name!\n", out.toString());
  }

  @Test
  public void testInvalidDateStringMessage() throws IOException {
    String invalidDateString = "foobarfoo";
    view_sample.invalidDateStringMessage(invalidDateString);
    assertEquals("\nInvalid Date String!: " + invalidDateString + "\n", out.toString());
  }

  @Test
  public void testInvalidChoiceMessage() throws IOException {
    view_sample.invalidChoiceMessage();
    assertEquals("Please enter valid choice!\n", out.toString());
  }

  @Test
  public void testStockTickerMessage() throws IOException {
    view_sample.invalidTickerName();
    assertEquals("Invalid Stock Ticker name! "
            + "Enter a character only string of maximum length 5!", out.toString());
  }

  @Test
  public void testInvalidFloatMessage() throws IOException {
    view_sample.invalidFloatValue();
    assertEquals("Invalid float! Please enter correct float value!\n", out.toString());
  }
}