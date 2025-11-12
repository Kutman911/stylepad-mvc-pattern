public class PrintDocumentHandler extends CommandHandler{
    private Viewer viewer;

    public PrintDocumentHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    void command() {
        viewer.printDocument();
    }
}
