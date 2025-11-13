
public class DeleteHandler extends CommandHandler {
  private Viewer viewer;

  public DeleteHandler(Viewer viewer) {
      this.viewer = viewer;
  }

  void command() {
    viewer.deleteText();
  }
}
