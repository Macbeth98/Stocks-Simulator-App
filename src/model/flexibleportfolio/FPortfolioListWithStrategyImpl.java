package model.flexibleportfolio;

import java.time.LocalDate;
import java.util.Map;

import model.strategy.PSDollarCostAverage;
import model.strategy.PortfolioStrategy;

public class FPortfolioListWithStrategyImpl extends FlexiblePortfolioListImpl
        implements FPortfolioListWithStrategy {

  private final PortfolioStrategy portfolioStrategy;

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
  public void periodicInvestmentPortfolioWithStrategy(String portfolioName,
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

    portfolioStrategy.periodicInvestmentPortfolioWithStrategy(
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
