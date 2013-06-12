package controle;

public class Operacao {
	protected String operacao;
	protected ElemLex elemLex;
	protected boolean habilitarOperacoes;

	public Operacao(String operacao, ElemLex elemLex,boolean habilitarOperacoes) {
		super();
		this.operacao = operacao;
		this.elemLex = elemLex;
		this.habilitarOperacoes = habilitarOperacoes;

	}

	public String getOperacao() {
		return operacao;
	}

	public ElemLex getElemLex() {
		return elemLex;
	}

}
