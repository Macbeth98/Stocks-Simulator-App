package view.guiview;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import controller.guicontroller.Features;

/**
 * Class that contains the frame for the view portfolio composition form.
 */
public class viewCompositionForm extends JFrame implements IView {

  private JButton backButton, submitButton;

  private JLabel display, compositionDatePrompt;

  private JSpinner dateSpinner;

  private JRadioButton[] radioButtons;

  private ButtonGroup rGroup;

  private JTable data_table;

  public viewCompositionForm(String[] portfolioNames, String compositionString) {
    super("View Portfolio Composition");

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500,500));

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

    // enter composition date
    compositionDatePrompt = new JLabel("Enter date:");
    formPanel.add(compositionDatePrompt);

    Date today = new Date();
    dateSpinner = new JSpinner(new SpinnerDateModel(today, null, today, Calendar.MONTH));
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MMM/yyyy");
    dateSpinner.setEditor(editor);
    formPanel.add(dateSpinner);

    // display composition if passed
    if (compositionString.length() != 0) {
      JPanel compositionPanel = new JPanel(new FlowLayout());

      String[] columns = {"Ticker", "Quantity"};
      String[] lines = compositionString.split("\n");
      String[][] data = new String[lines.length][2];
      for (int i = 0; i < lines.length; i++) {
        data[i][0] = lines[i].split(",")[0];
        data[i][1] = lines[i].split(",")[1];
      }

      data_table = new JTable(data, columns);
      compositionPanel.add(data_table);
      formPanel.add(compositionPanel);
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
      features.setViewPortfolioComposition(
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
