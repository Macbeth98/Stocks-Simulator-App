import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlexiblePortfolioView extends AbstractPortfolioView{

  protected FlexiblePortfolioView(Appendable out) {
    super(out);
  }

  @Override
  public void menuView() throws IOException {
    String menu = "\n\n-------------------------------" +
            "\nWelcome!\nWhat do you want to do? Press option key:\n\n"
            + "1. Create new Portfolio manually\n"
            + "2. Create new Portfolio from file\n"
            + "3. Examine/View a portfolio\n"
            + "4. Get total value of a portfolio for a date\n"
            + "5. Get Cost Basis of a portfolio for a date\n"
            + "6. Get a portfolio's performance graph\n"
            + "7. Exit\n"
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
    this.out.append("\nInvalid commission value entered!")
            .append("Please enter value greater than or equal to zero!");
  }

  @Override
  public void transactionSuccessMessage(String pName, TransactionType type, String stockName,
                                        float quantity, Date date) throws IOException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
    this.out.append("Transaction: ")
            .append(type.toString())
            .append(" successfully executed for ")
            .append(String.valueOf(quantity))
            .append(" no of ").append(stockName)
            .append(" stocks on date: ")
            .append(formatter.format(date));
  }

  @Override
  public void displayFlexiblePortfolio(PortfolioItem[] portfolioItems) throws IOException {
    System.out.println("I AM HERE FLEX" + portfolioItems.length);
    StringBuilder s = new StringBuilder();
    for (PortfolioItem portfolioItem : portfolioItems) {
      s.append(portfolioItem.compositionString());
    }
    this.out.append(s.toString());
  }
  @Override
  public void displayPortfolio(Portfolio portfolio) throws IOException {
  }
}
