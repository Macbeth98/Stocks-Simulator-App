package model.portfolio;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.AbstractPortfolioListImpl;
import model.stock.StockObjectImpl;

/**
 * This class implements the PortfolioList interface.
 * The class implement its method based on the files stored in System.
 */
public class PortfolioListImpl extends AbstractPortfolioListImpl implements PortfolioList {
  private final Map<String, Portfolio> portfolios;

  /**
   * This method constructs a PortfolioImpl Object. This method fetches the existing portfolio files
   * and map them to the portfolio Names.
   *
   * @throws RuntimeException when the directory where the portfolios cannot be created.
   */
  public PortfolioListImpl() throws RuntimeException {
    super("/portfolioCSVFiles/");

    this.portfolios = new HashMap<>();
  }

  private Portfolio loadPortfolio(String portfolioName, String path, Path filepath)
          throws IllegalArgumentException {

    Map<String, Float> stocksMap = new HashMap<>();

    List<String> portfolioLines = this.loadPortfolioData(path);

    for (String line : portfolioLines) {
      String[] portfolioItemValues = line.split(",");
      if (portfolioItemValues.length != 4) {
        throw new IllegalArgumentException("The file given is not in valid format.");
      }

      try {
        stocksMap.put(portfolioItemValues[0], Float.parseFloat(portfolioItemValues[1]));
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("The file given is not Valid format. "
                + "NumberException Occurred.");
      }
    }

    return this.createPortfolioImpl(portfolioName, stocksMap, filepath);
  }

  @Override
  public Portfolio getPortfolio(String portfolioName) throws IllegalArgumentException {

    if (portfolios.containsKey(portfolioName)) {
      return portfolios.get(portfolioName);
    } else if (portfolioFiles.containsKey(portfolioName)) {
      Path filepath = portfolioFiles.get(portfolioName);
      return this.loadPortfolio(portfolioName, filepath.toString(), filepath);
    } else {
      throw new IllegalArgumentException("The corresponding Portfolio File is not Found!");
    }
  }

  private Portfolio createPortfolioImpl(String portfolioName, Map<String, Float> stocksMap,
                                        Path filepath) throws IllegalArgumentException {
    PortfolioImpl.PortfolioBuilder portfolioBuilder = PortfolioImpl.getBuilder();
    portfolioBuilder = portfolioBuilder.portfolioName(portfolioName)
            .setPortfolioFileName(filepath);
    String[] keys = stocksMap.keySet().toArray(new String[0]);
    for (String key : keys) {
      portfolioBuilder = portfolioBuilder.addStockToPortfolio(
              new StockObjectImpl(key),
              stocksMap.get(key)
      );
    }

    Portfolio portfolio = portfolioBuilder.build();
    portfolios.put(portfolioName, portfolio);

    if (filepath == null) {
      try {
        String pathToFile = portfolio.savePortfolioToFile();
        portfolioFiles.put(portfolioName, Paths.get(pathToFile));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Storing of file error: " + e.getMessage());
      }

      // portfolioFiles.put(portfolioName, Paths.get(portfolio.getPortfolioFilePath()));
    }

    return portfolio;
  }

  @Override
  public String createPortfolio(String portfolioName, Map<String, Float> stocksMap)
          throws IllegalArgumentException {
    this.checkPortfolioNameAlreadyExists(portfolioName);
    return this.createPortfolioImpl(portfolioName, stocksMap, null).getPortfolioFilePath();
  }

  @Override
  public String createPortfolioFromFile(String portfolioName, String portfolioFilePath)
          throws IllegalArgumentException {
    this.checkPortfolioNameAlreadyExists(portfolioName);
    return loadPortfolio(portfolioName, portfolioFilePath, null).getPortfolioFilePath();
  }

  @Override
  public float getPortfolioValueAtDate(String portfolioName, LocalDate date) {
    Portfolio portfolio = this.getPortfolio(portfolioName);
    return portfolio.getPortfolioValueAtDate(date);
  }

  private void loadAllPortfolioFiles() {
    portfolioFiles.keySet().forEach(portfolioName -> {
      try {
        Path filepath = portfolioFiles.get(portfolioName);
        loadPortfolio(portfolioName, filepath.toString(), filepath);
      } catch (IllegalArgumentException e) {
        // throw new RuntimeException(e);
        System.out.println(portfolioName + "--- File not found -- Error message below!");
        System.out.println(e.getMessage());
      }
    });
  }
}
