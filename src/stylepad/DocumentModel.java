package stylepad;

import java.io.File;
import stylepad.commands.FileSynchronizer;

public class DocumentModel {
  
  private File currentFile;
  private FileSynchronizer fileSynchronizer;

  public DocumentModel(Viewer viewer) {
    this.fileSynchronizer = new FileSynchronizer(viewer, this);
  }

  public File getCurrentFile() {
    return currentFile;
  }

  public void setCurrentFile(File file) {
    this.currentFile = file;
    fileSynchronizer.setCurrentFile(file);
  }

}
