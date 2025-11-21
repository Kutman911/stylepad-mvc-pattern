package commands;
import src.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class FileSynchronizer implements ActionListener {

  private Viewer viewer;
  private File currentFile;
  private long lastModifiedTs;
  private Timer timer;

  public FileSynchronizer(Viewer viewer) {
    this.viewer = viewer;
    this.lastModifiedTs = -1;
    this.timer = new Timer(2000, this);
    this.timer.start();
  }

  public void setCurrentFile(File file) {
    this.currentFile = file;
    if (file != null && file.exists()) {
      this.lastModifiedTs = file.lastModified();
    } else {
      this.lastModifiedTs = -1;
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (currentFile == null || !currentFile.exists()) {
      return;
    }

    long ts = currentFile.lastModified();
    if (ts != lastModifiedTs) {
      try {
        String content = readFile(currentFile);
        viewer.update(content);
        lastModifiedTs = ts;
        System.out.println("File reloaded from disk: " + currentFile.getName());
      } catch (IOException ex) {
        System.out.println("Sync error: " + ex);
      }
    }
  }

  private String readFile(File file) throws IOException {
    FileInputStream fileInputStream = null;
    BufferedInputStream bufferedInputStream = null;
    StringBuilder container = new StringBuilder();

    try {
      fileInputStream = new FileInputStream(file);
      bufferedInputStream = new BufferedInputStream(fileInputStream);
      int unicode = -1;
      while ((unicode = bufferedInputStream.read()) != -1) {
        char symbol = (char) unicode;
        container.append(symbol);
      }
      return container.toString();
    } finally {
      if (bufferedInputStream != null) {
        try { bufferedInputStream.close(); } catch (IOException ignore) {}
      }
      if (fileInputStream != null) {
        try { fileInputStream.close(); } catch (IOException ignore) {}
      }
    }
  }
}
