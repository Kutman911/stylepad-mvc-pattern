import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class WrapHandler extends CommandHandler {
  private Viewer viewer;
  private boolean isWrapEnabled = false;

  public WrapHandler(Viewer viewer) {
    this.viewer = viewer;
  }

  void command() {
    isWrapEnabled = !isWrapEnabled;

    JTextPane textPane = viewer.getTextPane();
    Document currentDocument = textPane.getDocument();
    int caretPosition = textPane.getCaretPosition();

    if (isWrapEnabled) {
      textPane.setEditorKit(new WrapEditorKit());
    } else {
      textPane.setEditorKit(new StyledEditorKit());
    }

    textPane.setDocument(currentDocument);

    if (caretPosition <= currentDocument.getLength()) {
      textPane.setCaretPosition(caretPosition);
    } else {
      textPane.setCaretPosition(currentDocument.getLength());
    }

    textPane.revalidate();
    textPane.repaint();
  }

  static class WrapEditorKit extends StyledEditorKit {
    public ViewFactory getViewFactory() {
      return new CustomViewFactory();
    }

    static class CustomViewFactory implements ViewFactory {
      public View create(Element element) {
        String elementType = element.getName();

        if (AbstractDocument.ContentElementName.equals(elementType)) {
          return new WrapLabelView(element);
        } else if (AbstractDocument.ParagraphElementName.equals(elementType)) {
          return new ParagraphView(element);
        } else if (AbstractDocument.SectionElementName.equals(elementType)) {
          return new BoxView(element, View.Y_AXIS);
        } else if (StyleConstants.ComponentElementName.equals(elementType)) {
          return new ComponentView(element);
        } else if (StyleConstants.IconElementName.equals(elementType)) {
          return new IconView(element);
        }

        return new LabelView(element);
      }
    }
  }

  static class WrapLabelView extends LabelView {
    public WrapLabelView(Element element) {
      super(element);
    }

    public float getMinimumSpan(int axis) {
      if (axis == View.X_AXIS) {
        return 0;
      } else {
        return super.getMinimumSpan(axis);
      }
    }
  }
}
