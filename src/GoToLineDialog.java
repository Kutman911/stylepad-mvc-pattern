import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.JTextField;
import javax.swing.JComponent;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.BadLocationException;
import javax.swing.BorderFactory;

public class GoToLineDialog extends JDialog {

  private final Viewer viewer;
  private final JTextComponent textPane;
  private JTextField lineField;
  private JButton goButton;
  private JButton cancelButton;
  private JButton clearButton;

  public GoToLineDialog(Viewer viewer) {
    super(viewer.getFrame(), "Go to Line", false);
    this.viewer = viewer;
    this.textPane = viewer.getTextPane();

    initComponents();
    setupLayout();
    setupActions();

    pack();
    setLocationRelativeTo(viewer.getFrame());
  }

  private void initComponents() {
    lineField = new JTextField(10);

    clearButton = new JButton("x");
    goButton = new JButton("Go");
    cancelButton = new JButton("Cancel");
  }

  private void setupLayout() {
    JPanel goPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.LINE_START;

    JLabel LineLabel = new JLabel("Line number:");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    goPanel.add(LineLabel, gbc);

    clearButton.setBorder(null);
    clearButton.setFocusable(false);
    clearButton.setContentAreaFilled(false);
    clearButton.setOpaque(false);
    clearButton.setMargin(new Insets(0, 0, 0, 0));
    clearButton.setFont(clearButton.getFont().deriveFont(Font.PLAIN, 18f));

    JPanel fieldPanel = new JPanel(new BorderLayout());
    fieldPanel.setBorder(lineField.getBorder());
    lineField.setBorder(null);
    fieldPanel.add(lineField, BorderLayout.CENTER);
    fieldPanel.add(clearButton, BorderLayout.EAST);

    gbc.gridy = 1;
    goPanel.add(fieldPanel, gbc);

    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
    buttonsPanel.setBorder(BorderFactory.createEmptyBorder());
    buttonsPanel.setOpaque(false);
    buttonsPanel.add(goButton);
    buttonsPanel.add(cancelButton);

    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.LINE_END;
    goPanel.add(buttonsPanel, gbc);

    setContentPane(goPanel);
  }

  private void setupActions() {
    clearButton.addActionListener(e -> lineField.setText(""));
    goButton.addActionListener(e -> goToLine());
    cancelButton.addActionListener(e -> dispose());
    lineField.addActionListener(e -> goButton.doClick());
    getRootPane().registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  private void goToLine() {
    String text = lineField.getText().trim();
    if (text.isEmpty()) {
        return;
    }

    try {
      int lineNumber = Integer.parseInt(text);
      if (lineNumber <= 0) {
        JOptionPane.showMessageDialog(this, "Incorrect number", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      int totalLines = textPane.getDocument().getDefaultRootElement().getElementCount();

      if (lineNumber > totalLines) {
        JOptionPane.showMessageDialog(this,"Line doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      int offset = textPane.getDocument().getDefaultRootElement().getElement(lineNumber - 1).getStartOffset();

      textPane.setCaretPosition(offset);
      textPane.requestFocus();
      dispose();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Incorrect number", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
