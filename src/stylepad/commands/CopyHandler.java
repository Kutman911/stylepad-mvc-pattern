package stylepad.commands;
import stylepad.Viewer;

public class CopyHandler extends CommandHandler {
  private Viewer viewer;

  public CopyHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    viewer.copyText();
  }
}
