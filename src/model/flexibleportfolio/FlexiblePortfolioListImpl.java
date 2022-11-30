package model.flexibleportfolio;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import model.AbstractPortfolioListImpl;
import model.TransactionType;
import model.portfolio.PortfolioItem;
import model.stock.StockObjectImpl;

/**
 * This class implements the PortfolioList Interface.
 * This class has a list of Flexible Portfolios that are created.
 */
public class FlexiblePortfolioListImpl extends AbstractPortfolioListImpl
        implements FlexiblePortfolioList {
  private final Map<String, FlexiblePortfolio> portfolios;

  /**
   * Constructs a FlexiblePortfolioImpl object and assigns the portfolio save file directory.
   */
  public FlexiblePortfolioListImpl() {
    super("/portfolioTxnFiles/");

    this.portfolios = new HashMap<>();
  }

  private List<PortfolioItemTransaction> loadPortfolio(String path)
          throws IllegalArgumentException {

    List<String> portfolioLines = this.loadPortfolioData(path);

    List<PortfolioItemTransaction> portfolioItemTransactions = new ArrayList<>();

    for (String line : portfolioLines) {
      try {
        String[] portfolioItemValues = line.split(",");
        TransactionType type = Objects.equals(
                portfolioItemValues[0], TransactionType.BUY.toString()
        ) ? TransactionType.BUY : TransactionType.SELL;

        portfolioItemTransactions.add(
                new PortfolioItemTransaction(
                        type,
                        new StockObjectImpl(portfolioItemValues[1]),
                        Float.parseFloat(portfolioItemValues[2]),
                        LocalDate.parse(portfolioItemValues[3],
                                DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                        Float.parseFloat(portfolioItemValues[4])
                )
        );
      } catch (Exception e) {
        throw new IllegalArgumentException("The File format is not valid.");
      }

    }

    return portfolioItemTransactions;
  }

  @Override
  public FlexiblePortfolio getPortfolio(String portfolioName)
          throws IllegalArgumentException {
    if (portfolios.containsKey(portfolioName)) {
      return portfolios.get(portfolioName);
    } else if (portfolioFiles.containsKey(portfolioName)) {
      Path filepath = portfolioFiles.get(portfolioName);
      List<PortfolioItemTransaction> pITxnS = this.loadPortfolio(filepath.toString());
      FlexiblePortfolio portfolio = new FlexiblePortfolioImpl(portfolioName,
              filepath.getFileName().toString(), pITxnS);
      portfolios.put(portfolioName, portfolio);
      return portfolio;
    } else {
      throw new IllegalArgumentException("The corresponding Portfolio File is not Found!");
    }
  }

  @Override
  public String createPortfolio(String portfolioName, Map<String, Float> ignored) {
    this.checkPortfolioNameAlreadyExists(portfolioName);
    FlexiblePortfolio portfolio = new FlexiblePortfolioImpl(portfolioName);
    portfolios.put(portfolioName, portfolio);
    this.portfolioFiles.put(portfolioName, Paths.get(portfolio.getPortfolioFilePath()));
    return portfolio.getPortfolioFilePath();
  }

  @Override
  public String createPortfolioFromFile(String portfolioName, String portfolioFilePath)
          throws IllegalArgumentException {
    if(portfolioName.length() == 0) {
      throw new IllegalArgumentException("The name cannot be empty.");
    }
    this.checkPortfolioNameAlreadyExists(portfolioName);
    List<PortfolioItemTransaction> pITxnS = this.loadPortfolio(portfolioFilePath);
    FlexiblePortfolio portfolio = new FlexiblePortfolioImpl(portfolioName, null, pITxnS);
    portfolios.put(portfolioName, portfolio);
    this.portfolioFiles.put(portfolioName, Paths.get(portfolio.getPortfolioFilePath()));
    return portfolio.getPortfolioFilePath();
  }

  @Override
  public float getPortfolioValueAtDate(String portfolioName, LocalDate date) {
    FlexiblePortfolio portfolio = this.getPortfolio(portfolioName);
    return portfolio.getPortfolioValueAtDate(date);
  }

  @Override
  public PortfolioItem[] getPortfolioCompositionAtDate(String portfolioName, LocalDate date) {
    FlexiblePortfolio portfolio = this.getPortfolio(portfolioName);
    return portfolio.getPortfolioCompositionAtDate(date);
  }

  @Override
  public void addTransactionToPortfolio(String portfolioName, TransactionType type,
                                        String stockTicker, float quantity, LocalDate date,
                                        float commission) throws IllegalArgumentException {
    if (commission < 0) {
      throw new IllegalArgumentException("Commission given cannot be negative.");
    }

    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Cannot have a transaction with future date!");
    }

    FlexiblePortfolio portfolio = this.getPortfolio(portfolioName);
    try {
      if (type == TransactionType.BUY) {
        portfolio.addStock(stockTicker, quantity, date, commission);
      } else if (type == TransactionType.SELL) {
        portfolio.sellStock(stockTicker, quantity, date, commission);
      } else {
        throw new IllegalArgumentException("The transaction type cannot be null.");
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("The portfolio is not valid: " + e.getMessage());
    }
  }

  @Override
  public float getCostBasis(String portfolioName, LocalDate tillDate)
          throws IllegalArgumentException {
    FlexiblePortfolio portfolio = this.getPortfolio(portfolioName);
    return portfolio.getCostBasis(tillDate);
  }

}
