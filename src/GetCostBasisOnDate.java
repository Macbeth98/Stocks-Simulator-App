import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class GetCostBasisOnDate implements FlexiblePortfolioControllerCommand {

  private final FlexiblePortfolioList fpList;

  private final FlexiblePortfolioView view;
  public GetCostBasisOnDate(FlexiblePortfolioList fpList, FlexiblePortfolioView view) {
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
  public void go(Scanner scan) throws IOException {
    // get portfolio cost basis on a date
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

      float value;
      try {
        value = fpList.getCostBasis(pName, date);
      } catch (Exception e) {
        view.displayErrorPrompt("Flexible Portfolio Cost Basis on Date Failed! Error: " + e);
        continue;
      }

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

      view.displayCostBasis(pName, value, formatter.format(date));

      view.continuePrompt();
      if (toContinue(scan.next())) {
        break;
      }
    }
  }
}
