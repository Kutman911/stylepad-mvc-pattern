package commands;
import src.*;

public class ZoomOutHandler extends CommandHandler {

    private Viewer viewer;

    public ZoomOutHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        viewer.zoomOut();
    }
}
