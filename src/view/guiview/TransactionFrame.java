package view.guiview;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JOptionPane;


import controller.guicontroller.Features;
import model.TransactionType;

/**
 * Class consisting of the frame that shows the menu to add a transaction.
 */
public class TransactionFrame extends AbstractFrame {

  private final JLabel pNameDisplay;

  private final JComboBox<TransactionType> txnType;

  private final JSpinner qtySpinner;
  private final JSpinner dateSpinner;
  private final JSpinner commissionSpinner;

  private final JTextField stockNameInput;

  /**
   * Constructs the frame to add a transaction to a specific portfolio.
   *
   * @param portfolioName given portfolio's name
   */
  public TransactionFrame(String portfolioName) {
    super("Add Transaction to Portfolio");

    JPanel formPanel = new JPanel(new GridLayout(10, 1));

    // added for formatting reasons
    formPanel.add(new JLabel("Portfolio: "));

    pNameDisplay = new JLabel(portfolioName);
    formPanel.add(pNameDisplay);

    JLabel txnTypePrompt = new JLabel("Transaction Type: ");
    formPanel.add(txnTypePrompt);

    txnType = new JComboBox<>(TransactionType.values());
    formPanel.add(txnType);

    JLabel snamePrompt = new JLabel("Enter stock ticker name:");
    formPanel.add(snamePrompt);

    // enter portfolio name field
    stockNameInput = new JTextField();
    formPanel.add(stockNameInput);

    JLabel squantityPrompt = new JLabel("Enter quantity:");
    formPanel.add(squantityPrompt);

    SpinnerModel qtyValue = new SpinnerNumberModel(0, 0, 10000, 1);
    qtySpinner = new JSpinner(qtyValue);
    formPanel.add(qtySpinner);

    // date prompt
    JLabel txnDatePrompt = new JLabel("Enter transaction date:");
    formPanel.add(txnDatePrompt);

    Date today = new Date();
    dateSpinner = new JSpinner(new SpinnerDateModel(today, null, today, Calendar.MONTH));
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MMM/yyyy");
    dateSpinner.setEditor(editor);
    formPanel.add(dateSpinner);

    JLabel commissionPrompt = new JLabel("Enter commission fees:");
    formPanel.add(commissionPrompt);

    SpinnerModel commissionValue = new SpinnerNumberModel(0.0, 0.0,
            10000, 0.1);
    commissionSpinner = new JSpinner(commissionValue);
    formPanel.add(commissionSpinner);

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
      Date date = new Date();
      Date inputDate = (Date) this.dateSpinner.getValue();
      float commissionValue = ((Double) commissionSpinner.getValue()).floatValue();
      if (stockNameInput.getText().length() == 0) {
        JOptionPane.showMessageDialog(this, "Stock Ticker Not Entered!",
                "Create Portfolio Error", JOptionPane.WARNING_MESSAGE);
      } else if ((Integer) qtySpinner.getValue() <= 0) {
        JOptionPane.showMessageDialog(this, "Invalid Quantity!!",
                "Create Portfolio Error", JOptionPane.WARNING_MESSAGE);
      } else if (inputDate.compareTo(date) > 0) {
        JOptionPane.showMessageDialog(this, "Invalid Future Date!",
                "Create Portfolio Error", JOptionPane.WARNING_MESSAGE);
      } else if (commissionValue < 0) {
        JOptionPane.showMessageDialog(this, "Invalid Commission!!",
                "Create Portfolio Error", JOptionPane.WARNING_MESSAGE);
      } else {
        features.addTransactionToPortfolio(
                pNameDisplay.getText(),
                (TransactionType) txnType.getSelectedItem(),
                stockNameInput.getText().toUpperCase(),
                ((Integer) qtySpinner.getValue()).floatValue(),
                getDateSpinnerValue(dateSpinner),
                ((Double) commissionSpinner.getValue()).floatValue()
        );
      }
    });
    backButton.addActionListener(evt -> this.setVisible(false));
  }
}
