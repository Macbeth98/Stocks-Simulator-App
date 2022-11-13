import java.io.FileNotFoundException;
import java.util.Date;

public interface FlexiblePortfolio extends Portfolio {
  FlexiblePortfolio addStock(String stockTicker, float quantity, Date purchaseDate, float commission)
          throws FileNotFoundException;

  FlexiblePortfolio sellStock(String stockTicker, float quantity, Date saleDate, float commission) throws FileNotFoundException;

  float getCostBasis(Date tillDate);
}
