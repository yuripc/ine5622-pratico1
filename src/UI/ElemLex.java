package UI;

import javax.swing.JPanel;

public abstract class ElemLex extends JPanel {

	private static final long serialVersionUID = 1L;

	public ElemLex() {
		initialize();
	}

	public ElemLex(String s) throws Exception {
		initialize();
		loadString(s);
	}

	protected abstract void initialize();
	protected abstract void loadString(String s) throws Exception;

	public abstract String tipo();
}
