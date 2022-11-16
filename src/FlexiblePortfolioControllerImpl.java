import java.io.IOException;
import java.util.Scanner;

public class FlexiblePortfolioControllerImpl implements FlexiblePortfolioController {

  final Readable in;
  final Appendable out;

  /**
   * Constructs a FlexiblePortfolioControllerImpl object taking two arguments for input and output.
   *
   * @param in  input stream object of Readable interface type
   * @param out output stream object of Appendable interface type
   */
  FlexiblePortfolioControllerImpl(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void goController(FlexiblePortfolioList fpList) throws IOException {
    FlexiblePortfolioView view = new FlexiblePortfolioViewImpl(this.out);

    // Scanner scans input
    Scanner scan = new Scanner(this.in);
    FlexiblePortfolioControllerCommand cmd;

    while (true) {
      // view.menu()
      view.menuView();

      switch (scan.next()) {
        case "1":
          cmd = new CreateFlexiblePortfolio(fpList, view);
          cmd.go(scan);
          break;
        case "2":
          cmd = new CreateFlexiblePortfolioFromFile(fpList, view);
          cmd.go(scan);
          break;
        case "3":
          cmd = new FlexiblePortfolioComposition(fpList, view);
          cmd.go(scan);
          break;
        case "4":
          cmd = new GetFlexiblePortfolioValueOnDate(fpList, view);
          cmd.go(scan);
          break;
        case "5":
          cmd = new GetCostBasisOnDate(fpList, view);
          cmd.go(scan);
          break;
        case "6":
          cmd = new GetFlexiblePortfolioPerformanceGraph(fpList, view);
          cmd.go(scan);
          break;
        case "7":
          return;
        default:
          view.invalidChoiceMessage();
      }
    }
  }
}
