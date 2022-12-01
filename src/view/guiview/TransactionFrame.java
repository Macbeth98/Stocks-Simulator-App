package view.guiview;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import controller.guicontroller.Features;
import model.TransactionType;

public class TransactionFrame extends AbstractFrame implements IView {

  private final JLabel pNameDisplay;
  private final JLabel snamePrompt;
  private final JLabel squantityPrompt;
  private final JLabel txnDatePrompt;
  private final JLabel commissionPrompt;

  private final JLabel txnTypePrompt;

  private final JComboBox<TransactionType> txnType;

  private final JSpinner qtySpinner;
  private final JSpinner dateSpinner;
  private final JSpinner commissionSpinner;

  private final JTextField stockNameInput;

  private final JButton backButton;
  private final JButton submitButton;

  public TransactionFrame(String portfolioName) {
    super("Add Transaction to Portfolio");

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500, 500));

    this.setLayout(new FlowLayout());

    JPanel formPanel = new JPanel(new GridLayout(10, 1));

    // added for formatting reasons
    formPanel.add(new JLabel("Portfolio: "));

    pNameDisplay = new JLabel(portfolioName);
    formPanel.add(pNameDisplay);

    txnTypePrompt = new JLabel("Transaction Type: ");
    formPanel.add(txnTypePrompt);

    txnType = new JComboBox<>(TransactionType.values());
    formPanel.add(txnType);

    snamePrompt = new JLabel("Enter stock ticker name:");
    formPanel.add(snamePrompt);

    // enter portfolio name field
    stockNameInput = new JTextField();
    formPanel.add(stockNameInput);

    squantityPrompt = new JLabel("Enter quantity:");
    formPanel.add(squantityPrompt);

    SpinnerModel qtyValue = new SpinnerNumberModel(0, 0, 10000, 1);
    qtySpinner = new JSpinner(qtyValue);
    formPanel.add(qtySpinner);

    // date prompt
    txnDatePrompt = new JLabel("Enter transaction date:");
    formPanel.add(txnDatePrompt);

    Date today = new Date();
    dateSpinner = new JSpinner(new SpinnerDateModel(today, null, today, Calendar.MONTH));
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MMM/yyyy");
    dateSpinner.setEditor(editor);
    formPanel.add(dateSpinner);

    commissionPrompt = new JLabel("Enter commission fees:");
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
        features.AddTransactionToPortfolio(
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
