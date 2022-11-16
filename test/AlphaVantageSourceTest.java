import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class AlphaVantageSourceTest {

  private AlphaVantageSource testSource;

  @Before
  public void setUp () {
    testSource = new AlphaVantageSource();
  }

  @Test
  public void getPriceAtDateAPITest () {
    String dateString = "1999-11-01";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(52.00, result, 0.001);
  }

  @Test
  public void getPriceAtRecentDateAPITest () {
    String dateString = "2022-11-15";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(465.9500, result, 0.001);
  }

  @Test
  public void testAddPriceToCache () {

    String dateString = "2000-05-25";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(49.13, result, 0.001);

  }

  @Test
  public void testCachedPrices () {

    String dateString = "1999-11-01";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(52.00, result, 0.001);

  }
}