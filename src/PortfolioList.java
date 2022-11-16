import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Map;

/**
 * This interfaces contains methods which can be used to manage the
 * available list of Portfolios.
 */
public interface PortfolioList {

  /**
   * Fetches the list of PortfolioNames stored. All Portfolio files are saved in a directory
   * named portfolioCSVFiles.
   *
   * @return returns an array of stored Portfolio names.
   */
  String[] getPortfolioListNames();

  /**
   * This method gets the Portfolio based on the given Portfolio Name.
   *
   * @param portfolioName the name of the portfolio that is needed.
   * @return returns the selected Portfolio based on the name.
   * @throws IllegalArgumentException When the given Portfolio file is not found.
   */
  Portfolio getPortfolio(String portfolioName) throws IllegalArgumentException;

  /**
   * Creates a portfolio from manually entered user inputs received from controller.
   *
   * @param portfolioName portfolioName of this portfolio
   * @param stocksMap     hashMap containing ticker name and quantity of each stock
   * @return returns the Absolute Path of SavePortfolio.
   * @throws IllegalArgumentException When the Portfolio created cannot be saved into a file.
   */
  String createPortfolio(String portfolioName, Map<String, Float> stocksMap)
          throws IllegalArgumentException;

  /**
   * Creates a portfolio from a user provided file that follows portfolio format.
   *
   * @param portfolioName     name of the portfolio
   * @param portfolioFilePath path of the portfolio file to be created
   * @return returns the Absolute Path of SavePortfolio.
   * @throws IllegalArgumentException throws exception if file not found
   */
  String createPortfolioFromFile(String portfolioName, String portfolioFilePath)
          throws IllegalArgumentException;

  /**
   * Get the composition for a single Portfolio.
   *
   * @param portfolioName the name of the Portfolio.
   * @return returns the list of Portfolio Items.
   */
  PortfolioItem[] getPortfolioComposition(String portfolioName);


  /**
   * Gets  the Portfolio value at the given date.
   *
   * @param portfolioName the name of portfolio that needs to be fetched.
   * @param date the date at which the value of the Portfolio Needs to be fetched.
   * @return
   */
  float getPortfolioValueAtDate (String portfolioName, LocalDate date);

  /**
   * Gets the given Portfolio Stored File path.
   *
   * @param portfolioName the name of the portfolio for which the saved file needs to be fetched.
   * @return returns the Absolute path of the Portfolio in the string form.
   */
  String getPortfolioFilePath (String portfolioName);

}
