import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Controller implements ActionListener {

private Viewer viewer;
private File currentFile;

public Controller(Viewer viewer) {
  this.viewer = viewer;
}

public void actionPerformed(ActionEvent event) {
  String command = event.getActionCommand();

  if (command.equals("New_Document")) {
    File file = viewer.showFileDialog("New");

} else if (command.equals("Save_Document")) {
    if (currentFile != null) {
      String text = viewer.contentTextArea();
      boolean b = saveToFile(currentFile, text);
      if (b) {
          System.out.println("Saved to file");
      } else {
          System.out.println("Failed to save to file");
      }
  } else {
      File file = viewer.showFileDialog("Save");
      if (file != null) {
          currentFile = file;
          String text = viewer.contentTextArea();
          boolean b = saveToFile(file, text);
          if (b) {
              System.out.println("Saved to file");
          } else {
              System.out.println("Failed to save to file");
          }
        }
      }
  } else if (command.equals("SaveAs_Document")) {
      File file = viewer.showFileDialog("Save");
      if (file != null) {
          currentFile = file;
          String text = viewer.contentTextArea();
          saveToFile(file, text);
      }
  }

}
private boolean saveToFile(File file, String text) {

    PrintWriter out = null;

    try {
        out = new PrintWriter(new FileWriter(file));
        out.println(text);
        out.flush();
        return true;
    } catch (IOException ioe) {
        System.out.println(ioe);
        return false;
    } finally {
        if (out != null) {
            out.close();
        }
    }

 }
}
