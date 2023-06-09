package view.flexibleportfolio;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import model.TransactionType;
import model.portfolio.PortfolioItem;
import view.portfolio.PortfolioViewImpl;

/**
 * Implements all the methods of FlexiblePortfolioView and displays necessary prompts to the user.
 */
public class FlexiblePortfolioViewImpl extends PortfolioViewImpl implements FlexiblePortfolioView {

  /**
   * Constructs an Implementation object for flexible portfolio view.
   * @param out given output stream
   */
  public FlexiblePortfolioViewImpl(Appendable out) {
    super(out);
  }

  @Override
  public void menuView() throws IOException {
    String menu = "\n\n-------------------------------" +
            "\nWelcome!\nWhat do you want to do? Press option key:\n\n"
            + "1. Create new Portfolio manually\n"
            + "2. Create new Portfolio from file\n"
            + "3. Modify a portfolio\n"
            + "4. Examine/View a portfolio\n"
            + "5. Get total value of a portfolio for a date\n"
            + "6. Get Cost Basis of a portfolio for a date\n"
            + "7. Get a portfolio's performance graph\n"
            + "8. Exit\n"
            + "\n(Please press 0 at any time to return to main menu)\n"
            + "-------------------------------\n\n";
    this.out.append(menu);
  }

  @Override
  public void portfolioCreateMenu() throws IOException {
    String pfCreateMenu = "\n\nWhat operation do you want to input?\n"
            + "1. BUY\n"
            + "2. SELL\n"
            + "3. Exit\n"
            + "\n(Please press 0 at any time to return to main menu)\n\n";
    this.out.append(pfCreateMenu);
  }

  @Override
  public void transactionDatePrompt() throws IOException {
    this.out.append("\nEnter transaction date in (mm/dd/yyyy) format: ");
  }

  @Override
  public void commissionFeePrompt() throws IOException {
    this.out.append("\nEnter commission fee for this transaction: ");
  }

  @Override
  public void invalidCommissionValue() throws IOException {
    this.out.append("\nInvalid commission value entered! ")
            .append("Please enter value greater than or equal to zero!");
  }

  @Override
  public void transactionSuccessMessage(String pName, TransactionType type, String stockName,
                                        float quantity, LocalDate date) throws IOException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    this.out.append("\nTransaction: ")
            .append(type.toString())
            .append(" successfully executed for ")
            .append(String.valueOf(quantity))
            .append(" no of ").append(stockName)
            .append(" stocks on date: ")
            .append(date.format(formatter))
            .append("\n");
  }

  @Override
  public void displayFlexiblePortfolio(PortfolioItem[] portfolioItems) throws IOException {
    StringBuilder s = new StringBuilder();
    for (PortfolioItem portfolioItem : portfolioItems) {
      s.append(portfolioItem.compositionString()).append("\n");
    }
    this.out.append(s.toString());
  }

  @Override
  public void displayCostBasis(String pName, float costBasis, String date) throws IOException {
    this.out.append("Cost Basis of Portfolio: ")
            .append(pName)
            .append(" until Date: ")
            .append(date).append(" Is: $")
            .append(String.valueOf(costBasis))
            .append(".\n");
  }

  @Override
  public void rangeFromDatePrompt() throws IOException {
    this.out.append("\nEnter start date in (mm/dd/yyyy) format: ");
  }

  @Override
  public void rangeToDatePrompt() throws IOException {
    this.out.append("\nEnter end date in (mm/dd/yyyy) format: ");
  }

  @Override
  public void displayPerformanceGraph(Map<String, Float> portfolioPerformance,
                                      String startDate, String endDate,
                                      String portfolioName) throws IOException {
    this.out.append("\nPerformance of portfolio: ")
            .append(portfolioName).append(" from ")
            .append(startDate)
            .append(" to ")
            .append(endDate)
            .append("\n\n");
    float scale = portfolioPerformance.remove("scale");
    List<String> datesList = new ArrayList<>(portfolioPerformance.keySet());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    datesList.sort(Comparator.comparing(s -> LocalDate.parse(s, formatter)));
    for (String dateStr : datesList) {
      String graphLine = dateStr + ": ";
      int numOfStars = (int) Math.ceil(portfolioPerformance.get(dateStr) / scale);
      graphLine += "*".repeat(numOfStars) + "\n";
      this.out.append(graphLine);
    }
    this.out.append("\nScale: * = ").append(String.valueOf(scale)).append("\n");
  }

}
