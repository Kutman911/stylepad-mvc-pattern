public class CopyHandler extends CommandHandler {
  private Viewer viewer;

  public CopyHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  void command() {
    viewer.copyText();
  }
}
