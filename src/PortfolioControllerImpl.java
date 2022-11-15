import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * This class contains the implementation of the PortfolioController interface, and its methods.
 */
public class PortfolioControllerImpl implements PortfolioController {

  final Readable in;
  final Appendable out;

  /**
   * This method constructs a PortfolioController object taking two arguments for input and output.
   *
   * @param in  input stream object of Readable interface type
   * @param out output stream object of Appendable interface type
   */
  PortfolioControllerImpl(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  private boolean validPortfolioName(String[] pNamesList, String pName) {
    return Arrays.asList(pNamesList).contains(pName);
  }

  private boolean toContinue(String flag) {
    if ((flag.equals("N") || flag.equals("n"))) {
      return true;
    } else {
      return !flag.equals("Y") && !flag.equals("y");
    }
  }

  @Override
  public void goController(PortfolioList portfolioList) throws IOException {

    // create view object -> view
    PortfolioView view = new PortfolioViewImpl(this.out);

    // Scanner scans input
    Scanner scan = new Scanner(this.in);
    String pName;
    PortfolioControllerCommand cmd;

    while (true) {
      // view.menu()
      view.menuView();

      switch (scan.next()) {
        case "1":
          cmd = new CreatePortfolio(portfolioList, view);
          cmd.go(scan);
          break;
        case "2":
          cmd = new CreatePortfolioFromFile(portfolioList, view);
          cmd.go(scan);
          break;
        case "3":
          cmd = new ViewPortfolio(portfolioList, view);
          cmd.go(scan);
          break;
        case "4":
          cmd = new GetPortfolioValueOnDate(portfolioList, view);
          cmd.go(scan);
          break;
        case "5":
          return;

        default:
          view.invalidChoiceMessage();
      }
    }
  }
}
