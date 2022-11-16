import java.io.IOException;
import java.time.LocalDate;

public interface FlexiblePortfolioView extends PortfolioView {
  /**
   * Display portfolio creation menu for flexible portfolio.
   */
  void portfolioCreateMenu() throws IOException;

  /**
   * Display prompt asking for user to enter transaction date.
   */
  void transactionDatePrompt() throws IOException;

  /**
   * Display prompt asking for user to enter commission fee.
   */
  void commissionFeePrompt() throws IOException;

  /**
   * Display message if commission value is invalid.
   */
  void invalidCommissionValue() throws IOException;

  /**
   * Display message if transaction is successful for a portfolio.
   * @param pName name of portfolio
   * @param type type of transaction BUY/SELL
   * @param stockName name of the stock in the transaction
   * @param quantity quantity of stock
   * @param date date of transaction
   */
  void transactionSuccessMessage(String pName, TransactionType type, String stockName, float quantity, LocalDate date) throws IOException;

  /**
   * Display flexible portfolio's composition.
   * @param portfolioItems items present in flexible portfolio
   */
  void displayFlexiblePortfolio(PortfolioItem[] portfolioItems) throws IOException;

  /**
   * Display portfolio's cost basis until a given date.
   * @param pName given portfolio name
   * @param costBasis cost basis value of portfolio
   * @param date date until which cost basis
   */
  void displayCostBasis(String pName, float costBasis, String date) throws IOException;
}
