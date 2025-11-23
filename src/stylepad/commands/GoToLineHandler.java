package stylepad.commands;
import stylepad.Viewer;

public class GoToLineHandler extends CommandHandler {
    private Viewer viewer;

    public GoToLineHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        viewer.showGoToLineDialog();
    }
}
