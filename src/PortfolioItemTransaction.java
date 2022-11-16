import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PortfolioItemTransaction extends PortfolioItem {

  private final TransactionType type;
  private final float commission;
  private final Date date;

  /**
   * The method constructs an individual PortfolioItem object that will be in a Portfolio.
   * A PortfolioItem consists of StockObject, quantity and cost per share.
   *
   * @param stock      The StockObject in the Portfolio.
   * @param quantity   the quantity of the stocks purchased.
   * @param date       The date at which the stock was bought.
   * @param commission The commission that was charged while purchasing the stock.
   * @throws IllegalArgumentException if the given quantity is not greater than zero.
   */
  public PortfolioItemTransaction(TransactionType type, StockObject stock, float quantity, Date date, float commission)
          throws IllegalArgumentException {
    super(stock, quantity, stock.getCurrentPriceAtDate(date));

    this.type = type;

    if (commission < 0) {
      throw new IllegalArgumentException("Commission value cannot be less than zero.");
    }

    this.commission = commission;
    this.date = date;
  }

  public TransactionType getType() {
    return this.type;
  }

  @Override
  public float getQuantity() {
    return Objects.equals(this.type, TransactionType.BUY)
            ? super.getQuantity()
            : -1 * super.getQuantity();
  }


  /**
   * The method gets the commission that was charged for this stock.
   *
   * @return returns the commission that was charged for this stock.
   */
  public float getCommission() {
    return this.commission;
  }

  /**
   * This method gets the date at which the stock was purchased.
   *
   * @return returns the date the stock was purchased.
   */
  public Date getDate() {
    return date;
  }

  /**
   * Computes the total cost that was incurred for purchasing the given quantity of stocks.
   * The cost includes the stock share price multiplied by the quantity plus the commission that
   * was charged for the transaction.
   *
   * @return returns the total cost of the purchasing the stock including the commission paid.
   */
  public float getTotalCost() {
    return Objects.equals(this.type, TransactionType.BUY)
            ? this.getCost() + this.commission
            : this.getCost() - this.commission;
  }

  /**
   * Computes the cost basis for this Portfolio Transaction.
   *
   * @return returns the computed cost basis for this Portfolio transaction.
   */
  public float getCostBasis() {
    return Objects.equals(this.type, TransactionType.BUY)
            ? this.getCost() + this.commission
            : this.commission;
  }

  @Override
  public String toString() {
    return this.getType() + ","
            + this.getStock().getTicker() + ","
            + super.getQuantity() + ","
            + new SimpleDateFormat("MM/dd/yyyy").format(this.getDate()) + ","
            + this.getCommission() + ","
            + this.getCostPerShare() + ","
            + this.getCost() + ","
            + this.getTotalCost();
  }
}
