/**
 * This class represents a unique stock object and its data in the
 * Portfolio.
 */
public class PortfolioItem {
  private final StockObject stock;
  private final float quantity;
  private final float costPerShare;
  private final float cost;

  /**
   * The method constructs an individual PortfolioItem object that will be in a Portfolio.
   * A PortfolioItem consists of StockObject, quantity and cost per share.
   *
   * @param stock The StockObject in the Portfolio.
   * @param quantity the quantity of the stocks purchased.
   * @param costPerShare The cost of each stock.
   * @throws IllegalArgumentException if the given quantity is not greater than zero.
   */
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

  /**
   * This method fetches the stockObject.
   *
   * @return returns the current StockObject.
   */
  public StockObject getStock() {
    return this.stock;
  }

  /**
   * This method gives the current quantity of the stock owned.
   *
   * @return returns the quantity of the current stock.
   */
  public float getQuantity() {
    return this.quantity;
  }

  /**
   * This method fetches the cost of purchasing single share of current stock.
   *
   * @return returns the cost of each stock.
   */
  public float getCostPerShare() {
    return this.costPerShare;
  }

  /**
   * This method computes the total purchasing cost of the current stock.
   *
   * @return returns the total cost for investing in the current stock.
   */
  public float getCost() {
    return this.cost;
  }

  /**
   * This method fetches the current stock price for single share.
   *
   * @return returns the stock price of single share.
   */
  public float getCurrentPrice() {
    return this.stock.getCurrentPrice();
  }

  /**
   * This method fetches the current value of this stock based on the current price of
   * a single stock.
   * Which is basically quantity multiplied by current stock price.
   *
   * @return returns the current value of the stock.
   */
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
