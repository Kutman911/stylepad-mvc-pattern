import java.io.File;

public class OpenImageHandler extends CommandHandler {

  private Viewer viewer;

  public OpenImageHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    File imgFile = viewer.showFileDialog("Open");
    if (imgFile != null) {
        viewer.insertImage(imgFile);
    }
  }
}
