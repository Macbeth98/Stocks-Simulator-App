package controller.guicontroller;

import java.util.Map;

import javax.swing.*;

import model.TransactionType;

/**
 * Interface representing the features present as part of the GUI.
 */
public interface Features {

  /**
   * Method that triggers the process to view a single portfolio for a user.
   */
  void viewPortfolioComposition();

  /**
   * Method that allows user to exit from the application.
   */
  void exitProgram();

  /**
   * Method that allows user to create a portfolio.
   */
  void createPortfolio();

  /**
   * Method that allows user to create Portfolio from a file.
   */
  void createPortfolioFromFile();

  /**
   * Triggers the creation of the Portfolio from a file. View needs to pass the required parameters.
   *
   * @param portfolioName the name of the portfolio to be created.
   * @param filepath the filepath from where the portfolio needs to be created from.
   * @return returns the filepath of where this Portfolio is tored.
   */
  String setCreatePortfolioFromFile(String portfolioName, String filepath);

  /**
   * Method that allows user to enter transactions into a portfolio.
   * @param portfolioName name of portfolio for which transactions are added
   */
  void viewTransactionForm(String portfolioName);

  /**
   * Shows composition of portfolio on a date on the view.
   * @param portfolioName name of given portfolio
   * @param dateString Given date as string
   */
  void setViewPortfolioComposition(String portfolioName, String dateString);

  /**
   * Get value of portfolio on a given date and display on the view.
   * @param portfolioName name of the given portfolio
   * @param dateString date for value as a string
   */
  void viewPortfolioValueAtDate(String portfolioName, String dateString);

  /**
   * Get cost basis of a given portfolio on a given date on the view.
   * @param portfolioName name of the given portfolio
   * @param dateString date for getting cost basis as a string
   */
  void viewCostBasis(String portfolioName, String dateString);

  /**
   * Add a transaction to a portfolio based on inputs from the view.
   * @param pName name of given portfolio
   * @param txnType type of Transaction, BUY/SELL
   * @param ticker given stock ticker
   * @param quantity quantity of stock to be bought/sold
   * @param txnDate date of transaction
   * @param commission commission fee for transaction
   */
  void AddTransactionToPortfolio(String pName, TransactionType txnType, String ticker,
                                 float quantity, String txnDate, float commission);

  /**
   * Create an empty Flexible Portfolio before adding transactions to it
   * @param pName name of portfolio
   */
  void createEmptyPortfolio(String pName);


  /**
   * Method that allows the user to create a one time investment strategy to a Portfolio.
   */
  void setViewApplyOneTimeStrategyToPortfolio();

  /**
   * Creates and applies a One time Strategy on the selected Portfolio.
   *
   * @param portfolioName the portfolioName on which the strategy needs to be applied.
   * @param amount the amount to invest in portfolio.
   * @param commission the commission that is/will be charged.
   * @param date the date at which this investment strategy needs to be executed.
   * @param stocksDist the map of stocks to distribution percentages.
   */
  void applyOneTimeStrategyToPortfolio(String portfolioName, float amount, float commission,
                                       String date, Map<String, Float> stocksDist);
}
