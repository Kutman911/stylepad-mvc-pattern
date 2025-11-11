import javax.swing.JMenuItem;

public class WrapHandler extends CommandHandler {
    private Viewer viewer;
    private JMenuItem menuItem;
    private boolean wrap = true;


    public WrapHandler(Viewer viewer) {
        this.viewer = viewer;
    }
    void command() {
        wrap = !wrap;
        viewer.getTextArea().setLineWrap(wrap);
        viewer.getTextArea().setWrapStyleWord(wrap);
    }
}
