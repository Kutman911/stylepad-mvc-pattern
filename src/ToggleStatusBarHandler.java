
public class ToggleStatusBarHandler extends CommandHandler {

    private Viewer viewer;

    public ToggleStatusBarHandler(Viewer viewer) {
        this.viewer = viewer;
    }
    void command() {
        boolean next = !viewer.isStatusBarVisible();
        viewer.setStatusBarVisible(next);
    }
}
