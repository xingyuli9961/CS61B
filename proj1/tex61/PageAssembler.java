package tex61;

import static tex61.FormatException.error;

/** A PageAssembler accepts complete lines of text (minus any
 *  terminating newlines) and turns them into pages, adding form
 *  feeds as needed.  It prepends a form feed (Control-L  or ASCII 12)
 *  to the first line of each page after the first.  By overriding the
 *  'write' method, subtypes can determine what is done with
 *  the finished lines.
 *  @author Kiet Lam
 */
abstract class PageAssembler {

    /** Current page.*/
    private int _currentPage = 0;

    /** Infinite text height.*/
    public static final int INFINITE_HEIGHT = -1;

    /** Text height.*/
    private int _textHeight = INFINITE_HEIGHT;

    /** Current text height.*/
    private int _currentTextHeight = 0;

    /** Create a new PageAssembler that sends its output to OUT.
     *  Initially, its text height is unlimited. It prepends a form
     *  feed character to the first line of each page except the first. */
    PageAssembler() {
    }

    /** Add LINE to the current page, starting a new page with it if
     *  the previous page is full. A null LINE indicates a skipped line,
     *  and has no effect at the top of a page. */
    void addLine(String line) {

        if (line == null && _currentTextHeight == 0) {
            return;
        }

        if (line == null && _currentTextHeight == _textHeight) {
            return;
        }

        if (_currentTextHeight == _textHeight && line != null) {
            line = "\f" + line;
            _currentTextHeight = 0;
        } else {
            _currentTextHeight += 1;
        }

        if (line == null) {
            write("");
        } else {
            write(line);
        }
    }

    /** Set text height to VAL, where VAL > 0. */
    void setTextHeight(int val) {
        if (val <= 0) {
            throw error("VAL needs to be > 0 for text height");
        }

        _textHeight = val;
    }

    /** Perform final disposition of LINE, as determined by the
     *  concrete subtype. */
    abstract void write(String line);
}
