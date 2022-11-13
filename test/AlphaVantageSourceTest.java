import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class AlphaVantageSourceTest {

  private AlphaVantageSource testSource;

  @Before
  public void setUp () {
    testSource = new AlphaVantageSource();
  }

  @Test
  public void getPriceAtDateAPITest () throws ParseException {
    String dateString = "1999-11-01";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = formatter.parse(dateString);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(52.00, result, 0.001);
  }

  @Test
  public void getPriceAtCurrentDateAPITest () throws ParseException {
    Date date = new Date();
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(52.00, result, 0.001);
  }

  @Test
  public void testAddPriceToCache () throws ParseException {

    String dateString = "2000-05-25";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = formatter.parse(dateString);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(49.13, result, 0.001);

  }

  @Test
  public void testCachedPrices () throws ParseException {

    String dateString = "1999-11-01";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = formatter.parse(dateString);
    float result = testSource.getPriceAtDate("COKE", date);

    assertEquals(52.00, result, 0.001);

  }
}