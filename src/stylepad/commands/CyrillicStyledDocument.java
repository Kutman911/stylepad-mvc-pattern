package stylepad.commands;
import stylepad.Viewer;
import java.awt.Font;
import javax.swing.text.*;
import javax.swing.Icon;

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


    if (as != null && (as.isDefined(StyleConstants.IconAttribute) ||
            as.getAttribute(StyleConstants.IconAttribute) != null)) {
      super.insertString(offset, str, as);
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