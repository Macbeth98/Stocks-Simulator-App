package view.guiview;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import controller.guicontroller.Features;

public class viewPortfolioValueForm extends JFrame implements IView {

  private JButton backButton, submitButton;

  private JLabel display, valueDatePrompt, valueResultStatement;

  private JSpinner dateSpinner;

  private JRadioButton[] radioButtons;

  private ButtonGroup rGroup;

  public viewPortfolioValueForm(String[] portfolioNames, String portfolioName,
                                String dateString, String valueOnDate) {
    super("Get Portfolio Value On A Date");

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500, 500));

    this.setLayout(new FlowLayout());

    JPanel formPanel = new JPanel(new GridLayout(10, 1));

    // display existing portfolio names
    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    display = new JLabel("Please choose portfolio: ");
    formPanel.add(display);

    radioButtons = new JRadioButton[portfolioNames.length];
    rGroup = new ButtonGroup();

    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton(portfolioNames[i]);
      radioButtons[i].setActionCommand(radioButtons[i].getText());
      rGroup.add(radioButtons[i]);
      radioPanel.add(radioButtons[i]);
    }
    formPanel.add(radioPanel);

    // enter value date
    valueDatePrompt = new JLabel("Enter date:");
    formPanel.add(valueDatePrompt);

    Date today = new Date();
    dateSpinner = new JSpinner(new SpinnerDateModel(today, null, today, Calendar.MONTH));
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MMM/yyyy");
    dateSpinner.setEditor(editor);
    formPanel.add(dateSpinner);

    // display result value if passed
    if (dateString.length() > 0 && portfolioName.length() > 0 && valueOnDate.length() > 0) {
      valueResultStatement = new JLabel("Value of Portfolio: "
              + portfolioName
              + " on Date: "
              + dateString
              + ", is: $"
              + valueOnDate
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
      if (!(getRadioButtonSelection().length() > 0)) {
        this.portfolioSelectionWarningMessage();
      } else {
        this.setVisible(false);
        features.viewPortfolioValueAtDate(
                getRadioButtonSelection(),
                getDateSpinnerValue()
        );
      }
    });
    backButton.addActionListener(evt -> this.setVisible(false));
  }

  @Override
  public void displaySuccessMessage(String successMessage) {

  }

  private void portfolioSelectionWarningMessage() {
    JOptionPane.showMessageDialog(this, "Please select a portfolio!",
            "Portfolio is not Selected.", JOptionPane.WARNING_MESSAGE);
  }

  private String getRadioButtonSelection() {
    try {
      return rGroup.getSelection().getActionCommand();
    } catch (Exception e) {
      return "";
    }
  }

  private String getDateSpinnerValue() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
    return formatter.format(this.dateSpinner.getValue());
  }
}
