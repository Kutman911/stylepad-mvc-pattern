import java.io.File;

public class OpenImageHandler implements CommandHandler {

  private Viewer viewer;

  public OpenImageHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  @Override
  public void command() {
    File imgFile = viewer.showFileDialog("Open");
    if (imgFile != null) {
        viewer.insertImage(imgFile);
    }
  }
}
