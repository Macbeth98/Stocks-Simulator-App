import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class PortfolioControllerImpl implements PortfolioController{

  final InputStream in;
  final PrintStream out;
  
  PortfolioControllerImpl(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void go(PortfolioList portfolioList) {
    // create model object

    // create view object -> view

    // view.menu()

    // Scanner scans input key

    /*
    while input_key != 5
      if input_key == 1
        view.portfolioNamePrompt
        scan.nextString - gets name
        view.stockInfoPrompt
     */
  }
}
