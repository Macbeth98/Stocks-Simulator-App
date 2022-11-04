import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * This class implements the PortfolioList interface.
 * The class implement its method based on the files stored in System.
 */
public class PortfolioListImpl implements PortfolioList {

  private final Map<String, Path> portfolioFiles;
  private final Map<String, Portfolio> portfolios;

  /**
   * This method constructs a PortfolioImpl Object. This method fetches the existing portfolio files
   * and map them to the portfolio Names.
   *
   * @throws RuntimeException when the directory where the portfolios cannot be created.
   */
  public PortfolioListImpl() throws RuntimeException {
    String currentDirectory = System.getProperty("user.dir") + "/portfolioCSVFiles/";
    File directory = new File(currentDirectory);

    if (!directory.exists()) {
      boolean directoryCreated = directory.mkdir();
      if (!directoryCreated) {
        throw new RuntimeException("Directory is not found and cannot be created.");
      }
    }

    this.portfolioFiles = new HashMap<>();
    this.portfolios = new HashMap<>();

    try (Stream<Path> paths = Files.walk(Paths.get(currentDirectory))) {
      paths.filter(Files::isRegularFile)
              .forEach(file -> {
                String filePath = file.toString();
                String fileExt = filePath.substring(filePath.lastIndexOf(".") + 1);
                if ("csv".equalsIgnoreCase(fileExt)) {
                  portfolioFiles.put(file.getFileName().toString().split("_")[0], file);
                }
              });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String[] getPortfolioListNames() {
    return portfolioFiles.keySet().toArray(new String[0]);
  }

  private Portfolio loadPortfolio(String portfolioName, String path, Path filepath)
          throws FileNotFoundException {
    File file = new File(Paths.get(path).toString());

    Map<String, Float> stocksMap = new HashMap<>();

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
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
    }

    return this.createPortfolioImpl(portfolioName, stocksMap, filepath);
  }

  @Override
  public Portfolio getPortfolio(String portfolioName) throws FileNotFoundException {

    if (portfolios.containsKey(portfolioName)) {
      return portfolios.get(portfolioName);
    } else if (portfolioFiles.containsKey(portfolioName)) {
      Path filepath = portfolioFiles.get(portfolioName);
      return this.loadPortfolio(portfolioName, filepath.toString(), filepath);
    } else {
      throw new FileNotFoundException("The corresponding Portfolio File is not Found!");
    }
  }

  private Portfolio createPortfolioImpl(String portfolioName, Map<String, Float> stocksMap,
                                        Path filepath) throws FileNotFoundException {
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
      String pathToFile = portfolio.savePortfolioToFile();
      portfolioFiles.put(portfolioName, Paths.get(pathToFile));
      // portfolioFiles.put(portfolioName, Paths.get(portfolio.getPortfolioFilePath()));
    }

    return portfolio;
  }

  @Override
  public Portfolio createPortfolio(String portfolioName, Map<String, Float> stocksMap)
          throws FileNotFoundException {
    return this.createPortfolioImpl(portfolioName, stocksMap, null);
  }

  @Override
  public Portfolio createPortfolioFromFile(String portfolioName, String portfolioFilePath)
          throws FileNotFoundException {
    return loadPortfolio(portfolioName, portfolioFilePath, null);
  }

  private void loadAllPortfolioFiles() {
    portfolioFiles.keySet().forEach(portfolioName -> {
      try {
        Path filepath = portfolioFiles.get(portfolioName);
        loadPortfolio(portfolioName, filepath.toString(), filepath);
      } catch (FileNotFoundException e) {
        // throw new RuntimeException(e);
        System.out.println(portfolioName + "--- File not found -- Error message below!");
        System.out.println(e.getMessage());
      }
    });
  }
}
