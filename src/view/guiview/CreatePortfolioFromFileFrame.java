package view.guiview;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.guicontroller.Features;

/**
 * This class is a View class for creating of Portfolio from a file given by user.
 */
public class CreatePortfolioFromFileFrame extends AbstractFrame {

  private final JButton backButton;
  private final JButton submitButton;
  private final JButton selectFileButton;

  private final JTextField pNameInput;

  private JFileChooser pFileChooser;

  private int fileChooserResponse;

  private final JLabel fileSelectedPath;

  /**
   * Initiates and constructs the CreatePortfolioFromFileFrame view with a grid layout and the
   * required fields.
   */
  public CreatePortfolioFromFileFrame() {
    super("Load A Portfolio From File.");


    this.setMinimumSize(new Dimension(1000,500));

    JPanel formPanel = new JPanel(new GridLayout(10, 1, 0,10));

    JLabel display = new JLabel("Create a New Portfolio From File.");
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
      if (this.fileChooserResponse == JFileChooser.APPROVE_OPTION) {
        try {

          String createdFilepath = features.setCreatePortfolioFromFile(
                  pNameInput.getText(),
                  this.getSelectedFilePath()
          );
          this.displaySuccessMessage("Portfolio: " + pNameInput.getText()
                          + ". Successfully Created.\n" + "It is stored at: "+createdFilepath);
          this.setVisible(false);

        } catch (IllegalArgumentException e) {
          this.displayErrorMessage(e.getMessage());
        }
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
    pFileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "CSV"));
    pFileChooser.setCurrentDirectory(new File("."));

    this.fileChooserResponse = pFileChooser.showOpenDialog(this);
    if (this.fileChooserResponse == JFileChooser.APPROVE_OPTION) {
      fileSelectedPath.setText(this.getSelectedFilePath());
    } else {
      this.selectFileWarningMessage();
    }
  }
}
