package visao;

import javax.swing.JPanel;

public abstract class ElemLex extends JPanel implements Cloneable{

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

	@Override
	public ElemLex clone(){
		try {
			return (ElemLex) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public abstract void habilitarEdicao(boolean habilitar);
}
