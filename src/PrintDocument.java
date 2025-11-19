import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.Element;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class PrintDocument implements Printable {

    private final JTextPane textPane;
    private final Font baseFont;

    public PrintDocument(JTextPane textPane) {
        this.textPane = textPane;
        this.baseFont = textPane.getFont();
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(baseFont);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Define page margins
        double margin = 50;
        double pageXStart = pageFormat.getImageableX() + margin;
        double pageYStart = pageFormat.getImageableY() + margin;
        double printableWidth = pageFormat.getImageableWidth() - margin * 2;
        double printableHeight = pageFormat.getImageableHeight() - margin * 2;

        FontMetrics fontMetrics = graphics2D.getFontMetrics(baseFont);
        int lineHeight = fontMetrics.getHeight();

        List<PrintableElement> printableElements = extractPrintableElements(graphics2D, (int) printableWidth);
        List<Page> paginatedPages = paginateElements(printableElements, (int) printableHeight, lineHeight);

        if (pageIndex >= paginatedPages.size()) {
            return NO_SUCH_PAGE;
        }

        int currentYPosition = (int) pageYStart + fontMetrics.getAscent();

        // Draw all elements on the current page
        for (PrintableElement printableElement : paginatedPages.get(pageIndex).getElements()) {
            if (printableElement.isImage()) {
                graphics2D.drawImage(printableElement.getImage(), (int) pageXStart, currentYPosition,
                        printableElement.getWidth(), printableElement.getHeight(), null);
                currentYPosition = currentYPosition + printableElement.getHeight() + 10;
            } else {
                graphics2D.drawString(printableElement.getText(), (int) pageXStart, currentYPosition);
                currentYPosition = currentYPosition + lineHeight;
            }
        }

        return PAGE_EXISTS;
    }

    // Extract text and image elements from the StyledDocument
    private List<PrintableElement> extractPrintableElements(Graphics2D graphics2D, int maxLineWidth) {
        List<PrintableElement> printableElements = new ArrayList<>();
        StyledDocument styledDocument = textPane.getStyledDocument();

        try {
            int currentPosition = 0;
            while (currentPosition < styledDocument.getLength()) {
                Element characterElement = styledDocument.getCharacterElement(currentPosition);
                AttributeSet attributes = characterElement.getAttributes();
                Icon icon = StyleConstants.getIcon(attributes);

                if (icon != null) {
                    Image image = convertIconToImage(icon);
                    int imageWidth = image.getWidth(null);
                    int imageHeight = image.getHeight(null);

                    // Scale image if it exceeds the max line width
                    if (imageWidth > maxLineWidth) {
                        double scaleFactor = (double) maxLineWidth / imageWidth;
                        imageWidth = (int) (imageWidth * scaleFactor);
                        imageHeight = (int) (imageHeight * scaleFactor);
                        image = scaleImage(image, imageWidth, imageHeight);
                    }

                    printableElements.add(PrintableElement.createImageElement(image, imageWidth, imageHeight));
                    currentPosition = characterElement.getEndOffset();
                } else {
                    String textSegment = styledDocument.getText(currentPosition,
                            characterElement.getEndOffset() - currentPosition);
                    printableElements.addAll(splitTextIntoLines(textSegment, graphics2D, maxLineWidth));
                    currentPosition = currentPosition + textSegment.length();
                }
            }
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }

        return printableElements;
    }

    // Split long text into lines based on available width
    private List<PrintableElement> splitTextIntoLines(String text, Graphics2D graphics2D, int maxLineWidth) {
        List<PrintableElement> lines = new ArrayList<>();
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        BreakIterator wordBoundary = BreakIterator.getLineInstance();
        wordBoundary.setText(text);

        int start = wordBoundary.first();
        int end = wordBoundary.next();
        StringBuilder currentLineBuilder = new StringBuilder();

        while (end != BreakIterator.DONE) {
            String word = text.substring(start, end);
            String testLine = currentLineBuilder + word;

            if (fontMetrics.stringWidth(testLine) > maxLineWidth) {
                if (fontMetrics.stringWidth(word) > maxLineWidth) {
                  // Split very long words
                    currentLineBuilder.append(splitLongWordIntoLines(word, fontMetrics, maxLineWidth, lines));
                } else {
                    lines.add(PrintableElement.createTextElement(currentLineBuilder.toString().trim()));
                    currentLineBuilder = new StringBuilder(word.trim());
                }
            } else {
                currentLineBuilder.append(word);
            }

            start = end;
            end = wordBoundary.next();
        }

        if (!currentLineBuilder.isEmpty()) {
            lines.add(PrintableElement.createTextElement(currentLineBuilder.toString().trim()));
        }

        return lines;
    }

    // Helper to split a long word that exceeds max line width
    private String splitLongWordIntoLines(String word, FontMetrics fontMetrics, int maxLineWidth, List<PrintableElement> lines) {
        StringBuilder currentPart = new StringBuilder();
        for (char character : word.toCharArray()) {
            currentPart.append(character);
            if (fontMetrics.stringWidth(currentPart.toString()) > maxLineWidth) {
                lines.add(PrintableElement.createTextElement(currentPart.substring(0, currentPart.length() - 1)));
                currentPart = new StringBuilder(String.valueOf(character));
            }
        }
        return currentPart.toString();
    }

    // Split elements into pages based on maximum page height
    private List<Page> paginateElements(List<PrintableElement> elements, int maxPageHeight, int lineHeight) {
        List<Page> pages = new ArrayList<>();
        Page currentPage = new Page();
        int usedHeight = 0;

        for (PrintableElement element : elements) {
            int elementHeight = element.isImage() ? element.getHeight() + 10 : lineHeight;

            if (usedHeight + elementHeight > maxPageHeight) {
                pages.add(currentPage);
                currentPage = new Page();
                usedHeight = 0;
            }

            currentPage.addElement(element);
            usedHeight = usedHeight + elementHeight;
        }

        if (!currentPage.getElements().isEmpty()) {
            pages.add(currentPage);
        }

        return pages;
    }

    // Convert an Icon to an Image
    private Image convertIconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = image.createGraphics();
            icon.paintIcon(null, graphics2D, 0, 0);
            graphics2D.dispose();
            return image;
        }
    }

    // Scale an image to specified width and height
    private Image scaleImage(Image sourceImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(sourceImage, 0, 0, width, height, null);
        graphics2D.dispose();
        return resizedImage;
    }

    // Represents a text or image element ready for printing
    private static class PrintableElement {
        private final boolean imageFlag;
        private final String text;
        private final Image image;
        private final int width;
        private final int height;

        private PrintableElement(String text, Image image, boolean imageFlag, int width, int height) {
            this.text = text;
            this.image = image;
            this.imageFlag = imageFlag;
            this.width = width;
            this.height = height;
        }

        public static PrintableElement createTextElement(String text) {
            return new PrintableElement(text, null, false, 0, 0);
        }

        public static PrintableElement createImageElement(Image image, int width, int height) {
            return new PrintableElement(null, image, true, width, height);
        }

        public boolean isImage() {
            return imageFlag;
        }

        public String getText() {
            return text;
        }

        public Image getImage() {
            return image;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    // Represents a page of printable elements
    private static class Page {
        private final List<PrintableElement> elements = new ArrayList<>();

        public void addElement(PrintableElement element) {
            elements.add(element);
        }

        public List<PrintableElement> getElements() {
            return elements;
        }
    }
}
