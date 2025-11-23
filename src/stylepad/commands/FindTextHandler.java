package stylepad.commands;
import stylepad.Viewer;

public class FindTextHandler extends CommandHandler {
    private Viewer viewer;

    public FindTextHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        viewer.showFindDialog();
    }
}
