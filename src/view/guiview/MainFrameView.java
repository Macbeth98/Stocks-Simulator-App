package view.guiview;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.guicontroller.Features;

/**
 * Class that represents the Main Menu frame of the application.
 */
public class MainFrameView extends AbstractFrame {

  private final JButton exitButton;
  private final JButton compositionButton;
  private final JButton createPFButton;
  private final JButton createPFFileButton;
  private final JButton modifyPFButton;

  private final JButton oneTimeStrategyButton;
  private final JButton periodicStrategyButton;
  private final JButton pfValueButton;
  private final JButton costBasisButton;
  // private final JButton graphButton;

  /**
   * Constructs the main menu frame of the application.
   *
   * @param caption title of the application frame passed as a parameter
   */
  public MainFrameView(String caption) {
    super(caption);

    JPanel btnPanel = new JPanel(new GridLayout(10, 1, 10, 5));

    // create portfolio button
    createPFButton = new JButton("Create Portfolio");
    createPFButton.setActionCommand("Create Portfolio");
    btnPanel.add(createPFButton);

    // create portfolio button
    createPFFileButton = new JButton("Create Portfolio From File");
    createPFFileButton.setActionCommand("Create Portfolio From File");
    btnPanel.add(createPFFileButton);

    // modify portfolio button
    modifyPFButton = new JButton("Modify Portfolio");
    modifyPFButton.setActionCommand("Modify Portfolio");
    btnPanel.add(modifyPFButton);

    // add one time strategy to portfolio button
    oneTimeStrategyButton = new JButton("One-Time Investment Strategy");
    oneTimeStrategyButton.setActionCommand("One-Time Investment Strategy");
    btnPanel.add(oneTimeStrategyButton);

    // add periodic strategy to portfolio button
    periodicStrategyButton = new JButton("Periodic Investment Strategy");
    periodicStrategyButton.setActionCommand("Periodic Investment Strategy");
    btnPanel.add(periodicStrategyButton);

    // view composition button
    compositionButton = new JButton("View Portfolio's Composition");
    compositionButton.setActionCommand("Portfolio Composition");
    btnPanel.add(compositionButton);

    // get value button
    pfValueButton = new JButton("Get Portfolio's Value");
    pfValueButton.setActionCommand("Portfolio Value");
    btnPanel.add(pfValueButton);

    // get cost basis button
    costBasisButton = new JButton("Get Cost Basis");
    costBasisButton.setActionCommand("Portfolio Cost Basis");
    btnPanel.add(costBasisButton);

    // get portfolio performance graph
    // graphButton = new JButton("Get Performance Graph");
    // graphButton.setActionCommand("Portfolio Performance Graph");
    // btnPanel.add(graphButton);

    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit Button");
    btnPanel.add(exitButton);

    this.add(btnPanel);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    createPFButton.addActionListener(evt -> features.createPortfolio());
    createPFFileButton.addActionListener(evt -> features.createPortfolioFromFile());
    modifyPFButton.addActionListener(evt -> features.modifyPortfolio());
    compositionButton.addActionListener(evt -> features.viewPortfolioComposition());
    pfValueButton.addActionListener(evt -> features.viewPortfolioValueAtDate("", ""));
    costBasisButton.addActionListener(evt -> features.viewCostBasis("", ""));
    oneTimeStrategyButton.addActionListener(evt ->
            features.setViewApplyOneTimeStrategyToPortfolio());
    periodicStrategyButton.addActionListener(evt -> features.setViewPeriodicInvestmentStrategy());
    exitButton.addActionListener(evt -> features.exitProgram());
  }
}
