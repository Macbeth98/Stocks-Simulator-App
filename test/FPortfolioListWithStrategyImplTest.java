import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.flexibleportfolio.FPortfolioListWithStrategy;
import model.flexibleportfolio.FPortfolioListWithStrategyImpl;
import model.flexibleportfolio.FlexiblePortfolio;
import model.portfolio.PortfolioItem;
import model.stock.StockObject;
import model.stock.StockObjectImpl;

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
  public void testApplyStrategyToPortfolioNegativeDistribution() {
    String portfolioName = "testApplyStrategyPortfolioNegativeDistribution" + new Date().getTime();

    fPortfolioListStrategy.createPortfolio(portfolioName, null);

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 60f);
    stocksDistribution.put("TSLA", 40f);
    stocksDistribution.put("MSFT", -20f);
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


  private float addStocksInPeriodicStrategy(float amount, int frequencyInDays, float commission,
                                           float costBasis,
                                           Map<String, Float> stocksDistribution,
                                           Map<String, Float> stocksMap,
                                           LocalDate startDate, LocalDate endDate) {

    LocalDate currentDate = startDate;
    while (!currentDate.isAfter(LocalDate.now()) && !currentDate.isAfter(endDate)) {

      for (String ticker : stocksDistribution.keySet()) {
        StockObject stock = new StockObjectImpl(ticker);
        float priceAtDate = stock.getCurrentPriceAtDate(currentDate);
        float currentStockAmount;

        if(stocksMap.containsKey(ticker)) {
          currentStockAmount = stocksMap.get(ticker);
        } else {
          currentStockAmount = 0;
        }

        float investAmount = (amount * (stocksDistribution.get(ticker)/100)) - commission;
        float newQuantity = investAmount / priceAtDate;

        stocksMap.put(ticker, currentStockAmount + newQuantity);

        costBasis += investAmount + commission;
      }

      currentDate = currentDate.plusDays(frequencyInDays);

    }

    return costBasis;
  }


  private String checkPortfolioComposition(PortfolioItem[] portfolioItems,
                                           Map<String, Float> stocksMap) {
    int tickersFound = 0;
    float portfolioValue = 0;

    for (PortfolioItem portfolioItem : portfolioItems) {
      StockObject stock = portfolioItem.getStock();
      assertEquals(stocksMap.get(stock.getTicker()), portfolioItem.getQuantity(), 0.001);
      tickersFound++;
      portfolioValue += stocksMap.get(stock.getTicker()) * stock.getCurrentPrice();
    }

    return tickersFound + "__" + portfolioValue;
  }

  @Test
  public void testPeriodicInvestmentStrategyWithBothDates() {
    String portfolioName = "testPeriodicInvestmentPortfolioStrategy" + new Date().getTime();

    // from start to finish. create a portfolio with list of stocks and distribution. And build
    // Portfolio with given stocks, distribution, amount and time range.

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 40.76f);
    stocksDistribution.put("TSLA", 39.24f);
    stocksDistribution.put("COKE", 20f);

    float amount  = 1000f;
    int frequencyInDays = 30;
    float commission = 5f;

    LocalDate startDate = this.getDate("09/12/2022");
    LocalDate endDate  = this.getDate("11/12/2022");

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );

    Map<String, Float> stocksMap = new HashMap<>();

    float costBasis = 0;

    costBasis = this.addStocksInPeriodicStrategy(amount, frequencyInDays, commission, costBasis,
            stocksDistribution, stocksMap, startDate, endDate);

    assertEquals(costBasis, fPortfolioListStrategy.getCostBasis(portfolioName, LocalDate.now()),
            0.001);

    PortfolioItem[] portfolioItems = fPortfolioListStrategy.getPortfolioCompositionAtDate(
            portfolioName, LocalDate.now()
    );

    int tickersFound ;
    float portfolioValue;

    String[] valueStr = this.checkPortfolioComposition(portfolioItems, stocksMap).split("__");
    tickersFound = Integer.parseInt(valueStr[0]);
    portfolioValue = Float.parseFloat(valueStr[1]);

    assertEquals(tickersFound, stocksDistribution.keySet().size());
    assertEquals(portfolioValue, fPortfolioListStrategy.getPortfolioValueAtDate(
            portfolioName, LocalDate.now()), 0.001);

    // Applying the similar strategy to an existing portfolio.

    frequencyInDays = 15;

    startDate = this.getDate("10/10/2021");
    endDate = this.getDate("12/10/2021");

    stocksDistribution.put("COKE", 10f);
    stocksDistribution.put("MSFT", 10f);

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );

    costBasis = this.addStocksInPeriodicStrategy(amount, frequencyInDays, commission, costBasis,
            stocksDistribution, stocksMap, startDate, endDate);

    assertEquals(costBasis, fPortfolioListStrategy.getCostBasis(portfolioName, LocalDate.now()),
            0.001);

    portfolioItems = fPortfolioListStrategy.getPortfolioCompositionAtDate(portfolioName,
            LocalDate.now());

    valueStr = this.checkPortfolioComposition(portfolioItems, stocksMap).split("__");
    tickersFound = Integer.parseInt(valueStr[0]);
    portfolioValue = Float.parseFloat(valueStr[1]);

    assertEquals(tickersFound, stocksDistribution.keySet().size());
    assertEquals(portfolioValue, fPortfolioListStrategy.getPortfolioValueAtDate(
            portfolioName, LocalDate.now()), 0.001);

  }


  @Test
  public void testPeriodicInvestmentStrategyWithNullEndDate() {
    String portfolioName = "testPeriodicInvestmentPortfolioStrategyOnDate" + new Date().getTime();

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 40.76f);
    stocksDistribution.put("TSLA", 39.24f);
    stocksDistribution.put("COKE", 20f);

    float amount  = 1000f;
    int frequencyInDays = 30;
    float commission = 5f;

    LocalDate startDate = this.getDate("09/12/2022");

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            null,
            commission
    );

    Map<String, Float> stocksMap = new HashMap<>();

    float costBasis = 0;

    costBasis = this.addStocksInPeriodicStrategy(amount, frequencyInDays, commission, costBasis,
            stocksDistribution, stocksMap, startDate, // representing date in long future instead
            LocalDate.now().plusYears(20)); //  of null in the test.

    assertEquals(costBasis, fPortfolioListStrategy.getCostBasis(portfolioName, LocalDate.now()),
            0.001);

    PortfolioItem[] portfolioItems = fPortfolioListStrategy.getPortfolioCompositionAtDate(
            portfolioName, LocalDate.now()
    );

    int tickersFound ;
    float portfolioValue;

    String[] valueStr = this.checkPortfolioComposition(portfolioItems, stocksMap).split("__");
    tickersFound = Integer.parseInt(valueStr[0]);
    portfolioValue = Float.parseFloat(valueStr[1]);

    assertEquals(tickersFound, stocksDistribution.keySet().size());
    assertEquals(portfolioValue, fPortfolioListStrategy.getPortfolioValueAtDate(
            portfolioName, LocalDate.now()), 0.001);

  }


  @Test
  public void testPeriodicInvestmentStrategyWithFutureDates() {
    String portfolioName = "testPeriodicInvestmentPortfolioStFutureDate" + new Date().getTime();

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 40.76f);
    stocksDistribution.put("TSLA", 39.24f);
    stocksDistribution.put("COKE", 20f);

    float amount  = 1000f;
    int frequencyInDays = 30;
    float commission = 5f;

    LocalDate startDate = this.getDate("10/12/2022"); // Past Start Date.
    LocalDate endDate  = this.getDate("11/12/2023"); // Future end Date.

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );

    Map<String, Float> stocksMap = new HashMap<>();

    float costBasis = 0;

    costBasis = this.addStocksInPeriodicStrategy(amount, frequencyInDays, commission, costBasis,
            stocksDistribution, stocksMap, startDate, endDate);

    assertEquals(costBasis, fPortfolioListStrategy.getCostBasis(portfolioName, LocalDate.now()),
            0.001);

    PortfolioItem[] portfolioItems = fPortfolioListStrategy.getPortfolioCompositionAtDate(
            portfolioName, LocalDate.now()
    );

    int tickersFound ;
    float portfolioValue;

    String[] valueStr = this.checkPortfolioComposition(portfolioItems, stocksMap).split("__");
    tickersFound = Integer.parseInt(valueStr[0]);
    portfolioValue = Float.parseFloat(valueStr[1]);

    assertEquals(tickersFound, stocksDistribution.keySet().size());
    assertEquals(portfolioValue, fPortfolioListStrategy.getPortfolioValueAtDate(
            portfolioName, LocalDate.now()), 0.001);

    // Applying to existing Portfolio. Both Start and end dates are in the future.
    stocksDistribution.put("GOOG", 30.76f);
    stocksDistribution.put("MSFT", 10f);

    startDate = this.getDate("12/12/2022"); // Future Date.
    endDate = this.getDate("12/12/2023"); // Future Date.

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );

    assertEquals(costBasis, fPortfolioListStrategy.getCostBasis(portfolioName, LocalDate.now()),
            0.001);

    portfolioItems = fPortfolioListStrategy.getPortfolioCompositionAtDate(
            portfolioName, LocalDate.now()
    );

    assertEquals(tickersFound, portfolioItems.length);
    assertEquals(portfolioValue, fPortfolioListStrategy.getPortfolioValueAtDate(
            portfolioName, LocalDate.now()), 0.001);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testPeriodicInvestmentStrategyInvalidDistribution() {
    String portfolioName = "testPeriodicInvestmentStrategyInvalidDist" + new Date().getTime();

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 50.76f);
    stocksDistribution.put("TSLA", 39.24f);
    stocksDistribution.put("COKE", 20f); // totals to 110 percent

    float amount  = 1000f;
    int frequencyInDays = 30;
    float commission = 5f;

    LocalDate startDate = this.getDate("05/12/2022");
    LocalDate endDate  = this.getDate("11/12/2022");

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPeriodicInvestmentStrategyNegativeDistribution() {
    String portfolioName = "testPeriodicInvestmentStrategyNegativeDist" + new Date().getTime();

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 50.76f);
    stocksDistribution.put("TSLA", 39.24f);
    stocksDistribution.put("MSFT", -10f);
    stocksDistribution.put("COKE", 20f); // totals to 100 percent

    float amount  = 1000f;
    int frequencyInDays = 30;
    float commission = 5f;

    LocalDate startDate = this.getDate("05/12/2022");
    LocalDate endDate  = this.getDate("11/12/2022");

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPeriodicInvestmentStrategyNullDistribution() {
    String portfolioName = "testPeriodicInvestmentStrategyNullDist" + new Date().getTime();

    float amount  = 1000f;
    int frequencyInDays = 30;
    float commission = 5f;

    LocalDate startDate = this.getDate("05/12/2022");
    LocalDate endDate  = this.getDate("11/12/2022");

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            null,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPeriodicInvestmentStrategyEmptyDistribution() {
    String portfolioName = "testPeriodicInvestmentStrategyEmptyDist" + new Date().getTime();

    Map<String, Float> stocksDistribution = new HashMap<>();

    float amount  = 1000f;
    int frequencyInDays = 30;
    float commission = 5f;

    LocalDate startDate = this.getDate("05/12/2022");
    LocalDate endDate  = this.getDate("11/12/2022");

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPeriodicInvestmentStrategyInvalidAmount() {
    String portfolioName = "testPeriodicInvestmentStrategyInvalidAmount" + new Date().getTime();

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 40.76f);
    stocksDistribution.put("TSLA", 39.24f);
    stocksDistribution.put("COKE", 20f); // totals to 100 percent

    float amount  = -1000f;
    int frequencyInDays = 30;
    float commission = 5f;

    LocalDate startDate = this.getDate("05/12/2022");
    LocalDate endDate  = this.getDate("11/12/2022");

    try {
      fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
              portfolioName,
              stocksDistribution,
              amount,
              frequencyInDays,
              startDate,
              endDate,
              commission
      );

      fail("Negative Amount should have thrown an exception.");
    } catch (IllegalArgumentException ignored) {

    }

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            0, // amount is zero. Should throw exception.
            frequencyInDays,
            startDate,
            endDate,
            commission
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPeriodicInvestmentStrategyInvalidFrequency() {
    String portfolioName = "testPeriodicInvestmentStrategyInvalidFrequency" + new Date().getTime();

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 40.76f);
    stocksDistribution.put("TSLA", 39.24f);
    stocksDistribution.put("COKE", 20f); // totals to 100 percent

    float amount  = 1000f;
    int frequencyInDays = -30;
    float commission = 5f;

    LocalDate startDate = this.getDate("05/12/2022");
    LocalDate endDate  = this.getDate("11/12/2022");

    try {
      fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
              portfolioName,
              stocksDistribution,
              amount,
              frequencyInDays,
              startDate,
              endDate,
              commission
      );

      fail("Negative Frequency should have thrown an exception.");
    } catch (IllegalArgumentException ignored) {

    }

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            0, // zero frequency should throw exception.
            startDate,
            endDate,
            commission
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPeriodicInvestmentStrategyInvalidCommission() {
    String portfolioName = "testPeriodicInvestmentStrategyInvalidCommission" + new Date().getTime();

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 40.76f);
    stocksDistribution.put("TSLA", 39.24f);
    stocksDistribution.put("COKE", 20f); // totals to 100 percent

    float amount  = 1000f;
    int frequencyInDays = 30;
    float commission = -5f;

    LocalDate startDate = this.getDate("05/12/2022");
    LocalDate endDate  = this.getDate("11/12/2022");

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );
  }

  @Test
  public void testPeriodicInvestmentStrategyZeroCommission() {
    String portfolioName = "testPeriodicInvestmentStrategyZeroCommission" + new Date().getTime();

    Map<String, Float> stocksDistribution = new HashMap<>();
    stocksDistribution.put("GOOG", 40.76f);
    stocksDistribution.put("TSLA", 39.24f);
    stocksDistribution.put("COKE", 20f); // totals to 100 percent

    float amount  = 1000f;
    int frequencyInDays = 30;
    float commission = 0f;

    LocalDate startDate = this.getDate("05/12/2022");
    LocalDate endDate  = this.getDate("11/12/2022");

    fPortfolioListStrategy.periodicInvestmentPortfolioStrategy(
            portfolioName,
            stocksDistribution,
            amount,
            frequencyInDays,
            startDate,
            endDate,
            commission
    );

    Map<String, Float> stocksMap = new HashMap<>();

    float costBasis = 0;

    costBasis = this.addStocksInPeriodicStrategy(amount, frequencyInDays, commission, costBasis,
            stocksDistribution, stocksMap, startDate, endDate);

    assertEquals(costBasis, fPortfolioListStrategy.getCostBasis(portfolioName, LocalDate.now()),
            0.001);

    PortfolioItem[] portfolioItems = fPortfolioListStrategy.getPortfolioCompositionAtDate(
            portfolioName, LocalDate.now()
    );

    int tickersFound ;
    float portfolioValue;

    String[] valueStr = this.checkPortfolioComposition(portfolioItems, stocksMap).split("__");
    tickersFound = Integer.parseInt(valueStr[0]);
    portfolioValue = Float.parseFloat(valueStr[1]);

    assertEquals(tickersFound, stocksDistribution.keySet().size());
    assertEquals(portfolioValue, fPortfolioListStrategy.getPortfolioValueAtDate(
            portfolioName, LocalDate.now()), 0.001);
  }

}