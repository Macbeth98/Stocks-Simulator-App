package view.guiview;

import controller.guicontroller.Features;

/**
 * Interface representing the methods for rendering on the GUI.
 */
public interface IView {

  /**
   * Method to add the features of the buttons on the view.
   *
   * @param features features present on the gui
   */
  void addFeatures(Features features);

  /**
   * Method to output success messages on the screen.
   *
   * @param successMessage message to be displayed as string
   */
  void displaySuccessMessage(String successMessage);

  /**
   * Method to output error messages on the screen.
   *
   * @param errorMessage message to be displayed
   */
  void displayErrorMessage(String errorMessage);
}
