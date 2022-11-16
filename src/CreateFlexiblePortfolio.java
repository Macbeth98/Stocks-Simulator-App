import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class CreateFlexiblePortfolio implements FlexiblePortfolioControllerCommand {

  FlexiblePortfolioList flexiblePortfolioList;

  FlexiblePortfolioView view;

  public CreateFlexiblePortfolio(FlexiblePortfolioList fpList, FlexiblePortfolioView view) {
    this.flexiblePortfolioList = fpList;
    this.view = view;
  }

  private boolean validPortfolioName(String[] pNamesList, String pName) {
    return Arrays.asList(pNamesList).contains(pName);
  }

  @Override
  public void go(Scanner scan) throws IOException {
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

      String filePath = flexiblePortfolioList.createPortfolio(pName, null);
      view.displayPortfolioSuccess(pName, filePath);

      while(true) {
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
              flexiblePortfolioList.addTransactionToPortfolio(pName, TransactionType.BUY,
                      stockName, quantity, date, commission);
            } catch (IllegalArgumentException e) {
              throw new IllegalArgumentException(e.getMessage());
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
              flexiblePortfolioList.addTransactionToPortfolio(pName, TransactionType.SELL,
                      stockName, quantity, date, commission);
            } catch (IllegalArgumentException e) {
              throw new IllegalArgumentException(e.getMessage());
            }

            view.transactionSuccessMessage(pName, TransactionType.SELL, stockName, quantity, date);
            break;

          case "3":
            return;
          }
        }
    }
  }
}
