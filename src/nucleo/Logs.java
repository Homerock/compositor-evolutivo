package nucleo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.logging.*;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;



class Logs extends JFrame {
  private int width;

  private int height;

  private JTextArea textArea = null;

  private JScrollPane pane = null;

  public Logs(String title, int width, int height) {
    super(title);
    setSize(width, height);
    textArea = new JTextArea();
    textArea.setEditable(false);
    pane = new JScrollPane(textArea);
    getContentPane().add(pane);
    setVisible(false);
  }

  /**
   * This method appends the data to the text area.
   * 
   * @param data
   *            the Logging information data
   */
  public void showInfo(String data) {
    textArea.append(data);
    this.getContentPane().validate();
  }
}