package UI;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ElemLexGR extends JPanel implements ElemLex {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea;

	/**
	 * Create the panel.
	 */
	public ElemLexGR() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setMargin(new Insets(5, 5, 5, 5));
		scrollPane.setViewportView(textArea);
	}

	@Override
	public String toString() {
		return textArea.getText();
	}

	@Override
	public void loadString(String s) throws Exception {
		textArea.setText(s);
	}

	@Override
	public String tipo() {
		return "GR";
	}
}
