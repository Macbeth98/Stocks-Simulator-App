package model;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import fileinout.FileIO;
import fileinout.SaveToCSV;
import model.portfolio.Portfolio;
import model.portfolio.PortfolioItem;
import model.portfolio.PortfolioList;

/**
 * This is an Abstract PortfolioList impl that implements PortfolioList interface.
 * This represents the two types of portfolio lists available and abstracts them.
 */
public abstract class AbstractPortfolioListImpl implements PortfolioList {

  protected final Map<String, Path> portfolioFiles;
  protected final String currentDirectory;

  protected AbstractPortfolioListImpl(String directoryName) throws RuntimeException {
    this.currentDirectory = System.getProperty("user.dir") + directoryName;

    FileIO fileIO = new SaveToCSV();

    fileIO.checkDirectory(currentDirectory);

    portfolioFiles = fileIO.getFilesInDirectory(currentDirectory);
  }

  @Override
  public String[] getPortfolioListNames() {
    return portfolioFiles.keySet().toArray(new String[0]);
  }


  @Override
  abstract public Portfolio getPortfolio(String portfolioName) throws IllegalArgumentException;

  protected List<String> loadPortfolioData(String path) throws IllegalArgumentException {
    FileIO fileIO = new SaveToCSV();

    return fileIO.readData(path);
  }

  @Override
  public PortfolioItem[] getPortfolioComposition(String portfolioName)
          throws IllegalArgumentException {
    return this.getPortfolio(portfolioName).getPortfolioComposition();
  }

  @Override
  public String getPortfolioFilePath(String portfolioName) throws IllegalArgumentException {
    if (this.portfolioFiles.containsKey(portfolioName)) {
      return this.portfolioFiles.get(portfolioName).toString();
    } else {
      throw new IllegalArgumentException("Portfolio is not there.");
    }
  }

  protected void checkPortfolioNameAlreadyExists(String portfolioName) {
    if (portfolioFiles.containsKey(portfolioName)) {
      throw new IllegalArgumentException("A Portfolio with given name Already Exists.");
    }
  }

}
