package model;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

import static java.nio.file.Files.readString;

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
    } catch (IOException ignored) {
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
    if (jsonString.contains("Error Message")) {
      throw new IllegalArgumentException("Stock ticker: " + ticker + " is invalid!");
    }
    JSONObject jsonObj = new JSONObject(jsonString);
    String priceOnDate = "";
    while (priceOnDate.length() == 0) {
      try {
        priceOnDate = jsonObj.getJSONObject("Time Series (Daily)")
                .getJSONObject(strDate)
                .getString("4. close");
      } catch (Exception ignored) {
        date = date.minusDays(1);
        strDate = formatter.format(date);
      }
    }

    float price = Float.parseFloat(priceOnDate);

    // caching entire response
    try {
      storePriceOnFile(ticker, jsonString);
    } catch (Exception e) {
      throw new RuntimeException("STOCK PRICE SAVE TO FILE FAILED!" + e.getMessage());
    }

    return price;
  }

  private float loadPriceFromFile(String ticker, String strDate) throws IOException {
    String filePath = pricesDirectory + ticker + ".json";
    File file = new File(filePath);
    String pricesJson;
    Path pricesFilePath = Path.of(filePath);
    pricesJson = readString(pricesFilePath);
    JSONObject jsonObj = new JSONObject(pricesJson);

    String priceOnDate = "";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(strDate, formatter);
    while (priceOnDate.length() == 0) {
      try {
        priceOnDate = jsonObj.getJSONObject(strDate)
                .getString("4. close");
      } catch (Exception ignored) {
        date = date.minusDays(1);
        strDate = formatter.format(date);
      }
    }
    return Float.parseFloat(priceOnDate);
  }

  private boolean priceExistsOnFile(String ticker, String strDate) throws IOException {
    String filePath = pricesDirectory + ticker + ".json";
    File file = new File(filePath);
    return file.exists();
  }

  private void storePriceOnFile(String ticker, String jsonResponseString)
          throws IOException {
    String filePath = pricesDirectory + ticker + ".json";
    try {
      File file = new File(filePath);
    } catch (Exception e) {
      throw new FileNotFoundException("STOCK FILE NOT FOUND AT: " + filePath + ". Error: " + e);
    }
    try {
      JSONObject jsonObj = new JSONObject(jsonResponseString);
      JSONObject jsonDataObject = jsonObj.getJSONObject("Time Series (Daily)");
      writeJsonToFile(filePath, jsonDataObject.toString());
    } catch (IOException e) {
      throw new IOException("Writing JSON response to file failed. Error: " + e);
    }

  }

  private void writeJsonToFile(String filePath, String jsonString) throws IOException {
    FileWriter fileWriter = new FileWriter(filePath, false);
    fileWriter.write(jsonString);
    fileWriter.flush();
    fileWriter.close();
  }
}
