import org.junit.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.flexibleportfolio.FlexiblePortfolio;
import model.flexibleportfolio.FlexiblePortfolioImpl;
import model.flexibleportfolio.FlexiblePortfolioList;
import model.flexibleportfolio.FlexiblePortfolioListImpl;
import model.portfolio.PortfolioItem;

import static org.junit.Assert.assertEquals;

public class FlexiblePortfolioImplTest {

  @Test
  public void testCreationOfFlexiblePortfolio() throws ParseException, FileNotFoundException {
    FlexiblePortfolioList portfolioList = new FlexiblePortfolioListImpl();

    String portfolioName = "flexibletest1";
    FlexiblePortfolio portfolio = new FlexiblePortfolioImpl(portfolioName);

    String filepath = System.getProperty("user.dir") + "/portfolioTxnFiles/";

    String dateString = "09/10/2021";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate date = LocalDate.parse(dateString, formatter);

    portfolio = portfolio.addStock("GOOG", 89, date, 10);

    dateString = "08/10/2021";
    date = LocalDate.parse(dateString, formatter);

    portfolio.addStock("TSLA", 10, date, 5);

    dateString = "11/08/2021";
    date = LocalDate.parse(dateString, formatter);

    portfolio.addStock("GOOG", 11, date, 5);

    PortfolioItem[] items = portfolio.getPortfolioComposition();
    for (PortfolioItem item: items) {
      System.out.println(item.toString());
    }
  }

}