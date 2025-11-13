import java.io.File;

public class SaveAsHandler extends CommandHandler {
  private Viewer viewer;
  private SaveHandler saveHandler;

  public SaveAsHandler(Viewer viewer, SaveHandler saveHandler) {
      this.viewer = viewer;
      this.saveHandler = saveHandler;
  }

  public void command() {
      File newFile = viewer.showFileDialog("Save");
      if (newFile != null) {
          saveHandler.setCurrentFile(newFile);
          saveHandler.command();
      }
  }
}
