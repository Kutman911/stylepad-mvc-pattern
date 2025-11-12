public class PasteHandler extends CommandHandler {
  private Viewer viewer;

  public PasteHandler(Viewer viewer) {
      this.viewer = viewer;
  }

  void command() {
    viewer.pasteText();
  }
}
