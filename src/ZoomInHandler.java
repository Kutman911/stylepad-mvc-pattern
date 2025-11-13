
public class ZoomInHandler extends CommandHandler {

    private Viewer viewer;

    public ZoomInHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    void command() {
        viewer.zoomIn();
    }
}
