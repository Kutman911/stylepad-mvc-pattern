import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;


public class Viewer {

    public Viewer() {
      Controller controller = new Controller(this);
      createGUI(controller);
    }

    private void createGUI(Controller controller) {

        JMenuBar menuBar = createJMenuBar(controller);

        Font fontTextArea = new Font("Bernard MT Condensed", Font.PLAIN, 35);
        Color colorTextArea = Color.BLACK;
        JTextArea textArea = new JTextArea("Center");
        textArea.setFont(fontTextArea);
        textArea.setForeground(colorTextArea);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame frame = new JFrame("Stylepad MVC Pattern");
        frame.setSize(800, 800);
        frame.setLocation(500, 150);
        frame.setJMenuBar(menuBar);
        frame.add("Center", scrollPane);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JMenuBar createJMenuBar(Controller controller) {

        JMenu fileMenu = createFileMenu(controller);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        return menuBar;
    }

    private JMenu createFileMenu(Controller controller) {

        JMenuItem newDocument = new JMenuItem("Новый", new ImageIcon("images/new.gif"));
        newDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newDocument.addActionListener(controller);
        newDocument.setActionCommand("New_Document");

        JMenuItem openDocument = new JMenuItem("Открыть ...", new ImageIcon("images/open.gif"));
        openDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openDocument.addActionListener(controller);
        openDocument.setActionCommand("Open_Document");

        JMenu fileMenu = new JMenu("Файл");
        fileMenu.add(newDocument);
        fileMenu.add(openDocument);
        return fileMenu;
    }
}
