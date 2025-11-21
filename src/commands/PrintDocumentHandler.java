package commands;
import src.*;

public class PrintDocumentHandler extends CommandHandler{
    private Viewer viewer;

    public PrintDocumentHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        viewer.printDocument();
    }
}
