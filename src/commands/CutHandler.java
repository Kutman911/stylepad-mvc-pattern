package commands;
import src.*;

public class CutHandler extends CommandHandler {
  private Viewer viewer;

  public CutHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    viewer.cutText();
  }
}
