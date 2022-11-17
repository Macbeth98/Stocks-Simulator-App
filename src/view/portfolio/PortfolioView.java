package view.portfolio;

import java.io.IOException;
import java.time.LocalDate;

import model.portfolio.Portfolio;

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
   * Ask user if they want to continue.
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
  void displayValueAtDate(String portfolioName, LocalDate date, float value) throws IOException;

  /**
   * Display successful portfolio creation.
   */
  void displayPortfolioSuccess(String portfolioName, String portfolioPath) throws IOException;

  /**
   * Display this message if portfolio with given name already exists.
   *
   * @param portfolioName given portfolio name
   */
  void portfolioExistsMessage(String portfolioName) throws IOException;

  /**
   * Display this message if there are no portfolios present.
   */
  void noPortfoliosMessage() throws IOException;

  /**
   * Display this message if an invalid portfolio name is given, to be shown.
   */
  void portfolioNameErrorMessage() throws IOException;

  /**
   * Display this message if a given date string doesn't represent a valid date.
   *
   * @param dateString given date string input
   */
  void invalidDateStringMessage(String dateString) throws IOException;

  /**
   * Display this message if user inputs incorrect menu choice.
   */
  void invalidChoiceMessage() throws IOException;

  /**
   * Display this message if user inputs incorrect stock ticker name.
   */
  void invalidTickerName() throws IOException;

  /**
   * Display this message if the user inputs an incorrect quantity value.
   */
  void invalidQuantityValue() throws IOException;

  /**
   * Display any error info from controller.
   *
   * @param s string containing error info
   */
  void displayErrorPrompt(String s) throws IOException;
}
