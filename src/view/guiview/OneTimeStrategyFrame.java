package view.guiview;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerModel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import controller.guicontroller.Features;

/**
 * This class is a View class for creating of a one time Investment Strategy for an existing
 * Portfolio.
 */
public class OneTimeStrategyFrame extends AbstractFrame {

  private final JLabel portfolioSelected;

  private final JSpinner amountSpinner;

  private final JSpinner comSpinner;

  private final JButton submitButton;

  private final JButton backButton;

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

    topGridPanel.add(new JScrollPane(createPortfoliosListRadio(portfolioNames)));

    this.add(topGridPanel);

    JPanel secondGridPanel = new JPanel(new GridLayout(1, 2));

    JPanel rightSecondHalf = new JPanel(new GridLayout(3, 1, 10, 0));
    //rightSecondHalf.setLayout(new BoxLayout(rightSecondHalf, BoxLayout.PAGE_AXIS));

    JPanel leftSecondHalf = this.getViewStocksDistTableData();

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

    JPanel stockFormPanel = this.getStockFormGridPanel();

    rightSecondHalf.add(stockFormPanel);

    JPanel buttonPanel = new JPanel(new GridLayout(5, 2));
    submitButton = new JButton("Submit");
    submitButton.setActionCommand("Submit");
    buttonPanel.add(submitButton);
    rightSecondHalf.add(buttonPanel);



    secondGridPanel.add(leftSecondHalf);
    secondGridPanel.add(rightSecondHalf);

    this.add(secondGridPanel);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    addStockDistButton.addActionListener(evt -> this.addStockToList());
    submitButton.addActionListener(evt -> this.handleSubmitButton(features));
    backButton.addActionListener(evt -> this.setVisible(false));
  }

  @Override
  protected void radioButtonActionEvent(String name) {
    super.radioButtonActionEvent(name);
    portfolioSelected.setText("Portfolio Selected: " + name);
  }


  private void handleSubmitButton(Features features) {

    if (!this.pnRadioButtonSelected) {
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
