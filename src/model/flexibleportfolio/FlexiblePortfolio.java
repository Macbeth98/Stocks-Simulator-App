package model.flexibleportfolio;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Map;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioItem;

/**
 * This represents a Portfolio that can be modified. This interface extends the Portfolio interface.
 * This portfolio can add and sell stocks and can also get cost basis and portfolio performance.
 */
public interface FlexiblePortfolio extends Portfolio {
  /**
   * Adds a Stock to the given portfolio.
   *
   * @param stockTicker  the stock that needs to be added.
   * @param quantity     the quantity of the stock need to add.
   * @param purchaseDate the date at which the stock is purchased.
   * @param commission   the commission that was charged at the purchase of the stock.
   * @return returns the updated portfolio.
   * @throws FileNotFoundException if the portfolio is not found or cannot be saved.
   */
  FlexiblePortfolio addStock(String stockTicker, float quantity, LocalDate purchaseDate, float commission)
          throws FileNotFoundException;

  /**
   * Sells a stock from the given Portfolio. Decreases the value of stock from the Portfolio
   * depending on the number given.
   *
   * @param stockTicker the stock that needs to be sold.
   * @param quantity    the quantity of the stock that needs to be sold.
   * @param saleDate    the date at which the stock was sold.
   * @param commission  the commission that was charged while the sell transaction of the stock.
   * @return returns the updated portfolio after sell.
   * @throws FileNotFoundException if the portfolio is not found or cannot be saved.
   */
  FlexiblePortfolio sellStock(String stockTicker, float quantity, LocalDate saleDate, float commission)
          throws FileNotFoundException;

  float getCostBasis(LocalDate tillDate);

  /**
   * Gets the Composition of the Portfolio till the date given.
   *
   * @param date date on which the portfolio composition needs to get.
   * @return returns the composition of portfolio in Portfolio Item format.
   */
  PortfolioItem[] getPortfolioCompositionAtDate(LocalDate date);

  /**
   * Gets the portfolio performance values divided into equal span of time depending on the range
   * given.
   *
   * @param fromDate the date from which the performance values needs to be given.
   * @param toDate   the date till which the performance values needs to get.
   * @return returns the map of date in string format(dd MMM yyyy) to portfolio on that day.
   * @throws IllegalArgumentException if the given dates are not valid.
   */
  Map<String, Float> getPortfolioPerformance(LocalDate fromDate, LocalDate toDate)
          throws IllegalArgumentException;
}
