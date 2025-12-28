package stylepad.commands;
import stylepad.Viewer;
import stylepad.Controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OpenHandler extends CommandHandler {
  private File file;
  private FileInputStream fin;
  private InputStreamReader reader;
  private BufferedReader br;
  private final Viewer viewer;
  private final Controller controller;
  private final StringBuilder container;

  public OpenHandler(Viewer viewer, Controller controller) {
    this.viewer = viewer;
    this.controller = controller;
    container = new StringBuilder();
  }

  public void command() {
    file = viewer.showFileDialog("Open");
    if (file != null) {
      fin = null;
      try {
        fin = new FileInputStream(file);
        reader = new InputStreamReader(fin, StandardCharsets.UTF_8);
        br = new BufferedReader(reader);

        container.setLength(0);

        int unicode;
        while ((unicode = br.read()) != -1) {
          container.append((char) unicode);
        }
        viewer.update(container.toString());
        controller.setCurrentFile(file);

      } catch (IOException ioe) {
        System.out.println("Unable to open file: " + ioe.getMessage());
      } finally {
        try {
          if (fin != null) {
            fin.close();
          }
        } catch (IOException ioe) {
          System.out.println("File closing error: " + ioe.getMessage());
        }
      }
    }
  }
}
