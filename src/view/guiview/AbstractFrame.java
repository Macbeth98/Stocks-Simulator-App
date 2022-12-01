package view.guiview;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

public abstract class AbstractFrame extends JFrame implements IView {

  protected JRadioButton[] radioButtons;

  protected JSpinner dateSpinner;

  protected ButtonGroup rGroup;

  protected boolean pnRadioButtonSelected;

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
}
