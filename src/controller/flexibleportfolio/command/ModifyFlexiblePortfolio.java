package controller.flexibleportfolio.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

import controller.flexibleportfolio.FlexiblePortfolioControllerCommand;
import model.flexibleportfolio.FlexiblePortfolioList;
import view.flexibleportfolio.FlexiblePortfolioView;

public class ModifyFlexiblePortfolio implements FlexiblePortfolioControllerCommand {

  private final FlexiblePortfolioList fpList;
  private final FlexiblePortfolioView view;
  public ModifyFlexiblePortfolio(FlexiblePortfolioList fpList, FlexiblePortfolioView view) {
    this.fpList = fpList;
    this.view = view;
  }

  private boolean validPortfolioName(String[] pNamesList, String pName) {
    return Arrays.asList(pNamesList).contains(pName);
  }

  @Override
  public void go(Scanner scan) throws IOException {

    while (true) {
      String[] pNames = fpList.getPortfolioListNames();
      if (pNames.length < 1) {
        view.noPortfoliosMessage();
        return;
      }
      view.displayListOfPortfolios(pNames);

      view.portfolioNamePrompt();
      String pName = scan.next().toLowerCase();
      if (pName.equals("0")) {
        break;
      }

      if (!validPortfolioName(fpList.getPortfolioListNames(), pName)) {
        view.portfolioNameErrorMessage();
        continue;
      }

      view.portfolioCreateMenu();

      switch (scan.next()) {
        case "1":

          view.stockNamePrompt();
          String stockName = scan.next();
          if (stockName.equals("0")) {
            break;
          }
          if (stockName.length() > 5 || !stockName.matches("[a-zA-Z]+")) {
            view.invalidTickerName();
            continue;
          }

          float quantity;
          view.stockQuantityPrompt();
          try {
            quantity = Integer.parseInt(scan.next());
            if (quantity == 0) {
              break;
            }
          } catch (Exception e) {
            view.invalidQuantityValue();
            continue;
          }

          view.transactionDatePrompt();
          String transactionDate = scan.next();
          if (transactionDate.equals("0")) {
            break;
          }

          LocalDate date;
          try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            date = LocalDate.parse(transactionDate, formatter);
          } catch (Exception e) {
            view.invalidDateStringMessage(transactionDate);
            continue;
          }


          view.commissionFeePrompt();
          float commission = scan.nextFloat();

          if (commission < 0) {
            view.invalidCommissionValue();
            continue;
          }

          try {
            fpList.addTransactionToPortfolio(pName, TransactionType.BUY,
                    stockName, quantity, date, commission);
          } catch (IllegalArgumentException e) {
            view.displayErrorPrompt("Flexible Portfolio Transaction Failed! Error: " + e);
            continue;
          }

          view.transactionSuccessMessage(pName, TransactionType.BUY, stockName, quantity, date);
          break;

        case "2":
          view.stockNamePrompt();
          stockName = scan.next();
          if (stockName.equals("0")) {
            break;
          }
          if (stockName.length() > 5 || !stockName.matches("[a-zA-Z]+")) {
            view.invalidTickerName();
            continue;
          }

          view.stockQuantityPrompt();
          try {
            quantity = Integer.parseInt(scan.next());
            if (quantity == 0) {
              break;
            }
          } catch (Exception e) {
            view.invalidQuantityValue();
            continue;
          }

          view.transactionDatePrompt();
          transactionDate = scan.next();
          if (transactionDate.equals("0")) {
            break;
          }

          try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            date = LocalDate.parse(transactionDate, formatter);
          } catch (Exception e) {
            view.invalidDateStringMessage(transactionDate);
            continue;
          }


          view.commissionFeePrompt();
          commission = scan.nextFloat();

          if (commission < 0) {
            view.invalidCommissionValue();
            continue;
          }

          try {
            fpList.addTransactionToPortfolio(pName, TransactionType.SELL,
                    stockName, quantity, date, commission);
          } catch (IllegalArgumentException e) {
            view.displayErrorPrompt("Flexible Portfolio Transaction Failed! Error: " + e);
            continue;
          }

          view.transactionSuccessMessage(pName, TransactionType.SELL, stockName, quantity, date);
          break;

        case "3":
          return;

        default:
          view.invalidChoiceMessage();
      }
    }
  }
}
