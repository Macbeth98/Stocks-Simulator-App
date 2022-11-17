package controller.flexibleportfolio.command;

import java.io.IOException;
import java.util.Scanner;

public class AddTransactionToPortfolio implements FlexiblePortfolioControllerCommand {

  final FlexiblePortfolioList fpList;
  final PortfolioView view;

  public AddTransactionToPortfolio(FlexiblePortfolioList fpList, PortfolioView view) {
    this.fpList = fpList;
    this.view = view;
  }

  @Override
  public void go(Scanner scan) throws IOException {

  }
}
