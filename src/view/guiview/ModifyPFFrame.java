package view.guiview;

import java.awt.*;

import javax.swing.*;

import controller.guicontroller.Features;

/**
 * Class that contains the frame which allows user to modify a specific portfolio.
 */
public class ModifyPFFrame extends AbstractFrame {

  /**
   * Constructs the frame to modify a specific portfolio.
   * @param portfolioNames list of all existing portfolio names
   */
  public ModifyPFFrame(String[] portfolioNames) {
    super("Modify Portfolio");

    JPanel formPanel = new JPanel(new GridLayout(10, 1));

    this.add(createPortfoliosListRadio(portfolioNames));

    // submit button
    submitButton = new JButton("Modify");
    submitButton.setActionCommand("Modify");
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
      if (rGroup.getSelection() == null) {
        displayErrorMessage("Please select a portfolio first!");
      } else {
        this.setVisible(false);
        features.viewTransactionForm(getRadioButtonSelection());
      }
    });
    backButton.addActionListener(evt -> this.setVisible(false));
  }


}
