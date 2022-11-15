import java.io.IOException;

/**
 * Interface representing the controller for the flexible portfolio and its operations.
 */
public interface FlexiblePortfolioController {

  /**
   * Method that starts the controller for Flexible Portfolio.
   * @param fpList Flexible Portfolio List model object used by the controller
   * @throws IOException throws IOException when view method fails
   */
  void goController(FlexiblePortfolioList fpList) throws IOException;
}
