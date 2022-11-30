package view.guiview;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import controller.guicontroller.Features;

public class TransactionFrame extends JFrame implements IView {

  private JLabel snamePrompt, squantityPrompt, txnDatePrompt, commissionPrompt;

  private JSpinner qtySpinner, dateSpinner, commissionSpinner;

  private JTextField stockNameInput;

  private JButton backButton, submitButton;

  public TransactionFrame() {
    super("Add Transaction to Portfolio");

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500,500));

    this.setLayout(new FlowLayout());

    JPanel formPanel = new JPanel(new GridLayout(10, 1));

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

    backButton.addActionListener(evt -> this.setVisible(false));
  }
}
