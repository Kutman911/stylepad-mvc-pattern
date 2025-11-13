
public class ZoomResetHandler extends CommandHandler {

    private Viewer viewer;

    public ZoomResetHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    void command() {
        viewer.resetZoom();
    }
}
