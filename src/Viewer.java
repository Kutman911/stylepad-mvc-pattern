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

public class Viewer {

    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JDialog dialog;
    private JFrame frame;
    private Icon icon;
    private String currentFontFamily = "JetBrains Mono";
    private int currentFontStyle = Font.PLAIN;
    private int currentFontSize = 16;
    private JPanel statusBar;
    private JLabel charCountLabel;
    private JLabel encodingLabel;
    private JLabel zoomLabel;
    private String currentEncoding = "UTF-8";

    public Viewer() {
      Controller controller = new Controller(this);
      createGUI(controller);
    }

    private void createGUI(Controller controller) {

      currentFontFamily = "JetBrains Mono";
      currentFontStyle = Font.PLAIN;
      currentFontSize = 16;

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

      updateCharCountLabel();

      textArea.getDocument().addDocumentListener(new DocumentListener() {
            private void changedUpdateInternal() {
                updateCharCountLabel();

                String text = textArea.getText();
                if (text.isEmpty()) {
                    textArea.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
                    return;
                }

                char unicodeSymbol = text.charAt(text.length() - 1);

                if ((unicodeSymbol >= 65 && unicodeSymbol <= 90 || unicodeSymbol == ' ') ||
                    (unicodeSymbol >= 97 && unicodeSymbol <= 122)) {
                    textArea.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
                } else {
                  textArea.setFont(new Font("Arial", Font.PLAIN, 18));
                }
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdateInternal();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdateInternal();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

      JScrollPane scrollPane = new JScrollPane(textArea);
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
      gbc.weightx = 0.0; // Не расширять
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

      Image icon = Toolkit.getDefaultToolkit().getImage("images/duke_printer.png");
      frame.setIconImage(icon);

      GridBagConstraints mainGbc = new GridBagConstraints();
      mainGbc.fill = GridBagConstraints.BOTH;
      mainGbc.weightx = 1.0;
      mainGbc.weighty = 1.0; // Занять все доступное пространство по вертикали
      mainGbc.gridx = 0;
      mainGbc.gridy = 0;
      frame.add(scrollPane, mainGbc);

      GridBagConstraints statusGbc = new GridBagConstraints();
      statusGbc.fill = GridBagConstraints.HORIZONTAL;
      statusGbc.weightx = 1.0;
      statusGbc.weighty = 0.0; // Не расширять по вертикали
      statusGbc.gridx = 0;
      statusGbc.gridy = 1;
      statusGbc.anchor = GridBagConstraints.SOUTH;
      frame.add(statusBar, statusGbc);

      frame.setVisible(true);
    }

    private void updateCharCountLabel() {
        if (textArea != null && charCountLabel != null) {
            int count = textArea.getText().length();
            charCountLabel.setText("Символов: " + count);
        }
    }

    public void updateEncodingLabel() {
        if (encodingLabel != null) {
            encodingLabel.setText("Кодировка: " + currentEncoding);
        }
    }

    public void updateZoomLabel() {
        if (zoomLabel != null) {
            zoomLabel.setText("Масштаб: " + currentFontSize + "pt");
        }
    }

    private JMenuBar createJMenuBar(Controller controller) {
      JMenuBar menuBar = new JMenuBar();

      JMenu fileMenu = createFileMenu(controller);
      JMenu editMenu = createEditMenu(controller);
      JMenu formatMenu = createFormatMenu(controller);

      menuBar.add(fileMenu);
      menuBar.add(editMenu);
      menuBar.add(formatMenu);

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
      updateZoomLabel();
    }

    public void updateEncodingLabel(String encoding) {
         this.currentEncoding = encoding;
         updateEncodingLabel();
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

        // Font Sample
        JLabel sampleLable = new JLabel("Sample");
        sampleLable.setBounds(260, 230, 90, 25);
        dialog.add(sampleLable);

        JPanel samplePanel = new JPanel();
        samplePanel.setBounds(260, 255, 208, 100);
        samplePanel.setLayout(new BorderLayout());

        JLabel previewText = new JLabel("AaBbYyZz", SwingConstants.CENTER);
        previewText.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
        samplePanel.add(previewText, BorderLayout.CENTER);

        dialog.add(samplePanel);

        // Font
        JLabel fontLabel = new JLabel("Font:");
        fontLabel.setDisplayedMnemonic('F');
        fontLabel.setBounds(20, 20, 90, 25);
        dialog.add(fontLabel);

        JTextField fontField = new JTextField(currentFontFamily);
        fontField.setBounds(20, 45, 220, 25);
        fontField.setHorizontalAlignment(JTextField.LEFT);
        dialog.add(fontField);

        GraphicsEnvironment windowsFonts = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = windowsFonts.getAvailableFontFamilyNames();

        JList<String> fontList = new JList<>(fontNames);
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fontList.setSelectedValue(currentFontFamily, true);

        JScrollPane fontScroll = new JScrollPane(fontList);
        fontScroll.setBounds(20, 68, 220, 150);
        dialog.add(fontScroll);

        // Listener font family
        fontList.addListSelectionListener(e -> {
          if(e.getValueIsAdjusting()) {
            String selectedFontFamily = fontList.getSelectedValue();

            if(selectedFontFamily != null) {
              currentFontFamily = selectedFontFamily;
              fontField.setText(currentFontFamily);
              previewText.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
            }
            }
          }
        );

        // Font Style
        JLabel fontStyleLabel = new JLabel("Font Style:");
        fontStyleLabel.setDisplayedMnemonic('y');
        fontStyleLabel.setBounds(260, 20, 90, 25);
        dialog.add(fontStyleLabel);

        JTextField fontStyleField = new JTextField("Regular");
        fontStyleField.setBounds(260, 45, 90, 25);
        fontStyleField.setHorizontalAlignment(JTextField.LEFT);
        fontStyleField.setEditable(false);
        dialog.add(fontStyleField);

        String[] fontStyles = {"Regular", "Bold", "Italic", "Bold Italic"};

        JList<String> fontStyleList = new JList<>(fontStyles);
        fontStyleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fontStyleList.setSelectedIndex(currentFontStyle);

        JScrollPane fontStyleScroll = new JScrollPane(fontStyleList);
        fontStyleScroll.setBounds(260, 68, 90, 150);
        dialog.add(fontStyleScroll);

        // Listener font style
        fontStyleList.addListSelectionListener(e -> {
          if(e.getValueIsAdjusting()) {
            int selectedIndex = fontStyleList.getSelectedIndex();
            String selectedStyleName = fontStyleList.getSelectedValue();

            currentFontStyle = selectedIndex;
            fontStyleField.setText(selectedStyleName);

            previewText.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
          }
        });

        // Font Size
        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setDisplayedMnemonic('S');
        sizeLabel.setBounds(380, 20, 90, 25);
        dialog.add(sizeLabel);

        JTextField sizeField = new JTextField(Integer.toString(currentFontSize));
        sizeField.setBounds(380, 45, 90, 25);
        sizeField.setHorizontalAlignment(JTextField.LEFT);
        dialog.add(sizeField);

        Integer[] sizes = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 28, 32, 36, 48, 60, 72};
        JList<Integer> sizeList = new JList<>(sizes);
        sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sizeList.setSelectedValue(currentFontSize, true);

        JScrollPane sizeScroll = new JScrollPane(sizeList);
        sizeScroll.setBounds(380, 68, 90, 150);
        dialog.add(sizeScroll);

        sizeList.addListSelectionListener(e -> {
          if(e.getValueIsAdjusting()) {
            Integer selectedFontSize = sizeList.getSelectedValue();

            if(selectedFontSize != null) {
              currentFontSize = selectedFontSize;
              sizeField.setText(Integer.toString(currentFontSize));
              previewText.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
            }

            }
          }
        );

        JButton buttonOk = new JButton("OK");
        buttonOk.setBounds(260, 390, 100, 50);
        buttonOk.setFocusPainted(false);
        buttonOk.addActionListener(
            (eventButton) -> {
              Font fontTextArea = new Font(currentFontFamily, currentFontStyle, currentFontSize);
              textArea.setFont(fontTextArea);
              dialog.setVisible(false);
            });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(370, 390, 100, 50);
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
