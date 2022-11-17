package controller.flexibleportfolio;

import java.io.IOException;
import java.util.Scanner;

/**
 * This interface represents a command/menu option in the flexible portfolio controller.
 */
public interface FlexiblePortfolioControllerCommand {
  /**
   * Method that contains the controller command implementation.
   */
  void go(Scanner scan) throws IOException;
}
