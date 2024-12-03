package fieldFormats;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


public class DecimalDocumentFilter extends DocumentFilter {
    private int maxIntegerDigits;
    private int maxDecimalDigits;

    public DecimalDocumentFilter(int maxIntegerDigits, int maxDecimalDigits) {
        this.maxIntegerDigits = maxIntegerDigits;
        this.maxDecimalDigits = maxDecimalDigits;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + string + currentText.substring(offset);

        if (isValid(newText)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + string + currentText.substring(offset + length);

        if (isValid(newText)) {
            super.replace(fb, offset, length, string, attrs);
        }
    }

    private boolean isValid(String text) {
        if (text.isEmpty()) {
            return true;
        }

        // Permite números con un máximo de 5 dígitos antes del punto y 2 dígitos después del punto
        String regex = "^\\d{0," + maxIntegerDigits + "}(\\.\\d{0," + maxDecimalDigits + "})?$";
        return text.matches(regex);
    }
}
