import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import javax.swing.Icon;
import javax.swing.JOptionPane;


public class Viewer {

  private JTextArea textArea;
  private JFileChooser fileChooser;
  private JDialog dialog;
  private JFrame frame;
  private Icon icon;

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
      UIManager.put("Panel.background", new Color(255, 228, 240));
      UIManager.put("OptionPane.background", new Color(255, 228, 240));
      UIManager.put("Button.background", new Color(255, 204, 232));
      UIManager.put("Button.foreground", new Color(199, 21, 133));

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



      frame = new JFrame("StylePad MVC Pattern");
      frame.setSize(700, 600);
      frame.setLocationRelativeTo(null);
      frame.setJMenuBar(menuBar);
      frame.add("Center", scrollPane);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      Font fontTextArea = new Font(currentFontFamily, currentFontStyle, currentFontSize);
      textArea.setFont(fontTextArea);

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
      JMenu formatMenu = new JMenu("Format");

      JMenuItem wrapJMenuItem = new JMenuItem("Wrap", new ImageIcon(""));
      wrapJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

      JMenuItem fontJMenuItem = new JMenuItem("Font", new ImageIcon(""));
      fontJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
      fontJMenuItem.addActionListener(controller);
      fontJMenuItem.setActionCommand("Font");

      formatMenu.setMnemonic('F');
      formatMenu.add(wrapJMenuItem);
      formatMenu.add(fontJMenuItem);

      return formatMenu;
  }

    public void update(String text) {
      textArea.setText(text);
    }

    public void setFontSettings(String family, int style, int size) {
      currentFontFamily = family;
      currentFontStyle = style;
      currentFontSize = size;
      Font newFont = new Font(currentFontFamily, currentFontStyle, currentFontSize);
      textArea.setFont(newFont);
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

    public void showFontDialog() {
      int x = frame.getX();
      int y = frame.getY();

      if (dialog == null) {

        dialog = new JDialog(frame, "Font", true);

        JButton buttonOk = new JButton("OK");
        buttonOk.setBounds(250, 400, 100, 50);
        buttonOk.setFocusPainted(false);
        buttonOk.addActionListener(
            (eventButton) -> {
              // !!!!!!!!
              Font fontTextArea = new Font("Arial", Font.PLAIN, 16);
              textArea.setFont(fontTextArea);
              dialog.setVisible(false);
            });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(370, 400, 100, 50);
        buttonCancel.setFocusPainted(false);

        buttonCancel.addActionListener(
            (eventButton) -> {
              dialog.setVisible(false);
            });

        dialog.setSize(500, 500);
        dialog.setLocation(x + 100, y + 50);
        dialog.setResizable(false);
        dialog.setLayout(null);
        dialog.add(buttonOk);
        dialog.add(buttonCancel);
        dialog.setVisible(true);
      } else {
        dialog.setLocation(x + 100, y + 50);
        dialog.setVisible(true);
      }
    }

    public String contentTextArea() {
        return textArea.getText();
    }

    public void printDocument() {
      String content = textArea.getText();
      PrintDocument printDocument = new PrintDocument(content);
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPrintable(printDocument);
      boolean ok = job.printDialog();
      if (ok) {
        try {
          job.print();
          showResultPrintDocument();
        } catch (PrinterException ex) {

        }
      }
    }

    private void showResultPrintDocument() {
      if(icon == null) {
        icon = new ImageIcon(getClass().getResource("/images/duke_printer.png"));
      }
      JOptionPane.showMessageDialog(null,
      "The document has been successfully printed",
      "Printer Document Dialog - Stilepad MVC Pattern",
      JOptionPane.INFORMATION_MESSAGE,
      icon);
    }
}
