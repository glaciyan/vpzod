package cc.glaciyan.vpzod;

import com.vp.plugin.view.IDialog;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GenerateZodDialog {
  private JPanel contentPanel;
  private JButton okButton;
  private JButton cancelButton;
  private JTextField pathTextField;
  private JButton browseButton;
  private IDialog dialog;

  public GenerateZodDialog(String initialPath) {
    contentPanel.setSize(new Dimension(430, 140));

    pathTextField.setText(initialPath);

    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        File file = new File(pathTextField.getText());
        if (isFileInvalid(file)) return;

        try {
          ZodGenerator.generateZodSchema(file);

          Configurations.getInstance().getProjectConfigurations().setSchemaPath(pathTextField.getText());
          Configurations.getInstance().save();
        }
        catch (Exception err) {
          JOptionPane.showMessageDialog(contentPanel, "Could not generate schema!\n" + err.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
        finally {
          dialog.close();
        }
      }
    });

    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dialog.close();
      }
    });

    browseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {

          public String getDescription() {
            return "*.ts";
          }

          public boolean accept(File file) {
            return file.isDirectory() || file.getName().toLowerCase().endsWith(".ts");
          }
        });
        fileChooser.showSaveDialog(contentPanel);

        File selectedFile = fileChooser.getSelectedFile();
        if (selectedFile == null) return;

        if (isFileInvalid(selectedFile)) return;

        pathTextField.setText(selectedFile.getAbsolutePath());
      }
    });
  }

  private boolean isFileInvalid(File selectedFile) {
    if (selectedFile.exists() && !selectedFile.isFile()) {
      JOptionPane.showMessageDialog(contentPanel, "Selected path is not a file!", "Error!", JOptionPane.ERROR_MESSAGE);
      return true;
    }

    if (!selectedFile.getAbsolutePath().endsWith(".ts")) {
      JOptionPane.showMessageDialog(contentPanel, "Selected path is not a typescript file!", "Error!", JOptionPane.ERROR_MESSAGE);
      return true;
    }
    return false;
  }

  public JPanel getContentPanel() {
    return contentPanel;
  }

  public JButton getOkButton() {
    return okButton;
  }

  public void setDialog(IDialog dialog) {
    this.dialog = dialog;
  }
}
