import java.util.Date;

/**
 * An interface that represents a single stock object and its operations.
 */
public interface StockObject {

  /**
   * Returns the number of shares of a stock that are owned.
   * @return the current number of shares as a float
   */
  float getQuantity();

  /**
   * Returns the ticker symbol of this stock.
   * @return the ticker symbol as a string
   */
  String getTicker();

  /**
   * Returns the current day price of a single share of a stock.
   * @return the current price of the stock as a float
   */
  float getCurrentPrice();

  /**
   * Returns the price of a single share of a stock for a given date.
   *
   * @param date date given as input
   * @return the price of the stock for the given date as a float
   */
  float getCurrentPriceAtDate(Date date);

  /**
   * returns the current value of the stock, i.e, current price * quantity.
   * @return the value of the stock as a float
   */
  float getCurrentValue();

  /**
   * returns the value of the stock for a given date, i.e, price at date * quantity.
   * @param date date given as input
   * @return the value of the stock for that date as float
   */
  float getCurrentValueAtDate(Date date);
}
