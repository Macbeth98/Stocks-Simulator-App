import java.util.Date;

/**
 * This class contains the implementation for the StockObject interface.
 * Implements the interface methods and consists of a public constructor.
 */
public class StockObjectImpl implements StockObject {

  private final String ticker;

  private final float price;

  /**
   * This constructs a StockObjectImpl which takes the stock ticker symbol as argument.
   *
   * @param ticker stock ticker symbol
   */
  public StockObjectImpl(String ticker) {
    this.ticker = ticker;

    float temp;
    Date date = new Date();
    try {
      temp = new AlphaVantageSource().getPriceAtDate(ticker, date);
    }
    catch (Exception e) {
      temp = 0;
    }
    this.price = temp;
  }

  @Override
  public String getTicker() {
    return this.ticker;
  }

  @Override
  public float getCurrentPrice() {
    return this.price;
  }

  @Override
  public float getCurrentPriceAtDate(Date date) {
    return new AlphaVantageSource().getPriceAtDate(this.ticker, date);
  }

  @Override
  public float getCurrentValue(float quantity) {
    return quantity * this.price;
  }

  @Override
  public float getCurrentValueAtDate(Date date, float quantity) {
    return quantity * this.getCurrentPriceAtDate(date);
  }
}
