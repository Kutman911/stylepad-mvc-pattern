package stylepad.commands;
import stylepad.Viewer;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveHandler extends CommandHandler {
  private File currentFile;
  private Viewer viewer;

  public SaveHandler(Viewer viewer) {
      this.viewer = viewer;
  }

  public void setCurrentFile(File file) {
      this.currentFile = file;
  }
  private boolean saveToFile(File file, Document content) {
      try {
          String text = content.getText(0, content.getLength());

          Path filePath = file.toPath();
          Files.writeString(filePath, text, StandardCharsets.UTF_8);

          return true;
      } catch (IOException | BadLocationException e) {
          System.out.println("Error saving file: " + e);
          return false;
      }
  }

  public void command() {
      if (currentFile == null) {
          currentFile = viewer.showFileDialog("Save");
          if (currentFile == null) {
              System.out.println("Save canceled");
              return;
          }
      }

      Document text = viewer.contentTextPane();
      boolean result = saveToFile(currentFile, text);
      viewer.showResultSaveDocumentIntoModel(result);
  }



}
