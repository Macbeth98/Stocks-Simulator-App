import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class GetPortfolioValueOnDate implements PortfolioControllerCommand{

  private final PortfolioList portfolioList;
  private final PortfolioView view;

  public GetPortfolioValueOnDate (PortfolioList portfolioList, PortfolioView view) {
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
  public void go(Scanner scan) throws IOException {
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

      Date date;
      try {
        date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
      } catch (ParseException e) {
        view.invalidDateStringMessage(dateString);
        continue;
      }

      float value = portfolioList.getPortfolio(pName).getPortfolioValueAtDate(date);

      view.displayValueAtDate(pName, date, value);

      view.continuePrompt();
      if (toContinue(scan.next())) {
        break;
      }
    }
  }
}
