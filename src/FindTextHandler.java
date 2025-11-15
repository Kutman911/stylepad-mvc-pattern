public class FindTextHandler extends CommandHandler {
    private Viewer viewer;

    public FindTextHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    void command() {
        viewer.showFindDialog();
    }
}
