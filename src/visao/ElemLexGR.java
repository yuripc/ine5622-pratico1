package visao;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ElemLexGR extends ElemLex {
	private static final long serialVersionUID = 1L;

	JTextArea textArea;

	public ElemLexGR() {
		super();
	}

	public ElemLexGR(String s) throws Exception {
		super(s);
	}

	@Override
	protected void initialize() {
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
	protected void loadString(String s) throws Exception {
		textArea.setText(s);
	}

	@Override
	public void habilitarEdicao(boolean habilitar) {
		textArea.setEnabled(habilitar);
	}

	@Override
	public void irPara(int linha, int coluna) {
		int pos = 0;
		String[] linhas = textArea.getText().split("\n");
		for(int linhaTexto = 0;linhaTexto<linha; linhaTexto++){
			pos+=linhas[linhaTexto].length();
		}
		textArea.setCaretPosition(pos+coluna);
	}
}
