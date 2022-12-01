package view.guiview;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.guicontroller.Features;

/**
 * This class is a View class for creating of a one time Investment Strategy for an existing
 * Portfolio.
 */
public class OneTimeStrategyFrame extends AbstractFrame {

  private final JLabel portfolioSelected;

  private final JSpinner amountSpinner;

  private final JSpinner comSpinner;

  private final JTextField stockTickerField;

  private final JSpinner stockDistSpinner;

  private final JButton addStockDistButton;

  private final JTable dataTable;

  private final JButton submitButton;

  private final JButton backButton;

  private final Map<String, Float> stocksDist;

  /**
   * Initiates and constructs the OneTimeStrategyFame view with a Flow layout and the
   * required fields.
   */
  public OneTimeStrategyFrame(String[] portfolioNames) {
    super("One-Time Investment Strategy");

    stocksDist = new HashMap<>();

    this.setLayout(new GridLayout(2, 1));
    this.setMinimumSize(new Dimension(1000, 700));
    this.setResizable(true);

    JPanel topGridPanel = new JPanel();
    topGridPanel.setLayout(new BoxLayout(topGridPanel, BoxLayout.PAGE_AXIS));

    backButton = new JButton("Back");
    topGridPanel.add(backButton);

    topGridPanel.add(createPortfoliosListRadio(portfolioNames));

    this.add(topGridPanel);

    JPanel secondGridPanel = new JPanel(new GridLayout(1, 2));

    JPanel rightSecondHalf = new JPanel(new GridLayout(3, 1, 10, 0));
    //rightSecondHalf.setLayout(new BoxLayout(rightSecondHalf, BoxLayout.PAGE_AXIS));

    JPanel leftSecondHalf = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JPanel formPanel = new JPanel(new GridLayout(8, 2));

    portfolioSelected = new JLabel("No Portfolio Selected ");
    formPanel.add(portfolioSelected);

    JLabel display = new JLabel("Enter Amount to Invest:");
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

    display = new JLabel("Select the date to Invest:");
    formPanel.add(display);
    formPanel.add(this.getDateSpinner());

    rightSecondHalf.add(formPanel);

    JPanel stockFormPanel = new JPanel(new GridLayout(7, 2));
    display = new JLabel("Enter Stocks Distribution:");
    stockFormPanel.add(display);

    display = new JLabel("Enter Stock Ticker: ");
    stockFormPanel.add(display);

    // Enter Stock Ticker
    stockTickerField = new JTextField();
    stockFormPanel.add(stockTickerField);

    display = new JLabel("Enter Distribution Percentage: ");
    stockFormPanel.add(display);

    SpinnerModel distValue = new SpinnerNumberModel(0, 0, 100, 0.01);
    stockDistSpinner = new JSpinner(distValue);
    stockFormPanel.add(stockDistSpinner);

    addStockDistButton = new JButton("Add");
    addStockDistButton.setActionCommand("Add");
    stockFormPanel.add(addStockDistButton);


    rightSecondHalf.add(stockFormPanel);

    JPanel buttonPanel = new JPanel(new GridLayout(5, 2));
    submitButton = new JButton("Submit");
    submitButton.setActionCommand("Submit");
    buttonPanel.add(submitButton);
    rightSecondHalf.add(buttonPanel);

    display =  new JLabel("Stocks Distribution Selected:");
    leftSecondHalf.add(display);

    dataTable = new JTable(new DefaultTableModel(new Object[]{"Ticker", "Distribution Percentage"},
            0));
    DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
    tableModel.addRow(new Object[]{"Ticker", "Distribution Percentage"});
    leftSecondHalf.add(dataTable);

    secondGridPanel.add(leftSecondHalf);
    secondGridPanel.add(rightSecondHalf);

    this.add(secondGridPanel);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    addStockDistButton.addActionListener(evt -> this.AddStockToList());
    submitButton.addActionListener(evt -> this.handleSubmitButton(features));
    backButton.addActionListener(evt -> this.setVisible(false));
  }

  @Override
  protected void radioButtonActionEvent(String name) {
    super.radioButtonActionEvent(name);
    portfolioSelected.setText("Portfolio Selected: " + name);
  }

  private void removeFromTable(DefaultTableModel tableModel, String ticker) {
    Vector<Vector> tableData = tableModel.getDataVector();
    int count = 0;
    int row = -1;
    for (Vector tableDatum : tableData) {
      if(tableDatum.contains(ticker)) {
        row = count;
        break;
      }
      count++;
    }
    if(row >= 0) {
      tableModel.removeRow(row);
    }
  }

  private void addDataToTable(String ticker, float percentage) {
    DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
    if(stocksDist.containsKey(ticker)) {
      this.removeFromTable(tableModel, ticker);
    }
    stocksDist.put(ticker, percentage);
    tableModel.addRow(new Object[]{ticker, percentage});
  }

  private void AddStockToList() {
    String ticker = stockTickerField.getText().toUpperCase();
    if(ticker.length() == 0) {
      this.displayErrorMessage("The ticker symbol is not given.");
    }

    float percentage = Float.parseFloat(stockDistSpinner.getValue().toString());
    if(percentage <= 0) {
      this.displayErrorMessage("The percentage given is not valid.");
    }

    this.addDataToTable(ticker, percentage);

  }

  private void handleSubmitButton(Features features) {

    if(!this.pnRadioButtonSelected) {
      this.displayErrorMessage("Portfolio is not Selected.");
      return;
    }

    String portfolioName = this.getRadioButtonSelection();
    float amount = Float.parseFloat(this.amountSpinner.getValue().toString());
    float commission = Float.parseFloat(this.comSpinner.getValue().toString());
    String date = this.getDateSpinnerValue(dateSpinner);

    try {
      features.applyOneTimeStrategyToPortfolio(
              portfolioName,
              amount,
              commission,
              date,
              stocksDist
      );
      this.displaySuccessMessage("Strategy Successfully Executed.");
    } catch (IllegalArgumentException e) {
      this.displayErrorMessage(e.getMessage());
    }
  }
}
