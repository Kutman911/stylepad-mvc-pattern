import java.io.File;
import javax.swing.JOptionPane;

public class OpenImageHandler extends CommandHandler {

  private Viewer viewer;

  public OpenImageHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    File imgFile = viewer.showFileDialog("Open");

    if (imgFile != null) {

      String w = JOptionPane.showInputDialog(
        null,
        "Enter picture's width (px):",
        "Width",
        JOptionPane.PLAIN_MESSAGE
      );
      if (w == null) {
        return;
      }

      String h = JOptionPane.showInputDialog(
        null,
        "Enter picture's height (px):",
        "Heght",
        JOptionPane.PLAIN_MESSAGE
      );
      if (h == null){
        return;
      }

      try {
        int width = Integer.parseInt(w);
        int height = Integer.parseInt(h);

        viewer.insertImage(imgFile, width, height);

      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
          null,
          "Enter valid number",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      }
    }
  }
}
