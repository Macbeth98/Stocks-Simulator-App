package model.strategy;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import model.flexibleportfolio.FlexiblePortfolio;
import model.stock.StockObjectImpl;

/**
 * This class implements the Portfolio Strategy Interface. This class implements a strategy called
 * Dollar cost Averaging.
 */
public class PSDollarCostAverage implements PortfolioStrategy {

  private void addTransactionsToPortfolio(FlexiblePortfolio portfolio,
                                          Map<String, Float> stocksDistribution,
                                          float amount, float commission, LocalDate currentDate)
          throws IllegalArgumentException {
    try {
      for (String ticker : stocksDistribution.keySet()) {
        float stockAmount = (stocksDistribution.get(ticker) / 100) * amount - commission;
        float stockPrice = new StockObjectImpl(ticker).getCurrentPriceAtDate(currentDate);
        float quantity = stockAmount / stockPrice;
        portfolio.addStock(ticker, quantity, currentDate, commission);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Cannot add Transaction to Portfolio: "
              + e.getMessage());
    }
  }

  private float getTotalPercentage(Collection<Float> values) throws IllegalArgumentException {
    float totalValue = 0;

    for (Float value : values) {
      if (value <= 0) {
        throw new IllegalArgumentException("The percentage given in the Stocks "
                + "Distribution must be greater than 0");
      }
      totalValue += value;
    }
    return totalValue;
  }

  @Override
  public void applyStrategyToAnExistingPortfolio(FlexiblePortfolio portfolio, float amount,
                                                 LocalDate date,
                                                 Map<String, Float> stocksDistribution,
                                                 float commission)
          throws IllegalArgumentException {

    if (stocksDistribution == null) {
      throw new IllegalArgumentException("The Stocks Distribution must be given.");
    }

    if (amount <= 0) {
      throw new IllegalArgumentException("The Amount value given is not valid.");
    }

    if (date == null) {
      throw new IllegalArgumentException("Date must be given.");
    }

    if (commission < 0) {
      throw new IllegalArgumentException("The commission value given is not valid.");
    }

    float totalPercentage = this.getTotalPercentage(stocksDistribution.values());

    if (totalPercentage != 100f) {
      throw new IllegalArgumentException("The sum of given stock distribution is not 100%.");
    }

    if (date.isAfter(LocalDate.now())) {
      // store the strategy on a file and then
      return;
    }

    addTransactionsToPortfolio(portfolio, stocksDistribution, amount, commission, date);

  }

  @Override
  public void periodicInvestmentPortfolioStrategy(FlexiblePortfolio portfolio,
                                                  Map<String, Float> stocksDistribution,
                                                  float amount, int frequencyInDays,
                                                  LocalDate startDate, LocalDate endDate,
                                                  float commission)
          throws IllegalArgumentException {

    if (stocksDistribution == null) {
      throw new IllegalArgumentException("The Stocks Distribution must be given.");
    }

    if (amount <= 0) {
      throw new IllegalArgumentException("The Amount given is not valid.");
    }

    if (frequencyInDays < 1) {
      throw new IllegalArgumentException("The frequency given is not valid.");
    }

    if (endDate != null) {
      if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
        throw new IllegalArgumentException("The end date given is not valid.");
      }
    }

    if (commission < 0) {
      throw new IllegalArgumentException("The commission value given is not valid.");
    }

    float totalPercentage = this.getTotalPercentage(stocksDistribution.values());

    if (totalPercentage != 100f) {
      throw new IllegalArgumentException("The sum of given stocks distribution is not 100%.");
    }

    LocalDate now = LocalDate.now();

    LocalDate currentDate = startDate;

    boolean finished = false;

    while (!currentDate.isAfter(now)) {

      if (endDate != null && (currentDate.isAfter(endDate))) {
        finished = true;
        break;
      }

      addTransactionsToPortfolio(portfolio, stocksDistribution, amount, commission, currentDate);

      currentDate = currentDate.plusDays(frequencyInDays);
    }

    if (!finished) {
      // Implying this is either a future end date or null.
      // store the strategy.
    }

  }
}
