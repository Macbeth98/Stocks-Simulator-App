import controller.guicontroller.GUIController;
import model.flexibleportfolio.FPortfolioListWithStrategy;
import model.flexibleportfolio.FPortfolioListWithStrategyImpl;
import view.guiview.IView;
import view.guiview.MainFrameView;

/**
 * Class that initializes the GUI version of the portfolio application.
 */
public class GUIApplication {
  /**
   * This is the main method which will start the GUI version of the application.
   * This method will create the GUIController Object with a model object.
   *
   * @param args parameters that can be passed to the main method.
   */
  public static void main(String[] args) {
    FPortfolioListWithStrategy model = new FPortfolioListWithStrategyImpl();
    GUIController controller = new GUIController(model);
    IView view = new MainFrameView("Portfolio Application");
    controller.setView(view);
  }
}
