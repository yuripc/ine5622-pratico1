package visao;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public abstract class Celula extends JPanel {

	private static final long serialVersionUID = 1L;

	protected final DocumentFilter filtro = new DocumentFilter() {
		@Override
		public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
			fb.insertString(offset, text.replaceAll("[#\t]+", ""), attr);
		}

		@Override
		public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
			fb.replace(offset, length, text.replaceAll("[#\t]+", ""), attr);
		}

	};

	public Celula() {
		super();
		this.setLayout(new BorderLayout(0, 0));
	}

	public abstract String getText();

	public abstract void setText(String s);

	@Override
	public abstract void setEnabled(boolean enabled);

	public abstract void setFocus();
}
