package commands;
import src.*;

public class ZoomResetHandler extends CommandHandler {

    private Viewer viewer;

    public ZoomResetHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        viewer.resetZoom();
    }
}
