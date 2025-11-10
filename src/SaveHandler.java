import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveHandler extends CommandHandler {
    private File currentFile;
    private Viewer viewer;

    private boolean saveToFile(File file, String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(file));
            out.println(text);
            out.flush();
            return true;
        } catch (IOException ioe) {
            System.out.println(ioe);
            return false;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    void command() {
        if (currentFile != null) {
            String text = viewer.contentTextArea();
            boolean saved = saveToFile(currentFile, text);
            if (saved) {
                System.out.println("Saved to file");
            } else {
                System.out.println("Failed to save to file");
            }
    }
}}
