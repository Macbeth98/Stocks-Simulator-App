import java.time.LocalDate;

/**
 * An interface that contains operations to get stock related data from a specific source.
 */
public interface DataSource {
  /**
   * Method to fetch stock price at a given date for a given stock ticker from a data source.
   *
   * @param ticker given stock ticker as input
   * @param date   given date as input
   * @return stock price from the data source as float
   */
  float getPriceAtDate(String ticker, LocalDate date);
}
