package view.guiview;

import java.awt.*;

import javax.swing.*;

import controller.guicontroller.Features;

public class MainFrameView extends JFrame implements IView {

  private final JButton exitButton;
  private final JButton compositionButton;
  private final JButton createPFButton;
  private final JButton createPFFileButton;
  private final JButton modifyPFButton;

  private final JButton oneTimeStrategyButton;
  private final JButton periodicStrategyButton;
  private final JButton pfValueButton;
  private final JButton costBasisButton;
  private final JButton graphButton;

  public MainFrameView(String caption) {
    super(caption);

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setMinimumSize(new Dimension(500, 500));

    this.setLayout(new FlowLayout());

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
    oneTimeStrategyButton.setActionCommand("One-Time Investment");
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
    graphButton = new JButton("Get Performance Graph");
    graphButton.setActionCommand("Portfolio Performance Graph");
    btnPanel.add(graphButton);

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
    compositionButton.addActionListener(evt -> features.viewPortfolioComposition());
    pfValueButton.addActionListener(evt -> features.viewPortfolioValueAtDate("", ""));
    costBasisButton.addActionListener(evt -> features.viewCostBasis("", ""));
    exitButton.addActionListener(evt -> features.exitProgram());
  }

  @Override
  public void displaySuccessMessage(String successMessage) {

  }

  @Override
  public void displayErrorMessage(String errorMessage) {

  }
}
