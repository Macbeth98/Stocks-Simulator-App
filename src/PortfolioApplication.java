import java.io.IOException;
import java.io.InputStreamReader;

public class PortfolioApplication {
  public static void main(String[] args) {
    try {
      new PortfolioControllerImpl(new InputStreamReader(System.in), System.out)
              .go(new PortfolioListImpl());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
