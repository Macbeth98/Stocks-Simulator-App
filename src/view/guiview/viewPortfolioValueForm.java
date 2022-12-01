package view.guiview;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import controller.guicontroller.Features;

public class viewPortfolioValueForm extends AbstractFrame {

  private final JButton backButton;
  private final JButton submitButton;

  private final JLabel valueDatePrompt;
  private JLabel valueResultStatement;

  private final JSpinner dateSpinner;

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

    this.add(createPortfoliosListRadio(portfolioNames));

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
      if (rGroup.getSelection() == null) {
        displayErrorMessage("Please select a portfolio first!");
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


  private String getDateSpinnerValue() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
    return formatter.format(this.dateSpinner.getValue());
  }
}
