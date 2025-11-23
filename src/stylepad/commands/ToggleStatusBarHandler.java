package stylepad.commands;
import stylepad.Viewer;      
public class ToggleStatusBarHandler extends CommandHandler {

    private Viewer viewer;

    public ToggleStatusBarHandler(Viewer viewer) {
        this.viewer = viewer;
    }
    public void command() {
        boolean next = !viewer.isStatusBarVisible();
        viewer.setStatusBarVisible(next);
    }
}
