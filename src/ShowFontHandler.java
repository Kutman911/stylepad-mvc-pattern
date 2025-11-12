
public class ShowFontHandler extends CommandHandler{
    private Viewer viewer;
    public ShowFontHandler(Viewer viewer) {
        this.viewer = viewer;
    }
    void command() {
        viewer.showFontDialog();
    }
}
