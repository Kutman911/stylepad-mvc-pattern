import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveHandler extends CommandHandler {
    private File currentFile;
    private Viewer viewer;

    public SaveHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    private boolean saveToFile(File file, String content) {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.print(content);
            return true;
        } catch (IOException ioe) {
            System.out.println("Error saving file: " + ioe);
            return false;
        }
    }


    public void command() {
        if (currentFile == null) {
            currentFile = viewer.showFileDialog("Save");
            if (currentFile == null) {
                System.out.println("Save canceled");
                return;
            }
        }

        String text = viewer.contentTextArea();
        if (text == null) text = "";

        boolean result = saveToFile(currentFile, text);

        viewer.showResultSaveDocumentIntoModel(result);
    }
}
