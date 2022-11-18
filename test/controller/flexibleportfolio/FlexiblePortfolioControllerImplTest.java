package controller.flexibleportfolio;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import model.flexibleportfolio.FlexiblePortfolioListImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is a test class for FlexiblePortfolioControllerImpl class.
 * This class contains methods that test the FlexiblePortfolioControllerImpl methods.
 */
public class FlexiblePortfolioControllerImplTest {

  private FlexiblePortfolioController controller;

  private String menuString;

  @Before
  public void setUp() {
    menuString = "\n\n-------------------------------"
            + "\nWelcome!\nWhat do you want to do? Press option key:\n\n"
            + "1. Create new Portfolio manually\n"
            + "2. Create new Portfolio from file\n"
            + "3. Modify a portfolio\n"
            + "4. Examine/View a portfolio\n"
            + "5. Get total value of a portfolio for a date\n"
            + "6. Get Cost Basis of a portfolio for a date\n"
            + "7. Get a portfolio's performance graph\n"
            + "8. Exit\n"
            + "\n(Please press 0 at any time to return to main menu)\n"
            + "-------------------------------\n\n";
  }

  @Test
  public void testControllerExit() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("8");
    controller = new FlexiblePortfolioControllerImpl(in, out);
    controller.goController(new FlexiblePortfolioListImpl());
    assertEquals(menuString, out.toString());
  }

  @Test
  public void testControllerCreatePortfolioManual() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1 testFlexCreate 1 GOOG 10 09/10/2022 100 "
            + "1 TSLA 20 09/11/2022 100 3 8");
    controller = new FlexiblePortfolioControllerImpl(in, out);
    controller.goController(new FlexiblePortfolioListImpl());
    String resultPrompt = "Portfolio: testflexcreate. Successfully Created!";
    String transactionResultPrompt = "Transaction: BUY successfully executed for 10.0 no of " +
            "GOOG stocks on date: 10 September 2022";
    assertTrue(out.toString().contains(resultPrompt));
    assertTrue(out.toString().contains(transactionResultPrompt));
  }

  @Test
  public void testControllerCreatePortfolioFromFile() throws IOException {
    StringBuffer out = new StringBuffer();
    String filePath = "/Users/anantm/Desktop/NEU MS CS/PDP/Assignment5/"
            + "portfolioTxnFiles/sample_1668648886765.csv";
    Reader in = new StringReader("2 testflexcreatefile \n" + filePath + "\n n 8");
    controller = new FlexiblePortfolioControllerImpl(in, out);
    controller.goController(new FlexiblePortfolioListImpl());
    String resultPrompt = "Portfolio: testflexcreatefile. Successfully Created!";
    assertTrue(out.toString().contains(resultPrompt));
  }

  @Test
  public void testControllerAddTransactionToPortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 testflexcreate 1 GOOG 18 09/07/2010 100 0 8");
    controller = new FlexiblePortfolioControllerImpl(in, out);
    controller.goController(new FlexiblePortfolioListImpl());
    String transactionResultPrompt = "Transaction: BUY successfully executed for 18.0 no of " +
            "GOOG stocks on date: 07 September 2010";
    assertTrue(out.toString().contains(transactionResultPrompt));
  }

  @Test
  public void testControllerSellTransactionToPortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3 testflexcreate 2 GOOG 18 09/07/2010 100 0 8");
    controller = new FlexiblePortfolioControllerImpl(in, out);
    controller.goController(new FlexiblePortfolioListImpl());
    String transactionResultPrompt = "Transaction: SELL successfully executed for 18.0 no of " +
            "GOOG stocks on date: 07 September 2010";
    assertTrue(out.toString().contains(transactionResultPrompt));
  }

  @Test
  public void testControllerShowPortfolio() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("4 testflexcreate 11/16/2022 n 8");
    controller = new FlexiblePortfolioControllerImpl(in, out);
    controller.goController(new FlexiblePortfolioListImpl());
    assertTrue(out.toString().contains("GOOG,18.0"));
  }

  @Test
  public void testControllerTotalValueAtDate() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("5 testflexcreate 09/07/2010 n 8");
    controller = new FlexiblePortfolioControllerImpl(in, out);
    controller.goController(new FlexiblePortfolioListImpl());
    assertTrue(out.toString().contains("$9475.2"));
  }

  @Test
  public void testControllerCostBasis() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("6 testflexcreate 09/07/2010 n 8");
    controller = new FlexiblePortfolioControllerImpl(in, out);
    controller.goController(new FlexiblePortfolioListImpl());
    assertTrue(out.toString().contains("$19250.4"));
  }

  @Test
  public void testControllerPerformanceGraph() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("7 testflexcreate 09/07/2010 09/07/2011 n 8");
    controller = new FlexiblePortfolioControllerImpl(in, out);
    controller.goController(new FlexiblePortfolioListImpl());
    assertTrue(out.toString().contains("Scale: * = 190.0"));
  }

}