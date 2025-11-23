package stylepad.commands;
import stylepad.Viewer;

public class TimeAndDateHandler extends CommandHandler {
  private final Viewer viewer;

  public TimeAndDateHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    viewer.insertTimeAndDate();
  }
}
