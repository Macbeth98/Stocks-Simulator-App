import java.io.IOException;
import java.util.Scanner;

public class GetFlexiblePortfolioPerformanceGraph implements FlexiblePortfolioControllerCommand {

  private final FlexiblePortfolioList fpList;
  private final FlexiblePortfolioView view;

  public GetFlexiblePortfolioPerformanceGraph(FlexiblePortfolioList fpList,
                                              FlexiblePortfolioView view) {
    this.fpList = fpList;
    this.view = view;
  }


  @Override
  public void go(Scanner scan) throws IOException {
  }
}
