import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is the main class which calls the main(String[] args) method.
 */
public class PortfolioApplication {

  /**
   * This is the main method which will start the application. This method will create the
   * PortfolioController Object with an Input and Output source.
   *
   * @param args parameters that can be passed to the main method.
   */
  public static void main(String[] args) {
    try {
      new PortfolioControllerImpl(new InputStreamReader(System.in), System.out)
              .go(new PortfolioListImpl());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
