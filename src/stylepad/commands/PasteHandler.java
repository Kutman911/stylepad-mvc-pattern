package stylepad.commands;
import stylepad.Viewer;

public class PasteHandler extends CommandHandler {
  private Viewer viewer;

  public PasteHandler(Viewer viewer) {
      this.viewer = viewer;
  }

  public void command() {
    viewer.pasteText();
  }
}
