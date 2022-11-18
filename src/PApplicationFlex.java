import java.io.IOException;
import java.io.InputStreamReader;

import controller.flexibleportfolio.FlexiblePortfolioControllerImpl;
import model.flexibleportfolio.FlexiblePortfolioListImpl;

public class PApplicationFlex {
  /**
   * This is the main method which will start the application. This method will create the
   * FlexiblePortfolioController Object with an Input and Output source.
   *
   * @param args parameters that can be passed to the main method.
   */
  public static void main(String[] args) {
    try {
      new FlexiblePortfolioControllerImpl(new InputStreamReader(System.in), System.out)
              .goController(new FlexiblePortfolioListImpl());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
