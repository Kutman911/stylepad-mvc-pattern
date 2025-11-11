public class ZoomOutHandler extends CommandHandler {

    private Viewer viewer;

    public ZoomOutHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    void command() {
        viewer.zoomOut();
    }
}
