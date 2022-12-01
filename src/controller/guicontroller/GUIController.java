package controller.guicontroller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import model.TransactionType;
import model.flexibleportfolio.FPortfolioListWithStrategy;
import model.portfolio.PortfolioItem;
import view.guiview.CostBasisForm;
import view.guiview.CreatePFFrame;
import view.guiview.CreatePortfolioFromFileFrame;
import view.guiview.IView;
import view.guiview.ModifyPFFrame;
import view.guiview.OneTimeStrategyFrame;
import view.guiview.PeriodicInvestmentStrategyFrame;
import view.guiview.TransactionFrame;
import view.guiview.ViewCompositionForm;
import view.guiview.ViewPortfolioValueForm;

/**
 * Class consisting implementation for the controller that takes inputs from the GUI.
 */
public class GUIController implements Features {

  private final FPortfolioListWithStrategy model;

  private IView view;

  /**
   * Constructs a controller object with a given model object.
   *
   * @param m model object of FPortfolioListWithStrategy interface type
   */
  public GUIController(FPortfolioListWithStrategy m) {
    model = m;
  }

  /**
   * Sets view object for the controller object to use.
   *
   * @param v view object of IView interface type
   */
  public void setView(IView v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }

  private LocalDate getLocalDate(String txnDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
    return LocalDate.parse(txnDate, formatter);
  }

  @Override
  public void viewPortfolioComposition() {
    String[] pNames = model.getPortfolioListNames();
    IView compositionFrame = new ViewCompositionForm(pNames, "");
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
  public void setViewApplyOneTimeStrategyToPortfolio() {
    String[] portfolioNames = model.getPortfolioListNames();
    IView oneTimeStrategyFrame = new OneTimeStrategyFrame(portfolioNames);
    this.setView(oneTimeStrategyFrame);
  }

  @Override
  public void applyOneTimeStrategyToPortfolio(String portfolioName, float amount, float commission,
                                              String date, Map<String, Float> stocksDist) {
    LocalDate localDate = this.getLocalDate(date);
    model.applyStrategyToAnExistingPortfolio(
            portfolioName, amount, localDate, stocksDist, commission
    );
  }

  @Override
  public void setViewPeriodicInvestmentStrategy() {
    String[] portfolioNames = model.getPortfolioListNames();
    this.setView(new PeriodicInvestmentStrategyFrame(portfolioNames));
  }

  @Override
  public void periodicInvestmentStrategyToPortfolio() {

  }

  @Override
  public void modifyPortfolio() {
    String[] pNames = model.getPortfolioListNames();
    IView modifyFrame = new ModifyPFFrame(pNames);
    this.setView(modifyFrame);
  }

  @Override
  public void createPortfolioFromFile() {
    IView portfolioFromFileFrame = new CreatePortfolioFromFileFrame();
    this.setView(portfolioFromFileFrame);
  }

  @Override
  public String setCreatePortfolioFromFile(String portfolioName, String filepath) {
    return model.createPortfolioFromFile(portfolioName, filepath);
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
    String[] pNames = model.getPortfolioListNames();
    try {
      PortfolioItem[] portfolioItems = model.getPortfolioCompositionAtDate(portfolioName, date);
      StringBuilder s = new StringBuilder();
      for (PortfolioItem portfolioItem : portfolioItems) {
        s.append(portfolioItem.compositionString()).append("\n");
      }
      IView compositionFrame = new ViewCompositionForm(pNames, s.toString());
      this.setView(compositionFrame);
    } catch (Exception e) {
      IView compositionFrame = new ViewCompositionForm(pNames, "");
      this.setView(compositionFrame);
      this.view.displayErrorMessage("Error While Getting Composition: " + e.getMessage());
    }
  }

  @Override
  public void viewPortfolioValueAtDate(String portfolioName, String dateString) {
    String[] pNames = model.getPortfolioListNames();
    if (dateString.length() > 1 && portfolioName.length() > 1) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
      LocalDate date = LocalDate.parse(dateString, formatter);
      try {
        float value = model.getPortfolioValueAtDate(portfolioName, date);
        IView valueFrame = new ViewPortfolioValueForm(pNames, portfolioName,
                dateString, String.valueOf(value));
        this.setView(valueFrame);
      } catch (Exception e) {
        IView valueFrame = new ViewPortfolioValueForm(pNames, "",
                "", "");
        this.setView(valueFrame);
        this.view.displayErrorMessage("Error While Getting Value: " + e.getMessage());
      }
    } else {
      IView valueFrame = new ViewPortfolioValueForm(pNames, "", "", "");
      this.setView(valueFrame);
    }
  }

  @Override
  public void viewCostBasis(String portfolioName, String dateString) {
    String[] pNames = model.getPortfolioListNames();
    if (dateString.length() > 1 && portfolioName.length() > 1) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
      LocalDate date = LocalDate.parse(dateString, formatter);
      try {
        float costBasis = model.getCostBasis(portfolioName, date);
        IView cbFrame = new CostBasisForm(pNames, portfolioName,
                dateString, String.valueOf(costBasis));
        this.setView(cbFrame);
      } catch (Exception e) {
        IView valueFrame = new CostBasisForm(pNames, "",
                "", "");
        this.setView(valueFrame);
        this.view.displayErrorMessage("Error While Getting Cost Basis: " + e.getMessage());
      }
    } else {
      IView valueFrame = new CostBasisForm(pNames, "", "", "");
      this.setView(valueFrame);
    }
  }

  @Override
  public void addTransactionToPortfolio(String pName, TransactionType txnType, String ticker,
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
