package model.flexibleportfolio;

import java.time.LocalDate;
import java.util.Map;

/**
 * This interface refers to the Flexible Portfolio list and the investment strategies that can
 * be applied on the Portfolios. This interface extends FlexiblePortfolioList interface which
 * represents a list of FlexiblePortfolio and the methods pertaining to it. In addition to it,
 * the interface provides methods to apply a strategy to a Portfolio.
 */
public interface FPortfolioListWithStrategy extends FlexiblePortfolioList {

  /**
   * Applies the strategy to the given Portfolio. The amount to invest and the stocks distribution
   * and the date to invest defines the strategy.
   *
   * @param portfolioName the name of the portfolio to which the strategy needs to be applied.
   * @param amount the total amount to invest in the portfolio.
   * @param date the date on which the strategy needs to be executed.
   * @param stocksDistribution the map of stocks to the percentage of investment amount allocation.
   * @param commission the commission needs to be charged per transaction of this strategy.
   * @throws IllegalArgumentException if any of the given params are not valid.
   */
  void applyStrategyToAnExistingPortfolio(String portfolioName, float amount, LocalDate date,
                                          Map<String, Float> stocksDistribution, float commission)
          throws IllegalArgumentException;


  /**
   * Creates a new Portfolio with the given stocks distribution corresponding to the amount and
   * the frequency given.
   *
   * @param portfolioName the name of the portfolio that will be created.
   * @param stocksDistribution the map of stocks to the percentage of investment amount allocation.
   * @param amount the amount to invest in portfolio.
   * @param frequencyInDays the frequency in days this investment needs to be repeated.
   * @param startDate the investment start date.
   * @param endDate the investment end date. Can be null. Then it is an ongoing strategy.
   * @param commission the commission that needs to be charged per transaction.
   * @throws IllegalArgumentException if any of the given params are not valid.
   */
  void periodicInvestmentPortfolioWithStrategy (
          String portfolioName,
          Map<String, Float> stocksDistribution,
          float amount,
          int frequencyInDays,
          LocalDate startDate,
          LocalDate endDate,
          float commission
  ) throws IllegalArgumentException;

}
