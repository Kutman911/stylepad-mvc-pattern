package stylepad.ui;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.UIManager;

public class HelpDialog extends JDialog {

  public HelpDialog(JFrame parent) {
    super(parent, "Help", true);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);

    UIManager.put("TabbedPane.selected", new Color(255, 204, 232));
    UIManager.put("TabbedPane.contentAreaColor", new Color(255, 228, 240));
    UIManager.put("TabbedPane.focus", new Color(255, 105, 180));
    UIManager.put("TabbedPane.borderHightlightColor", new Color(255, 105, 180));
    UIManager.put("TabbedPane.darkShadow", new Color(255, 105, 180));
    UIManager.put("TabbedPane.light", new Color(255, 190, 220));
    UIManager.put("TabbedPane.highlight", new Color(255, 190, 220));

    JPanel root = new JPanel(new BorderLayout());
    root.setBackground(new Color(255, 228, 240));
    root.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180), 2));

    JLabel title = new JLabel("♡ StylePad Help ♡", JLabel.CENTER);
    title.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
    title.setForeground(new Color(199, 21, 133));
    title.setBorder(BorderFactory.createEmptyBorder(10, 10, 4, 10));
    root.add(title, BorderLayout.NORTH);

    JTabbedPane tabs = new JTabbedPane();
    tabs.setBackground(new Color(255, 240, 247));
    tabs.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

    tabs.addTab("File",   createSectionPanel(new String[][]{
            {"New (Ctrl + N) - ", "Create a new empty document."},
            {"Open (Ctrl + O) - ", "Open an existing file."},
            {"Save (Ctrl + S) - ", "Save the current document."},
            {"Save As - ", "Save the current document with a new name."},
            {"Open Image (Ctrl + Q) - ", "Open or insert an image file."},
            {"Print (Ctrl + P) - ", "Print the current document."},
            {"Exit - ", "Close StylePad."}
    }));

    tabs.addTab("Edit",   createSectionPanel(new String[][]{
            {"Cut (Ctrl + X) - ", "Cut the selection to the clipboard."},
            {"Copy (Ctrl + C) - ", "Copy the selection to the clipboard."},
            {"Paste (Ctrl + V) - ", "Paste from the clipboard."},
            {"Delete (Ctrl + D) - ", "Delete the selected text."},
            {"Find (Ctrl + F) - ", "Search for text in the document."},
            {"Go (Ctrl + G) - ", "Jump to a specific line."},
            {"Select All (Ctrl + A) - ", "Select the entire document."},
            {"Time and date (F5) - ", "Insert the current time and date."}
    }));

    tabs.addTab("Format", createSectionPanel(new String[][]{
            {"Wrap (Ctrl + W) - ", "Toggle word wrap for long lines."},
            {"Font (Ctrl + Shift + F) - ", "Choose font family, style and size."}
    }));

    tabs.addTab("View",   createSectionPanel(new String[][]{
            {"Status Bar - ", "Show or hide the status bar."},
            {"Char Counter - ", "Show or hide the character counter."},
            {"Zoom in (Ctrl + Plus) - ", "Increase the text size."},
            {"Zoom out (Ctrl + Minus) - ", "Decrease the text size."},
            {"Reset Zoom (Ctrl + 0) - ", "Reset zoom to the default size."}
    }));

    root.add(tabs, BorderLayout.CENTER);

    JButton closeButton = new JButton("Got it ♡");
    closeButton.setBackground(new Color(255, 204, 232));
    closeButton.setForeground(new Color(199, 21, 133));
    closeButton.setFocusPainted(false);
    closeButton.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
    closeButton.addActionListener(e -> dispose());

    closeButton.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        closeButton.setBackground(new Color(255, 190, 225));
      }
      public void mouseExited(MouseEvent e) {
        closeButton.setBackground(new Color(255, 204, 232));
      }
    });

    JPanel bottom = new JPanel();
    bottom.setOpaque(false);
    bottom.setBorder(BorderFactory.createEmptyBorder(4, 0, 10, 0));
    bottom.add(closeButton);

    root.add(bottom, BorderLayout.SOUTH);

    setContentPane(root);
    pack();
    setLocationRelativeTo(parent);
  }

  private JPanel createSectionPanel(String[][] rows) {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(255, 240, 247));
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));

    for (String[] row : rows) {
      String command = row[0];
      String description = row[1];

      JPanel line = new JPanel(new BorderLayout());
      line.setOpaque(false);
      line.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

      JLabel cmdLabel = new JLabel(command);
      cmdLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
      cmdLabel.setForeground(new Color(199, 21, 133));

      JLabel descLabel = new JLabel(description);
      descLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
      descLabel.setForeground(new Color(199, 21, 133));

      line.add(cmdLabel, BorderLayout.WEST);
      line.add(descLabel, BorderLayout.CENTER);

      JSeparator separator = new JSeparator();
      separator.setForeground(new Color(255, 210, 230));

      panel.add(line);
      panel.add(separator);
    }

    return panel;
  }
}
