package view.guiview;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import controller.guicontroller.Features;

/**
 * Class that defines the frame used by GUI to create a portfolio.
 */
public class CreatePFFrame extends AbstractFrame {

  private final JButton backButton;
  private final JButton submitButton;

  private final JTextField pNameInput;

  /**
   * Constructs the frame containing the form for creating a portfolio.
   */
  public CreatePFFrame() {
    super("Create Portfolio Menu");

    JPanel formPanel = new JPanel(new GridLayout(10, 1));

    JLabel display = new JLabel("Create a new portfolio");
    formPanel.add(display);

    display = new JLabel("Enter portfolio name:");
    formPanel.add(display);

    // enter portfolio name field
    pNameInput = new JTextField();
    formPanel.add(pNameInput);

    // submit button
    submitButton = new JButton("Submit");
    submitButton.setActionCommand("Submit");
    formPanel.add(submitButton);

    // exit button
    backButton = new JButton("Back");
    backButton.setActionCommand("Back");
    formPanel.add(backButton);

    this.add(formPanel);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    submitButton.addActionListener(evt -> {
      if (pNameInput.getText().length() == 0) {
        displayErrorMessage("Portfolio Name Not Entered!");
      } else {
        try {
          features.createEmptyPortfolio(pNameInput.getText());
          this.setVisible(false);
          features.viewTransactionForm(pNameInput.getText());
        } catch (Exception e) {
          displayErrorMessage("Portfolio Creation Error: "
                  + e.getMessage());
          this.setVisible(false);
        }
      }
    });
    backButton.addActionListener(evt -> this.setVisible(false));
  }
}
