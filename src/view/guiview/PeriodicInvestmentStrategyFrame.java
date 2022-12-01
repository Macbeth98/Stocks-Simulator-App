package view.guiview;

import java.util.HashMap;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import controller.guicontroller.Features;

/**
 * This class is a View class for creating of a Periodic Investment Strategy for an existing
 * Portfolio or can be used to create a New Portfolio with this Strategy.
 */
public class PeriodicInvestmentStrategyFrame extends AbstractFrame {

  private final JButton backButton;

  private final JTextField portfolioNameField;

  private final JButton createPortfolioButton;

  private final JLabel portfolioSelected;

  private final JSpinner amountSpinner;

  private final JSpinner comSpinner;

  private final JSpinner frequencyInDaysSpinner;

  private final JButton submitButton;

  private final JSpinner startDateSpinner;
  private final JSpinner endDateSpinner;

  private final JButton endDateToggleButton;

  private boolean endDateToggle;

  private final String endDateButtonMsgYes;
  private final String endDateButtonMsgNo;

  /**
   * Initiates and constructs the PeriodicInvestmentStrategy view with a Flow layout and the
   * required fields.
   */
  public PeriodicInvestmentStrategyFrame(String[] portfolioNames) {
    super("Periodic Investment Strategy");

    stocksDist = new HashMap<>();

    this.pack();
    this.setVisible(true);

    this.setLayout(new GridLayout(2, 1));
    this.setMinimumSize(new Dimension(1000, 700));
    this.setResizable(true);

    JPanel topGridPanel = new JPanel(new GridLayout(1, 3));
    JPanel topGridLeftPanel = new JPanel();
    topGridLeftPanel.setLayout(new BoxLayout(topGridLeftPanel, BoxLayout.PAGE_AXIS));

    backButton = new JButton("Back");
    topGridLeftPanel.add(backButton);

    topGridLeftPanel.add(new JScrollPane(createPortfoliosListRadio(portfolioNames)));

    JPanel topGridMiddlePanel = new JPanel(new FlowLayout());
    JLabel display = new JLabel("OR");
    topGridMiddlePanel.add(display);
    // topGridMiddlePanel.setLayout(new BoxLayout(topGridLeftPanel, BoxLayout.LINE_AXIS));

    JPanel topGridRightPanel = new JPanel(new GridLayout(10, 5, 10, 0));

    display = new JLabel("Create a New Portfolio. Enter Portfolio Name: ");
    topGridRightPanel.add(display);

    portfolioNameField = new JTextField();
    topGridRightPanel.add(portfolioNameField);
    // topGridRightPanel.setLayout(new BoxLayout(topGridRightPanel, BoxLayout.PAGE_AXIS));

    createPortfolioButton = new JButton("Create.");
    topGridRightPanel.add(createPortfolioButton);
    //topGridRightPanel.setLayout(new BoxLayout(topGridRightPanel, BoxLayout.PAGE_AXIS));

    topGridPanel.add(topGridLeftPanel);
    topGridPanel.add(topGridMiddlePanel);
    topGridPanel.add(topGridRightPanel);

    this.add(topGridPanel);

    JPanel secondGridPanel = new JPanel(new GridLayout(1, 2));

    JPanel rightSecondHalf = new JPanel(new GridLayout(2, 1, 3, 0));

    JPanel leftSecondHalf = this.getViewStocksDistTableData();

    JPanel formPanel = new JPanel(new GridLayout(11, 2));

    JPanel stockFormPanel = this.getStockFormGridPanel();

    portfolioSelected = new JLabel("No Portfolio Selected ");
    formPanel.add(portfolioSelected);

    display = new JLabel("Enter Amount to Invest:");
    formPanel.add(display);

    SpinnerModel amountValue = new SpinnerNumberModel(0, 0, 10000,
            0.01);
    amountSpinner = new JSpinner(amountValue);
    formPanel.add(amountSpinner);

    display = new JLabel("Enter the commission Amount: ");
    formPanel.add(display);

    SpinnerModel comValue = new SpinnerNumberModel(0, 0, 100, 0.1);
    comSpinner = new JSpinner(comValue);
    formPanel.add(comSpinner);

    formPanel.add(new JLabel("Enter the Frequency in Days: "));

    SpinnerModel frequencyInDaysValue = new SpinnerNumberModel(0, 0, 1000,
            1);
    frequencyInDaysSpinner = new JSpinner(frequencyInDaysValue);
    formPanel.add(frequencyInDaysSpinner);

    display = new JLabel("Enter Start Date: ");
    formPanel.add(display);

    startDateSpinner = this.getDateSpinner();
    formPanel.add(startDateSpinner);

    this.endDateButtonMsgYes = "End Date? Click me!";
    this.endDateButtonMsgNo = "No End Date? Click Me!";
    endDateToggleButton = new JButton("End Date? Click me!");
    endDateToggleButton.setActionCommand("endDateToggle");
    formPanel.add(endDateToggleButton);

    this.endDateToggle = false;
    endDateSpinner = this.getDateSpinner();
    endDateSpinner.setVisible(false);
    formPanel.add(endDateSpinner);

    rightSecondHalf.add(formPanel);
    rightSecondHalf.add(stockFormPanel);

    JPanel buttonPanel = new JPanel(new GridLayout(5, 2));
    buttonPanel.add(new JLabel(""));
    submitButton = new JButton("Submit");
    submitButton.setActionCommand("Submit");
    buttonPanel.add(submitButton);

    rightSecondHalf.add(buttonPanel);

    secondGridPanel.add(leftSecondHalf);
    secondGridPanel.add(rightSecondHalf);

    this.add(secondGridPanel);
  }

