package controller.portfolio.command;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import model.portfolio.PortfolioList;
import view.portfolio.PortfolioView;

public class CreatePortfolioFromFile implements PortfolioControllerCommand {
  PortfolioList portfolioList;
  PortfolioView view;

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

  public CreatePortfolioFromFile(PortfolioList portfolioList, PortfolioView view) {
    this.portfolioList = portfolioList;
    this.view = view;
  }

  public void go(Scanner scan) throws IOException {
    // create a new portfolio from a file
    String[] pNames = portfolioList.getPortfolioListNames();
    if (pNames.length > 1) {
      view.displayListOfPortfolios(pNames);
    }

    while (true) {
      view.portfolioNamePrompt();
      String pName = scan.next().toLowerCase();
      if (pName.equals("0")) {
        break;
      }

      if (validPortfolioName(portfolioList.getPortfolioListNames(), pName)) {
        view.portfolioExistsMessage(pName);
        continue;
      }

      view.portfolioFilePathPrompt();
      scan.nextLine();
      String pPath = scan.nextLine();
      if (pPath.equals("0")) {
        break;
      }

      String portfolioPath = "";
      try{
        portfolioPath = portfolioList.createPortfolioFromFile(pName, pPath);
      } catch (IllegalArgumentException e) {
        view.displayErrorPrompt("Portfolio Creation From File, Failed! Error: " + e);
        continue;
      }

      view.displayPortfolioSuccess(pName, portfolioPath);

      view.continuePrompt();
      if (toContinue(scan.next())) {
        break;
      }
    }
  }
}
