package commands;
import src.*;

public class GoToLineHandler extends CommandHandler {
    private Viewer viewer;

    public GoToLineHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        viewer.showGoToLineDialog();
    }
}
