import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PortfolioControllerImpl implements PortfolioController {

  final Readable in;
  final Appendable out;

  PortfolioControllerImpl(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  private boolean validPortfolioName(String[] pNamesList, String pName) {
    return Arrays.asList(pNamesList).contains(pName);
  }

  private boolean toContinue(String flag) {
    if ((flag.equals("N") || flag.equals("n"))) {
      return false;
    }
    else return flag.equals("Y") || flag.equals("y");
  }

  @Override
  public void go(PortfolioList portfolioList) throws IOException {

    // create view object -> view
    PortfolioView view = new PortfolioViewImpl(this.out);

    // Scanner scans input
    Scanner scan = new Scanner(this.in);
    String pName;

    while (true) {
      // view.menu()
      view.menuView();

      switch (scan.next()) {
        case "1":
          // create a new portfolio manually

          String[] pNames = portfolioList.getPortfolioListNames();
          if (pNames.length > 1) {
            view.displayListOfPortfolios(pNames);
          }

          while (true) {
            view.portfolioNamePrompt();
            pName = scan.next().toLowerCase();

            if (validPortfolioName(portfolioList.getPortfolioListNames(), pName)) {
              view.portfolioExistsMessage(pName);
              continue;
            }

            Map<String, Float> stockMap = new HashMap<String, Float>();
            while (true) {
              view.stockNamePrompt();
              String stockName = scan.next();
              view.stockQuantityPrompt();
              float quantity = scan.nextFloat();
              stockMap.put(stockName.toUpperCase(), quantity);
              view.continuePrompt();
              if (toContinue(scan.next())) {
                break;
              }
            }

            Portfolio createdPortfolio = portfolioList.createPortfolio(pName, stockMap);

            view.displayPortfolioSuccess(pName, createdPortfolio.getPortfolioFilePath());

            view.continuePrompt();
            if (!toContinue(scan.next())) {
              break;
            }
          }

          break;

        case "2":
          // create a new portfolio from a file
          while (true) {
            view.portfolioNamePrompt();
            pName = scan.next().toLowerCase();

            if (validPortfolioName(portfolioList.getPortfolioListNames(), pName)) {
              view.portfolioExistsMessage(pName);
              continue;
            }

            view.portfolioFilePathPrompt();
            scan.nextLine();
            String pPath = scan.nextLine();

            Portfolio createdPortfolio = portfolioList.createPortfolioFromFile(pName, pPath);

            view.displayPortfolioSuccess(pName, createdPortfolio.getPortfolioFilePath());

            view.continuePrompt();
            if (!toContinue(scan.next())) {
              break;
            }
          }

        case "3":
          // view a portfolio
          pNames = portfolioList.getPortfolioListNames();
          if (pNames.length < 1) {
            view.noPortfoliosMessage();
            continue;
          }
          view.displayListOfPortfolios(pNames);

          while (true) {
            view.portfolioNamePrompt();
            pName = scan.next().toLowerCase();

            if (!validPortfolioName(portfolioList.getPortfolioListNames(), pName)) {
              view.portfolioNameErrorMessage();
              continue;
            }

            Portfolio portfolio = portfolioList.getPortfolio(pName);
            view.displayPortfolio(portfolio);

            view.continuePrompt();
            String continueFlag = scan.next();
            if (!toContinue(continueFlag)) {
              break;
            }
          }

          break;

        case "4":
          // get portfolio value on a date
          pNames = portfolioList.getPortfolioListNames();
          if (pNames.length < 1) {
            view.noPortfoliosMessage();
            continue;
          }
          view.displayListOfPortfolios(pNames);

          while (true) {
            view.portfolioNamePrompt();
            pName = scan.next().toLowerCase();

            if (!validPortfolioName(portfolioList.getPortfolioListNames(), pName)) {
              view.portfolioNameErrorMessage();
              continue;
            }

            view.datePrompt();
            String dateString = scan.next();

            Date date = null;
            try {
              date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
            } catch (ParseException e) {
              view.invalidDateStringMessage(dateString);
              continue;
            }

            float value = portfolioList.getPortfolio(pName).getPortfolioValueAtDate(date);

            view.displayValueAtDate(pName, date, value);

            view.continuePrompt();
            if (!toContinue(scan.next())) {
              break;
            }
          }

          break;

        case "5":
          return;

        default:
          view.invalidChoiceMessage();
      }
    }
  }
}