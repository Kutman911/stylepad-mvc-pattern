package commands;
import src.*;

public class FindTextHandler extends CommandHandler {
    private Viewer viewer;

    public FindTextHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        viewer.showFindDialog();
    }
}
