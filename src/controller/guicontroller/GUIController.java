package controller.guicontroller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.flexibleportfolio.FlexiblePortfolioList;
import model.portfolio.PortfolioItem;
import view.guiview.CostBasisForm;
import view.guiview.CreatePFFrame;
import view.guiview.IView;
import view.guiview.TransactionFrame;
import view.guiview.viewCompositionForm;
import view.guiview.viewPortfolioValueForm;

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
  public void viewTransactionForm() {
     IView txnFrame = new TransactionFrame();
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
      IView cbFrame = new viewPortfolioValueForm(pNames, portfolioName,
              dateString, String.valueOf(costBasis));
      this.setView(cbFrame);
    }
    else {
      IView valueFrame = new CostBasisForm(pNames, "", "", "");
      this.setView(valueFrame);
    }
  }
}
