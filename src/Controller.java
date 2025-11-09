import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.Timer;
import java.util.Optional;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

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
    if (command.equals("Open_Document")) {
      currentFile = viewer.showFileDialog("Open");

      if (currentFile != null) {
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        StringBuilder container = new StringBuilder();
        try {
          fileInputStream = new FileInputStream(currentFile);
          bufferedInputStream = new BufferedInputStream(fileInputStream);
          int unicode = -1;
          while ((unicode = bufferedInputStream.read()) != -1) {
            char symbol = (char) unicode;
            container.append(symbol);
          }

          viewer.update(container.toString());
          lastModifiedTs = currentFile.lastModified();
        } catch (IOException ioe) {
          System.out.println("Error file: " + ioe);
        } finally {
          try {
            fileInputStream.close();
            bufferedInputStream.close();
          } catch (IOException ioe) {
            System.out.println("Error closing file: " + ioe);
          }
        }
      }
    } else if (command.equals("New_Document")) {
      currentFile = null;
      viewer.update("");
      lastModifiedTs = -1;

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

    } else if (command.equals("Print_Document")) {
      viewer.printDocument();
    } else if (command.equals("Exit")) {
            System.out.println("Exiting application");
            System.exit(0);
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
