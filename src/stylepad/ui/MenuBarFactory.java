package stylepad.ui;
import stylepad.Controller;
import stylepad.Viewer;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public final class MenuBarFactory {

  public static JMenuBar createJMenuBar(Controller controller, Viewer viewer) {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu   = createFileMenu(controller);
    JMenu editMenu   = createEditMenu(controller);
    JMenu formatMenu = createFormatMenu(controller);
    JMenu viewMenu   = createViewMenu(controller);
    JMenu helpMenu   = createHelpMenu(viewer);

    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    menuBar.add(formatMenu);
    menuBar.add(viewMenu);
    menuBar.add(helpMenu);

    return menuBar;
  }

  private static JMenu createFileMenu(Controller controller) {

    JMenuItem newDocument = new JMenuItem("New", new ImageIcon(Viewer.class.getResource("Images/new.png")));
    newDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    newDocument.addActionListener(controller);
    newDocument.setActionCommand("New_Document");

    JMenuItem openDocument = new JMenuItem("Open ...", new ImageIcon(Viewer.class.getResource("Images/open.png")));
    openDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    openDocument.addActionListener(controller);
    openDocument.setActionCommand("Open_Document");

    JMenuItem saveDocument = new JMenuItem("Save", new ImageIcon(Viewer.class.getResource("Images/save.png")));
    saveDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    saveDocument.addActionListener(controller);
    saveDocument.setActionCommand("Save_Document");

    JMenuItem saveAsDocument = new JMenuItem("Save As ...", new ImageIcon(Viewer.class.getResource("Images/save_as.png")));
    saveAsDocument.addActionListener(controller);
    saveAsDocument.setActionCommand("SaveAs_Document");

    JMenuItem printDocument = new JMenuItem("Print ...", new ImageIcon(Viewer.class.getResource("Images/print.png")));
    printDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    printDocument.addActionListener(controller);
    printDocument.setActionCommand("Print_Document");

    JMenuItem imageDocument = new JMenuItem("Open Image", new ImageIcon(Viewer.class.getResource("Images/open.png")));
    imageDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
    imageDocument.addActionListener(controller);
    imageDocument.setActionCommand("Open_Image");

    JMenuItem closeProgram = new JMenuItem("Exit");
    closeProgram.addActionListener(controller);
    closeProgram.setActionCommand("Exit");

    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic('F');
    fileMenu.add(newDocument);
    fileMenu.add(openDocument);
    fileMenu.add(saveDocument);
    fileMenu.add(saveAsDocument);
    fileMenu.add(new JSeparator());
    fileMenu.add(imageDocument);
    fileMenu.add(new JSeparator());
    fileMenu.add(printDocument);
    fileMenu.add(new JSeparator());
    fileMenu.add(closeProgram);

    return fileMenu;
  }

  private static JMenu createEditMenu(Controller controller) {

    JMenuItem cutText = new JMenuItem("Cut", new ImageIcon(Viewer.class.getResource("Images/cut.png")));
    cutText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    cutText.addActionListener(controller);
    cutText.setActionCommand("Cut_Text");

    JMenuItem copyText = new JMenuItem("Copy", new ImageIcon(Viewer.class.getResource("Images/copy.png")));
    copyText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    copyText.addActionListener(controller);
    copyText.setActionCommand("Copy_Text");

    JMenuItem pasteText = new JMenuItem("Paste", new ImageIcon(Viewer.class.getResource("Images/paste.png")));
    pasteText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    pasteText.addActionListener(controller);
    pasteText.setActionCommand("Paste_Text");

    JMenuItem deleteText = new JMenuItem("Delete", new ImageIcon(Viewer.class.getResource("Images/delete.png")));
    deleteText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
    deleteText.addActionListener(controller);
    deleteText.setActionCommand("Delete_Text");

    JMenuItem findText = new JMenuItem("Find", new ImageIcon(Viewer.class.getResource("Images/find.png")));
    findText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
    findText.addActionListener(controller);
    findText.setActionCommand("Find_Text");

    JMenuItem goToLine = new JMenuItem("Go", new ImageIcon(Viewer.class.getResource("Images/go.png")));
    goToLine.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
    goToLine.addActionListener(controller);
    goToLine.setActionCommand("Go_To_Line");

    JMenuItem selectAllText = new JMenuItem("Select All Text", new ImageIcon(Viewer.class.getResource("Images/select_all.png")));
    selectAllText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    selectAllText.addActionListener(controller);
    selectAllText.setActionCommand("Select_All_Text");

    JMenuItem timeAndDate = new JMenuItem("Time and date", new ImageIcon(Viewer.class.getResource("Images/time_and_date.png")));
    timeAndDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
    timeAndDate.addActionListener(controller);
    timeAndDate.setActionCommand("Time_And_Date");

    JMenu editMenu = new JMenu("Edit");
    editMenu.setMnemonic('E');
    editMenu.add(cutText);
    editMenu.add(copyText);
    editMenu.add(pasteText);
    editMenu.add(deleteText);
    editMenu.add(new JSeparator());
    editMenu.add(findText);
    editMenu.add(goToLine);
    editMenu.add(new JSeparator());
    editMenu.add(selectAllText);
    editMenu.add(timeAndDate);

    return editMenu;
  }

  private static JMenu createFormatMenu(Controller controller) {
    JMenu formatMenu = new JMenu("Format");

    JMenuItem wrapJMenuItem = new JMenuItem("Wrap", new ImageIcon(Viewer.class.getResource("Images/wrap.png")));
    wrapJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
    wrapJMenuItem.addActionListener(controller);
    wrapJMenuItem.setActionCommand("Wrap");

    JMenuItem fontJMenuItem = new JMenuItem("Font", new ImageIcon(Viewer.class.getResource("Images/font.png")));
    fontJMenuItem.setAccelerator(
    KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
    fontJMenuItem.addActionListener(controller);
    fontJMenuItem.setActionCommand("Show_Font_Dialog");

    formatMenu.setMnemonic('F');
    formatMenu.add(wrapJMenuItem);
    formatMenu.add(fontJMenuItem);

    return formatMenu;
  }

  private static JMenu createViewMenu(Controller controller) {
    JMenu viewMenu = new JMenu("View");

    JMenuItem toggleStatus = new JMenuItem("Status Bar On/Off");
    toggleStatus.addActionListener(controller);
    toggleStatus.setActionCommand("View_Toggle_StatusBar");

    JMenuItem toggleChars = new JMenuItem("Char Counter On/Off");
    toggleChars.addActionListener(controller);
    toggleChars.setActionCommand("View_Toggle_CharCounter");

    JMenuItem zoomIn = new JMenuItem("Zoom In", new ImageIcon(Viewer.class.getResource("Images/zoom_in.png")));
    zoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK));
    zoomIn.addActionListener(controller);
    zoomIn.setActionCommand("View_ZoomIn");

    JMenuItem zoomOut = new JMenuItem("Zoom Out", new ImageIcon(Viewer.class.getResource("Images/zoom_out.png")));
    zoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
    zoomOut.addActionListener(controller);
    zoomOut.setActionCommand("View_ZoomOut");

    JMenuItem zoomReset = new JMenuItem("Reset Zoom");
    zoomReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));
    zoomReset.addActionListener(controller);
    zoomReset.setActionCommand("View_ZoomReset");

    viewMenu.setMnemonic('V');

    viewMenu.add(toggleStatus);
    viewMenu.add(toggleChars);
    viewMenu.add(new JSeparator());
    viewMenu.add(zoomIn);
    viewMenu.add(zoomOut);
    viewMenu.add(zoomReset);

    return viewMenu;
  }

  private static JMenu createHelpMenu(Viewer viewer) {
    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic('H');

    JMenuItem helpItem = new JMenuItem("View Help");
    helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
    helpItem.addActionListener(e -> viewer.showHelpDialog());
    helpMenu.add(helpItem);

    helpMenu.add(new JSeparator());

    JMenuItem aboutItem = new JMenuItem("About StylePad");
    aboutItem.addActionListener(e -> viewer.showAboutDialog());
    helpMenu.add(aboutItem);

    return helpMenu;
  }
}
