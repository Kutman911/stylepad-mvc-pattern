import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Viewer {

  private JTextArea textArea;
  private JFileChooser fileChooser;

    public Viewer() {
      Controller controller = new Controller(this);
      createGUI(controller);
    }

    private void createGUI(Controller controller) {

      UIManager.put("MenuBar.background", new Color(255, 204, 232));
      UIManager.put("MenuBar.foreground", Color.WHITE);
      UIManager.put("MenuBar.border", BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 105, 180)));
      UIManager.put("Menu.selectionBackground", new Color(255, 105, 180));
      UIManager.put("Menu.selectionForeground", Color.WHITE);
      UIManager.put("MenuItem.selectionBackground", new Color(255, 160, 200));
      UIManager.put("MenuItem.selectionForeground", Color.WHITE);
      UIManager.put("MenuItem.background", new Color(255, 228, 240));
      UIManager.put("MenuItem.foreground", new Color(199, 21, 133));

      JMenuBar menuBar = createJMenuBar(controller);

      Font fontTextArea = new Font("JetBrains Mono", Font.PLAIN, 16);
      Color colorTextArea = Color.BLACK;

      textArea = new JTextArea();
      textArea.setFont(fontTextArea);
      textArea.setForeground(colorTextArea);
      textArea.setBackground(Color.WHITE);
      textArea.setCaretColor(new Color(199, 21, 133));
      textArea.setSelectedTextColor(Color.WHITE);
      textArea.setSelectionColor(new Color(255, 105, 180));
      textArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, new Color(255, 204, 232)),BorderFactory.createEmptyBorder(8, 10, 8, 10)));
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);

      textArea.getDocument().addDocumentListener(new DocumentListener() {
      private void changedUpdate() {
        String text = textArea.getText();
        char unicodeSymbol = text.charAt(text.length() - 1);
        if (text.isEmpty()) {
          return;
        } else if((unicodeSymbol >= 65 && unicodeSymbol <= 90 || unicodeSymbol == ' ') ||
                    unicodeSymbol >= 97 && unicodeSymbol <= 122 || unicodeSymbol == ' ') {
                      textArea.setFont(fontTextArea);
        } else {
          textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        }
      }
      @Override
      public void insertUpdate(DocumentEvent e) {
        changedUpdate();
      }
      @Override
      public void removeUpdate(DocumentEvent e) {

      }
      @Override
      public void changedUpdate(DocumentEvent e) {

      }
    });

      JScrollPane scrollPane = new JScrollPane(textArea);
      scrollPane.setBorder(BorderFactory.createMatteBorder(0,2,2,2, new Color(255, 105, 180)));



      JFrame frame = new JFrame("StylePad MVC Pattern");
      frame.setSize(500, 500);
      frame.setLocation(320, 140);
      frame.setJMenuBar(menuBar);
      frame.add("Center", scrollPane);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

    }

    private JMenuBar createJMenuBar(Controller controller) {
      JMenuBar menuBar = new JMenuBar();

      JMenu fileMenu = createFileMenu(controller);
      JMenu formatMenu = createFormatMenu(controller);

      menuBar.add(fileMenu);
      menuBar.add(formatMenu);

      return menuBar;
    }


    private JMenu createFileMenu(Controller controller) {

      JMenuItem newDocument = new JMenuItem("New",new ImageIcon("images/new.png"));
      newDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
      newDocument.addActionListener(controller);
      newDocument.setActionCommand("New_Document");

      JMenuItem openDocument = new JMenuItem("Open ...", new ImageIcon("images/open.gif"));
      openDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
      openDocument.addActionListener(controller);
      openDocument.setActionCommand("Open_Document");
      JMenuItem saveDocument = new JMenuItem("Save", new ImageIcon("images/save.gif"));
      saveDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
      saveDocument.addActionListener(controller);
      saveDocument.setActionCommand("Save_Document");

      JMenuItem saveAsDocument = new JMenuItem("Save As ...", new ImageIcon("images/save_as.gif"));
      saveAsDocument.addActionListener(controller);
      saveAsDocument.setActionCommand("SaveAs_Document");

      JMenuItem printDocument = new JMenuItem("Print ...", new ImageIcon("images/print.gif"));
      printDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
      printDocument.addActionListener(controller);
      printDocument.setActionCommand("Print_Document");

      JMenuItem imageDocument = new JMenuItem("Open Image", new ImageIcon("images/wordSpace.gif"));
      imageDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
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


    private JMenu createFormatMenu(Controller controller) {
      JMenuItem chooseFontWindow = new JMenuItem("Font", new ImageIcon(""));
      chooseFontWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
      chooseFontWindow.addActionListener(controller);
      chooseFontWindow.setActionCommand("Open_Font_Window");

      JMenu formatMenu = new JMenu("Format");
      formatMenu.setMnemonic('F');
      formatMenu.add(chooseFontWindow);

      return formatMenu;
    }

    public void update(String text) {
      textArea.setText(text);
    }

    private void applyFont() {
      String fontName = (String) fontBox.getSelectedItem();
      int fontSize = (Integer) sizeBox.getSelectedItem();

      int style = Font.PLAIN;
      if (boldCheck.isSelected()) style |= Font.BOLD;
      if (italicCheck.isSelected()) style |= Font.ITALIC;

      viewer.setFontSettings(fontName, style, fontSize);
      dispose();
    }
    public File showFileDialog(String status) {

        if (fileChooser == null) {
          fileChooser = new JFileChooser();

          FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
          FileNameExtensionFilter docxFilter = new FileNameExtensionFilter("Word Documents (*.docx)", "docx");
          FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON Files (*.json)", "json");
          FileNameExtensionFilter allFilter = new FileNameExtensionFilter("All Files (*.*)", "*");

          fileChooser.addChoosableFileFilter(txtFilter);
          fileChooser.addChoosableFileFilter(docxFilter);
          fileChooser.addChoosableFileFilter(jsonFilter);
          fileChooser.addChoosableFileFilter(allFilter);

          fileChooser.setFileFilter(txtFilter);
        }

        int returnValue;
        if (status.equals("Open")) {
          returnValue = fileChooser.showOpenDialog(null);
        } else {
          returnValue = fileChooser.showSaveDialog(null);
        }

        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();

          FileNameExtensionFilter filter = (FileNameExtensionFilter) fileChooser.getFileFilter();
          String ext = filter.getExtensions()[0];

          if (!file.getName().toLowerCase().endsWith("." + ext)) {
            file = new File(file.getAbsolutePath() + "." + ext);
          }

          return file;
        }

        return null;
      }


      public String contentTextArea() {
        return textArea.getText();
      }
}
