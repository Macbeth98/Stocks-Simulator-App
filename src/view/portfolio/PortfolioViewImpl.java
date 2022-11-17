package view.portfolio;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioItem;

/**
 * Implementations for the view interface. Contains user prompts.
 */
public class PortfolioViewImpl implements PortfolioView {

  protected final Appendable out;

  /**
   * This constructor constructs a view object that is used to render the text based interface.
   *
   * @param out output stream variable of Appendable interface type
   */
  public PortfolioViewImpl(Appendable out) {
    this.out = out;
  }

  @Override
  public void menuView() throws IOException {
    String menu = "\n\n-------------------------------" +
            "\nWelcome!\nWhat do you want to do? Press option key:\n\n"
            + "1. Create new Portfolio manually\n"
            + "2. Create new Portfolio from file\n"
            + "3. Examine/View a portfolio\n"
            + "4. Get total value of a portfolio for a date\n"
            + "5. Exit\n"
            + "\n(Please press 0 at any time to return to main menu)\n"
            + "-------------------------------\n\n";
    this.out.append(menu);
  }

  @Override
  public void displayPortfolio(Portfolio portfolio) throws IOException {
    PortfolioItem[] items = portfolio.getPortfolioComposition();
    this.out.append("\nTICKER,QUANTITY,COST_PER_SHARE,COST\n");
    for (PortfolioItem item : items) {
      this.out.append(item.toString())
              .append("\n");
    }
  }

  @Override
  public void portfolioNamePrompt() throws IOException {
    this.out.append("\nEnter portfolio name (will be converted to lowercase): ");
  }

  @Override
  public void stockNamePrompt() throws IOException {
    this.out.append("\nEnter stock ticker (will be converted to uppercase, 5 char max): ");
  }

  @Override
  public void stockQuantityPrompt() throws IOException {
    this.out.append("\nEnter stock quantity: ");
  }

  @Override
  public void continuePrompt() throws IOException {
    this.out.append("\nDo you wish to continue? ((y/Y) | (n/N)): ");
  }

  @Override
  public void displayListOfPortfolios(String[] portfolioNames) throws IOException {
    this.out.append("\nPortfolios present now: \n")
            .append(String.join("\n", portfolioNames)).append("\n");
  }

  @Override
  public void portfolioFilePathPrompt() throws IOException {
    this.out.append("\nEnter portfolio file path (absolute path only): ");
  }

  @Override
  public void datePrompt() throws IOException {
    this.out.append("\nEnter particular date in (mm/dd/yyyy) format: ");
  }

  @Override
  public void displayValueAtDate(String portfolioName, LocalDate date, float value) throws IOException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    this.out.append("\nValue of portfolio: ")
            .append(portfolioName)
            .append(", at Date: ")
            .append(date.format(formatter))
            .append(" IS : $")
            .append(String.valueOf(value))
            .append("\n");
  }

  @Override
  public void displayPortfolioSuccess(String portfolioName, String portfolioPath)
          throws IOException {
    this.out.append("\nPortfolio: ")
            .append(portfolioName)
            .append(". Successfully Created! It is stored at: ")
            .append(portfolioPath)
            .append("\n");
  }

  @Override
  public void portfolioExistsMessage(String portfolioName) throws IOException {
    this.out.append("\nPortfolio Name: ")
            .append(portfolioName)
            .append(", already exists! ")
            .append("Please use a different name!\n");
  }

  @Override
  public void noPortfoliosMessage() throws IOException {
    this.out.append("\nNo portfolios present! Please create one first!\n");
  }

  @Override
  public void portfolioNameErrorMessage() throws IOException {
    this.out.append("\nPlease enter valid portfolio name!\n");
  }

  @Override
  public void invalidDateStringMessage(String dateString) throws IOException {
    this.out.append("\nInvalid Date String!: ").append(dateString).append("\n");
  }

  @Override
  public void invalidChoiceMessage() throws IOException {
    this.out.append("Please enter valid choice!\n");
  }

  @Override
  public void invalidTickerName() throws IOException {
    this.out.append("Invalid Stock Ticker name! " +
            "Enter a character only string of maximum length 5!");
  }

  @Override
  public void invalidQuantityValue() throws IOException {
    this.out.append("Invalid value! Please enter correct, positive integer value!\n");
  }

  @Override
  public void displayErrorPrompt(String s) throws IOException {
    this.out.append("\n").append(s).append("\n");
  }
}
