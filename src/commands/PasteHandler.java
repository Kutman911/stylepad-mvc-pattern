package commands;
import src.*;
public class PasteHandler extends CommandHandler {
  private Viewer viewer;

  public PasteHandler(Viewer viewer) {
      this.viewer = viewer;
  }

  public void command() {
    viewer.pasteText();
  }
}
