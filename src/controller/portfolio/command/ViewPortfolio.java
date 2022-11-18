package controller.portfolio.command;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioList;
import view.portfolio.PortfolioView;

/**
 * Command class that implements the command to show a portfolio's composition.
 */
public class ViewPortfolio implements PortfolioControllerCommand {

  private final PortfolioList portfolioList;
  private final PortfolioView view;

  /**
   * Constructs command object to view portfolio for the controller to interact with.
   * @param portfolioList portfolio list model
   * @param portfolioView given view model
   */
  public ViewPortfolio(PortfolioList portfolioList, PortfolioView portfolioView) {
    this.portfolioList = portfolioList;
    this.view = portfolioView;
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
  public void goCommand(Scanner scan) throws IOException {
    // view a portfolio
    String[] pNames = portfolioList.getPortfolioListNames();
    if (pNames.length < 1) {
      view.noPortfoliosMessage();
      return;
    }
    view.displayListOfPortfolios(pNames);

    while (true) {
      view.portfolioNamePrompt();
      String pName = scan.next().toLowerCase();
      if (pName.equals("0")) {
        break;
      }

      if (!validPortfolioName(portfolioList.getPortfolioListNames(), pName)) {
        view.portfolioNameErrorMessage();
        continue;
      }

      Portfolio portfolio;
      try {
        portfolio = portfolioList.getPortfolio(pName);
      } catch (IllegalArgumentException e) {
        view.displayErrorPrompt("Inflexible Portfolio Composition Failed! Error: " + e);
        continue;
      }
      view.displayPortfolio(portfolio);

      view.continuePrompt();
      String continueFlag = scan.next();
      if (toContinue(continueFlag)) {
        break;
      }
    }
  }
}
