import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.Timer;
import java.util.HashMap;
import java.util.Map;
public class Controller implements ActionListener {

  private Viewer viewer;
  private File currentFile;
  private long lastModifiedTs;
  private Timer syncTimer;
  private Map<String, CommandHandler> map;

  public Controller(Viewer viewer) {
    this.viewer = viewer;
    lastModifiedTs = -1;
    syncTimer = new Timer(2000, new SyncTimerListener());
    syncTimer.start();

    map = new HashMap<>();
    map.put("Save_Document", new SaveHandler());
  }

  public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
    CommandHandler object = map.get(command);
    if (object != null) {
      currentFile = viewer.showFileDialog("Save Document");
      object.command();
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
        try { bufferedInputStream.close();
        } catch (IOException ignore) {}
      }
      if (fileInputStream != null) {
        try { fileInputStream.close();
        } catch (IOException ignore) {}
      }
    }
  }

  private class SyncTimerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (currentFile != null && currentFile.exists()) {
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
    }
  }
}
