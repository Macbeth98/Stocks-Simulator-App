import java.io.FileNotFoundException;
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
   * @throws FileNotFoundException When the given Portfolio file is not found.
   */
  Portfolio getPortfolio(String portfolioName) throws FileNotFoundException;

  /**
   * Creates a portfolio from manually entered user inputs received from controller.
   *
   * @param portfolioName portfolioName of this portfolio
   * @param stocksMap     hashMap containing ticker name and quantity of each stock
   * @return Portfolio Object containing this information
   * @throws FileNotFoundException When the Portfolio created cannot be saved into a file.
   */
  Portfolio createPortfolio(String portfolioName, Map<String, Float> stocksMap)
          throws FileNotFoundException;

  /**
   * Creates a portfolio from a user provided file that follows portfolio format.
   *
   * @param portfolioName     name of the portfolio
   * @param portfolioFilePath path of the portfolio file to be created
   * @return Portfolio Object containing this information
   * @throws FileNotFoundException throws exception if file not found
   */
  Portfolio createPortfolioFromFile(String portfolioName, String portfolioFilePath)
          throws FileNotFoundException;

}
