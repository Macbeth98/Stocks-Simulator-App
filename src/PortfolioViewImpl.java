import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementations for the view interface. Contains user prompts.
 */
public class PortfolioViewImpl implements PortfolioView {

  final Appendable out;

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
            + "-------------------------------\n\n";
    this.out.append(menu);
  }

  @Override
  public void portfolioNamePrompt() throws IOException {
    this.out.append("\nEnter portfolio name: ");
  }

  @Override
  public void stockNamePrompt() throws IOException {
    this.out.append("\nEnter stock ticker: ");
  }

  @Override
  public void stockQuantityPrompt() throws IOException {
    this.out.append("\nEnter stock quantity: ");
  }

  @Override
  public void continuePrompt() throws IOException {
    this.out.append("\nDo you wish to continue? (Y/N): ");
  }

  @Override
  public void displayListOfPortfolios(String[] portfolioNames) throws IOException {
    this.out.append("\nPortfolios present now: \n" +
            String.join("\n", portfolioNames) + "\n");
  }

  @Override
  public void displayPortfolio(Portfolio portfolio) throws IOException {
    PortfolioItem[] items = portfolio.getPortfolioComposition();
    this.out.append("\nTICKER\tQUANTITY\tCOST PER SHARE\tCOST\n");
    for (int i = 0; i < items.length; i++) {
      this.out.append(items[i].toString())
              .append("\n");
    }
  }

  @Override
  public void portfolioFilePathPrompt() throws IOException {
    this.out.append("\nEnter portfolio file path: ");
  }

  @Override
  public void datePrompt() throws IOException {
    this.out.append("\nEnter particular date in (mm/dd/yyyy) format: ");
  }

  @Override
  public void displayValueAtDate(String portfolioName, Date date, float value) throws IOException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
    this.out.append("Value of portfolio: ")
            .append(portfolioName)
            .append(", at Date: ")
            .append(formatter.format(date))
            .append(" IS : ")
            .append(String.valueOf(value))
            .append("\n");
  }

  @Override
  public void displayPortfolioSuccess(String portfolioName, String portfolioPath)
          throws IOException {
    this.out.append("Portfolio: ")
            .append(portfolioName)
            .append(". Successfully Created! It is stored at: ")
            .append(portfolioPath)
            .append("\n");
  }
}
