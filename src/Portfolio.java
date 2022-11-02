import java.io.FileNotFoundException;
import java.util.Date;

/**
 * The interface represents a Portfolio Object and the operations that can be performed
 * on a portfolio. A portfolio is a collection of Stock Objects.
 */
public interface Portfolio {

  /**
   * This method gives the name of the current Portfolio.
   *
   * @return returns the name of the current Portfolio.
   */
  String getPortfolioName();

  /**
   * This method gets the saved file path of the Portfolio.
   *
   * @return returns the file path of Portfolio.
   */
  String getPortfolioFilePath();

  /**
   * This method gets the list of stocks and its current values in the current Portfolio.
   *
   * @return returns the list of stocks available in the current Portfolio.
   */
  PortfolioItem[] getPortfolioComposition();

  /**
   * This method computes the current Portfolio Value based on the composition of its Stocks.
   *
   * @return returns the current Portfolio Value.
   */
  float getPortfolioValue();

  /**
   * This method computes the Portfolio Value at a given date based on the composition of Stocks.
   *
   * @param date the date at which the value of Portfolio is needed.
   * @return returns the Portfolio Value at the given date.
   */
  float getPortfolioValueAtDate(Date date);

}
