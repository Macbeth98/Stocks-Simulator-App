import java.io.IOException;
import java.io.InputStreamReader;

public class GenericPortfolioApplication {
  public static void main(String[] args) {
    try {
      new GenericPortfolioControllerImpl(new InputStreamReader(System.in), System.out)
              .goGenericController(new GenericPortfolioListImpl());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

