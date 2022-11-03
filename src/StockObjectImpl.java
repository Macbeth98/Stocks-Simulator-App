import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class StockObjectImpl implements StockObject {

  private final String ticker;

  private final float price;

  private final Map<String,Float> priceAtDate;

  private final String currentDirectory;
  private final String fileName;

  private String getDateString(Date date) {
    return new SimpleDateFormat("MM/dd/yyyy").format(date);
  }

  public StockObjectImpl(String ticker) {
    this.ticker = ticker;
    Random r = new Random();
    this.price = r.nextFloat();

    priceAtDate = new HashMap<>();
    currentDirectory = System.getProperty("user.dir") + "/stockPrices/";
    fileName = this.ticker + ".csv";

    try {
      this.loadPrices();
    } catch (IOException e) {
      throw new RuntimeException("Could not load Stock Prices.");
    }

  }

  private void loadPrices() throws IOException {
    File directory = new File(currentDirectory);

    if (!directory.exists()) {
      boolean directoryCreated = directory.mkdir();
      if (!directoryCreated) {
        throw new RuntimeException("Stock Prices Directory is not found and cannot be created.");
      }
    }

    File file = new File(currentDirectory + this.fileName);
    if(file.exists()){
      try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          String[] priceData = line.split(",");
          priceAtDate.put(priceData[0], Float.parseFloat(priceData[1]));
        }
      }
    }

    Date currentDate = new Date();
    String dateString = getDateString(currentDate);

    priceAtDate.put(dateString, this.price);
    try {
      this.writePriceToFile(dateString, price);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private void writePriceToFile(String dateString, float price) throws IOException {
    FileWriter fileWriter = new FileWriter(currentDirectory + fileName, true);
    BufferedWriter bw = new BufferedWriter(fileWriter);
    PrintWriter out = new PrintWriter(bw);

    out.println(dateString+","+price);
    out.flush();
    out.close();
  }

  @Override
  public String getTicker() {
    return this.ticker;
  }

  @Override
  public float getCurrentPrice() {
    return this.price;
  }

  @Override
  public float getCurrentPriceAtDate(Date date) {
    Random r = new Random();

    String dateString = getDateString(date);

    if(priceAtDate.containsKey(dateString)) {
      return priceAtDate.get(dateString);
    } else {
      float priceValue = r.nextFloat();
      priceAtDate.put(dateString, priceValue);

      try {
        this.writePriceToFile(dateString, priceValue);
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }

      return priceValue;
    }
  }

  @Override
  public float getCurrentValue(float quantity) {
    return quantity * this.price;
  }

  @Override
  public float getCurrentValueAtDate(Date date, float quantity) {
    return quantity * this.getCurrentPriceAtDate(date);
  }
}
