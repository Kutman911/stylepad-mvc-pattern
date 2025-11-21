package commands;
import src.*;
public class ToggleCharCounterHandler extends CommandHandler {

    private Viewer viewer;

    public ToggleCharCounterHandler(Viewer viewer) {
        this.viewer = viewer;
    }
    public void command() {
        boolean next = !viewer.isCharCounterVisible();
        viewer.setCharCounterVisible(next);
    }
}
