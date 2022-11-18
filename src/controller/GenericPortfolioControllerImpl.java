package controller;

import java.io.IOException;
import java.util.Scanner;

import controller.flexibleportfolio.FlexiblePortfolioControllerImpl;
import controller.portfolio.PortfolioControllerImpl;
import model.GenericPortfolioList;
import view.GenericPortfolioView;
import view.GenericPortfolioViewImpl;

/**
 * Represents a generic controller that allows user to operate on both kinds of portfolios.
 */
public class GenericPortfolioControllerImpl implements GenericPortfolioController {

  final Readable in;
  final Appendable out;

  /**
   * Constructs a GenericPortfolioControllerImpl object taking two arguments for input and output.
   *
   * @param in  input stream object of Readable interface type
   * @param out output stream object of Appendable interface type
   */
  public GenericPortfolioControllerImpl(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void goGenericController(GenericPortfolioList gpList) throws IOException {
    GenericPortfolioView view = new GenericPortfolioViewImpl(this.out);

    // Scanner scans input
    Scanner scan = new Scanner(this.in);
    while (true) {
      view.menuView();

      switch (scan.next()) {
        case "1":
          new FlexiblePortfolioControllerImpl(this.in, this.out)
                  .goController(gpList.getFlexiblePortfolioList());
          break;
        case "2":
          new PortfolioControllerImpl(this.in, this.out)
                  .goController(gpList.getPortfolioList());
          break;
        case "3":
          return;

        default:
          view.invalidChoiceMessage();
      }
    }
  }
}
