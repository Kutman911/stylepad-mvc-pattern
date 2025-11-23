package stylepad.commands;
import stylepad.Viewer;

public class ShowFontHandler extends CommandHandler {
  private Viewer viewer;

  public ShowFontHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    viewer.showFontDialog();
  }
}
