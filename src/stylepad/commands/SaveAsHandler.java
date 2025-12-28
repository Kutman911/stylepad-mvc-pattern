package stylepad.commands;
import stylepad.Viewer;
import stylepad.Controller;
import java.io.File;

public class SaveAsHandler extends CommandHandler {

  private Viewer viewer;
  private SaveHandler saveHandler;
  private Controller controller;

  public SaveAsHandler(Viewer viewer, SaveHandler saveHandler, Controller controller) {
    this.viewer = viewer;
    this.saveHandler = saveHandler;
    this.controller = controller;
  }

  public void command() {
    File newFile = viewer.showFileDialog("Save");

    if (newFile != null) {
      saveHandler.setCurrentFile(newFile);
      controller.setCurrentFile(newFile);
      saveHandler.command();
    }
  }
}
