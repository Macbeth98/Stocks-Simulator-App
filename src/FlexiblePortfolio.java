import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FlexiblePortfolio extends Portfolio {
  FlexiblePortfolio addStock(String stockTicker, float quantity, LocalDate purchaseDate, float commission)
          throws FileNotFoundException;

  FlexiblePortfolio sellStock(String stockTicker, float quantity, LocalDate saleDate, float commission)
          throws FileNotFoundException;

  float getCostBasis(LocalDate tillDate);

  /**
   * Gets the Composition of the Portfolio till the date given.
   * @param date date on which the portfolio composition needs to get.
   * @return returns the composition of portfolio in Portfolio Item format.
   */
  PortfolioItem[] getPortfolioCompositionAtDate(LocalDate date);

  List<Map<String, Float>> getPortfolioPerformance(LocalDate fromDate, LocalDate toDate)
          throws IllegalArgumentException;
}
