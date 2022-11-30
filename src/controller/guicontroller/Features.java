package controller.guicontroller;

import javax.swing.*;

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
   * @param frame the JFrame in which this view being displayed.
   * @param portfolioName the name of the portfolio to be created.
   * @param filepath the filepath from where the portfolio needs to be created from.
   */
  void setCreatePortfolioFromFile(JFrame frame, String portfolioName, String filepath);

  /**
   * Method that allows user to enter transactions into a portfolio.
   */
  void viewTransactionForm();

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
}
