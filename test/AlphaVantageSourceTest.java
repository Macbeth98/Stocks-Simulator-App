import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.AlphaVantageSource;

import static org.junit.Assert.assertEquals;

/**
 * This class is a test class for AlphaVantage Class.
 * This class contains methods that tests the AlphaVantage methods.
 */
public class AlphaVantageSourceTest {

  private AlphaVantageSource testSource;

  @Before
  public void setUp() {
    testSource = new AlphaVantageSource();
  }

  @Test
  public void getPriceAtDateAPITest() {
    String dateString = "1999-11-01";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(52.00, result, 0.001);
  }

  @Test
  public void getPriceAtRecentDateAPITest() {
    String dateString = "2022-11-15";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(465.9500, result, 0.001);
  }

  @Test
  public void getPriceAtRecentInvalidDateAPITest() {
    String dateString = "2022-11-13";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(468.9800, result, 0.001);
  }

  @Test
  public void testAddPriceToCache() {

    String dateString = "2000-05-25";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(49.13, result, 0.001);

  }

  @Test
  public void testCachedPrices() {

    String dateString = "1999-11-01";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(52.00, result, 0.001);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTicker() {

    String dateString = "1999-11-01";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("GOOGLE", date);

    assertEquals(52.00, result, 0.001);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiplePricesAtOnce() {

    String[] dateStrings = new String[]{"2014-03-27", "2014-04-27", "2014-05-27", "2014-09-27",
            "2014-06-27", "2015-10-22", "2016-11-11", "2014-12-12", "2015-10-10", "2014-10-10"};

    String[] tickers = new String[]{"GOOG", "COKE", "V"};

    for (String dateString : dateStrings) {
      for (String ticker : tickers) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        float result = testSource.getPriceAtDate(ticker, date);
        System.out.println(ticker + "___" + result);
      }
    }

    testSource.getPriceAtDate("GOOG", LocalDate.now().minusYears(14));

  }
}