package controller.flexibleportfolio.command;

import java.io.IOException;
import java.util.Scanner;

import controller.flexibleportfolio.FlexiblePortfolioControllerCommand;
import model.flexibleportfolio.FlexiblePortfolioList;
import view.flexibleportfolio.FlexiblePortfolioView;

public class GetFlexiblePortfolioPerformanceGraph implements FlexiblePortfolioControllerCommand {

  private final FlexiblePortfolioList fpList;
  private final FlexiblePortfolioView view;

  public GetFlexiblePortfolioPerformanceGraph(FlexiblePortfolioList fpList,
                                              FlexiblePortfolioView view) {
    this.fpList = fpList;
    this.view = view;
  }


  @Override
  public void go(Scanner scan) throws IOException {
  }
}
