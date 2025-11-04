import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Controller implements ActionListener {

  private Viewer viewer;

  public Controller(Viewer viewer) {
      this.viewer = viewer;
  }

  public void actionPerformed(ActionEvent event) {
      String command = event.getActionCommand();
      System.out.println(command);
  }
}
