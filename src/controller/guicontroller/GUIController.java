package controller.guicontroller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import model.TransactionType;
import model.flexibleportfolio.FlexiblePortfolioList;
import model.portfolio.PortfolioItem;
import view.guiview.*;

public class GUIController implements Features {

  private FlexiblePortfolioList model;

  private IView view;

  public GUIController(FlexiblePortfolioList m) {
    model = m;
  }

  public void setView(IView v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }

  private void showDialogBox(JFrame frame, Object message, String title, int messageType) {
    JOptionPane.showMessageDialog(frame, message, title, messageType);
  }

  @Override
  public void viewPortfolioComposition() {
    String[] pNames = model.getPortfolioListNames();
    IView compositionFrame = new viewCompositionForm(pNames, "");
    this.setView(compositionFrame);
    // model.getPortfolioCompositionAtDate()
    // view.showPortfolioComposition(portfolioName);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void createPortfolio() {
    IView pfFrame = new CreatePFFrame();
    this.setView(pfFrame);
  }

  @Override
  public void createEmptyPortfolio(String pName) {
    try {
      String filePath = model.createPortfolio(pName, null);
      this.view.displaySuccessMessage("Portfolio: "
              + pName
              + " successfully created! It is stored at: "
              + filePath
      );
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  @Override
  public void createPortfolioFromFile() {
    IView portfolioFromFileFrame = new CreatePortfolioFromFileFrame();
    this.setView(portfolioFromFileFrame);
  }

  @Override
  public void setCreatePortfolioFromFile(JFrame frame, String portfolioName, String filepath) {
    if(portfolioName.length() == 0) {
      this.showDialogBox(frame, "Portfolio Name is not given", "Load Portfolio",
              JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {

      String createdFilepath = model.createPortfolioFromFile(portfolioName, filepath);
      this.showDialogBox(null, "Portfolio: " + portfolioName
                      + ". Successfully Created.\n" + "It is stored at: "+createdFilepath,
              "Portfolio Created!", JOptionPane.INFORMATION_MESSAGE);
      frame.setVisible(false);

    } catch (IllegalArgumentException e) {
      this.showDialogBox(frame, e.getMessage(), "Load Portfolio", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void viewTransactionForm(String portfolioName) {
     IView txnFrame = new TransactionFrame(portfolioName);
     this.setView(txnFrame);
  }

  @Override
  public void setViewPortfolioComposition(String portfolioName, String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
    LocalDate date = LocalDate.parse(dateString, formatter);
    PortfolioItem[] portfolioItems = model.getPortfolioCompositionAtDate(portfolioName, date);
    StringBuilder s = new StringBuilder();
    for (PortfolioItem portfolioItem : portfolioItems) {
      s.append(portfolioItem.compositionString()).append("\n");
    }
    String[] pNames = model.getPortfolioListNames();
    IView compositionFrame = new viewCompositionForm(pNames, s.toString());
    this.setView(compositionFrame);
  }

  @Override
  public void viewPortfolioValueAtDate(String portfolioName, String dateString) {
    String[] pNames = model.getPortfolioListNames();
    if (dateString.length() > 1 && portfolioName.length() > 1) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
      LocalDate date = LocalDate.parse(dateString, formatter);
      float value = model.getPortfolioValueAtDate(portfolioName, date);
      IView valueFrame = new viewPortfolioValueForm(pNames, portfolioName,
              dateString, String.valueOf(value));
      this.setView(valueFrame);
    }
    else {
      IView valueFrame = new viewPortfolioValueForm(pNames, "", "", "");
      this.setView(valueFrame);
    }
  }

  @Override
  public void viewCostBasis(String portfolioName, String dateString) {
    String[] pNames = model.getPortfolioListNames();
    if (dateString.length() > 1 && portfolioName.length() > 1) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
      LocalDate date = LocalDate.parse(dateString, formatter);
      float costBasis = model.getCostBasis(portfolioName, date);
      IView cbFrame = new CostBasisForm(pNames, portfolioName,
              dateString, String.valueOf(costBasis));
      this.setView(cbFrame);
    }
    else {
      IView valueFrame = new CostBasisForm(pNames, "", "", "");
      this.setView(valueFrame);
    }
  }

  @Override
  public void AddTransactionToPortfolio(String pName, TransactionType txnType, String ticker,
                                        float quantity, String txnDate, float commission) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
      LocalDate date = LocalDate.parse(txnDate, formatter);
      model.addTransactionToPortfolio(pName, txnType, ticker, quantity, date, commission);
      this.view.displaySuccessMessage("Successfully Executed: "
              + txnType
              + " transaction for "
              + (int) quantity
              + " no. of stocks of "
              + ticker
              + " on Date: "
              + txnDate
      );
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage("Error While Executing: "
              + txnType
              + " transaction: "
              + e.getMessage()
      );
    }
  }

}
