import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

public class OpenHandler extends CommandHandler {
  private File file;
  private Viewer viewer;
  private StringBuilder container;
  private FileInputStream fis;
  private BufferedInputStream bis;

  public OpenHandler(Viewer viewer) {
    this.viewer = viewer;
    container = new StringBuilder();
  }

  public void command() {
    file = viewer.showFileDialog("Open");
    if(file != null) {
      fis = null;
      bis = null;

      try {
        fis = new FileInputStream(file);
        bis = new BufferedInputStream(fis);
        int unicode = -1;
        while ((unicode = bis.read()) != -1) {
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
          fis.close();
          bis.close();
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
