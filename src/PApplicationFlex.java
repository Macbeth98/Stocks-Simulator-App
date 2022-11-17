import java.io.IOException;
import java.io.InputStreamReader;

import controller.flexibleportfolio.FlexiblePortfolioControllerImpl;
import model.flexibleportfolio.FlexiblePortfolioListImpl;

public class PApplicationFlex {
  public static void main(String[] args) {
    try {
      new FlexiblePortfolioControllerImpl(new InputStreamReader(System.in), System.out)
              .goController(new FlexiblePortfolioListImpl());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
