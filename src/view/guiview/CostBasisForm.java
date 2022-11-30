package view.guiview;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import controller.guicontroller.Features;

public class CostBasisForm extends JFrame implements IView {

  private JButton backButton, submitButton;

  private JLabel display, valueDatePrompt, valueResultStatement;

  private JSpinner dateSpinner;

  private JRadioButton[] radioButtons;

  private ButtonGroup rGroup;

  public CostBasisForm(String[] portfolioNames, String portfolioName,
                       String dateString, String costBasis) {
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
      this.setVisible(false);
      features.viewCostBasis(
              getRadioButtonSelection(),
              getDateSpinnerValue()
      );
    });
    backButton.addActionListener(evt -> this.setVisible(false));
  }

  private String getRadioButtonSelection() {
    return rGroup.getSelection().getActionCommand();
  }

  private String getDateSpinnerValue() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
    return formatter.format(this.dateSpinner.getValue());
  }
}
