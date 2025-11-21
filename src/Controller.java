import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.Timer;
import java.util.HashMap;
import java.util.Map;

public class Controller implements ActionListener {

  private Viewer viewer;
  private File currentFile;
  private FileSynchronizer fileSynchronizer;
  private Map<String, CommandHandler> map;

  public Controller(Viewer viewer) {

    this.viewer = viewer;
    this.fileSynchronizer = new FileSynchronizer(viewer);
    map = new HashMap<>();
    SaveHandler saveHandler = new SaveHandler(viewer);
    
    map.put("Save_Document", saveHandler);
    map.put("New_Document", new NewHandler(viewer, saveHandler));
    map.put("SaveAs_Document", new SaveAsHandler(viewer, saveHandler, this));
    map.put("Open_Document", new OpenHandler(viewer, this));
    map.put("Show_Font_Dialog", new ShowFontHandler(viewer));
    map.put("Print_Document", new PrintDocumentHandler(viewer));
    map.put("Wrap", new WrapHandler(viewer));
    map.put("Copy_Text", new CopyHandler(viewer));
    map.put("Paste_Text", new PasteHandler(viewer));
    map.put("Cut_Text", new CutHandler(viewer));
    map.put("Delete_Text", new DeleteHandler(viewer));
    map.put("View_Toggle_StatusBar", new ToggleStatusBarHandler(viewer));
    map.put("View_Toggle_CharCounter", new ToggleCharCounterHandler(viewer));
    map.put("View_ZoomIn", new ZoomInHandler(viewer));
    map.put("View_ZoomOut", new ZoomOutHandler(viewer));
    map.put("View_ZoomReset", new ZoomResetHandler(viewer));
    map.put("Exit", new ExitHandler(viewer));
    map.put("Time_And_Date", new TimeAndDateHandler(viewer));
    map.put("Open_Image", new OpenImageHandler(viewer));
    map.put("Find_Text", new FindTextHandler(viewer));
    map.put("Go_To_Line", new GoToLineHandler(viewer));
    map.put("Select_All_Text", new SelectAllHandler(viewer));

  }

  public void actionPerformed(ActionEvent event) {
    String command = event.getActionCommand();
    CommandHandler object = map.get(command);

    if (object != null) {
      object.command();
    }

  }

  public void setCurrentFile(File file) {
    this.currentFile = file;
    fileSynchronizer.setCurrentFile(file);
  }

  public File getCurrentFile() {
    return currentFile;
  }
}
