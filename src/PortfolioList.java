import java.io.FileNotFoundException;

/**
 * This interfaces contains methods which can be used to manage the
 * available list of Portfolios.
 */
public interface PortfolioList {

  /**
   * Fetches the list of PortfolioNames stored.
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

}
