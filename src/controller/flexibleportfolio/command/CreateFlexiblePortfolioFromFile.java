package controller.flexibleportfolio.command;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import model.flexibleportfolio.FlexiblePortfolioList;
import view.flexibleportfolio.FlexiblePortfolioView;

/**
 * Command class that implements the command to create a flexible portfolio from a file.
 */
public class CreateFlexiblePortfolioFromFile implements FlexiblePortfolioControllerCommand {

  FlexiblePortfolioList flexiblePortfolioList;

  FlexiblePortfolioView view;

  /**
   * Constructs the command object to create flexible portfolio for the controller to interact with.
   * @param fpList given flexible portfolio list model
   * @param view given view model
   */
  public CreateFlexiblePortfolioFromFile(FlexiblePortfolioList fpList, FlexiblePortfolioView view) {
    this.flexiblePortfolioList = fpList;
    this.view = view;
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
    // create a new portfolio from a file
    String[] pNames = flexiblePortfolioList.getPortfolioListNames();
    if (pNames.length > 1) {
      view.displayListOfPortfolios(pNames);
    }

    while (true) {
      view.portfolioNamePrompt();
      String pName = scan.next().toLowerCase();
      if (pName.equals("0")) {
        break;
      }

      if (validPortfolioName(flexiblePortfolioList.getPortfolioListNames(), pName)) {
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
      try {
        portfolioPath = flexiblePortfolioList.createPortfolioFromFile(pName, pPath);
      } catch (IllegalArgumentException e) {
        view.displayErrorPrompt("Flexible Portfolio Creation From File, Failed! Error: " + e);
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
