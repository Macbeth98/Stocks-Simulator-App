import java.util.Date;
import java.util.Random;

public class StockObjectImpl implements StockObject {

  private final String ticker;

  private final float price;

  public StockObjectImpl(String ticker) {
    this.ticker = ticker;
    Random r = new Random();
    this.price = r.nextFloat();
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
    Random r = new Random();
    return r.nextFloat();
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