  @Override
  public void addFeatures(Features features) {
    createPortfolioButton.addActionListener(evt -> this.handleCreatePortfolioButton());
    endDateToggleButton.addActionListener(evt -> {
      this.endDateToggleButton.setText(this.endDateToggle ? endDateButtonMsgYes : endDateButtonMsgNo);
      this.endDateToggle = !this.endDateToggle;
      endDateSpinner.setVisible(this.endDateToggle);
    });
    addStockDistButton.addActionListener(evt -> this.addStockToList());
    submitButton.addActionListener(evt -> this.handleSubmitButton(features));
    backButton.addActionListener(evt -> this.setVisible(false));
  }

  private void handleCreatePortfolioButton() {
    if (portfolioNameField.getText().length() == 0) {
      this.displayErrorMessage("Portfolio Name cannot be Empty.");
      return;
    }
    this.pnRadioButtonSelected = false;
    portfolioSelected.setText("Portfolio Selected: " + portfolioNameField.getText());
  }

  @Override
  protected void radioButtonActionEvent(String name) {
    super.radioButtonActionEvent(name);
    portfolioSelected.setText("Portfolio Selected: " + name);
    this.portfolioNameField.setText("");
  }

  private void handleSubmitButton(Features features) {

    String portfolioName;

    if (this.pnRadioButtonSelected || this.portfolioNameField.getText().length() > 2) {
      portfolioName = this.pnRadioButtonSelected ? this.getRadioButtonSelection() :
              this.portfolioNameField.getText();
    } else {
      this.displayErrorMessage("Portfolio is not Selected.");
      return;
    }

    float amount = Float.parseFloat(this.amountSpinner.getValue().toString());

    String startDate = this.getDateSpinnerValue(startDateSpinner);

    String endDate = null;

    if (endDateToggle) {
      endDate = this.getDateSpinnerValue(endDateSpinner);
    } else {
      System.out.println("End Date not given.");
    }

    float commission = Float.parseFloat(this.comSpinner.getValue().toString());

    int frequencyInDays = (Integer) this.frequencyInDaysSpinner.getValue();


    try {
      features.periodicInvestmentStrategyToPortfolio(
              portfolioName, stocksDist, amount, frequencyInDays, startDate, endDate, commission
      );
      this.displaySuccessMessage("Strategy Successfully Executed.");
    } catch (IllegalArgumentException e) {
      this.displayErrorMessage(e.getMessage());
    }
  }

}
