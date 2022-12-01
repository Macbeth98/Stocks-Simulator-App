package view.guiview;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import controller.guicontroller.Features;
import model.TransactionType;

public class TransactionFrame extends JFrame implements IView {

  private JLabel pNameDisplay, snamePrompt, squantityPrompt, txnDatePrompt, commissionPrompt;

  private JLabel txnTypePrompt;

  private JComboBox<TransactionType> txnType;

  private JSpinner qtySpinner, dateSpinner, commissionSpinner;

  private JTextField stockNameInput;

  private JButton backButton, submitButton;

  public TransactionFrame(String portfolioName) {
    super("Add Transaction to Portfolio");

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500,500));

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

    SpinnerModel commissionValue = new SpinnerNumberModel(0.0f, 0.0f,
            10000, 0.1f);
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
      formValidations();
      features.AddTransactionToPortfolio(
              pNameDisplay.getText(),
              (TransactionType) txnType.getSelectedItem(),
              stockNameInput.getText(),
              (float) qtySpinner.getValue(),
              getDateSpinnerValue(),
              (float) commissionSpinner.getValue()
      );
    });
    backButton.addActionListener(evt -> this.setVisible(false));
  }

  @Override
  public void displaySuccessMessage(String successMessage) {

  }

  private void formValidations() {
    if(stockNameInput.getText().length() == 0) {
      JOptionPane.showMessageDialog(this, "Stock Ticker Not Entered!",
              "Create Portfolio Error", JOptionPane.WARNING_MESSAGE);
    }
    if((Integer) qtySpinner.getValue() <= 0) {
      JOptionPane.showMessageDialog(this, "Invalid Quantity!!",
              "Create Portfolio Error", JOptionPane.WARNING_MESSAGE);
    }
    Date date = new Date();
    Date inputDate = (Date) this.dateSpinner.getValue();
    if(inputDate.compareTo(date) > 0) {
      JOptionPane.showMessageDialog(this, "Invalid Future Date!",
              "Create Portfolio Error", JOptionPane.WARNING_MESSAGE);
    }
    float commissionValue = (float) commissionSpinner.getValue();
    if(commissionValue < 0) {
      JOptionPane.showMessageDialog(this, "Invalid Commission!!",
              "Create Portfolio Error", JOptionPane.WARNING_MESSAGE);
    }
  }

  private String getDateSpinnerValue() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
    return formatter.format(this.dateSpinner.getValue());
  }
}
