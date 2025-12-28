package stylepad.commands;
import stylepad.Viewer;

public class NewHandler extends CommandHandler {
    private Viewer viewer;
    private SaveHandler saveHandler;

    public NewHandler(Viewer viewer, SaveHandler saveHandler) {
        this.viewer = viewer;
        this.saveHandler = saveHandler;
    }

    public void command() {
        if (viewer != null) {
            viewer.update("");
            saveHandler.setCurrentFile(null);
        }
    }
}
