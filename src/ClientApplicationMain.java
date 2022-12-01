import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import controller.GenericPortfolioControllerImpl;
import controller.guicontroller.GUIController;
import model.GenericPortfolioListImpl;
import model.flexibleportfolio.FPortfolioListWithStrategy;
import model.flexibleportfolio.FPortfolioListWithStrategyImpl;
import view.guiview.IView;
import view.guiview.MainFrameView;

/**
 * Class that initializes the full-fledged command line version of the portfolio application.
 */
public class ClientApplicationMain {
  /**
   * This is the main method which will start the full application.
   * This method will create the appropriate controller object for either type of user
   * interface selected, i.e, text based or gui.
   *
   * @param args parameter that is passed. Expects a single argument, either 'gui' or 'text'.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("No Command Line Argument passed! "
              + "\nPlease pass either 'gui' or 'text' for the user interface that you need!");
    }
    if (Objects.equals(args[0], "gui")) {
      FPortfolioListWithStrategy model = new FPortfolioListWithStrategyImpl();
      GUIController controller = new GUIController(model);
      IView view = new MainFrameView("Portfolio Application");
      controller.setView(view);
    }
    else if (Objects.equals(args[0], "text")) {
      try {
        new GenericPortfolioControllerImpl(new InputStreamReader(System.in), System.out)
                .goGenericController(new GenericPortfolioListImpl());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    else {
      throw new IllegalArgumentException("Invalid Command Line Argument passed! "
              + "\nPlease pass either 'gui' or 'text' for the user interface that you need!");
    }

  }
}
