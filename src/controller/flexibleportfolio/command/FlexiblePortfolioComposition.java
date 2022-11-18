package controller.flexibleportfolio.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

import model.flexibleportfolio.FlexiblePortfolioList;
import model.portfolio.PortfolioItem;
import view.flexibleportfolio.FlexiblePortfolioView;

/**
 * Class containing command implementation for viewing flexible portfolio's composition.
 */
public class FlexiblePortfolioComposition implements FlexiblePortfolioControllerCommand {

  private final FlexiblePortfolioList fpList;

  private final FlexiblePortfolioView view;

  /**
   * Constructs command object to view flexible portfolio's composition.
   * @param fpList given flexible portfolio list model object
   * @param view given view object
   */
  public FlexiblePortfolioComposition(FlexiblePortfolioList fpList, FlexiblePortfolioView view) {
    this.fpList = fpList;
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
    String[] pNames = fpList.getPortfolioListNames();
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

      if (!validPortfolioName(fpList.getPortfolioListNames(), pName)) {
        view.portfolioNameErrorMessage();
        continue;
      }

      view.datePrompt();
      String dateString = scan.next();
      if (dateString.equals("0")) {
        break;
      }

      LocalDate date;
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        date = LocalDate.parse(dateString, formatter);
      } catch (Exception e) {
        view.invalidDateStringMessage(dateString);
        continue;
      }

      PortfolioItem[] portfolioItems;
      try {
        portfolioItems = fpList.getPortfolioCompositionAtDate(pName, date);
      } catch (Exception e) {
        view.displayErrorPrompt("Flexible Portfolio Composition on Date Failed! Error: " + e);
        continue;
      }
      view.displayFlexiblePortfolio(portfolioItems);

      view.continuePrompt();
      String continueFlag = scan.next();
      if (toContinue(continueFlag)) {
        break;
      }
    }
  }
}
