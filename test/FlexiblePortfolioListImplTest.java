import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;

import fileinout.FileIO;
import fileinout.SaveToCSV;
import model.flexibleportfolio.FlexiblePortfolio;
import model.flexibleportfolio.FlexiblePortfolioList;
import model.flexibleportfolio.FlexiblePortfolioListImpl;
import model.portfolio.PortfolioItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is a test class for FlexiblePortfolioListImpl class.
 * This class contains methods that test FlexiblePortfolioListImpl methods.
 */
public class FlexiblePortfolioListImplTest {

  FlexiblePortfolioList portfolioList;

  @Before
  public void setUp() {
    portfolioList = new FlexiblePortfolioListImpl();
  }

  private Map<String, Path> getFileNames () {
    FileIO fileIO = new SaveToCSV();
    String directory = System.getProperty("user.dir") + "/portfolioTxnFiles/";

    return fileIO.getFilesInDirectory(directory);
  }

  @Test
  public void testFlexiblePortfolioCreation() {
    Map<String, Path> fileData = getFileNames();
    String[]  names = portfolioList.getPortfolioListNames();

    for (String name : names) {
      assertEquals(fileData.get(name).toString(), portfolioList.getPortfolioFilePath(name));
    }
  }

  @Test
  public void testGetAvailablePortfolioNames() {

    String[] names = portfolioList.getPortfolioListNames();

    String[] fetchedNames = getFileNames().keySet().toArray(new String[0]);

    assertEquals(names.length, fetchedNames.length);

    for (String name : names) {
      assertTrue(Arrays.asList(fetchedNames).contains(name));
    }
  }

  @Test
  public void testCreatePortfolioManual() {
    String portfolioName = "testflexportmanual";
    String filePath = portfolioList.createPortfolio(portfolioName, null);

    Map<String, Path> fileData = getFileNames();

    assertEquals(filePath, fileData.get(portfolioName).toString());

    FlexiblePortfolio portfolio = portfolioList.getPortfolio(portfolioName);
    assertEquals(portfolioName, portfolio.getPortfolioName());
    assertEquals(filePath, portfolio.getPortfolioFilePath());
  }

  @Test
  public void testCreatePortfolioFromFile() {
    String portfolioName = "testflexportfile";
    String filepath = getFileNames().get("sample").toString();

    String newFilePath = portfolioList.createPortfolioFromFile(portfolioName, filepath);

    FlexiblePortfolio portfolioGiven = portfolioList.getPortfolio("sample");
    FlexiblePortfolio portfolioCreated = portfolioList.getPortfolio(portfolioName);

    assertEquals(newFilePath, portfolioCreated.getPortfolioFilePath());

    PortfolioItem[] givenItems = portfolioGiven.getPortfolioComposition();
    PortfolioItem[] createdItems = portfolioCreated.getPortfolioComposition();

    assertEquals(givenItems.length, createdItems.length);

    for (int i = 0; i < givenItems.length; i++) {
      assertEquals(givenItems[i].toString(), createdItems[i].toString());
    }
  }

}