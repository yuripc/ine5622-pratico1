package visao;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public abstract class Celula extends JPanel{

	private static final long serialVersionUID = 1L;

	public Celula(){
		super();
		this.setLayout(new BorderLayout(0, 0));
	}


	public abstract String getText();
	public abstract void setText(String s);

}
