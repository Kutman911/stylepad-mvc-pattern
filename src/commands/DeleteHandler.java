package commands;
import src.*;

public class DeleteHandler extends CommandHandler {
  private Viewer viewer;

  public DeleteHandler(Viewer viewer) {
      this.viewer = viewer;
  }

  public void command() {
    viewer.deleteText();
  }
}
