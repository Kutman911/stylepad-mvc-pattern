import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class FontChooserDialog extends JDialog {

  private String currentFontFamily;
  private int currentFontStyle;
  private int currentFontSize;
  private GraphicsEnvironment windowsFonts;
  private String[] fontNames;

  private boolean approved = false;

  private Integer[] generateSizes() {
    List<Integer> sizes = new ArrayList<>();

    for (int i = 8; i <= 24; i++) {
      sizes.add(i);
    }

    for (int i = 26; i <= 72; i += 2) {
      sizes.add(i);
    }

    return sizes.toArray(new Integer[0]);
  }

  public FontChooserDialog(JFrame frame, String fontFamily, int fontStyle, int fontSize) {
    super(frame, "Font", true);

    this.currentFontFamily = fontFamily;
    this.currentFontStyle = fontStyle;
    this.currentFontSize = fontSize;

    int x = frame.getX();
    int y = frame.getY();

    setLayout(null);
    setSize(500, 500);
    setLocation(x + 100, y + 50);
    setResizable(false);

    windowsFonts = GraphicsEnvironment.getLocalGraphicsEnvironment();
    fontNames = windowsFonts.getAvailableFontFamilyNames();

    // Sample
    JLabel sampleLabel = new JLabel("Sample");
    sampleLabel.setBounds(179, 230, 100, 25);
    add(sampleLabel);

    JPanel samplePanel = new JPanel(new BorderLayout());
    samplePanel.setBounds(179, 255, 290, 100);
    add(samplePanel);

    JLabel previewText = new JLabel("AaBbYyZz", SwingConstants.CENTER);
    previewText.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
    samplePanel.add(previewText, BorderLayout.CENTER);

    // Font
    JLabel fontLabel = new JLabel("Font:");
    fontLabel.setBounds(20, 20, 90, 25);
    add(fontLabel);

    JTextField fontField = new JTextField(currentFontFamily);
    fontField.setBounds(20, 45, 220, 25);
    add(fontField);

    JList<String> fontList = new JList<>(fontNames);
    fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fontList.setSelectedValue(currentFontFamily, true);

    JScrollPane fontScroll = new JScrollPane(fontList);
    fontScroll.setBounds(20, 68, 220, 150);
    add(fontScroll);

    // Style
    JLabel fontStyleLabel = new JLabel("Font Style:");
    fontStyleLabel.setBounds(260, 20, 90, 25);
    add(fontStyleLabel);

    JTextField fontStyleField = new JTextField("Regular");
    fontStyleField.setBounds(260, 45, 90, 25);
    fontStyleField.setEditable(false);
    add(fontStyleField);

    String[] fontStyles = {"Regular", "Bold", "Italic", "Bold Italic"};
    JList<String> fontStyleList = new JList<>(fontStyles);
    fontStyleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fontStyleList.setSelectedIndex(fontStyle);

    JScrollPane styleScroll = new JScrollPane(fontStyleList);
    styleScroll.setBounds(260, 68, 90, 150);
    add(styleScroll);

    // Size
    JLabel sizeLabel = new JLabel("Size:");
    sizeLabel.setBounds(380, 20, 90, 25);
    add(sizeLabel);

    JTextField sizeField = new JTextField(Integer.toString(fontSize));
    sizeField.setBounds(380, 45, 90, 25);
    add(sizeField);

    Integer[] sizes = generateSizes();
    JList<Integer> sizeList = new JList<>(sizes);
    sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    sizeList.setSelectedValue(fontSize, true);

    JScrollPane sizeScroll = new JScrollPane(sizeList);
    sizeScroll.setBounds(380, 68, 90, 150);
    add(sizeScroll);

    // Listeners
    fontList.addListSelectionListener(
        e -> {
          if (e.getValueIsAdjusting()) {
            currentFontFamily = fontList.getSelectedValue();
            fontField.setText(currentFontFamily);
            previewText.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
          }
        });

    fontStyleList.addListSelectionListener(
        e -> {
          if (e.getValueIsAdjusting()) {
            currentFontStyle = fontStyleList.getSelectedIndex();
            fontStyleField.setText(fontStyleList.getSelectedValue());
            previewText.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
          }
        });

    sizeList.addListSelectionListener(
        e -> {
          if (e.getValueIsAdjusting()) {
            Integer val = sizeList.getSelectedValue();
            if (val != null) {
              currentFontSize = val;
              sizeField.setText(Integer.toString(val));
              previewText.setFont(new Font(currentFontFamily, currentFontStyle, currentFontSize));
            }
          }
        });

    // Buttons
    JButton buttonOk = new JButton("OK");
    buttonOk.setBounds(260, 390, 100, 50);
    add(buttonOk);

    JButton buttonCancel = new JButton("Cancel");
    buttonCancel.setBounds(370, 390, 100, 50);
    add(buttonCancel);

    buttonOk.addActionListener(
        e -> {
          approved = true;
          setVisible(false);
        });

    buttonCancel.addActionListener(
        e -> {
          approved = false;
          setVisible(false);
        });
  }

  public Font showDialog() {
    setVisible(true);

    if (approved) {
      return new Font(currentFontFamily, currentFontStyle, currentFontSize);
    }
    return null;
  }
}
