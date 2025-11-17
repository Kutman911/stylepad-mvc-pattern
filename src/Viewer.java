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
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class Viewer {

    private JTextPane textPane;
    private JFileChooser fileChooser;
    private JFrame frame;
    private Icon icon;
    private String currentFontFamily;
    private int currentFontStyle;
    private int currentFontSize;
    private JPanel statusBar;
    private JLabel charCountLabel;
    private JLabel encodingLabel;
    private JLabel zoomLabel;
    private String currentEncoding;
    private String[] fontNames;
    public JMenuItem wrapJMenuItem;
    private boolean statusBarVisible;
    private boolean charCounterVisible;
    private FindDialog findDialog;
    private FontChooserDialog fontDialog;
    private GoToLineDialog goToLineDialog;

    public Viewer() {
      Controller controller = new Controller(this);
      createGUI(controller);
    }

    private void createGUI(Controller controller) {

      statusBarVisible = true;
      charCounterVisible = true;
      currentFontFamily = "JetBrains Mono";
      currentFontStyle = Font.PLAIN;
      currentFontSize = 16;
      currentEncoding = "UTF-8";

      GraphicsEnvironment windowsFonts = GraphicsEnvironment.getLocalGraphicsEnvironment();
      fontNames = windowsFonts.getAvailableFontFamilyNames();

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
      UIManager.put("Panel.background", new Color(255, 228, 240));
      UIManager.put("Label.foreground", new Color(199, 21, 133));
      UIManager.put("List.background", new Color(255, 235, 245));
      UIManager.put("List.foreground", new Color(199, 21, 133));
      UIManager.put("List.selectionBackground", new Color(255, 170, 200));
      UIManager.put("List.selectionForeground", Color.WHITE);
      UIManager.put("TextField.background", Color.WHITE);
      UIManager.put("TextField.foreground", new Color(199, 21, 133));
      UIManager.put("Panel.border", BorderFactory.createLineBorder(new Color(220, 220, 220)));
      UIManager.put("Panel.background", new Color(255, 228, 240));

      JMenuBar menuBar = createJMenuBar(controller);

      Font fontTextArea = new Font(currentFontFamily, currentFontStyle, currentFontSize);
      Color colorTextArea = Color.BLACK;

      textPane = new JTextPane();
      textPane.setEditorKit(new WrapHandler.WrapEditorKit());

      textPane.setFont(fontTextArea);
      textPane.setForeground(colorTextArea);
      textPane.setBackground(Color.WHITE);
      textPane.setCaretColor(new Color(199, 21, 133));
      textPane.setSelectedTextColor(Color.WHITE);
      textPane.setSelectionColor(new Color(255, 105, 180));
      textPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, new Color(255, 204, 232)),BorderFactory.createEmptyBorder(8, 10, 8, 10)));

      textPane.getDocument().addDocumentListener(new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
          updateCharCountLabel();
        }
        public void removeUpdate(DocumentEvent e) {
          updateCharCountLabel();
        }
        public void insertUpdate(DocumentEvent e) {
          updateCharCountLabel();
        }
      });

      updateCharCountLabel();



      JScrollPane scrollPane = new JScrollPane(textPane);
      scrollPane.setBorder(BorderFactory.createMatteBorder(0,2,2,2, new Color(255, 105, 180)));

      statusBar = new JPanel(new GridBagLayout());
      statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(255, 105, 180)));
      statusBar.setBackground(new Color(255, 228, 240));

      charCountLabel = new JLabel();
      encodingLabel = new JLabel();
      zoomLabel = new JLabel();


      updateCharCountLabel();
      updateEncodingLabel();
      updateZoomLabel();

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(2, 5, 2, 5);
      gbc.weightx = 1.0;
      gbc.anchor = GridBagConstraints.WEST;

      gbc.gridx = 0;
      statusBar.add(charCountLabel, gbc);

      gbc.gridx = 1;
      gbc.weightx = 0.0;
      gbc.anchor = GridBagConstraints.CENTER;
      statusBar.add(new JSeparator(JSeparator.VERTICAL), gbc);

      gbc.gridx = 2;
      gbc.weightx = 0.0;
      gbc.anchor = GridBagConstraints.WEST;
      statusBar.add(encodingLabel, gbc);

      gbc.gridx = 3;
      gbc.anchor = GridBagConstraints.CENTER;
      statusBar.add(new JSeparator(JSeparator.VERTICAL), gbc);

      gbc.gridx = 4;
      gbc.weightx = 0.0;
      gbc.anchor = GridBagConstraints.WEST;
      statusBar.add(zoomLabel, gbc);


      frame = new JFrame("StylePad MVC Pattern");
      frame.setSize(700, 600);
      frame.setLocationRelativeTo(null);
      frame.setJMenuBar(menuBar);
      frame.add("Center", scrollPane);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new GridBagLayout());

      Image icon = Toolkit.getDefaultToolkit().getImage("images/notepad_icon.png");
      frame.setIconImage(icon);

      GridBagConstraints mainGbc = new GridBagConstraints();
      mainGbc.fill = GridBagConstraints.BOTH;
      mainGbc.weightx = 1.0;
      mainGbc.weighty = 1.0;
      mainGbc.gridx = 0;
      mainGbc.gridy = 0;
      frame.add(scrollPane, mainGbc);

      GridBagConstraints statusGbc = new GridBagConstraints();
      statusGbc.fill = GridBagConstraints.HORIZONTAL;
      statusGbc.weightx = 1.0;
      statusGbc.weighty = 0.0;
      statusGbc.gridx = 0;
      statusGbc.gridy = 1;
      statusGbc.anchor = GridBagConstraints.SOUTH;
      frame.add(statusBar, statusGbc);

      frame.setVisible(true);
    }

    private void updateCharCountLabel() {
        if (textPane != null && charCountLabel != null) {
            int count = textPane.getText().length();
            charCountLabel.setText("Characters: " + count);
        }
    }

    public void updateEncodingLabel() {
        if (encodingLabel != null) {
            encodingLabel.setText("Endcoding: " + currentEncoding);
        }
    }

    public void updateZoomLabel() {
        if (zoomLabel != null) {
            zoomLabel.setText("Zoom: " + currentFontSize + "pt");
        }
    }

    private JMenuBar createJMenuBar(Controller controller) {
      JMenuBar menuBar = new JMenuBar();

      JMenu fileMenu = createFileMenu(controller);
      JMenu editMenu = createEditMenu(controller);
      JMenu formatMenu = createFormatMenu(controller);
      JMenu viewMenu = createViewMenu(controller);

      menuBar.add(fileMenu);
      menuBar.add(editMenu);
      menuBar.add(formatMenu);
      menuBar.add(viewMenu);

      return menuBar;
    }


    private JMenu createFileMenu(Controller controller) {

      JMenuItem newDocument = new JMenuItem("New",new ImageIcon("images/new.png"));
      newDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
      newDocument.addActionListener(controller);
      newDocument.setActionCommand("New_Document");

      JMenuItem openDocument = new JMenuItem("Open ...", new ImageIcon("images/open.png"));
      openDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
      openDocument.addActionListener(controller);
      openDocument.setActionCommand("Open_Document");

      JMenuItem saveDocument = new JMenuItem("Save", new ImageIcon("images/save.png"));
      saveDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
      saveDocument.addActionListener(controller);
      saveDocument.setActionCommand("Save_Document");

      JMenuItem saveAsDocument = new JMenuItem("Save As ...", new ImageIcon("images/save_as.png"));
      saveAsDocument.addActionListener(controller);
      saveAsDocument.setActionCommand("SaveAs_Document");

      JMenuItem printDocument = new JMenuItem("Print ...", new ImageIcon("images/print.png"));
      printDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
      printDocument.addActionListener(controller);
      printDocument.setActionCommand("Print_Document");

      JMenuItem imageDocument = new JMenuItem("Open Image", new ImageIcon("images/img.png"));
      imageDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
      imageDocument.addActionListener(controller);
      imageDocument.setActionCommand("Open_Image");

      JMenuItem closeProgram = new JMenuItem("Exit");
      imageDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
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

    private JMenu createEditMenu(Controller controller) {

      JMenuItem cutText = new JMenuItem("Cut",new ImageIcon("images/cut.png"));
      cutText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
      cutText.addActionListener(controller);
      cutText.setActionCommand("Cut_Text");

      JMenuItem copyText = new JMenuItem("Copy", new ImageIcon("images/copy.png"));
      copyText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
      copyText.addActionListener(controller);
      copyText.setActionCommand("Copy_Text");

      JMenuItem pasteText = new JMenuItem("Paste", new ImageIcon("images/paste.png"));
      pasteText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
      pasteText.addActionListener(controller);
      pasteText.setActionCommand("Paste_Text");

      JMenuItem deleteText = new JMenuItem("Delete", new ImageIcon("images/delete.png"));
      deleteText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
      deleteText.addActionListener(controller);
      deleteText.setActionCommand("Delete_Text");

      JMenuItem findText = new JMenuItem("Find", new ImageIcon("images/find.png"));
      findText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
      findText.addActionListener(controller);
      findText.setActionCommand("Find_Text");

      JMenuItem goToLine = new JMenuItem("Go", new ImageIcon("images/go.png"));
      goToLine.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
      goToLine.addActionListener(controller);
      goToLine.setActionCommand("Go_To_Line");

      JMenuItem selectAllText = new JMenuItem("Open Image", new ImageIcon("images/select_all.png"));
      selectAllText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
      selectAllText.addActionListener(controller);
      selectAllText.setActionCommand("Select_All_Text");

      JMenuItem timeAndDate = new JMenuItem("Time and date", new ImageIcon("images/time_and_date.png"));
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

    public void insertTimeAndDate() {
      DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String stamp = LocalDateTime.now().format(fmt);

      int start = textPane.getSelectionStart();
      int end = textPane.getSelectionEnd();

      if (start != end) {
          // If there is selected text, replace it with a date/time string
        textPane.replaceSelection(stamp);
      } else {
            // If there is no selection, insert it at the cursor position.
        try {
          textPane.getDocument().insertString(textPane.getCaretPosition(), stamp, null);
          } catch (BadLocationException e) {
            System.out.println("Insert time error: " + e.getMessage());
          }
      }

      updateCharCountLabel();
    }


    public void insertImage(File imageFile) {
    try {
      if (imageFile != null && imageFile.exists()) {
        ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());

        textPane.insertIcon(imageIcon);
      } else {
        JOptionPane.showMessageDialog(frame,
              "Invalid image file selected.",
              "Image Error",
              JOptionPane.ERROR_MESSAGE);
      }
  } catch (Exception ex) {
      JOptionPane.showMessageDialog(frame,
            "Error inserting image: " + ex.getMessage(),
            "Insert Image Error",
            JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
    }
}


    private JMenu createFormatMenu(Controller controller) {
      JMenu formatMenu = new JMenu("Format");

      wrapJMenuItem = new JMenuItem("Wrap", new ImageIcon(""));
      wrapJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
      wrapJMenuItem.addActionListener(controller);
      wrapJMenuItem.setActionCommand("Wrap");

      JMenuItem fontJMenuItem = new JMenuItem("Font", new ImageIcon(""));
      fontJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
      fontJMenuItem.addActionListener(controller);
      fontJMenuItem.setActionCommand("Show_Font_Dialog");

      formatMenu.setMnemonic('F');
      formatMenu.add(wrapJMenuItem);
      formatMenu.add(fontJMenuItem);

      return formatMenu;
  }

    private JMenu createViewMenu(Controller controller) {
      JMenu viewMenu = new JMenu("View");

      JMenuItem toggleStatus = new JMenuItem("Status Bar On/Off");
      toggleStatus.addActionListener(controller);
      toggleStatus.setActionCommand("View_Toggle_StatusBar");

      JMenuItem toggleChars = new JMenuItem("Char Counter On/Off");
      toggleChars.addActionListener(controller);
      toggleChars.setActionCommand("View_Toggle_CharCounter");

      JMenuItem zoomIn = new JMenuItem("Zoom In");
      zoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK));
      zoomIn.addActionListener(controller);
      zoomIn.setActionCommand("View_ZoomIn");

      JMenuItem zoomOut = new JMenuItem("Zoom Out");
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

    public boolean isStatusBarVisible() {
      return statusBar != null && statusBar.isVisible();
    }
    public void setStatusBarVisible(boolean visible) {
      if (statusBar != null) {
        statusBar.setVisible(visible);
        statusBarVisible = visible;
        if (frame != null) { frame.revalidate(); frame.repaint(); }
      }
    }

    public boolean isCharCounterVisible() {
      return charCountLabel != null && charCountLabel.isVisible();
    }
    public void setCharCounterVisible(boolean visible) {
      if (charCountLabel != null) {
          charCountLabel.setVisible(visible);
          charCounterVisible = visible;
          if (frame != null) { frame.revalidate(); frame.repaint(); }
      }
    }

    public void zoomIn() {
      setFontSettings(currentFontFamily, currentFontStyle, Math.min(currentFontSize + 2, 72));
    }
    public void zoomOut() {
      setFontSettings(currentFontFamily, currentFontStyle, Math.max(currentFontSize - 2, 8));
    }
    public void resetZoom() {
      setFontSettings(currentFontFamily, currentFontStyle, 16);
    }


    public void update(String text) {
      textPane.setText(text);
    }

    public void setFontSettings(String family, int style, int size) {
    currentFontFamily = family;
    currentFontStyle = style;
    currentFontSize = size;

    Font newFont = new Font(currentFontFamily, currentFontStyle, currentFontSize);

    int selectionStart = textPane.getSelectionStart();
    int selectionEnd = textPane.getSelectionEnd();

    textPane.setFont(newFont);
    textPane.select(selectionStart, selectionEnd);

    updateZoomLabel();
    textPane.repaint();
    }


    public void updateEncodingLabel(String encoding) {
         this.currentEncoding = encoding;
         updateEncodingLabel();
    }

    public File showFileDialog(String status) {
      File file = null;
      int returnValue;

      if(fileChooser == null) {
        fileChooser = new JFileChooser();
      }

      if (status.equals("Open")) {
        returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          file = fileChooser.getSelectedFile();

          if (file != null && !file.exists()) {
            JOptionPane.showMessageDialog(frame,
                      "File not found", "Open", JOptionPane.WARNING_MESSAGE);
            return null;
          }
        }
        return file;

      } else if (status.equals("Save")){
        returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          file = fileChooser.getSelectedFile();
        }
        return file;
      }

      return null;
    }


    public void showFontDialog() {
        FontChooserDialog dialog = new FontChooserDialog(
            frame,
            currentFontFamily,
            currentFontStyle,
            currentFontSize
        );

        Font selectedFont = dialog.showDialog();

        if (selectedFont != null) {
            setFontSettings(
                selectedFont.getFamily(),
                selectedFont.getStyle(),
                selectedFont.getSize()
            );
        }
    }

    public Document contentTextPane() {
        return textPane.getDocument();
    }

    public JTextPane getTextPane() {
      return textPane;
    }

    public void copyText() {
      textPane.copy();
    }


    public void pasteText() {
      textPane.paste();
    }

    public void cutText() {
      getTextPane().cut();
    }

    public void deleteText() {
      getTextPane().replaceSelection(null);
    }

    public void printDocument() {
      PrintDocument printDocument = new PrintDocument(textPane);
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPrintable(printDocument);
      boolean ok = job.printDialog();
      if (ok) {
          try {
              job.print();
              showResultPrintDocument();
          } catch (PrinterException ex) {
              System.out.println("PrinterException: " + ex);
          }
      }
    }

    private void showResultPrintDocument() {
      if(icon == null) {
        icon = new ImageIcon(getClass().getResource("/images/catPhoto.png"));
      }
      JOptionPane.showMessageDialog(null,
      "The document has been successfully printed",
      "Printer Document Dialog - StylePad MVC Pattern",
      JOptionPane.INFORMATION_MESSAGE,
      icon);
    }

    public void showFindDialog() {
      if (findDialog == null) {
        findDialog = new FindDialog(this);
      }
      findDialog.setVisible(true);
    }

    public void showGoToLineDialog() {
      if (goToLineDialog == null) {
        goToLineDialog = new GoToLineDialog(this);
      }
      goToLineDialog.setVisible(true);
    }


  public void showResultSaveDocumentIntoModel(boolean result) {
    if(result) {
      JOptionPane.showMessageDialog(null,
              "The file was saved successfully.");
    } else {
      JOptionPane.showMessageDialog(null,
              "The file was not saved.",
              "Error saving",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  public JFrame getFrame() {
    return frame;
  }
}
