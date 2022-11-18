package controller.flexibleportfolio;

import java.io.IOException;
import java.util.Scanner;

import controller.flexibleportfolio.command.CreateFlexiblePortfolio;
import controller.flexibleportfolio.command.CreateFlexiblePortfolioFromFile;
import controller.flexibleportfolio.command.FlexiblePortfolioComposition;
import controller.flexibleportfolio.command.FlexiblePortfolioControllerCommand;
import controller.flexibleportfolio.command.GetCostBasisOnDate;
import controller.flexibleportfolio.command.GetFlexiblePortfolioPerformanceGraph;
import controller.flexibleportfolio.command.GetFlexiblePortfolioValueOnDate;
import controller.flexibleportfolio.command.ModifyFlexiblePortfolio;
import model.flexibleportfolio.FlexiblePortfolioList;
import view.flexibleportfolio.FlexiblePortfolioView;
import view.flexibleportfolio.FlexiblePortfolioViewImpl;

/**
 * Class containing the implementation of the flexible portfolio controller and its operations.
 */
public class FlexiblePortfolioControllerImpl implements FlexiblePortfolioController {

  final Readable in;
  final Appendable out;

  /**
   * Constructs a FlexiblePortfolioControllerImpl object taking two arguments for input and output.
   *
   * @param in  input stream object of Readable interface type
   * @param out output stream object of Appendable interface type
   */
  public FlexiblePortfolioControllerImpl(Readable in, Appendable out) {
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
          cmd.goCommand(scan);
          break;
        case "2":
          cmd = new CreateFlexiblePortfolioFromFile(fpList, view);
          cmd.goCommand(scan);
          break;
        case "3":
          cmd = new ModifyFlexiblePortfolio(fpList, view);
          cmd.goCommand(scan);
          break;
        case "4":
          cmd = new FlexiblePortfolioComposition(fpList, view);
          cmd.goCommand(scan);
          break;
        case "5":
          cmd = new GetFlexiblePortfolioValueOnDate(fpList, view);
          cmd.goCommand(scan);
          break;
        case "6":
          cmd = new GetCostBasisOnDate(fpList, view);
          cmd.goCommand(scan);
          break;
        case "7":
          cmd = new GetFlexiblePortfolioPerformanceGraph(fpList, view);
          cmd.goCommand(scan);
          break;
        case "8":
          return;
        default:
          view.invalidChoiceMessage();
      }
    }
  }
}
