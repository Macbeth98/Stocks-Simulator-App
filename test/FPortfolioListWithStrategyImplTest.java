import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.flexibleportfolio.FPortfolioListWithStrategy;
import model.flexibleportfolio.FPortfolioListWithStrategyImpl;
import model.flexibleportfolio.FlexiblePortfolio;
import model.portfolio.PortfolioItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class represents a Test Class for the class FPortfolioListWithStrategyImpl. This class has
 * methods that tests the methods of the above-mentioned class. This class extends the
 * FlexiblePortfolioListImplTest class which is a test class for the FlexiblePortfolioListImpl
 * class which is extended by the above class.
 */
public class FPortfolioListWithStrategyImplTest extends FlexiblePortfolioListImplTest {

  private FPortfolioListWithStrategy fPortfolioListStrategy;

  @Override
  public void setUp() {
    this.fPortfolioListStrategy = new FPortfolioListWithStrategyImpl();
    this.portfolioList = fPortfolioListStrategy;

    this.initiateStocks();
  }

  @Test
  public void testApplyStrategyToAPortfolio() {
    String portfolioName = "testApplyStrategyPortfolio" + new Date().getTime();

    fPortfolioListStrategy.createPortfolio(portfolioName, null);

    FlexiblePortfolio portfolio = fPortfolioListStrategy.getPortfolio(portfolioName);

    this.addStockTransactions(portfolio, portfolioName);

    float costBasisValue = 379896.7f; // after adding the transactions this is the cost basis.

    String dateString = "05/10/2022";
    LocalDate date = this.getDate(dateString);

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 40.76f); // existing stock
    stocksDistribution.put("TSLA", 39.24f); // existing stock
    stocksDistribution.put("COKE", 20f);  // new stock

    this.stocks.put("COKE", 0f);

    float commission = 5f;
    float amount = 2000f;

    fPortfolioListStrategy.applyStrategyToAnExistingPortfolio(
            portfolioName,
            amount,
            date,
            stocksDistribution,
            commission
    );

    PortfolioItem[] portfolioItems = portfolio.getPortfolioComposition();

    float addedCostBasis = 0;

    int tickersFound = 0;

    float portfolioValue = 0;

    Map<String, Float> stocksMap = new HashMap<>();

    for (PortfolioItem portfolioItem : portfolioItems) {
      String ticker = portfolioItem.getStock().getTicker();

      float price = portfolioItem.getStock().getCurrentPriceAtDate(date);
      float todayPrice = portfolioItem.getStock().getCurrentPrice();
      float quantity = portfolioItem.getQuantity();

      if(!stocksDistribution.containsKey(ticker)) {
        portfolioValue += stocks.get(ticker) * todayPrice;
        continue;
      }

      tickersFound++;

      float investAmount = (amount * (stocksDistribution.get(ticker)/100)) - commission;
      float expectedAmount = stocks.get(ticker) + (investAmount/price);
      portfolioValue += expectedAmount * todayPrice;

      assertEquals(expectedAmount, quantity, 0.001);

      addedCostBasis += investAmount + commission;

      stocksMap.put(ticker, expectedAmount);
    }

    assertEquals(stocksDistribution.keySet().size(), tickersFound);

    float expectedCostBasis = costBasisValue + addedCostBasis;

    assertEquals(expectedCostBasis, portfolio.getCostBasis(LocalDate.now()), 0.001);

    assertEquals(portfolioValue, portfolio.getPortfolioValueAtDate(LocalDate.now()), 0.001);

    // Give a Future Date. The values should remain same, as it is a future transaction date.

    LocalDate futureDate = this.getDate("01/01/2023");

    fPortfolioListStrategy.applyStrategyToAnExistingPortfolio(
            portfolioName,
            amount,
            futureDate,
            stocksDistribution,
            commission
    );

    portfolioItems = portfolio.getPortfolioComposition();

    for (PortfolioItem portfolioItem : portfolioItems) {
      String ticker = portfolioItem.getStock().getTicker();

      if(!stocksMap.containsKey(ticker)) {
        continue;
      }

      assertEquals(stocksMap.get(ticker), portfolioItem.getQuantity(), 0.001);
    }

    assertEquals(expectedCostBasis, portfolio.getCostBasis(LocalDate.now()), 0.001);

    assertEquals(portfolioValue, portfolio.getPortfolioValueAtDate(LocalDate.now()), 0.001);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyStrategyToPortfolioInvalidDate() {
    String portfolioName = "testApplyStrategyPortfolioInvalidDate" + new Date().getTime();

    fPortfolioListStrategy.createPortfolio(portfolioName, null);

    fPortfolioListStrategy.applyStrategyToAnExistingPortfolio(
            portfolioName,
            1000,
            null,
            new HashMap<>(),
            5f
    );

  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyStrategyToPortfolioInvalidDistribution() {
    String portfolioName = "testApplyStrategyPortfolioInvalidDistribution" + new Date().getTime();

    fPortfolioListStrategy.createPortfolio(portfolioName, null);

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 40.76f);
    stocksDistribution.put("TSLA", 40.24f);
    stocksDistribution.put("COKE", 20f);


    fPortfolioListStrategy.applyStrategyToAnExistingPortfolio(
            portfolioName,
            1000,
            LocalDate.now(),
            stocksDistribution,
            5f
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyStrategyToPortfolioDistributionNull() {
    String portfolioName = "testApplyStrategyPortfolioInvalidDistNull" + new Date().getTime();

    fPortfolioListStrategy.createPortfolio(portfolioName, null);


    fPortfolioListStrategy.applyStrategyToAnExistingPortfolio(
            portfolioName,
            1000,
            LocalDate.now(),
            null,
            5f
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyStrategyToPortfolioDistributionEmpty() {
    String portfolioName = "testApplyStrategyPortfolioInvalidDistEmpty" + new Date().getTime();

    fPortfolioListStrategy.createPortfolio(portfolioName, null);


    fPortfolioListStrategy.applyStrategyToAnExistingPortfolio(
            portfolioName,
            1000,
            LocalDate.now(),
            new HashMap<>(),
            5f
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyStrategyToPortfolioInvalidAmount() {
    String portfolioName = "testApplyStrategyPortfolioInvalidDistNull" + new Date().getTime();

    fPortfolioListStrategy.createPortfolio(portfolioName, null);

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 35f);
    stocksDistribution.put("TSLA", 45f);
    stocksDistribution.put("COKE", 20f);

    // CASE: Amount given 0.
    try {
      fPortfolioListStrategy.applyStrategyToAnExistingPortfolio(
              portfolioName,
              0,
              LocalDate.now(),
              stocksDistribution,
              5f
      );

      fail("Should have failed when amount given is zero.");
    } catch (IllegalArgumentException ignored) {

    }

    // CASE: Amount given negative.
    fPortfolioListStrategy.applyStrategyToAnExistingPortfolio(
            portfolioName,
            -1500,
            LocalDate.now(),
            stocksDistribution,
            5f
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyStrategyToPortfolioNegativeCommission() {
    String portfolioName = "testApplyStrategyPortfolioNegativeCommission" + new Date().getTime();

    fPortfolioListStrategy.createPortfolio(portfolioName, null);

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 35f);
    stocksDistribution.put("TSLA", 45f);
    stocksDistribution.put("COKE", 20f);

    fPortfolioListStrategy.applyStrategyToAnExistingPortfolio(
            portfolioName,
            1500,
            LocalDate.now(),
            stocksDistribution,
            -5f
    );
  }

  // Test Periodic Investment Strategy

  @Test
  public void testPeriodicInvestmentStrategy() {

  }
}