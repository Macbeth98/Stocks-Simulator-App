package controller.guicontroller;

/**
 * Interface representing the features present as part of the GUI.
 */
public interface Features {

  /**
   * Method that triggers the process to view a single portfolio for a user.
   * @param portfolioName name of given portfolio
   */
  void viewPortfolioComposition(String portfolioName);

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
}
