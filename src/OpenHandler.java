import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

public class OpenHandler extends CommandHandler {
  private File file;
  private Viewer viewer;
  private StringBuilder container;
  private FileInputStream fin;
  private BufferedInputStream bin;

  public OpenHandler(Viewer viewer) {
    this.viewer = viewer;
    container = new StringBuilder();
  }

  public void command() {
    file = viewer.showFileDialog("Open");
    if(file != null) {
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

      } catch (IOException ioe) {
        JOptionPane.showMessageDialog(viewer.getFrame(),
                    "Unable to open file", "Open", JOptionPane.WARNING_MESSAGE);
      } finally {
        try {
          fin.close();
          bin.close();
        } catch (IOException ioe) {
          JOptionPane.showMessageDialog(viewer.getFrame(),
                      "File closing error", "Open", JOptionPane.ERROR_MESSAGE);
        }
    }
  } else {
    JOptionPane.showMessageDialog(viewer.getFrame(),
                "File not found", "Open", JOptionPane.WARNING_MESSAGE);
  }
}
}
