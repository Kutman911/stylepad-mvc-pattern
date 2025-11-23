package stylepad.commands;
import stylepad.Viewer;

public class DeleteHandler extends CommandHandler {
  private Viewer viewer;

  public DeleteHandler(Viewer viewer) {
      this.viewer = viewer;
  }

  public void command() {
    viewer.deleteText();
  }
}
