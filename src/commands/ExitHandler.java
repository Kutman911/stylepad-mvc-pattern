package commands;
import src.*;

public class ExitHandler extends CommandHandler {

  private Viewer viewer;

  public ExitHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    viewer.getFrame().dispose();
  }
}
