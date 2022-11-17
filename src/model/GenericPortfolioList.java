package model;

import model.flexibleportfolio.FlexiblePortfolioList;

/**
 * Consists of all the main model methods that operate on a single/list of generic portfolios.
 */
public interface GenericPortfolioList {

  /**
   * Gets the Portfolio list implementation Model.
   * PortfolioList represents a collection of Inflexible Portfolios.
   *
   * @return returns the collection of Inflexible Portfolios.
   */
  PortfolioList getPortfolioList();

  /**
   * Gets the Flexible Portfolio list implementation Model.
   * FlexiblePortfolioList represents a collection of Flexible Portfolios.
   *
   * @return returns the collection of Flexible Portfolios.
   */
  FlexiblePortfolioList getFlexiblePortfolioList();


}
