package visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class CelulaCorpo extends Celula {
	private static final long serialVersionUID = 1L;

	private JTextArea textArea;

	private static final Dimension dimension = new Dimension(60, 45);
	private static final LineBorder border = new LineBorder(Color.BLACK);

	public CelulaCorpo() {
		super();

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(false);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setDisabledTextColor(Color.BLACK);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		scrollPane.setPreferredSize(dimension);
		scrollPane.setBorder(border);

		this.add(scrollPane, BorderLayout.CENTER);
	}

	public CelulaCorpo(String s) {
		this();
		setText(s);
	}

	@Override
	public String getText() {
		if (textArea.getText().equals("")) {
			return " ";
		} else {
			return textArea.getText().replace("\t", "");
		}
	}

	@Override
	public void setText(String s) {
		if (s.equals(" ")) {
			s = "";
		}
		textArea.setText(s);
	}

	@Override
	public void setEnabled(boolean enabled) {
		textArea.setEnabled(enabled);
	}

	@Override
	public void setFocus() {
		textArea.grabFocus();
	}

}
