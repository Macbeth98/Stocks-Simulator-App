package view.guiview;

import controller.guicontroller.Features;

/**
 * Interface representing the methods for rendering on the GUI.
 */
public interface IView {

//  /**
//   * Method to output the composition of the portfolio on the screen.
//   * @param s String containing portfolio composition
//   */
//  void showPortfolioComposition(String s);

  /**
   * Method to add the features of the buttons on the view.
   * @param features features present on the gui
   */
  void addFeatures(Features features);
}
