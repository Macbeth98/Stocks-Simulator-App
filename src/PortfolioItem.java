/**
 * This class represents a unique stock object and its data in the
 * Portfolio.
 */
public class PortfolioItem {
  private final StockObject stock;
  private final float quantity;
  private final float costPerShare;
  private final float cost;

  public PortfolioItem(StockObject stock, float quantity, float costPerShare)
          throws IllegalArgumentException {
    if (quantity <= 0) {
      throw new IllegalArgumentException("The quantity value must be greater than zero.");
    }
    this.stock = stock;
    this.quantity = quantity;
    this.costPerShare = costPerShare;
    this.cost = costPerShare * quantity;
  }

  public StockObject getStock() {
    return this.stock;
  }

  public float getQuantity() {
    return this.quantity;
  }


  public float getCostPerShare() {
    return this.costPerShare;
  }

  public float getCost() {
    return this.cost;
  }

  public float getCurrentPrice() {
    return this.stock.getCurrentPrice();
  }

  public float getCurrentValue() {
    return this.stock.getCurrentValue(this.quantity);
  }

  @Override
  public String toString() {
    return this.stock.getTicker() + ","
            + this.getQuantity() + ","
            + this.getCostPerShare() + ","
            + this.getCost();
  }
}
