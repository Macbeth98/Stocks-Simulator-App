import controller.guicontroller.GUIController;
import model.flexibleportfolio.FlexiblePortfolioList;
import model.flexibleportfolio.FlexiblePortfolioListImpl;

import view.guiview.IView;
import view.guiview.MainFrameView;

public class GUIApplication {
  public static void main(String []args)
  {
    FlexiblePortfolioList model = new FlexiblePortfolioListImpl();
    GUIController controller = new GUIController(model);
    IView view = new MainFrameView("Portfolio Application");
    controller.setView(view);
  }
}
