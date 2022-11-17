import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import model.TransactionType;
import model.flexibleportfolio.FlexiblePortfolio;
import model.flexibleportfolio.FlexiblePortfolioImpl;
import model.flexibleportfolio.PortfolioItemTransaction;
import model.portfolio.PortfolioItem;
import model.stock.StockObjectImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is a test class for FlexiblePortfolioImpl Class. This class contains methods that tests
 * and validates the FlexiblePortfolio interface that is implemented by FlexiblePortfolioImpl.
 */
public class FlexiblePortfolioImplTest {

  @Test
  public void testCreationOfFlexiblePortfolio() throws FileNotFoundException {

    String portfolioName = "flexibletest1";
    FlexiblePortfolio portfolio = new FlexiblePortfolioImpl(portfolioName);

    String filepath = System.getProperty("user.dir") + "/portfolioTxnFiles/";

    assertTrue(portfolio.getPortfolioFilePath().contains(Paths.get(filepath).toString()));

    String dateString = "09/10/2021";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate date = LocalDate.parse(dateString, formatter);

    portfolio = portfolio.addStock("GOOG", 89, date, 10);

    portfolio.sellStock("GOOG", 5, date.plusDays(5), 5);

    dateString = "08/10/2021";
    date = LocalDate.parse(dateString, formatter);

    portfolio.addStock("TSLA", 10, date, 5);

    dateString = "11/08/2021";
    date = LocalDate.parse(dateString, formatter);

    portfolio.addStock("GOOG", 11, date, 5);


    portfolio.sellStock("TSLA", 5, date.plusDays(10), 8);

    portfolio.addStock("MSFT", 9, date.plusDays(8), 9);

    Map<String, Float> stocksFinal = new HashMap<>();
    stocksFinal.put("GOOG", 95f);
    stocksFinal.put("TSLA", 5f);
    stocksFinal.put("MSFT", 9f);

    PortfolioItem[] items = portfolio.getPortfolioComposition();
    for (PortfolioItem item : items) {
      String ticker = item.getStock().getTicker();
      assertEquals(item.getQuantity(), stocksFinal.get(ticker), 0.01);
    }
  }

  @Test
  public void testCreatePortfolioByGivingTransactions() {
    String portfolioName = "testportfoliotxns";

    List<PortfolioItemTransaction> items = new ArrayList<>();

    String[] transactions = new String[]{
            "BUY_GOOG_98_08/10/2021_7",
            "BUY_TSLA_100_09/10/2021_9",
            "BUY_MSFT_90_11/11/2021_5",
            "SELL_GOOG_8_09/11/2021_7",
            "SELL_MSFT_9_04/03/2022_10"
    };

    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 90f);
    stocks.put("TSLA", 100f);
    stocks.put("MSFT", 81f);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    for (String transaction : transactions) {
      String[] values = transaction.split("_");
      items.add(
              new PortfolioItemTransaction(
                      Objects.equals(TransactionType.BUY.toString(), values[0])
                              ? TransactionType.BUY : TransactionType.SELL,
                      new StockObjectImpl(values[1]),
                      Float.parseFloat(values[2]),
                      LocalDate.parse(values[3], formatter),
                      Float.parseFloat(values[4])
              )
      );
    }

    FlexiblePortfolio portfolio = new FlexiblePortfolioImpl(portfolioName, null, items);

    assertEquals(portfolioName, portfolio.getPortfolioName());

    PortfolioItem[] portfolioItems = portfolio.getPortfolioComposition();
    PortfolioItem[] portfolioItemsAtDate = portfolio.getPortfolioCompositionAtDate(
            LocalDate.now().minusDays(5));

    assertEquals(portfolioItems.length, portfolioItemsAtDate.length);

    for (int i = 0; i < portfolioItems.length; i++) {
      PortfolioItem item = portfolioItems[i];
      PortfolioItem itemAtDate = portfolioItemsAtDate[i];

      assertEquals(item.getStock().getTicker(), itemAtDate.getStock().getTicker());
      assertEquals(item.getQuantity(), itemAtDate.getQuantity(), 0.01);

      String ticker = item.getStock().getTicker();
      assertEquals(stocks.get(ticker), itemAtDate.getQuantity(), 0.01);
    }

  }

}