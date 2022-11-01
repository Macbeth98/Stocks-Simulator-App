import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class PortfolioImplTest {

  @Test
  public void savePortfolioToFile() throws FileNotFoundException {
    String currentDirectory = System.getProperty("user.dir") + "/portfolioCSVFiles/";
    System.out.println(currentDirectory);
    File outputFile = new File(currentDirectory + "output_" + new Date().getTime() + ".csv");
    FileOutputStream fileOut = new FileOutputStream(outputFile);
    PrintStream out = new PrintStream(fileOut);

    int i = 10;

    while (i > 0) {
      String portfolioItem = "TESLA" + ","
              + i * 2 + ","
              + i * 12 + ","
              + (i * 2 * i * 12);

      out.println(portfolioItem);
      i--;
    }

    assertTrue(outputFile.exists());

    System.out.println("Getting the list of Files...!");

    try (Stream<Path> paths = Files.walk(Paths.get(currentDirectory))) {
      paths
              .filter(Files::isRegularFile)
              .forEach(file -> System.out.println(file + "_____" + file.getFileName().toString().split("_")[0]));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  public void readPortfolioFile() throws FileNotFoundException {
    String currentDirectory = System.getProperty("user.dir") + "/portfolioCSVFiles/";
    try (Scanner scanner = new Scanner(new File(currentDirectory + "output.csv"))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        System.out.println(line);
      }
    }

  }

}