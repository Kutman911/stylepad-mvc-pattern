public class GoToLineHandler extends CommandHandler {
    private Viewer viewer;

    public GoToLineHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    void command() {
        viewer.showGoToLineDialog();
    }
}
