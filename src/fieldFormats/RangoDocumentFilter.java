package fieldFormats;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class RangoDocumentFilter extends DocumentFilter {
    private int minValue;
    private int maxValue;

    public RangoDocumentFilter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
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

        try {
            int value = Integer.parseInt(text);
            return value >= minValue && value <= maxValue;
        } catch (NumberFormatException e) {
            return false; // No es un número válido
        }
    }
}
