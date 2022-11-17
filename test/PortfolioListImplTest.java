import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioList;
import model.portfolio.PortfolioListImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * This class is a test class for PortfolioListImpl.
 * This class contains methods which tests the methods and properties of PortfolioListImpl.
 */
public class PortfolioListImplTest {

  private PortfolioList portfolioList;

  @Before
  public void setUp() {
    portfolioList = new PortfolioListImpl();
  }

  @Test
  public void testGetPortfolioFileNames() {

    String[] portfolioNames = portfolioList.getPortfolioListNames();

    List<String> fileNames = new ArrayList<>(List.of(portfolioNames));

    String currentDirectory = System.getProperty("user.dir") + "/portfolioCSVFiles/";
    try (Stream<Path> paths = Files.walk(Paths.get(currentDirectory))) {
      paths.filter(Files::isRegularFile)
              .forEach(file -> {
                String filePath = file.toString();
                String fileExt = filePath.substring(filePath.lastIndexOf(".") + 1);
                if ("csv".equalsIgnoreCase(fileExt)) {
                  assertTrue(fileNames.contains(file.getFileName().toString().split("_")[0]));
                }
              });
    } catch (IOException e) {
      fail("The directory should have been there!");
    }
  }

  @Test
  public void testGetPortfolio() throws FileNotFoundException {
    String[] portfolioNames = portfolioList.getPortfolioListNames();

    for (String portfolioName : portfolioNames) {
      Portfolio portfolio = portfolioList.getPortfolio(portfolioName);
      assertEquals(portfolioName, portfolio.getPortfolioName());
    }
  }

  @Test(expected = FileNotFoundException.class)
  public void testGetPortfolioInvalidName() throws FileNotFoundException {
    Portfolio portfolio = portfolioList.getPortfolio("randomFileName12345678");
  }

  @Test
  public void testCreatePortfolio() throws FileNotFoundException {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int charactersLength = characters.length();

    String portfolioName = "test123";
    Map<String, Float> stocksMap = new HashMap<>();

    Random r = new Random();

    int stocksCount = Utils.getNextRandomInteger(r, 1, 100);

    while (stocksCount > 0) {
      int tickerLength = Utils.getNextRandomInteger(r, 3, 5);
      StringBuilder ticker = new StringBuilder();
      while (tickerLength > 0) {
        ticker.append(characters.charAt(
                Utils.getNextRandomInteger(r, 0, charactersLength - 1)
        ));
        tickerLength--;
      }

      float quantity = (float) Utils.getNextRandomInteger(r, 1, 1000)
              + ((float) (Utils.getNextRandomInteger(r, 0, 1000)) / 1000);
      stocksMap.put(ticker.toString(), quantity);
      stocksCount--;
    }

    String portfolioFilePath = portfolioList.createPortfolio(portfolioName, stocksMap);

    Portfolio portfolio = portfolioList.getPortfolio(portfolioName);

    assertEquals(portfolioName, portfolio.getPortfolioName());
    assertEquals(portfolioFilePath, portfolio.getPortfolioFilePath());

    String[] portfolioNames = portfolioList.getPortfolioListNames();

    List<String> portfolioNamesList = new ArrayList<>(List.of(portfolioNames));

    assertTrue(portfolioNamesList.contains(portfolioName));

    assertEquals(portfolio, portfolioList.getPortfolio(portfolioName));
  }

  @Test
  public void testCreatePortfolioFromFile() throws FileNotFoundException {
    Portfolio oldPortfolio = portfolioList.getPortfolio(portfolioList.getPortfolioListNames()[0]);
    String path = oldPortfolio.getPortfolioFilePath();

    String portfolioName = "NewTest5";
    String portfolioFilePath = portfolioList.createPortfolioFromFile(portfolioName, path);

    String[] portfolioNames = portfolioList.getPortfolioListNames();

    List<String> portfolioNamesList = new ArrayList<>(List.of(portfolioNames));

    assertTrue(portfolioNamesList.contains(portfolioName));

    Portfolio portfolio = portfolioList.getPortfolio(portfolioName);

    assertEquals(portfolioName, portfolio.getPortfolioName());
    assertEquals(portfolioFilePath, portfolio.getPortfolioFilePath());
  }

  @Test(expected = FileNotFoundException.class)
  public void testCreatePortfolioFromFileNotFound() throws FileNotFoundException {
    String portfolioFilePath = portfolioList.createPortfolioFromFile("NoFileset",
            "Users/portfolioCSVFiles/out.csv");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePortfolioFromInvalidFileContentFormat() throws FileNotFoundException {
    String currentDirectory = System.getProperty("user.dir") + "/portfolioCSVFiles/";
    String portfolioFileName = "invalidContentFormat.csv";
    File outputFile = new File(currentDirectory + portfolioFileName);
    FileOutputStream fileOut = new FileOutputStream(outputFile);
    PrintStream out = new PrintStream(fileOut);

    out.println("24.87,GOOG,1.908,56.78");

    String path = outputFile.getAbsolutePath();
    String portfolioFilePath = portfolioList.createPortfolioFromFile("InvalidTest", path);
  }

}