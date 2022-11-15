import java.io.IOException;

/**
 * Implementations for the view interface. Contains user prompts.
 */
public class PortfolioViewImpl extends AbstractPortfolioView {

  /**
   * This constructor constructs a view object that is used to render the text based interface.
   *
   * @param out output stream variable of Appendable interface type
   */
  public PortfolioViewImpl(Appendable out) {
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
}
