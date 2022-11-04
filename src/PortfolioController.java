import java.io.IOException;

/**
 * Interface representing the controller for the portfolio application and it's operations.
 */
public interface PortfolioController {
  /**
   * This method is called by the controller to initiate the application.
   *
   * @param portfolioList model input variable of PortfolioList type used by the controller
   * @throws IOException thrown by input stream
   */
  void goController(PortfolioList portfolioList) throws IOException;
}
