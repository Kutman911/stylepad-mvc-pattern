import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

public class OpenHandler extends CommandHandler {
  private File file;
  private Viewer viewer;
  private Controller controller;
  private StringBuilder container;
  private FileInputStream fin;
  private BufferedInputStream bin;

  public OpenHandler(Viewer viewer, Controller controller) {
    this.viewer = viewer;
    this.controller = controller;
    container = new StringBuilder();
  }

  public void command() {
    file = viewer.showFileDialog("Open");
    if (file != null) {
      fin = null;
      bin = null;
      try {
        fin = new FileInputStream(file);
        bin = new BufferedInputStream(fin);
        int unicode = -1;
        while ((unicode = bin.read()) != -1) {
          char symbol = (char) unicode;
          container.append(symbol);
        }
        viewer.update(container.toString());
        container.setLength(0);
        controller.setCurrentFile(file);

      } catch (IOException ioe) {
        System.out.println("Unable to open file: " + ioe.getMessage());
      } finally {
        try {
          if (fin != null) {
            fin.close();
          }
          if (bin != null) {
            bin.close();
          }
        } catch (IOException ioe) {
          System.out.println("File closing error: " + ioe.getMessage());
        }
      }
    }
  }
}
