import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class PortfolioImplTest {

  @Test
  public void savePortfolioToFile () throws FileNotFoundException {
    File outputFile = new File("output.csv");
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
  }

}