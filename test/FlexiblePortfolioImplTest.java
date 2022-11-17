import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    String portfolioName = "testportfoliofromfile";

    List<PortfolioItemTransaction> items = new ArrayList<>();

    String[] transactions = new String[] {
            "BUY_GOOG_98_08/10/2021_7"
    };




  }

}