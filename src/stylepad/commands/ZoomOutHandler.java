package stylepad.commands;
import stylepad.Viewer;      

public class ZoomOutHandler extends CommandHandler {

    private Viewer viewer;

    public ZoomOutHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        viewer.zoomOut();
    }
}
