import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class PrintDocument implements Printable {

    private String content;
    private Font font;

    public PrintDocument(String content, Font font) {
        this.content = content;
        this.font = font;
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);
        g2d.setPaint(Color.black);

        // print area dimensions
        double pageX = pf.getImageableX();
        double pageY = pf.getImageableY();
        double pageWidth = pf.getImageableWidth();
        double pageHeight = pf.getImageableHeight();

        // adding internal padding
        double margin = 50;
        double x = pageX + margin;
        double y = pageY + margin;
        double width = pageWidth - margin * 2;
        double height = pageHeight - margin * 2;

        FontMetrics metrics = g2d.getFontMetrics();
        int lineHeight = metrics.getHeight();

        // split the text into lines based on width
        String[] paragraphs = content.split("\n");
        List<String> lines = new ArrayList<>();

        for (String paragraph : paragraphs) {
            if (paragraph.isEmpty()) {
                lines.add("");
                continue;
            }

            BreakIterator boundary = BreakIterator.getLineInstance();
            boundary.setText(paragraph);
            int start = boundary.first();
            int end = boundary.next();

            StringBuilder currentLine = new StringBuilder();

            while (end != BreakIterator.DONE) {
                String word = paragraph.substring(start, end);
                String testLine = currentLine + word;

                if (metrics.stringWidth(testLine) > width) {
                    lines.add(currentLine.toString().trim());
                    currentLine = new StringBuilder(word.trim());
                } else {
                    currentLine.append(word);
                }

                start = end;
                end = boundary.next();
            }

            if (currentLine.length() > 0) {
                lines.add(currentLine.toString());
            }
        }

        int linesPerPage = (int) (height / lineHeight);
        int totalPages = (int) Math.ceil((double) lines.size() / linesPerPage);

        if (pageIndex >= totalPages) {
            return NO_SUCH_PAGE;
        }

        int startLine = pageIndex * linesPerPage;
        int endLine = Math.min(startLine + linesPerPage, lines.size());

        int yPosition = (int) y + metrics.getAscent();

        for (int i = startLine; i < endLine; i++) {
            g2d.drawString(lines.get(i), (int) x, yPosition);
            yPosition += lineHeight;
        }

        return PAGE_EXISTS;
    }
}
