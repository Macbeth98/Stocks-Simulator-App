import java.io.IOException;

/**
 * Interface represting the controller for the portfolio application and it's operations.
 */
public interface PortfolioController {
  void go(PortfolioList portfolioList) throws IOException;
}
