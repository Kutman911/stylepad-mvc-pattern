package commands;
import src.*;
public class ShowFontHandler extends CommandHandler {
  private Viewer viewer;

  public ShowFontHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    viewer.showFontDialog();
  }
}
