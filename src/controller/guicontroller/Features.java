package controller.guicontroller;

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
   * Method that allows user to enter transactions into a portfolio.
   */
  void viewTransactionForm();

  /**
   * Shows composition of portfolio on a date on the view.
   * @param portfolioName name of given portfolio
   * @param dateString Given date as string
   */
  void setViewPortfolioComposition(String portfolioName, String dateString);
}
