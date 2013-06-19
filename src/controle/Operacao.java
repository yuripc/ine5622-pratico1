package controle;

public class Operacao {
	protected String operacao;
	protected ElemLex elemLex;

	public Operacao(String operacao, ElemLex elemLex) {
		super();
		this.operacao = operacao;
		this.elemLex = elemLex;

	}

	public String getOperacao() {
		return operacao;
	}

	public ElemLex getElemLex() {
		return elemLex;
	}

}
