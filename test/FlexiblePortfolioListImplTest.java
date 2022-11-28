import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fileinout.FileIO;
import fileinout.SaveToCSV;
import model.TransactionType;
import model.flexibleportfolio.FlexiblePortfolio;
import model.flexibleportfolio.FlexiblePortfolioList;
import model.flexibleportfolio.FlexiblePortfolioListImpl;
import model.portfolio.PortfolioItem;
import model.stock.StockObjectImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class is a test class for FlexiblePortfolioListImpl class.
 * This class contains methods that test FlexiblePortfolioListImpl methods.
 */
public class FlexiblePortfolioListImplTest {

  protected FlexiblePortfolioList portfolioList;

  protected  Map<String, Float> stocks;

  protected void initiateStocks() {
    stocks = new HashMap<>();
    stocks.put("GOOG", 89f);
    stocks.put("TSLA", 90f);
    stocks.put("MSFT", 10f);
  }

  protected LocalDate getDate(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    return LocalDate.parse(dateString, formatter);
  }

  @Before
  public void setUp() {
    portfolioList = new FlexiblePortfolioListImpl();

    this.initiateStocks();
  }

  private Map<String, Path> getFileNames() {
    FileIO fileIO = new SaveToCSV();
    String directory = System.getProperty("user.dir") + "/portfolioTxnFiles/";

    return fileIO.getFilesInDirectory(directory);
  }

  @Test
  public void testFlexiblePortfolioCreation() {
    Map<String, Path> fileData = getFileNames();
    String[] names = portfolioList.getPortfolioListNames();

    for (String name : names) {
      assertEquals(fileData.get(name).toString(), portfolioList.getPortfolioFilePath(name));
    }
  }

  @Test
  public void testGetAvailablePortfolioNames() {

    String[] names = portfolioList.getPortfolioListNames();

    String[] fetchedNames = getFileNames().keySet().toArray(new String[0]);

    assertEquals(names.length, fetchedNames.length);

    for (String name : names) {
      assertTrue(Arrays.asList(fetchedNames).contains(name));
    }
  }

  @Test
  public void testCreatePortfolioManual() {
    String portfolioName = "testflexportmanual" + new Date().getTime();
    String filePath = portfolioList.createPortfolio(portfolioName, null);

    Map<String, Path> fileData = getFileNames();

    assertEquals(filePath, fileData.get(portfolioName).toString());

    FlexiblePortfolio portfolio = portfolioList.getPortfolio(portfolioName);
    assertEquals(portfolioName, portfolio.getPortfolioName());
    assertEquals(filePath, portfolio.getPortfolioFilePath());
  }

