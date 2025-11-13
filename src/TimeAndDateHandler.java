public class TimeAndDateHandler extends CommandHandler {
  private final Viewer viewer;

  public TimeAndDateHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  void command() {
    viewer.insertTimeAndDate();
  }
}
