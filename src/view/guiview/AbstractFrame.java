package view.guiview;

import java.awt.*;

import javax.swing.*;

public abstract class AbstractFrame extends JFrame implements IView {

  public AbstractFrame(String caption) {
    super(caption);

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500, 500));

    this.setLayout(new FlowLayout());
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
