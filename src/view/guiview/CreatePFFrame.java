package view.guiview;

import java.awt.*;

import javax.swing.*;

import controller.guicontroller.Features;


public class CreatePFFrame extends AbstractFrame {

  private final JButton backButton;
  private final JButton submitButton;

  private final JTextField pNameInput;

  public CreatePFFrame() {
    super("Create Portfolio Menu");

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500, 500));

    this.setLayout(new FlowLayout());

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
