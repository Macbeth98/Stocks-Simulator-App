package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

abstract class AbstractPortfolioListImpl implements PortfolioList {

  protected final Map<String, Path> portfolioFiles;
  protected final String currentDirectory;

  protected AbstractPortfolioListImpl(String directoryName) throws RuntimeException {
    this.currentDirectory = System.getProperty("user.dir") + directoryName;

    File directory = new File(currentDirectory);

    if (!directory.exists()) {
      boolean directoryCreated = directory.mkdir();
      if (!directoryCreated) {
        throw new RuntimeException("Directory is not found and cannot be created.");
      }
    }

    portfolioFiles = new HashMap<>();

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


  @Override
  abstract public Portfolio getPortfolio(String portfolioName) throws IllegalArgumentException ;

  protected List<String> loadPortfolioData(String path) throws IllegalArgumentException {
    File file = new File(Paths.get(path).toString());

    List<String> portfolioLines = new ArrayList<>();

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        portfolioLines.add(line);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found!");
    }

    return portfolioLines;
  }

  @Override
  public PortfolioItem[] getPortfolioComposition(String portfolioName)
          throws IllegalArgumentException {
    return this.getPortfolio(portfolioName).getPortfolioComposition();
  }

  @Override
  public String getPortfolioFilePath(String portfolioName) throws IllegalArgumentException {
    if(this.portfolioFiles.containsKey(portfolioName)) {
      return this.portfolioFiles.get(portfolioName).toString();
    } else {
      throw new IllegalArgumentException("Portfolio is not there.");
    }
  }

}
