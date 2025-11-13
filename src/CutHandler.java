
public class CutHandler extends CommandHandler {
  private Viewer viewer;

  public CutHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  void command() {
    viewer.cutText();
  }
}
