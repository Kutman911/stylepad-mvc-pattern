package stylepad.commands;
import stylepad.Viewer;      

public class ZoomInHandler extends CommandHandler {

    private Viewer viewer;

    public ZoomInHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        viewer.zoomIn();
    }
}
