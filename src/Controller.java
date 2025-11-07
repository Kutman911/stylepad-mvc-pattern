import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.Timer;
import java.util.Optional;
import java.io.FileInputStream;


public class Controller implements ActionListener {

  private Viewer viewer;
  private File currentFile;
  private long lastModifiedTs;
  private Timer syncTimer;

  public Controller(Viewer viewer) {
    this.viewer = viewer;
    lastModifiedTs = -1;

    syncTimer = new Timer(2000, new SyncTimerListener());
    syncTimer.start();
  }

  public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
    if(command.equals("Open_Document")) {
      Optional<File> fileOptional = viewer.showFileDialog();

      if(fileOptional.isPresent()) {
        File file = fileOptional.get();
        FileInputStream in = null;
        try {
          in = new FileInputStream(file);
          StringBuilder container = new StringBuilder();
          int unicode = -1;
          while ((unicode = in.read()) != -1) {
            char symbol = (char) unicode;
            container.append(symbol);
          }
          viewer.update(container.toString());
        } catch (IOException ioe) {
          System.out.println(ioe);
        } finally {
          try {
            in.close();
          } catch (IOException ioe) {
            System.out.println(ioe);
          }
        }
      }
    }
    if (command.equals("New_Document")) {
      File file = viewer.showFileDialog("New");

    } else if (command.equals("Save_Document")) {
      if (currentFile != null) {
        String text = viewer.contentTextArea();
        boolean saved = saveToFile(currentFile, text);
        if (saved) {
          System.out.println("Saved to file");
        } else {
          System.out.println("Failed to save to file");
        }
      } else {
        File file = viewer.showFileDialog("Save");
        if (file != null) {
          currentFile = file;
          String text = viewer.contentTextArea();
          boolean saved = saveToFile(file, text);
          if (saved) {
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

    } else if (command.equals("Font")) {
      viewer.showFontDialog();
    }
  }

  private boolean saveToFile(File file, String text) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new FileWriter(file));
      out.println(text);
      out.flush();
      lastModifiedTs = file.lastModified();
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

  private class SyncTimerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (currentFile != null && currentFile.exists()) {
        long ts = currentFile.lastModified();
        if (ts != lastModifiedTs) {
          System.out.println("File was updated: " + currentFile.getName());
          lastModifiedTs = ts;
        }
      }
    }
  }
}
