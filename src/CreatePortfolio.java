import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreatePortfolio implements PortfolioControllerCommand{

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

  public CreatePortfolio(PortfolioList portfolioList, PortfolioView view) {
    this.portfolioList = portfolioList;
    this.view = view;
  }

  @Override
  public void go(Scanner scan) throws IOException {
    String[] pNames = portfolioList.getPortfolioListNames();
    if (pNames.length > 1) {
      view.displayListOfPortfolios(pNames);
    }

    createPFLoop:
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

      Map<String, Float> stockMap = new HashMap<>();
      while (true) {

        view.stockNamePrompt();
        String stockName = scan.next();
        if (stockName.equals("0")) {
          break createPFLoop;
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
            break createPFLoop;
          }
        } catch (Exception e) {
          view.invalidQuantityValue();
          continue;
        }
        if (quantity < 0) {
          view.invalidQuantityValue();
          continue;
        }


        stockMap.put(stockName.toUpperCase(), quantity);

        view.continuePrompt();
        if (toContinue(scan.next())) {
          break;
        }
      }

      String portfolioPath = "";
      try{
        portfolioPath = portfolioList.createPortfolio(pName, stockMap);
      } catch (IllegalArgumentException e) {
        view.displayErrorPrompt("Portfolio Creation Failed! Error: " + e);
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
