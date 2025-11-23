package stylepad.commands;
import stylepad.Viewer;

public class CutHandler extends CommandHandler {
  private Viewer viewer;

  public CutHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    viewer.cutText();
  }
}
