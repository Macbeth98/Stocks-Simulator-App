package view;

import java.io.IOException;

public class GenericPortfolioViewImpl implements GenericPortfolioView {

  private final Appendable out;

  public GenericPortfolioViewImpl(Appendable out) {
    this.out = out;
  }

  @Override
  public void menuView() throws IOException {
    String menu = "\n\n-------------------------------" +
            "\nWelcome!\nWhat kind of portfolios do you want to work with? Press option key:\n\n"
            + "1. Flexible Portfolio\n"
            + "2. Inflexible Portfolio\n"
            + "3. Exit\n"
            + "-------------------------------\n\n";
    this.out.append(menu);
  }

  @Override
  public void invalidChoiceMessage() throws IOException {
    this.out.append("Please enter valid choice!\n");
  }

}
