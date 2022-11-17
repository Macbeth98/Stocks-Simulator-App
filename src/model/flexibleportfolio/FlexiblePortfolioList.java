package model.flexibleportfolio;

import java.time.LocalDate;

import model.TransactionType;
import model.flexibleportfolio.FlexiblePortfolio;
import model.portfolio.PortfolioItem;
import model.portfolio.PortfolioList;

/**
 * This interfaces contains methods which can be used to manage the
 * available list of Flexible Portfolios.
 */
public interface FlexiblePortfolioList extends PortfolioList {
/*
  /**
   * Fetches the list of Flexible Portfolio Names Available.
   *
   * @return returns the list of Flexible Portfolio names.
   * /
  String[] getPortfolioListNames();

  /**
   * Gets the FlexiblePortfolio from the given Portfolio Name.
   *
   * @param portfolioName the portfolio that needs to be fetched.
   * @return returns the FlexiblePortfolio for the given portfolioName.
   * /
  FlexiblePortfolio getPortfolio(String portfolioName) throws FileNotFoundException;

  /**
   * Creates a Flexible Portfolio by Manually entering Portfolio Details.
   * Flexible Portfolio supports the creation of empty Portfolio.
   *
   * @param portfolioName the unique name of the Portfolio that needs to be created.
   * @return returns the created Flexible Portfolio.
   * /
  FlexiblePortfolio createPortfolio(String portfolioName);

  /**
   * Creates a Flexible Portfolio from the user given file. The file needs to be in a specific
   * format that is mentioned in the read me of the project documentation.
   *
   * @param portfolioName the portfolio Name to be assigned for the created Portfolio from the file.
   * @param portfolioFilePath the filepath from where the Portfolio needs to be loaded.
   * @return
   * @throws FileNotFoundException if the given file is not found.
   * /
  FlexiblePortfolio createPortfolioFromFile(String portfolioName, String portfolioFilePath)
          throws FileNotFoundException;


  /**
   * Get the composition for a single Portfolio.
   *
   * @param portfolioName the name of the Portfolio.
   * @return returns the list of Portfolio Items.
   * /
  PortfolioItem[] getPortfolioComposition(String portfolioName);
*/


  /**
   * Gets the FlexiblePortfolio from the given Portfolio Name.
   *
   * @param portfolioName the portfolio that needs to be fetched.
   * @return returns the FlexiblePortfolio for the given portfolioName.
   */
  FlexiblePortfolio getPortfolio(String portfolioName) throws IllegalArgumentException;

  /**
   * Gets the Composition of the Portfolio At the given date.
   *
   * @param portfolioName the name of portfolio for which the composition needs to be fetched.
   * @param date          the date at which the composition needs to be returned.
   * @return returns the list of stocks data in the form of PortfolioItem.
   */
  PortfolioItem[] getPortfolioCompositionAtDate(String portfolioName, LocalDate date);


  /**
   * Adds a Transaction to the given Portfolio. A transaction can either be a buy or sell type.
   *
   * @param type        the type of transaction that is being added => buy/sell.
   * @param stockTicker the stock ticker that is being transacted.
   * @param quantity    the quantity of the stock that is being transacted.
   * @param date        the date on which the transaction has happened.
   * @param commission  the commission that was charged for the transaction.
   * @throws IllegalArgumentException if any of the given arguments are not valid.
   */
  void addTransactionToPortfolio(String portfolioName, TransactionType type, String stockTicker,
                                 float quantity, LocalDate date, float commission)
          throws IllegalArgumentException;


  /**
   * Computes the total amount of money invested in a portfolio by the given specified date.
   *
   * @param portfolioName the name of the portfolio for which the cost basis should be computed.
   * @param tillDate      the date at which the cost basis needs to be calculated.
   * @return returns the amount of money invested in the portfolio.
   */
  float getCostBasis(String portfolioName, LocalDate tillDate);

}
