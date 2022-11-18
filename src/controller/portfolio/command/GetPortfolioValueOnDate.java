package controller.portfolio.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

import model.portfolio.PortfolioList;
import view.portfolio.PortfolioView;

/**
 * Command class that implements the command to get a portfolio's value on a date.
 */
public class GetPortfolioValueOnDate implements PortfolioControllerCommand {

  private final PortfolioList portfolioList;
  private final PortfolioView view;

  /**
   * Constructs command object to get portfolio value on a date for the controller to interact with.
   * @param portfolioList portfolio list model
   * @param view given view model
   */
  public GetPortfolioValueOnDate(PortfolioList portfolioList, PortfolioView view) {
    this.portfolioList = portfolioList;
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

      float value;
      try {
        value = portfolioList.getPortfolio(pName).getPortfolioValueAtDate(date);
      } catch (IllegalArgumentException e) {
        view.displayErrorPrompt("Inflexible Portfolio Value at Date Failed! Error: " + e);
        continue;
      }

      view.displayValueAtDate(pName, date, value);

      view.continuePrompt();
      if (toContinue(scan.next())) {
        break;
      }
    }
  }
}
