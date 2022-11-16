import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AlphaVantageSource implements DataSource {

  private final String apiKey;
  private final String pricesDirectory;

  public AlphaVantageSource() {
    apiKey = "6ZFNAYRHG2K7KINU";
    pricesDirectory = System.getProperty("user.dir") + "/stockPrices/";
    File directory = new File(this.pricesDirectory);

    if (!directory.exists()) {
      boolean directoryCreated = directory.mkdir();
      if (!directoryCreated) {
        throw new RuntimeException("Stock Prices Directory is not found and cannot be created.");
      }
    }
  }

  @Override
  public float getPriceAtDate(String ticker, LocalDate date) {

    // convert date to string
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String strDate = formatter.format(date);

    // load price from file if it exists
    try {
      if (priceExistsOnFile(ticker, strDate)) {
        // return price from file
        return loadPriceFromFile(ticker, strDate);
      }
    } catch (FileNotFoundException ignored) {
    }

    URL url;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + this.apiKey + "&datatype=json");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the AlphaVantage API has either changed or "
              + "no longer works");
    }

    InputStream in;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + ticker);
    }

    String jsonString = output.toString();
    JSONObject jsonObj = new JSONObject(jsonString);
    String priceOnDate = "";
    try {
      priceOnDate = jsonObj.getJSONObject("Time Series (Daily)")
              .getJSONObject(strDate)
              .getString("4. close");
    }
    catch (Exception e) {
      throw new IllegalArgumentException("Stock " + ticker + " value not found for date: "
              + strDate);
    }


    float price = Float.parseFloat(priceOnDate);

    // caching
    try {
      storePriceOnFile(ticker, price, strDate);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("STOCK PRICE SAVE TO FILE FAILED!" + e.getMessage());
    }

    return price;
  }

  private float loadPriceFromFile(String ticker, String strDate) {
    String filePath = pricesDirectory + ticker + ".json";
    File file = new File(filePath);
    StringBuilder pricesJson = new StringBuilder();
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        pricesJson.append(line);
      }
    } catch (FileNotFoundException ignored) {
    }
    JSONObject jsonObj = new JSONObject(pricesJson.toString());
    return jsonObj.getFloat(strDate);
  }

  private boolean priceExistsOnFile(String ticker, String strDate) throws FileNotFoundException {
    String filePath = pricesDirectory + ticker + ".json";
    File file = new File(filePath);
    StringBuilder pricesJson = new StringBuilder();
    if (file.exists()) {
      try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          pricesJson.append(line);
        }
      }
      JSONObject jsonObj = new JSONObject(pricesJson.toString());
      return jsonObj.has(strDate);
    } else {
      return false;
    }
  }

  private void storePriceOnFile(String ticker, float price, String strDate)
          throws FileNotFoundException {
    String filePath = pricesDirectory + ticker + ".json";
    File file = new File(filePath);
    StringBuilder pricesJson = new StringBuilder();
    if (file.exists()) {
      try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          pricesJson.append(line);
        }
      }
      JSONObject jsonObj = new JSONObject(pricesJson.toString());
      if (!jsonObj.has(strDate)) {
        jsonObj.put(strDate, price);
        try {
          writeJsonToFile(filePath, jsonObj.toString());
        } catch (IOException ignored) {
        }
      }
    } else {
      JSONObject jsonObj = new JSONObject();
      jsonObj.put(strDate, price);
      try {
        writeJsonToFile(filePath, jsonObj.toString());
      } catch (IOException ignored) {
      }
    }

  }

  private void writeJsonToFile(String filePath, String jsonString) throws IOException {
    FileWriter fileWriter = new FileWriter(filePath, false);
    fileWriter.write(jsonString);
    fileWriter.flush();
    fileWriter.close();
  }
}
