import java.util.Date;

/**
 * Consists of all the main model methods that operate on a single/list of generic portfolios.
 */
public interface GenericPortfolioList extends PortfolioList {

  /**
   * Gives the composition of a given portfolio on a specific date, as a list of portfolio items.
   * @param portfolioName name of a given portfolio
   * @param date specific date given as input
   * @return list of portfolio items that make up the composition of this portfolio
   */
  PortfolioItem[] getPortfolioCompositionAtDate(String portfolioName, Date date);


  /**
   * Computes the value of a given portfolio on a specific date.
   * @param portfolioName name of a given portfolio
   * @param date specific date give as input
   * @return the value of the given portfolio on a specific date
   */
  float getPortfolioValueAtDate(String portfolioName, Date date);

  /**
   * Adds a given transaction to a portfolio, takes the following parameters.
   * @param portfolioName name of given portfolio
   * @param ticker ticker for stock present in transaction
   * @param quantity quantity of stock being bought/sold
   * @param commission commission fee being added to transaction
   * @param type type of transaction, buy/sell
   * @param date date of transaction
   */
  void addTransactionToPortfolio(String portfolioName, String ticker, float quantity, float commission, TransactionType type, Date date);

  /**
   * Computes the cost basis, i.e, the total investment in a given portfolio.
   * @param portfolioName name of given portfolio
   * @return the cost basis of the given portfolio
   */
  float getCostBasisOfPortfolio(String portfolioName);


}
