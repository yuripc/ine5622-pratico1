package visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class CelulaCabecalho extends Celula {
	private static final long serialVersionUID = 1L;

	private JTextField textField;

	public CelulaCabecalho() {
		// TODO Auto-generated method stub
		super();

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(30, 20));
		textField.setBorder(new LineBorder(new Color(0, 0, 0)));
		textField.setHorizontalAlignment(JTextField.CENTER);

		this.add(textField, BorderLayout.CENTER);

	}

	public CelulaCabecalho(String s) {
		this();
		setText(s);
	}

	@Override
	public String getText() {
		if (textField.getText().equals("")) {
			return " ";
		} else {
			return textField.getText();
		}
	}

	@Override
	public void setText(String s) {
		if (s.equals(" ")) {
			s = "";
		}
		textField.setText(s);
	}

}
