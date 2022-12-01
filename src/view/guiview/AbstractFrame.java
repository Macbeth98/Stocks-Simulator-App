package view.guiview;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public abstract class AbstractFrame extends JFrame implements IView {

  protected JRadioButton[] radioButtons;

  protected JSpinner dateSpinner;

  protected ButtonGroup rGroup;

  protected boolean pnRadioButtonSelected;

  protected JTable dataTable;

  protected JTextField stockTickerField;

  protected JSpinner stockDistSpinner;

  protected JButton addStockDistButton;

  protected Map<String, Float> stocksDist;

  public AbstractFrame(String caption) {
    super(caption);

    this.pnRadioButtonSelected = false;

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500, 500));

    this.setLayout(new FlowLayout());
  }

  @Override
  public void displaySuccessMessage(String successMessage) {
    JOptionPane.showMessageDialog(this, successMessage,
            "Portfolio Application", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage,
            "Portfolio Application", JOptionPane.ERROR_MESSAGE);
  }

  protected JPanel createPortfoliosListRadio(String[] portfolioNames) {
    // display existing portfolio names
    JPanel radioPanel = new JPanel();
    radioPanel.setBorder(BorderFactory.createTitledBorder("Please Select Portfolio"));

    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
    radioButtons = new JRadioButton[portfolioNames.length];
    rGroup = new ButtonGroup();

    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton(portfolioNames[i]);
      radioButtons[i].setActionCommand(radioButtons[i].getText());
      int finalI = i;
      radioButtons[i].addActionListener(evt -> this.radioButtonActionEvent(portfolioNames[finalI]));
      rGroup.add(radioButtons[i]);
      radioPanel.add(radioButtons[i]);
    }

    //radioButtons[radioButtons.length-1].doClick();
    return radioPanel;
  }

  protected void radioButtonActionEvent(String name) {
    this.pnRadioButtonSelected = true;
  }

  protected String getRadioButtonSelection() {
    return rGroup.getSelection().getActionCommand();
  }

  protected JSpinner getDateSpinner() {
    Date today = new Date();
    dateSpinner = new JSpinner(new SpinnerDateModel(today, null, today, Calendar.MONTH));
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MMM/yyyy");
    dateSpinner.setEditor(editor);
    return dateSpinner;
  }

  protected String getDateSpinnerValue(JSpinner dateSpinner) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
    return formatter.format(dateSpinner.getValue());
  }

  protected JPanel getViewStocksDistTableData() {
    JPanel leftSecondHalf = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JLabel display =  new JLabel("Stocks Distribution Selected:");
    leftSecondHalf.add(display);

    dataTable = new JTable(new DefaultTableModel(new Object[]{"Ticker", "Distribution Percentage"},
            0));
    DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
    tableModel.addRow(new Object[]{"Ticker", "Distribution Percentage"});
    leftSecondHalf.add(dataTable);

    return leftSecondHalf;
  }

  protected JPanel getStockFormGridPanel() {
    JPanel stockFormPanel = new JPanel(new GridLayout(7, 2));
    JLabel display = new JLabel("Enter Stocks Distribution:");
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

    return stockFormPanel;
  }

  protected void removeFromTable(DefaultTableModel tableModel, String ticker) {
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

  protected void addDataToTable(String ticker, float percentage) {
    DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
    if(stocksDist.containsKey(ticker)) {
      this.removeFromTable(tableModel, ticker);
    }
    stocksDist.put(ticker, percentage);
    tableModel.addRow(new Object[]{ticker, percentage});
  }

  protected void AddStockToList() {
    String ticker = stockTickerField.getText().toUpperCase();
    if(ticker.length() == 0) {
      this.displayErrorMessage("The ticker symbol is not given.");
      return;
    }

    float percentage = Float.parseFloat(stockDistSpinner.getValue().toString());
    if(percentage <= 0) {
      this.displayErrorMessage("The percentage given is not valid.");
      return;
    }

    this.addDataToTable(ticker, percentage);

  }
}
