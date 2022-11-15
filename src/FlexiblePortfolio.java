import java.io.FileNotFoundException;
import java.util.Date;

public interface FlexiblePortfolio extends Portfolio {
  FlexiblePortfolio addStock(String stockTicker, float quantity, Date purchaseDate, float commission)
          throws FileNotFoundException;

  FlexiblePortfolio sellStock(String stockTicker, float quantity, Date saleDate, float commission)
          throws FileNotFoundException;

  float getCostBasis(Date tillDate);

  /**
   * Gets the Composition of the Portfolio till the date given.
   * @param date date on which the portfolio composition needs to get.
   * @return returns the composition of portfolio in Portfolio Item format.
   */
  PortfolioItem[] getPortfolioCompositionAtDate(Date date);
}
