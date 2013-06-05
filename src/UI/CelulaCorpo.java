package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class CelulaCorpo extends Celula{
	private static final long serialVersionUID = 1L;

	private JTextArea textArea;

	public CelulaCorpo(){
		// TODO Auto-generated method stub
		super();

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(false);
		textArea.setMargin(new Insets(5, 5, 5, 5));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		scrollPane.setPreferredSize(new Dimension(60, 45));
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));

		this.add(scrollPane, BorderLayout.CENTER);
	}

	public CelulaCorpo(String s){
		this();
		setText(s);
	}
	@Override
	public String getText() {
		if (textArea.getText().equals("")) {
			return " ";
		} else {
			return textArea.getText();
		}
	}

	@Override
	public void setText(String s) {
		if (s.equals(" ")) {
			s = "";
		}
		textArea.setText(s);
	}

}
