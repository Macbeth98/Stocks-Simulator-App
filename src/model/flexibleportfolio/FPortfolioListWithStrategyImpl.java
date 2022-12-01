package model.flexibleportfolio;

import java.time.LocalDate;
import java.util.Map;

import model.strategy.PSDollarCostAverage;
import model.strategy.PortfolioStrategy;

/**
 * Class that consists of the implementations of strategies for a flexible portfolio list.
 */
public class FPortfolioListWithStrategyImpl extends FlexiblePortfolioListImpl
        implements FPortfolioListWithStrategy {

  private final PortfolioStrategy portfolioStrategy;

  /**
   * Constructs a model object of a list of flexible portfolios with strategies.
   */
  public FPortfolioListWithStrategyImpl() {
    super();
    portfolioStrategy = new PSDollarCostAverage();
  }

  @Override
  public void applyStrategyToAnExistingPortfolio(String portfolioName, float amount, LocalDate date,
                                                 Map<String, Float> stocksDistribution,
                                                 float commission)
          throws IllegalArgumentException {

    FlexiblePortfolio portfolio = this.getPortfolio(portfolioName);

    portfolioStrategy.applyStrategyToAnExistingPortfolio(
            portfolio,
            amount,
            date,
            stocksDistribution,
            commission
    );
  }

  @Override
  public void periodicInvestmentPortfolioStrategy(String portfolioName,
                                                      Map<String, Float> stocksDistribution,
                                                      float amount, int frequencyInDays,
                                                      LocalDate startDate, LocalDate endDate,
                                                      float commission)
          throws IllegalArgumentException {

    FlexiblePortfolio portfolio;

    if (!this.portfolioFiles.containsKey(portfolioName)) {
      this.createPortfolio(portfolioName, null);
    }
    portfolio = this.getPortfolio(portfolioName);

    portfolioStrategy.periodicInvestmentPortfolioStrategy(
            portfolio,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );
  }

}
