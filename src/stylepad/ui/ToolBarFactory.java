package stylepad.ui;
import stylepad.Controller;
import stylepad.Viewer;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class ToolBarFactory {

  public static JToolBar createToolBar(Controller controller, Viewer viewer) {
    JToolBar toolBar = new JToolBar();
    toolBar.setFloatable(false);
    toolBar.setBackground(new Color(255, 245, 250));
    toolBar.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, new Color(255, 105, 180)));
    toolBar.setMargin(new Insets(4, 4, 4, 4));

    JButton newButton   = createToolbarButton("Images/new.png", "New", "New_Document", controller);
    JButton openButton  = createToolbarButton("Images/open.png", "Open", "Open_Document", controller);
    JButton saveButton  = createToolbarButton("Images/save.png", "Save", "Save_Document", controller);

    JButton cutButton   = createToolbarButton("Images/cut.png", "Cut","Cut_Text", controller);
    JButton copyButton  = createToolbarButton("Images/copy.png", "Copy", "Copy_Text", controller);
    JButton pasteButton = createToolbarButton("Images/paste.png", "Paste", "Paste_Text", controller);

    JButton alignLeftButton   = createToolbarButton("Images/align_left.png", "Align left");
    JButton alignCenterButton = createToolbarButton("Images/align_center.png", "Align center");
    JButton alignRightButton  = createToolbarButton("Images/align_right.png", "Align right");

    alignLeftButton.addActionListener(e -> viewer.alignLeft());
    alignCenterButton.addActionListener(e -> viewer.alignCenter());
    alignRightButton.addActionListener(e -> viewer.alignRight());

    toolBar.add(newButton);
    toolBar.add(openButton);
    toolBar.add(saveButton);
    toolBar.add(cutButton);
    toolBar.add(copyButton);
    toolBar.add(pasteButton);
    toolBar.add(alignLeftButton);
    toolBar.add(alignCenterButton);
    toolBar.add(alignRightButton);

    return toolBar;
  }

  private static JButton createBaseToolbarButton(String iconPath, String tooltip) {
    java.net.URL url = ToolBarFactory.class.getResource("/stylepad/" + iconPath);
    JButton button = new JButton(new ImageIcon(url));
    button.setToolTipText(tooltip);
    button.setFocusable(false);
    button.setOpaque(true);

    button.setBackground(new Color(255, 240, 245));
    button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(255, 220, 235));
      }
      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(255, 240, 245));
      }
    });

    return button;
  }

  private static JButton createToolbarButton(String iconPath, String tooltip, String actionCommand, Controller controller) {
    JButton button = createBaseToolbarButton(iconPath, tooltip);
    button.addActionListener(controller);
    button.setActionCommand(actionCommand);
    return button;
  }

  private static JButton createToolbarButton(String iconPath, String tooltip) {
    return createBaseToolbarButton(iconPath, tooltip);
  }


}
