package controller.portfolio;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import controller.portfolio.command.CreatePortfolio;
import controller.portfolio.command.CreatePortfolioFromFile;
import controller.portfolio.command.GetPortfolioValueOnDate;
import controller.portfolio.command.PortfolioControllerCommand;
import controller.portfolio.command.ViewPortfolio;
import model.portfolio.PortfolioList;
import view.portfolio.PortfolioView;
import view.portfolio.PortfolioViewImpl;

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
  public PortfolioControllerImpl(Readable in, Appendable out) {
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
    PortfolioControllerCommand cmd;

    while (true) {
      // view.menu()
      view.menuView();

      switch (scan.next()) {
        case "1":
          cmd = new CreatePortfolio(portfolioList, view);
          cmd.goCommand(scan);
          break;
        case "2":
          cmd = new CreatePortfolioFromFile(portfolioList, view);
          cmd.goCommand(scan);
          break;
        case "3":
          cmd = new ViewPortfolio(portfolioList, view);
          cmd.goCommand(scan);
          break;
        case "4":
          cmd = new GetPortfolioValueOnDate(portfolioList, view);
          cmd.goCommand(scan);
          break;
        case "5":
          return;

        default:
          view.invalidChoiceMessage();
      }
    }
  }
}
