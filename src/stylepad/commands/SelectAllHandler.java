package stylepad.commands;
import stylepad.Viewer;

public class SelectAllHandler extends CommandHandler {
  private Viewer viewer;

  public SelectAllHandler(Viewer viewer) {
      this.viewer = viewer;
  }

  public void command() {
      viewer.getTextArea().requestFocusInWindow();
      viewer.getTextArea().selectAll();
  }
}
