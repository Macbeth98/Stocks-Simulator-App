import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This class implements the PortfolioList interface.
 * The class implement its method based on the files stored in System.
 */
public class PortfolioListImpl implements PortfolioList{

  private final Map<String, Path> portfolioFiles;
  private final String currentDirectory;

  public PortfolioListImpl () {
    this.currentDirectory = System.getProperty("user.dir") + "/portfolioCSVFiles/";
    this.portfolioFiles = new HashMap<>();

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

  @Override
  public Portfolio getPortfolio(String portfolioName) {
    // need to check how to read the file
    File file = new File(currentDirectory + portfolioFiles.get(portfolioName));
    return PortfolioImpl.getBuilder().AddStockToPortfolio(
            new StockObjectImpl("ticker"),
            34,
            123
    ).build();
  }
}
