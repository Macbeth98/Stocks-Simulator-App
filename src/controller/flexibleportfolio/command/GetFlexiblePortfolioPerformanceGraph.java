package controller.flexibleportfolio.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import model.flexibleportfolio.FlexiblePortfolioList;
import view.flexibleportfolio.FlexiblePortfolioView;

/**
 * Contains command implementation for viewing flexible portfolio's performance over a date range.
 */
public class GetFlexiblePortfolioPerformanceGraph implements FlexiblePortfolioControllerCommand {

  private final FlexiblePortfolioList fpList;
  private final FlexiblePortfolioView view;

  /**
   * Constructs command object for viewing a portfolio's performance.
   * @param fpList given FlexiblePortfolioList object
   * @param view given view object
   */
  public GetFlexiblePortfolioPerformanceGraph(FlexiblePortfolioList fpList,
                                              FlexiblePortfolioView view) {
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
    // get portfolio value on a date
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

      view.rangeFromDatePrompt();
      String dateString1 = scan.next();
      if (dateString1.equals("0")) {
        break;
      }

      LocalDate date1;
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        date1 = LocalDate.parse(dateString1, formatter);
      } catch (Exception e) {
        view.invalidDateStringMessage(dateString1);
        continue;
      }

      view.rangeToDatePrompt();
      String dateString2 = scan.next();
      if (dateString2.equals("0")) {
        break;
      }

      LocalDate date2;
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        date2 = LocalDate.parse(dateString2, formatter);
      } catch (Exception e) {
        view.invalidDateStringMessage(dateString2);
        continue;
      }

      Map<String, Float> portfolioPerformance;
      try {
        portfolioPerformance = fpList.getPortfolio(pName)
                .getPortfolioPerformance(date1, date2);
      } catch (Exception e) {
        view.displayErrorPrompt("Flexible Portfolio Performance Graph Failed! Error: " + e);
        continue;
      }

      view.displayPerformanceGraph(portfolioPerformance, dateString1, dateString2, pName);

      view.continuePrompt();
      if (toContinue(scan.next())) {
        break;
      }
    }
  }
}
