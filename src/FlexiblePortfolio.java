import java.util.Date;

public interface FlexiblePortfolio extends Portfolio {
  FlexiblePortfolio addStock(StockObject stock, float quantity, Date purchaseDate);

  FlexiblePortfolio sellStock(StockObject stock, float quantity, Date saleDate);

  float getCostBasis(Date tillDate);
}
