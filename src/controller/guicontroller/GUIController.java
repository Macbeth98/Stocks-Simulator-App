package controller.guicontroller;

import model.flexibleportfolio.FlexiblePortfolioList;
import view.guiview.CreatePFFrame;
import view.guiview.IView;

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
  public void viewPortfolioComposition(String portfolioName) {
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
    IView txnFrame = new
  }
}
