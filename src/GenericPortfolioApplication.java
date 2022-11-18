import java.io.IOException;
import java.io.InputStreamReader;

import controller.GenericPortfolioControllerImpl;
import model.GenericPortfolioListImpl;

/**
 * Generic portfolio application, which initializes the application.
 */
public class GenericPortfolioApplication {

  /**
   * This is the main method which will start the application. This method will create the
   * GenericPortfolioController Object with an Input and Output source.
   *
   * @param args parameters that can be passed to the main method.
   */
  public static void main(String[] args) {
    try {
      new GenericPortfolioControllerImpl(new InputStreamReader(System.in), System.out)
              .goGenericController(new GenericPortfolioListImpl());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

