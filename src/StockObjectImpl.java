import java.util.Date;

public class StockObjectImpl implements StockObject{
  @Override
  public float getQuantity() {
    return 0;
  }

  @Override
  public String getTicker() {
    return null;
  }

  @Override
  public float getCurrentPrice() {
    return 0;
  }

  @Override
  public float getCurrentPriceAtDate(Date date) {
    return 0;
  }

  @Override
  public float getCurrentValue() {
    return 0;
  }

  @Override
  public float getCurrentValueAtDate(Date date) {
    return 0;
  }
}
