import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is the main class which calls the main(String[] args) method.
 */
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
