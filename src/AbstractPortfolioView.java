import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractPortfolioView implements PortfolioView {
  protected final Appendable out;

  protected AbstractPortfolioView(Appendable out) {
    this.out = out;
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
  public void displayValueAtDate(String portfolioName, Date date, float value) throws IOException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
    this.out.append("\nValue of portfolio: ")
            .append(portfolioName)
            .append(", at Date: ")
            .append(formatter.format(date))
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
    this.out.append("Invalid value! Please enter correct integer value!\n");
  }

  @Override
  public void portfolioCreateMenu() throws IOException {
  }

  @Override
  public void transactionDatePrompt() throws IOException {
  }

  @Override
  public void commissionFeePrompt() throws IOException {
  }

  @Override
  public void invalidCommissionValue() throws IOException {
  }

  @Override
  public void transactionSuccessMessage(String pName, TransactionType type, String stockName,
                                        float quantity, Date date) throws IOException {
  }

  @Override
  public void displayFlexiblePortfolio(PortfolioItem[] portfolioItems) throws IOException {
    System.out.println("I AM HERE");
  }
}
