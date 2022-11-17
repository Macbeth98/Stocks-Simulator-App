package view;

import java.io.IOException;

/**
 * Represents an interface containing view methods for a generic portfolio.
 */
public interface GenericPortfolioView {
  /**
   * Display portfolio creation menu for flexible portfolio.
   */
  void menuView() throws IOException;

  /**
   * Display this message if user inputs incorrect menu choice.
   */
  void invalidChoiceMessage() throws IOException;
}
