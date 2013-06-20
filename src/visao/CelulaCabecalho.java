package visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class CelulaCabecalho extends Celula {
	private static final long serialVersionUID = 1L;

	private JTextField textField;

	private static final Dimension dimension = new Dimension(30, 20);
	private static final LineBorder border = new LineBorder(Color.BLACK);

	public CelulaCabecalho() {
		super();

		textField = new JTextField();
		textField.setPreferredSize(dimension);
		textField.setBorder(border);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setDisabledTextColor(Color.BLACK);

		this.add(textField, BorderLayout.CENTER);
	}

	public CelulaCabecalho(String s) {
		this();
		if (s == null) {
			setEnabled(false);
		} else {
			setText(s);
		}
	}

	@Override
	public String getText() {
		if (textField.getText().equals("")) {
			return " ";
		} else {
			return textField.getText().replace("\t", "");
		}
	}

	@Override
	public void setText(String s) {
		if (s.equals(" ")) {
			s = "";
		}
		textField.setText(s);
	}

	@Override
	public void setEnabled(boolean enabled) {
		textField.setEnabled(enabled);
	}

	@Override
	public void setFocus() {
		textField.grabFocus();
	}

}
