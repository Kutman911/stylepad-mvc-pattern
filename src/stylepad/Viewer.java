package stylepad;
import stylepad.commands.FindDialog;
import stylepad.commands.FontChooserDialog;
import stylepad.commands.GoToLineDialog;
import stylepad.commands.PrintDocument;
import stylepad.commands.WrapHandler;
import stylepad.ui.MenuBarFactory;
import stylepad.ui.ToolBarFactory;
import stylepad.ui.AboutDialog;
import stylepad.ui.HelpDialog;
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
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Image;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.undo.UndoManager;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

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
    private boolean statusBarVisible;
    private boolean charCounterVisible;
    private FindDialog findDialog;
    private GoToLineDialog goToLineDialog;
    private UndoManager undoManager;

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


      JMenuBar menuBar = MenuBarFactory.createJMenuBar(controller, this);
      JToolBar toolBar = ToolBarFactory.createToolBar(controller, this);

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
      initZoomKeyBindings();

      undoManager = new UndoManager();

      textPane.getDocument().addUndoableEditListener(undoManager);

      textPane.getInputMap(JComponent.WHEN_FOCUSED).put(
              KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK),
              "Undo"
      );
      textPane.getActionMap().put("Undo", new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
              if (undoManager.canUndo()) {
                  undoManager.undo();
              }
          }
      });

      textPane.getInputMap(JComponent.WHEN_FOCUSED).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK),
        "Redo"
      );

      textPane.getActionMap().put("Redo", new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
              if (undoManager.canRedo()) {
                  undoManager.redo();
              }
          }
      });

      JScrollPane scrollPane = new JScrollPane(textPane);
      scrollPane.setBorder(BorderFactory.createMatteBorder(0,2,2,2, new Color(255, 105, 180)));
      initZoomMouseWheel(scrollPane);

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
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new GridBagLayout());

      Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Images/notepad_icon.png"));
      frame.setIconImage(icon);

      GridBagConstraints toolbarGbc = new GridBagConstraints();
      toolbarGbc.fill = GridBagConstraints.HORIZONTAL;
      toolbarGbc.weightx = 1.0;
      toolbarGbc.weighty = 0.0;
      toolbarGbc.gridx = 0;
      toolbarGbc.gridy = 0;
      frame.add(toolBar, toolbarGbc);

      GridBagConstraints mainGbc = new GridBagConstraints();
      mainGbc.fill = GridBagConstraints.BOTH;
      mainGbc.weightx = 1.0;
      mainGbc.weighty = 1.0;
      mainGbc.gridx = 0;
      mainGbc.gridy = 1;
      frame.add(scrollPane, mainGbc);

      GridBagConstraints statusGbc = new GridBagConstraints();
      statusGbc.fill = GridBagConstraints.HORIZONTAL;
      statusGbc.weightx = 1.0;
      statusGbc.weighty = 0.0;
      statusGbc.gridx = 0;
      statusGbc.gridy = 2;
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
            encodingLabel.setText("Encoding: " + currentEncoding);
        }
    }

    public void updateZoomLabel() {
        if (zoomLabel != null) {
            zoomLabel.setText("Zoom: " + currentFontSize + "pt");
        }
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


  public void insertImage(File imageFile, int width, int height) {
    try {
      if (imageFile != null && imageFile.exists()) {

        ImageIcon original = new ImageIcon(imageFile.getAbsolutePath());

        Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);

        // Insert the scaled image into the text pane
        textPane.insertIcon(scaledIcon);

      } else {

        // Show an error message when the file is invalid
        JOptionPane.showMessageDialog(frame,
                "Invalid image file selected.",
                "Image Error",
                JOptionPane.ERROR_MESSAGE);
      }
    } catch (Exception ex) {

      // Display the exception if something goes wrong while inserting the image
      JOptionPane.showMessageDialog(frame,
              "Error inserting image: " + ex.getMessage(),
              "Insert Image Error",
              JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
    }
  }

    public boolean isStatusBarVisible() {
      return statusBar != null && statusBar.isVisible();
    }
    public void setStatusBarVisible(boolean visible) {
      if (statusBar != null) {
        statusBar.setVisible(visible);
        statusBarVisible = visible;
        if (frame != null) {
           frame.revalidate();
           frame.repaint();
         }
      }
    }

    public boolean isCharCounterVisible() {
      return charCountLabel != null && charCountLabel.isVisible();
    }
    public void setCharCounterVisible(boolean visible) {
      if (charCountLabel != null) {
          charCountLabel.setVisible(visible);
          charCounterVisible = visible;
          if (frame != null) {
             frame.revalidate();
             frame.repaint();
           }
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

    private void initZoomKeyBindings() {
      InputMap im = textPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
      ActionMap am = textPane.getActionMap();

      KeyStroke ctrlPlusMain   = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK);
      KeyStroke ctrlPlusNumpad = KeyStroke.getKeyStroke(KeyEvent.VK_ADD, InputEvent.CTRL_DOWN_MASK);
      KeyStroke ctrlMinusMain   = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK);
      KeyStroke ctrlMinusNumpad = KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, InputEvent.CTRL_DOWN_MASK);
      KeyStroke ctrlZero = KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK);

      im.put(ctrlPlusMain, "zoomIn");
      im.put(ctrlPlusNumpad, "zoomIn");
      im.put(ctrlMinusMain, "zoomOut");
      im.put(ctrlMinusNumpad, "zoomOut");
      im.put(ctrlZero, "zoomReset");

      am.put("zoomIn", new ZoomInAction());
      am.put("zoomOut", new ZoomOutAction());
      am.put("zoomReset", new ZoomResetAction());
    }

    private class ZoomInAction extends AbstractAction {
      public void actionPerformed(ActionEvent e) {
        zoomIn();
      }
    }

    private class ZoomOutAction extends AbstractAction {
      public void actionPerformed(ActionEvent e) {
        zoomOut();
      }
    }

    private class ZoomResetAction extends AbstractAction {
      public void actionPerformed(ActionEvent e) {
        resetZoom();
      }
    }
    private void initZoomMouseWheel(JScrollPane scrollPane) {
      scrollPane.addMouseWheelListener(new ZoomMouseWheelListener());
    }

    private class ZoomMouseWheelListener implements MouseWheelListener {
      public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {
          int rotation = e.getWheelRotation();
          if (rotation < 0) {
            zoomIn();
          } else if (rotation > 0) {
            zoomOut();
          }
          e.consume();
        }
      }
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
        icon = new ImageIcon(getClass().getResource("Images/catPhoto.png"));
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

  public JTextPane getTextArea() {
    return textPane;
  }

  public void alignLeft() {
    applyAlignment(StyleConstants.ALIGN_LEFT);
  }

  public void alignCenter() {
    applyAlignment(StyleConstants.ALIGN_CENTER);
  }

  public void alignRight() {
    applyAlignment(StyleConstants.ALIGN_RIGHT);
  }

  private void applyAlignment(int alignment) {
    StyledDocument doc = textPane.getStyledDocument();
    SimpleAttributeSet attrs = new SimpleAttributeSet();
    StyleConstants.setAlignment(attrs, alignment);

    int start = textPane.getSelectionStart();
    int end   = textPane.getSelectionEnd();

    if (start == end) {
      doc.setParagraphAttributes(start, 1, attrs, false);
    } else {
        doc.setParagraphAttributes(start, end - start, attrs, false);
    }

    textPane.repaint();
  }

  public void showHelpDialog() {
    HelpDialog dialog = new HelpDialog(frame);
    dialog.setVisible(true);
  }

  public void showAboutDialog() {
    AboutDialog dialog = new AboutDialog(frame);
    dialog.setVisible(true);
  }

  public JFrame getFrame() {
    return frame;
  }
}
