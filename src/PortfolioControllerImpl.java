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
          while (true) {
            view.portfolioNamePrompt();
            pName = scan.next();

            if (validPortfolioName(portfolioList.getPortfolioListNames(), pName)) {
              this.out.append("\nPortfolio Name: ")
                      .append(pName)
                      .append(", already exists! ")
                      .append("Please use a different name!");
              continue;
            }

            Map<String, Float> stockMap = new HashMap<String, Float>();
            while (true) {
              view.stockNamePrompt();
              String stockName = scan.next();
              view.stockQuantityPrompt();
              float quantity = scan.nextFloat();
              stockMap.put(stockName, quantity);
              view.continuePrompt();
              if (scan.next().equals("N")) {
                break;
              }
            }

            Portfolio createdPortfolio = portfolioList.createPortfolio(pName, stockMap);

            view.displayPortfolioSuccess(pName, createdPortfolio.getPortfolioFilePath());

            view.continuePrompt();
            if (scan.next().equals("N")) {
              break;
            }
          }

          break;

        case "2":
          // create a new portfolio from a file
          view.portfolioNamePrompt();
          pName = scan.next();

          view.portfolioFilePathPrompt();
          scan.nextLine();
          String pPath = scan.nextLine();

          Portfolio createdPortfolio = portfolioList.createPortfolioFromFile(pName, pPath);

          view.displayPortfolioSuccess(pName, createdPortfolio.getPortfolioFilePath());
          break;

        case "3":
          // view a portfolio
          view.displayListOfPortfolios(portfolioList.getPortfolioListNames());

          while (true) {
            view.portfolioNamePrompt();
            pName = scan.next();

            if (!validPortfolioName(portfolioList.getPortfolioListNames(), pName)) {
              this.out.append("Please enter valid portfolio name!\n");
              continue;
            }

            Portfolio portfolio = portfolioList.getPortfolio(pName);
            view.displayPortfolio(portfolio);

            view.continuePrompt();
            if (scan.next().equals("N")) {
              break;
            }
          }

          break;

        case "4":
          // get portfolio value on a date
          view.displayListOfPortfolios(portfolioList.getPortfolioListNames());

          while (true) {
            view.portfolioNamePrompt();
            pName = scan.next();

            if (!validPortfolioName(portfolioList.getPortfolioListNames(), pName)) {
              this.out.append("Please enter valid portfolio name!\n");
              continue;
            }

            view.datePrompt();
            String dateString = scan.next();

            Date date = null;
            try {
              date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
            } catch (ParseException e) {
              this.out.append("\nInvalid Date String!: ")
                      .append(dateString).append("\n");
              continue;
            }

            float value = portfolioList.getPortfolio(pName).getPortfolioValueAtDate(date);

            view.displayValueAtDate(pName, date, value);

            view.continuePrompt();
            if (scan.next().equals("N")) {
              break;
            }
          }

          break;

        case "5":
          return;
      }
    }
  }
}
