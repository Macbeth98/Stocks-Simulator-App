import java.io.IOException;
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
  void menuView() throws IOException;


  /*
    Enter portfolio name:
   */

  /**
   * Ask User for a portfolio name.
   */
  void portfolioNamePrompt() throws IOException;

  /**
   * Ask user for stock ticker name.
   */
  void stockNamePrompt() throws IOException;

  /**
   * Ask user for stock quantity.
   */
  void stockQuantityPrompt() throws IOException;

  /**
   * Ask user if they want to continue
   */
  void continuePrompt() throws IOException;

  /*
    Display List of Portfolios
   */

  /**
   * Display the list of portfolios present.
   *
   * @param portfolioNames string Array containing portfolio names
   */
  void displayListOfPortfolios(String[] portfolioNames) throws IOException;

  // Display single portfolio

  /**
   * Display a single portfolio.
   *
   * @param portfolio portfolio to be displayed
   */
  void displayPortfolio(Portfolio portfolio) throws IOException;

  // Create portfolio from File

  /**
   * Prompt for filepath of user created portfolio.
   */
  void portfolioFilePathPrompt() throws IOException;

  /**
   * Ask user for date as input in (mm/dd/yyyy) format.
   */
  // Ask User for date input in (mm/dd/yy)
  void datePrompt() throws IOException;

  /**
   * Display value of portfolio at a current date.
   */
  void displayValueAtDate(String portfolioName, Date date, float value) throws IOException;

  /**
   * Display successful portfolio creation.
   */
  void displayPortfolioSuccess(String portfolioName, String portfolioPath) throws IOException;

}
