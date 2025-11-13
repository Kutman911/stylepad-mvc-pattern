
public class ToggleCharCounterHandler extends CommandHandler {

    private Viewer viewer;

    public ToggleCharCounterHandler(Viewer viewer) {
        this.viewer = viewer;
    }
    void command() {
        boolean next = !viewer.isCharCounterVisible();
        viewer.setCharCounterVisible(next);
    }
}
