import controller.guicontroller.GUIController;
import model.flexibleportfolio.FPortfolioListWithStrategy;
import model.flexibleportfolio.FPortfolioListWithStrategyImpl;

import view.guiview.IView;
import view.guiview.MainFrameView;

public class GUIApplication {
  public static void main(String []args)
  {
    FPortfolioListWithStrategy model = new FPortfolioListWithStrategyImpl();
    GUIController controller = new GUIController(model);
    IView view = new MainFrameView("Portfolio Application");
    controller.setView(view);
  }
}
