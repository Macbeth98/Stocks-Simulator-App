package view.guiview;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import controller.guicontroller.Features;

/**
 * This class is a View class for creating of a Periodic Investment Strategy for an existing
 * Portfolio or can be used to create a New Portfolio with this Strategy.
 */
public class PeriodicInvestmentStrategyFrame extends AbstractFrame{

  private final JButton backButton;

  private final JTextField portfolioNameField;

  private final JButton createPortfolioButton;

  /**
   * Initiates and constructs the PeriodicInvestmentStrategy view with a Flow layout and the
   * required fields.
   */
  public PeriodicInvestmentStrategyFrame(String[] portfolioNames) {
    super("Periodic Investment Strategy");

    this.pack();
    this.setVisible(true);

    this.setLayout(new GridLayout(2, 1));
    this.setMinimumSize(new Dimension(1000, 700));
    this.setResizable(true);

    JPanel topGridPanel = new JPanel(new GridLayout(1, 3));
    JPanel topGridLeftPanel = new JPanel();
    topGridLeftPanel.setLayout(new BoxLayout(topGridLeftPanel, BoxLayout.PAGE_AXIS));

    backButton = new JButton("Back");
    topGridLeftPanel.add(backButton);

    topGridLeftPanel.add(createPortfoliosListRadio(portfolioNames));

    JPanel topGridMiddlePanel = new JPanel(new FlowLayout());
    JLabel display = new JLabel("OR");
    topGridMiddlePanel.add(display);
    // topGridMiddlePanel.setLayout(new BoxLayout(topGridLeftPanel, BoxLayout.LINE_AXIS));

    JPanel topGridRightPanel = new JPanel(new GridLayout(10, 5, 10, 0));

    display = new JLabel("Create a New Portfolio. Enter Portfolio Name: ");
    topGridRightPanel.add(display);

    portfolioNameField = new JTextField();
    topGridRightPanel.add(portfolioNameField);
    // topGridRightPanel.setLayout(new BoxLayout(topGridRightPanel, BoxLayout.PAGE_AXIS));

    createPortfolioButton = new JButton("Create.");
    topGridRightPanel.add(createPortfolioButton);
    //topGridRightPanel.setLayout(new BoxLayout(topGridRightPanel, BoxLayout.PAGE_AXIS));

    topGridPanel.add(topGridLeftPanel);
    topGridPanel.add(topGridMiddlePanel);
    topGridPanel.add(topGridRightPanel);

    this.add(topGridPanel);


  }

  @Override
  public void addFeatures(Features features) {

  }
}
