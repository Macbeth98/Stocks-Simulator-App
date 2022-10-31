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
public class PortfolioListImpl implements PortfolioList{

  private final Map<String, Path> portfolioFiles;
  private final Map<String, Portfolio> portfolios;

  public PortfolioListImpl () throws RuntimeException {
    String currentDirectory = System.getProperty("user.dir") + "/portfolioCSVFiles/";

    this.portfolioFiles = new HashMap<>();
    this.portfolios = new HashMap<>();

    try (Stream<Path> paths = Files.walk(Paths.get(currentDirectory))) {
      paths.filter(Files::isRegularFile)
              .forEach(file -> {
                  portfolioFiles.put(file.getFileName().toString().split("_")[0], file);
              });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String[] getPortfolioListNames() {
    return portfolioFiles.keySet().toArray(new String[0]);
  }

  private Portfolio loadPortfolio (String portfolioName) throws FileNotFoundException {
    File file = new File(portfolioFiles.get(portfolioName).toUri());

    PortfolioImpl.PortfolioBuilder portfolioBuilder = PortfolioImpl.getBuilder();

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        System.out.println(line);
        String[] portfolioItemValues = line.split(",");
        portfolioBuilder = portfolioBuilder.AddStockToPortfolio(
                new StockObjectImpl(portfolioItemValues[0]),
                Float.parseFloat(portfolioItemValues[1]),
                Float.parseFloat(portfolioItemValues[2])
        );
      }
    }

    return portfolioBuilder.build();
  }

  @Override
  public Portfolio getPortfolio(String portfolioName) throws FileNotFoundException {

    if(portfolios.containsKey(portfolioName)) {
      return portfolios.get(portfolioName);
    } else {
      return this.loadPortfolio(portfolioName);
    }
  }

  private void loadAllPortfolioFiles() {
    portfolioFiles.keySet().forEach(portfolioName -> {
      try {
        loadPortfolio(portfolioName);
      } catch (FileNotFoundException e) {
        // throw new RuntimeException(e);
        System.out.println(portfolioName + "--- File not found -- Error message below!");
        System.out.println(e.getMessage());
      }
    });
  }
}