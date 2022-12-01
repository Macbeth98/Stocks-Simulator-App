package view.guiview;

import javax.swing.*;

public abstract class AbstractFrame extends JFrame implements IView {

  public AbstractFrame(String caption) {
    super(caption);
  }

  @Override
  public void displaySuccessMessage(String successMessage) {
    JOptionPane.showMessageDialog(this, successMessage,
            "Portfolio Application", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage,
            "Portfolio Application", JOptionPane.ERROR_MESSAGE);
  }
}
