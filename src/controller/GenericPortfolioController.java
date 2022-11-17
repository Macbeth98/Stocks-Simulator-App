package controller;

import java.io.IOException;

import model.GenericPortfolioList;

/**
 * Represents the interface that contains the generic controller methods.
 */
public interface GenericPortfolioController {

  /**
   * Initializes the generic controller that the client interacts with.
   */
  void goGenericController(GenericPortfolioList gpList) throws IOException;
}
