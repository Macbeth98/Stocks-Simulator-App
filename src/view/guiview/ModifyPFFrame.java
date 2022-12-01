package view.guiview;

import java.awt.*;

import javax.swing.*;

import controller.guicontroller.Features;

public class ModifyPFFrame extends AbstractFrame implements IView {

  private final JButton backButton;
  private final JButton submitButton;

  private final JLabel display;

  private final JRadioButton[] radioButtons;

  private final ButtonGroup rGroup;

  public ModifyPFFrame(String[] portfolioNames) {
    super("Modify Portfolio");

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500, 500));

    this.setLayout(new FlowLayout());

    JPanel formPanel = new JPanel(new GridLayout(10, 1));

    // display existing portfolio names
    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    display = new JLabel("Please choose portfolio: ");
    formPanel.add(display);

    radioButtons = new JRadioButton[portfolioNames.length];
    rGroup = new ButtonGroup();

    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton(portfolioNames[i]);
      radioButtons[i].setActionCommand(radioButtons[i].getText());
      rGroup.add(radioButtons[i]);
      radioPanel.add(radioButtons[i]);
    }
    formPanel.add(radioPanel);

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

  }
}
