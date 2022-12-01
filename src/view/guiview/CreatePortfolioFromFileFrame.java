package view.guiview;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.guicontroller.Features;

public class CreatePortfolioFromFileFrame extends JFrame implements IView {

  private JButton backButton, submitButton, selectFileButton;

  private JTextField pNameInput;

  private JFileChooser pFileChooser;

  private int fileChooserResponse;

  private JLabel fileSelectedPath;

  private JLabel display;

  public CreatePortfolioFromFileFrame() {
    super("Load A Portfolio From File.");

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setMinimumSize(new Dimension(1000,500));

    this.setLayout(new FlowLayout());

    JPanel formPanel = new JPanel(new GridLayout(10, 1, 0,10));

    display = new JLabel("Create a New Portfolio From File.");
    formPanel.add(display);

    display = new JLabel("Enter Portfolio Name:");
    formPanel.add(display);

    // enter portfolio name field
    pNameInput = new JTextField();
    formPanel.add(pNameInput);

    display = new JLabel("File Selected: ");
    formPanel.add(display);

    fileSelectedPath = new JLabel("No File has been selected.");
    formPanel.add(fileSelectedPath);

    // select the file.
    selectFileButton = new JButton("Select File");
    formPanel.add(selectFileButton);


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

    selectFileButton.addActionListener(evt -> this.selectFileChooserAction());

    submitButton.addActionListener(evt -> {
      if(this.fileChooserResponse == JFileChooser.APPROVE_OPTION) {
        features.setCreatePortfolioFromFile(
                this,
                pNameInput.getText(),
                this.getSelectedFilePath()
        );
      } else {
        this.selectFileWarningMessage();
      }
    });

    backButton.addActionListener(evt -> this.setVisible(false));
  }

  private String getSelectedFilePath() {
    return pFileChooser.getSelectedFile().getAbsolutePath();
  }

  private void selectFileWarningMessage() {
    JOptionPane.showMessageDialog(this, "Please Select a FILE.",
            "File is not Selected.", JOptionPane.WARNING_MESSAGE);
  }

  private void selectFileChooserAction() {
    pFileChooser = new JFileChooser();
    pFileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files","CSV"));
    pFileChooser.setCurrentDirectory(new File("."));

    this.fileChooserResponse =  pFileChooser.showOpenDialog(this);
    if(this.fileChooserResponse == JFileChooser.APPROVE_OPTION) {
      fileSelectedPath.setText(this.getSelectedFilePath());
    } else {
      this.selectFileWarningMessage();
    }
  }
}
