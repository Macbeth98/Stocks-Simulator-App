package view.guiview;

import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JTable;
import javax.swing.BoxLayout;

import controller.guicontroller.Features;

/**
 * Class that contains the frame for the view portfolio composition form.
 */
public class ViewCompositionForm extends AbstractFrame implements IView {

  private final JButton backButton;
  private final JButton submitButton;

  private final JSpinner dateSpinner;

  /**
   * Constructs a form to view a portfolio's composition.
   *
   * @param portfolioNames    list of existing portfolios
   * @param compositionString composition of given portfolio as a string
   */
  public ViewCompositionForm(String[] portfolioNames, String compositionString) {
    super("View Portfolio Composition");

    JPanel formPanel = new JPanel(new FlowLayout());

    this.add(createPortfoliosListRadio(portfolioNames));

    // enter composition date
    JLabel compositionDatePrompt = new JLabel("Enter date:");
    formPanel.add(compositionDatePrompt);

    Date today = new Date();
    dateSpinner = new JSpinner(new SpinnerDateModel(today, null, today, Calendar.MONTH));
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MMM/yyyy");
    dateSpinner.setEditor(editor);
    formPanel.add(dateSpinner);

    // display composition if passed
    if (compositionString.length() != 0) {
      String[] columns = {"Ticker", "Quantity"};
      String[] lines = compositionString.split("\n");
      String[][] data = new String[lines.length][2];
      for (int i = 0; i < lines.length; i++) {
        data[i][0] = lines[i].split(",")[0];
        data[i][1] = lines[i].split(",")[1];
      }

      JTable dataTable = new JTable(data, columns);
      formPanel.add(dataTable);
    }

    // submit button
    submitButton = new JButton("Submit");
    submitButton.setActionCommand("Submit");
    formPanel.add(submitButton);

    // exit button
    backButton = new JButton("Back");
    backButton.setActionCommand("Back");
    formPanel.add(backButton);

    BoxLayout vertical = new BoxLayout(formPanel, BoxLayout.PAGE_AXIS);
    formPanel.setLayout(vertical);

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
        features.setViewPortfolioComposition(
                getRadioButtonSelection(),
                getDateSpinnerValue(dateSpinner)
        );
      }
    });
    backButton.addActionListener(evt -> this.setVisible(false));
  }
}
