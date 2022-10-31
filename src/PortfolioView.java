import java.util.Date;

/**
 * This interface contains the text based user interface parts of the view and its operations.
 */
public interface PortfolioView {

  /*
  Welcome

    What do you want to do? Press key

    1. Create new PF manually
    2. Create new PF from File
    3. View Portfolio
    4. Get Portfolio value at specific date
   */

  /**
   * Display initial user menu.
   */
  void MenuView();


  /*
    Enter portfolio name:
   */

  /**
   * Ask User for a portfolio name.
   */
  void PortfolioNamePrompt();

  /*
    Display List of Portfolios
   */

  /**
   * Display the list of portfolios present.
   *
   * @param portfolioNames string Array containing portfolio names
   */
  void displayListOfPortfolios(String[] portfolioNames);

  // Display single portfolio

  /**
   * Display a single portfolio.
   *
   * @param portfolio portfolio to be displayed
   */
  void displayPortfolio(Portfolio portfolio);

  // Create portfolio from File

  /**
   * Prompt for filepath of user created portfolio.
   */
  void portfolioFilePathPrompt();

  /**
   * Ask user for date as input in (mm/dd/yyyy) format.
   */
  // Ask User for date input in (mm/dd/yy)
  void datePrompt();

  /**
   * Display value of portfolio at a current date
   */
  void displayValueAtDate(String portfolioName, Date date, float value);

}
