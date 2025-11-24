package stylepad.commands;
import stylepad.Viewer;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import java.awt.Container;
import java.awt.Image;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public class OpenImageHandler extends CommandHandler {
  private Viewer viewer;

  public OpenImageHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  public void command() {
    File imgFile = viewer.showFileDialog("Open");
    if (imgFile == null || !imgFile.exists()) return;

    Image img = new ImageIcon(imgFile.getAbsolutePath()).getImage();

    JComponent imgComp = new JComponent() {
      private Point prev;
      private boolean resizing = false;
      private static final int HANDLE = 8;
      {
        setSize(img.getWidth(null), img.getHeight(null));
        addMouseListener(new MouseAdapter() {
          public void mousePressed(java.awt.event.MouseEvent e) {
            prev = e.getPoint();
            resizing = e.getX() >= getWidth() - HANDLE && e.getY() >= getHeight() - HANDLE;
          }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
          public void mouseDragged(java.awt.event.MouseEvent e) {
            int dx = e.getX() - prev.x;
            int dy = e.getY() - prev.y;
            if (resizing) setSize(Math.max(getWidth() + dx, 10), Math.max(getHeight() + dy, 10));
            else setLocation(getX() + dx, getY() + dy);
            prev = e.getPoint();
            revalidate();
            repaint();
          }
        });
      }
      
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        g.fillRect(getWidth() - HANDLE, getHeight() - HANDLE, HANDLE, HANDLE);
      }
    };

    Container parent = viewer.getTextPane().getParent();
    if (!(parent instanceof JLayeredPane)) parent.setLayout(null);
    parent.add(imgComp);
    imgComp.setLocation(50, 50);
    imgComp.repaint();
  }
}
