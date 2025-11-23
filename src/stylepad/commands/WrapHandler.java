package stylepad.commands;
import stylepad.Viewer;      
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class WrapHandler extends CommandHandler {

    private Viewer viewer;
    private boolean isWrapEnabled = true;

    public WrapHandler(Viewer viewer) {
        this.viewer = viewer;
    }

    public void command() {
        isWrapEnabled = !isWrapEnabled;

        JTextPane textPane = viewer.getTextPane();
        Document currentDocument = textPane.getDocument();
        int caretPosition = textPane.getCaretPosition();

        if (isWrapEnabled) {
            textPane.setEditorKit(new WrapEditorKit());
        } else {
            textPane.setEditorKit(new StyledEditorKit());
        }

        SwingUtilities.invokeLater(() ->
            SwingUtilities.invokeLater(() -> {
                textPane.setDocument(currentDocument);
                textPane.setCaretPosition(Math.min(caretPosition, currentDocument.getLength()));
                textPane.revalidate();
                textPane.repaint();
            })
        );
    }

    public static class WrapEditorKit extends StyledEditorKit {
        public ViewFactory getViewFactory() {
            return new CustomViewFactory();
        }
    }

    static class CustomViewFactory implements ViewFactory {

        public View create(Element element) {
            String type = element.getName();

            if (AbstractDocument.ContentElementName.equals(type))
                return new WrapLabelView(element);
            if (AbstractDocument.ParagraphElementName.equals(type))
                return new ParagraphView(element);
            if (AbstractDocument.SectionElementName.equals(type))
                return new BoxView(element, View.Y_AXIS);
            if (StyleConstants.ComponentElementName.equals(type))
                return new ComponentView(element);
            if (StyleConstants.IconElementName.equals(type))
                return new IconView(element);

            return new LabelView(element);
        }
    }

    public static class WrapLabelView extends LabelView {
        public WrapLabelView(Element element) {
            super(element);
        }

        public float getMinimumSpan(int axis) {
            if (axis == View.X_AXIS) {
                return 0;
            }
            return super.getMinimumSpan(axis);
        }
    }
}
