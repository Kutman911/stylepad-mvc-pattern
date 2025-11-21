package commands;
import src.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.BadLocationException;

public class FindDialog extends JDialog {

    private final Viewer viewer;
    private final JTextComponent textPane;
    private JTextField findField;
    private JCheckBox matchCaseCheckBox;
    private int searchStartIndex;

    public FindDialog(Viewer viewer) {
        super(viewer.getFrame(), "Find", false);
        this.viewer = viewer;
        this.textPane = viewer.getTextPane();

        setupDialogUI();
    }

    private void setupDialogUI() {
        searchStartIndex = 0;
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel findLabel = new JLabel("Find what:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(findLabel, gbc);

        findField = new JTextField(20);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        searchPanel.add(findField, gbc);

        matchCaseCheckBox = new JCheckBox("Match case");
        matchCaseCheckBox.setBackground(searchPanel.getBackground());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        searchPanel.add(matchCaseCheckBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton findNextButton = new JButton("Find Next");
        findNextButton.addActionListener(e -> findNext());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));

        buttonPanel.add(findNextButton);
        buttonPanel.add(cancelButton);

        add(searchPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setResizable(false);
    }


    private void findNext() {
        String searchText = findField.getText();
        if (searchText.isEmpty()) {
            return;
        }

        String documentText;
        try {
            Document doc = textPane.getDocument();
            documentText = doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            System.err.println("Error reading document text: " + e.getMessage());
            return;
        }

        boolean matchCase = matchCaseCheckBox.isSelected();
        String searchTarget = matchCase ? searchText : searchText.toLowerCase();
        String source = matchCase ? documentText : documentText.toLowerCase();

        if (searchStartIndex >= source.length()) {
            searchStartIndex = 0;
        }

        int index = source.indexOf(searchTarget, searchStartIndex);

        if (index != -1) {

            textPane.select(index, index + searchText.length());

            searchStartIndex = index + searchText.length();

        } else {
            searchStartIndex = 0;

            int option = JOptionPane.showConfirmDialog(
                this,
                "Cannot find \"" + searchText + "\". Continue search from the beginning?",
                "Find",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                findNext();
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            setLocationRelativeTo(viewer.getFrame());
            searchStartIndex = textPane.getSelectionEnd();
        }
        super.setVisible(visible);
    }
}
