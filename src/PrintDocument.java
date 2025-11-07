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
    g2d.translate(pf.getImageableX(), pf.getImageableY());

    int x = 50;
    int y = 50;
    int step = 25;

    String line = "";

    for (int i = 0; i < content.length(); i++) {
      char symbol = content.charAt(i);
      if (symbol == '\n') {
        g.drawString(line, x, y);
        y += step;
        line = "";
      } else {
        line += symbol;
      }
    }
    if (!line.isEmpty()) {
      g.drawString(line, x, y);
    }

    return PAGE_EXISTS;
  }
}
