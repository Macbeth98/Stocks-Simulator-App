package view.guiview;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import controller.guicontroller.Features;

/**
 * Class that implements the form to get cost basis.
 */
public class CostBasisForm extends AbstractFrame {

  private final JButton backButton;
  private final JButton submitButton;
  private final JLabel valueDatePrompt;
  private JLabel valueResultStatement;

  private final JSpinner dateSpinner;


  /**
   * Constructs a cost basis form frame with the following parameters.
   *
   * @param portfolioNames list of portfolios available for the user to choose from
   * @param portfolioName  name of given portfolio
   * @param dateString     given date as a string for cost basis
   * @param costBasis      cost basis on the given date
   */
  public CostBasisForm(String[] portfolioNames, String portfolioName,
                       String dateString, String costBasis) {
    super("Cost Basis of Portfolio On A Date");

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setMinimumSize(new Dimension(500, 500));

    this.setLayout(new FlowLayout());

    JPanel formPanel = new JPanel(new GridLayout(10, 1));

    // display existing portfolio names
    this.add(createPortfoliosListRadio(portfolioNames));

    // enter value date
    valueDatePrompt = new JLabel("Enter date:");
    formPanel.add(valueDatePrompt);

    Date today = new Date();
    dateSpinner = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.MONTH));
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MMM/yyyy");
    dateSpinner.setEditor(editor);
    formPanel.add(dateSpinner);

    // display result value if passed
    if (dateString.length() > 0 && portfolioName.length() > 0 && costBasis.length() > 0) {
      valueResultStatement = new JLabel("Cost Basis of Portfolio: "
              + portfolioName
              + " on Date: "
              + dateString
              + ", is: $"
              + costBasis
      );
      formPanel.add(valueResultStatement);
    }

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
      Date inputDate = (Date) this.dateSpinner.getValue();
      Date date = new Date();
      if (rGroup.getSelection() == null) {
        displayErrorMessage("Please select a portfolio first!");
      } else if (inputDate.compareTo(date) > 0) {
        JOptionPane.showMessageDialog(this, "Invalid Future Date!",
                "Create Portfolio Error", JOptionPane.WARNING_MESSAGE);
      } else {
        this.setVisible(false);
        features.viewCostBasis(
                getRadioButtonSelection(),
                getDateSpinnerValue(dateSpinner)
        );
      }
    });
    backButton.addActionListener(evt -> this.setVisible(false));
  }
}