  @Test
  public void testCreatePortfolioFromFile() {
    String portfolioName = "testflexportfile" + new Date().getTime();
    String filepath = getFileNames().get("sample").toString();

    String newFilePath = portfolioList.createPortfolioFromFile(portfolioName, filepath);

    FlexiblePortfolio portfolioGiven = portfolioList.getPortfolio("sample");
    FlexiblePortfolio portfolioCreated = portfolioList.getPortfolio(portfolioName);

    assertEquals(newFilePath, portfolioCreated.getPortfolioFilePath());

    PortfolioItem[] givenItems = portfolioGiven.getPortfolioComposition();
    PortfolioItem[] createdItems = portfolioCreated.getPortfolioComposition();

    assertEquals(givenItems.length, createdItems.length);

    for (int i = 0; i < givenItems.length; i++) {
      assertEquals(givenItems[i].toString(), createdItems[i].toString());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePortfolioFromFileInvalidContent() throws FileNotFoundException {
    String currentDirectory = System.getProperty("user.dir") + "/testCSVFiles/";
    File file = new File(currentDirectory);
    if (!file.exists()) {
      file.mkdir();
    }
    String portfolioFileName = "invalidFlexible.csv";
    File outputFile = new File(currentDirectory + portfolioFileName);
    FileOutputStream fileOut = new FileOutputStream(outputFile);
    PrintStream out = new PrintStream(fileOut);

    out.println("SELL,9.0,MSFT,04/03/2022,10.0,309.42,2784.78,2774.78");

    String path = outputFile.getAbsolutePath();
    String portfolioFilePath = portfolioList.createPortfolioFromFile("InvalidTest", path);
  }

  private void addTransactionsToPortfolio(
          FlexiblePortfolio portfolio, String portfolioName, TransactionType type, String ticker,
          float quantity, LocalDate date, float commission, float resultQuantity
  ) {

    portfolioList.addTransactionToPortfolio(
            portfolioName,
            type,
            ticker,
            quantity,
            date,
            commission
    );

    PortfolioItem[] items = portfolio.getPortfolioComposition();
    boolean stockFound = false;

    for (PortfolioItem item : items) {
      if (Objects.equals(item.getStock().getTicker(), ticker)) {
        stockFound = true;
        assertEquals(ticker, item.getStock().getTicker());
        assertEquals(resultQuantity, item.getQuantity(), 0.01);
        break;
      }
    }

    if (!stockFound) {
      fail("The Stock is not found with the quantity..." + ticker + "_" + quantity
              + "_" + resultQuantity);
    }

  }

  protected void addStockTransactions(FlexiblePortfolio portfolio, String portfolioName) {
    String dateString = "09/10/2021";
    LocalDate date = this.getDate(dateString);


    addTransactionsToPortfolio(portfolio, portfolioName, TransactionType.BUY, "GOOG",
            50f, date.plusDays(9), 9f, 50f);

    addTransactionsToPortfolio(portfolio, portfolioName, TransactionType.BUY, "TSLA",
            90f, date.plusDays(5), 5f, 90f);

    addTransactionsToPortfolio(portfolio, portfolioName, TransactionType.SELL, "GOOG",
            11f, date.plusDays(25), 7f, 39f);

    addTransactionsToPortfolio(portfolio, portfolioName, TransactionType.BUY, "MSFT",
            100f, date.plusDays(10), 6f, 100f);

    addTransactionsToPortfolio(portfolio, portfolioName, TransactionType.BUY, "GOOG",
            50f, date.plusDays(12), 5f, 89f);

    addTransactionsToPortfolio(portfolio, portfolioName, TransactionType.SELL, "MSFT",
            90f, date.plusDays(15), 8f, 10f);
  }

  @Test
  public void testAddStockTransactionsToPortfolio() {
    String portfolioName = "testaddtransactionstoportfolio" + new Date().getTime();
    String filepath = portfolioList.createPortfolio(portfolioName, null);

    FlexiblePortfolio portfolio = portfolioList.getPortfolio(portfolioName);

    assertEquals(portfolioName, portfolio.getPortfolioName());

    this.addStockTransactions(portfolio, portfolioName);

    PortfolioItem[] portfolioItems = portfolio.getPortfolioComposition();
    for (PortfolioItem portfolioItem : portfolioItems) {
      String ticker = portfolioItem.getStock().getTicker();
      assertEquals(this.stocks.get(ticker), portfolioItem.getQuantity(), 0.01);
    }

  }


  @Test
  public void testGetPortfolioCompositionAndValueAtDate() {
    String portfolioName = "testportfoliocomposition" + new Date().getTime();
    String filepath = portfolioList.createPortfolio(portfolioName, null);

    FlexiblePortfolio portfolio = portfolioList.getPortfolio(portfolioName);

    assertEquals(portfolioName, portfolio.getPortfolioName());

    this.addStockTransactions(portfolio, portfolioName);

    String dateString = "09/20/2021";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate date = LocalDate.parse(dateString, formatter);

    Map<String, Float> stocksAtTime = new HashMap<>();
    stocksAtTime.put("GOOG", 50f);
    stocksAtTime.put("TSLA", 90f);
    stocksAtTime.put("MSFT", 100f);

    PortfolioItem[] items = portfolio.getPortfolioCompositionAtDate(date);

    for (PortfolioItem item : items) {
      String ticker = item.getStock().getTicker();

      assertEquals(stocksAtTime.get(ticker), item.getQuantity(), 0.01);
    }

    float portfolioValue = portfolio.getPortfolioValueAtDate(date);
    float expectedValue = 0f;

    for (String s : stocksAtTime.keySet()) {
      float price = new StockObjectImpl(s).getCurrentPriceAtDate(date);
      expectedValue += price * stocksAtTime.get(s);
    }

    assertEquals(expectedValue, portfolioValue, 0.01);

    date = date.plusDays(5);

    stocksAtTime.put("GOOG", 100f);
    stocksAtTime.put("TSLA", 90f);
    stocksAtTime.put("MSFT", 10f);

    items = portfolio.getPortfolioCompositionAtDate(date);

    for (PortfolioItem item : items) {
      String ticker = item.getStock().getTicker();

      assertEquals(stocksAtTime.get(ticker), item.getQuantity(), 0.01);
    }

    portfolioValue = portfolio.getPortfolioValueAtDate(date);
    expectedValue = 0f;

    for (String s : stocksAtTime.keySet()) {
      float price = new StockObjectImpl(s).getCurrentPriceAtDate(date);
      expectedValue += price * stocksAtTime.get(s);
    }

    assertEquals(expectedValue, portfolioValue, 0.01);

  }

  @Test
  public void testCostBasis() {
    String portfolioName = "testcostbasisportfolio" + new Date().getTime();
    String filepath = portfolioList.createPortfolio(portfolioName, null);

    FlexiblePortfolio portfolio = portfolioList.getPortfolio(portfolioName);

    assertEquals(portfolioName, portfolio.getPortfolioName());

    this.addStockTransactions(portfolio, portfolioName);

    float value = 379896.7f;

    float costValue = portfolioList.getCostBasis(portfolioName, LocalDate.now());
    float costValueAtDate = portfolioList.getCostBasis(portfolioName,
            LocalDate.now().minusDays(5));

    assertEquals(value, costValue, 0.01);
    assertEquals(costValue, costValueAtDate, 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSellTransaction() {
    String portfolioName = "testInvalidSell" + new Date().getTime();
    String filepath = portfolioList.createPortfolio(portfolioName, null);

    FlexiblePortfolio portfolio = portfolioList.getPortfolio(portfolioName);

    this.addStockTransactions(portfolio, portfolioName);

    String dateString = "09/10/2021";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate date = LocalDate.parse(dateString, formatter);

    this.addTransactionsToPortfolio(
            portfolio,
            portfolioName,
            TransactionType.SELL,
            "GOOG",
            60f,
            date.plusDays(11),
            9f,
            0f
    );
  }

  @Test
  public void testTransactionsWithInvalidDates() {
    String portfolioName = "testTransactionInvalidDate" + new Date().getTime();
    String filepath = portfolioList.createPortfolio(portfolioName, null);

    FlexiblePortfolio portfolio = portfolioList.getPortfolio(portfolioName);

    this.addStockTransactions(portfolio, portfolioName);

    LocalDate date = LocalDate.now();

    try {
      portfolioList.addTransactionToPortfolio(
              portfolioName,
              TransactionType.BUY,
              "GOOG",
              100f,
              date.plusDays(9),
              89f
      );
      fail("Future Date purchase Should have been failed!");
    } catch (IllegalArgumentException ignored) {

    }

    try {
      portfolioList.addTransactionToPortfolio(
              portfolioName,
              TransactionType.SELL,
              "GOOG",
              100f,
              date.plusDays(9),
              89f
      );
      fail("Future Date SEll Should have been failed!");
    } catch (IllegalArgumentException ignored) {

    }
  }

  @Test
  public void testInvalidQuantityAndCommissionValues() {
    String portfolioName = "testTransactionInvalidValues" + new Date().getTime();
    String filepath = portfolioList.createPortfolio(portfolioName, null);

    FlexiblePortfolio portfolio = portfolioList.getPortfolio(portfolioName);

    String[] combos = new String[]{"-10f_9f", "10f_-9f", "0f_9f"};

    for (String combo : combos) {
      String[] values = combo.split("_");
      for (TransactionType value : TransactionType.values()) {
        try {
          portfolioList.addTransactionToPortfolio(
                  portfolioName,
                  value,
                  "GOOG",
                  Float.parseFloat(values[0]),
                  LocalDate.now().minusDays(1),
                  Float.parseFloat(values[1])
          );
          fail("Should have been failed with the combo.." + value + "_" + combo);
        } catch (IllegalArgumentException ignored) {

        }
      }
    }

  }

}