package stylepad.ui;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.border.Border;


public class AboutDialog extends JDialog {

  public AboutDialog(JFrame parent) {
    super(parent, "About", true);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);

    JPanel card = new JPanel();
    card.setOpaque(true);
    Border outerBorder = BorderFactory.createLineBorder(new Color(255, 105, 180), 2);
    Border innerBorder = BorderFactory.createLineBorder(new Color(255, 190, 220), 2);
    Border padding = BorderFactory.createEmptyBorder(18, 24, 18, 24);

    Border innerCombo = BorderFactory.createCompoundBorder(innerBorder, padding);
    Border fullBorder = BorderFactory.createCompoundBorder(outerBorder, innerCombo);

    card.setBorder(fullBorder);

    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

    JLabel title = new JLabel("\u2661 StylePad \u2661");
    title.setFont(new Font("JetBrains Mono", Font.BOLD, 22));
    title.setForeground(new Color(199, 21, 133));
    title.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    JLabel subtitle = new JLabel("Text editor");
    subtitle.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
    subtitle.setForeground(new Color(199, 21, 133));
    subtitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    JLabel info = new JLabel(
            "<html>" +
            "<div style='text-align:left; margin-top:6px;'>" +
            "<ul style='margin:0; padding-left:16px;'>" +
            "<li>Java Swing</li>" +
            "<li>StylePad MVC Pattern</li>" +
            "<li>Team:Strategy Spring Pattern</li>" +
            "<li>Made with love \u2661</li>" +
            "</ul>" +
            "</div>" +
            "</html>"
    );
    info.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
    info.setForeground(new Color(199, 21, 133));
    info.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    JButton closeButton = new JButton("Close \u2661");
    closeButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    closeButton.setBackground(new Color(255, 204, 232));
    closeButton.setForeground(new Color(199, 21, 133));
    closeButton.setFocusPainted(false);
    closeButton.setBorder(BorderFactory.createEmptyBorder(6, 22, 6, 22));
    closeButton.addActionListener(e -> dispose());

    closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent e) {
        closeButton.setBackground(new Color(255, 190, 225));
      }
      public void mouseExited(java.awt.event.MouseEvent e) {
        closeButton.setBackground(new Color(255, 204, 232));
      }
    });

    JLabel footer = new JLabel("\u2661  StylePad · for cozy writing  \u2661");
    footer.setFont(new Font("JetBrains Mono", Font.PLAIN, 10));
    footer.setForeground(new Color(210, 120, 170));
    footer.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    card.add(title);
    card.add(Box.createVerticalStrut(6));
    card.add(subtitle);
    card.add(Box.createVerticalStrut(14));
    card.add(info);
    card.add(Box.createVerticalStrut(16));
    card.add(closeButton);
    card.add(Box.createVerticalStrut(10));
    card.add(footer);

    add(card);

    pack();
    setLocationRelativeTo(parent);
  }
}
