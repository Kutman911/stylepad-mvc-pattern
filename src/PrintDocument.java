import java.awt.print.Printable;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.Graphics2D;
import java.awt.Font;

public class PrintDocument implements Printable {

  private String content;
  private Font font;

  public PrintDocument(String content) {
    this.content = content;
    font = new Font("Arial", Font.BOLD | Font.ITALIC, 25);
  }
  public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
    if (page > 0) {
      return NO_SUCH_PAGE;
    }

    g.setFont(font);
    Graphics2D g2d = (Graphics2D)g;
    g2d.translate(pf.getImagebleX(), pf.getImagebleY());

    g.drawString("Some words...", 100, 100);

    return PAGE_EXISTS;
  }
}
