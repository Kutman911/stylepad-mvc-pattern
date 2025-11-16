import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;

public class FontChooserDialog extends JDialog {

  private String selectedFontFamily;
  private int selectedFontStyle;
  private int selectedFontSize;
  private boolean dialogResult;

  private final JLabel previewText;
  private final JList<String> fontList;
  private final JList<String> fontStyleList;
  private final JList<Integer> sizeList;
  private final JTextField fontField;
  private final JTextField fontStyleField;
  private final JTextField sizeField;

  private static final String[] FONT_STYLES = {"Regular", "Bold", "Italic", "Bold Italic"};
  private static final int[] DEFAULT_SIZES = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 28, 32, 36, 48, 60, 72};


  public FontChooserDialog(JFrame owner, String currentFamily, int currentStyle, int currentSize) {
    super(owner, "Font", true);

    this.selectedFontFamily = currentFamily;
    this.selectedFontStyle = currentStyle;
    this.selectedFontSize = currentSize;

    fontField = new JTextField(selectedFontFamily);
    fontStyleField = new JTextField(FONT_STYLES[selectedFontStyle]);
    sizeField = new JTextField(Integer.toString(selectedFontSize));

    previewText = new JLabel("AaBbYyZz", SwingConstants.CENTER);

    String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    fontList = new JList<>(fontNames);
    fontStyleList = new JList<>(FONT_STYLES);
    sizeList = new JList<>(getSupportedFontSizes(selectedFontFamily));

    setupUI();
    setupListeners();

    fontList.setSelectedValue(selectedFontFamily, true);
    fontStyleList.setSelectedIndex(selectedFontStyle);
    sizeList.setSelectedValue(selectedFontSize, true);
  }

  private void setupUI() {
    setLayout(new BorderLayout(10, 10));

    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;

    addListComponent(centerPanel, gbc, 0, 0, "Font:", fontField, fontList);

    fontStyleField.setEditable(false);
    addListComponent(centerPanel, gbc, 1, 0, "Font Style:", fontStyleField, fontStyleList);

    addListComponent(centerPanel, gbc, 2, 0, "Size:", sizeField, sizeList);

    add(centerPanel, BorderLayout.CENTER);

    JPanel previewPanel = new JPanel(new BorderLayout());
    previewPanel.setBorder(BorderFactory.createTitledBorder("Sample"));
    previewText.setFont(new Font(selectedFontFamily, selectedFontStyle, selectedFontSize));
    previewPanel.add(previewText, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    JButton buttonOk = new JButton("OK");
    JButton buttonCancel = new JButton("Cancel");

    buttonOk.addActionListener(e -> handleOk());
    buttonCancel.addActionListener(e -> handleCancel());

    buttonPanel.add(buttonOk);
    buttonPanel.add(buttonCancel);

    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.add(previewPanel, BorderLayout.CENTER);
    southPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(southPanel, BorderLayout.SOUTH);

    pack();
    setSize(500, 480);
    setResizable(false);
    setLocationRelativeTo(getOwner());
  }

  private void addListComponent(JPanel panel, GridBagConstraints gbc, int gridx, int gridy, String labelText, JTextField textField, JList<?> list) {
    gbc.gridx = gridx;

    JLabel label = new JLabel(labelText);
    gbc.gridy = gridy;
    gbc.weightx = (gridx == 0) ? 1.0 : 0.5;
    gbc.anchor = GridBagConstraints.WEST;
    panel.add(label, gbc);

    gbc.gridy = gridy + 1;
    gbc.weighty = 0.0;
    panel.add(textField, gbc);

    JScrollPane scrollPane = new JScrollPane(list);
    scrollPane.setPreferredSize(new Dimension(textField.getPreferredSize().width, 150));
    gbc.gridy = gridy + 2;
    gbc.weighty = 1.0;
    panel.add(scrollPane, gbc);

    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  private void setupListeners() {

    fontList.addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        String family = fontList.getSelectedValue();
        if (family != null) {
          selectedFontFamily = family;
          fontField.setText(family);
          updatePreview();

          sizeList.setListData(getSupportedFontSizes(selectedFontFamily));
          sizeList.setSelectedValue(selectedFontSize, true);
        }
      }
    });

    fontStyleList.addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        selectedFontStyle = fontStyleList.getSelectedIndex();
        fontStyleField.setText(FONT_STYLES[selectedFontStyle]);
        updatePreview();
      }
    });

    sizeList.addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        Integer size = sizeList.getSelectedValue();
        if (size != null) {
          selectedFontSize = size;
          sizeField.setText(Integer.toString(size));
          updatePreview();
        }
      }
    });
  }

  private void updatePreview() {
    Font newFont = new Font(selectedFontFamily, selectedFontStyle, selectedFontSize);
    previewText.setFont(newFont);
  }

  private void handleOk() {
    dialogResult = true;
    setVisible(false);
  }

  public void updateSettings(String family, int style, int size) {
    this.selectedFontFamily = family;
    this.selectedFontStyle = style;
    this.selectedFontSize = size;

    fontField.setText(family);
    fontStyleField.setText(FONT_STYLES[style]);
    sizeField.setText(Integer.toString(size));

    fontList.setSelectedValue(family, true);
    fontStyleList.setSelectedIndex(style);

    sizeList.setListData(getSupportedFontSizes(family));
    sizeList.setSelectedValue(size, true);

    updatePreview();
}

  private void handleCancel() {
    dialogResult = false;
    setVisible(false);
  }

  public Font getSelectedFont() {
    if (dialogResult) {
      return new Font(selectedFontFamily, selectedFontStyle, selectedFontSize);
    }
    return null;
  }


  private Integer[] getSupportedFontSizes(String fontName) {
    List<Integer> list = new ArrayList<>();
    Font font = new Font(fontName, Font.PLAIN, 12);

    for (int s : DEFAULT_SIZES) {
      list.add(s);
    }
    return list.toArray(new Integer[0]);
  }
}
