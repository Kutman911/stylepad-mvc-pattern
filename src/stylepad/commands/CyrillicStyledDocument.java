package stylepad.commands;
import stylepad.Viewer;
import java.awt.Font;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class CyrillicStyledDocument extends DefaultStyledDocument {

  private Font latinFont;
  private Font cyrillicFallbackFont;

  public CyrillicStyledDocument(Font latinFont, Font cyrillicFallbackFont) {
    this.latinFont = latinFont;
    this.cyrillicFallbackFont = cyrillicFallbackFont;
  }

  public void insertString(int offset, String str, AttributeSet as) throws BadLocationException {
    if (str == null) {
      return;
    }

    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);

      Font useFont = null;

      if (latinFont.canDisplay(ch)) {
        useFont = latinFont;
      } else {
        useFont = cyrillicFallbackFont;
      }

      SimpleAttributeSet sas = new SimpleAttributeSet();
      StyleConstants.setFontFamily(sas, useFont.getFamily());
      StyleConstants.setFontSize(sas, useFont.getSize());
      StyleConstants.setBold(sas, (useFont.getStyle() & Font.BOLD) != 0);
      StyleConstants.setItalic(sas, (useFont.getStyle() & Font.ITALIC) != 0);

      super.insertString(offset + i, String.valueOf(ch), sas);
    }
  }
}
