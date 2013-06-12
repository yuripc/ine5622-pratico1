package controle;

import java.util.Vector;

public abstract class ElemLex implements Cloneable {

	Vector<Character> alfabeto;
	Vector<String> estados;
	String estadoInicial;
	Vector<String> estadosFinais;
	Vector<Vector<String>> operacoes;
	String input;

	public Vector<Operacao> determinizar() {
		ElemLexAutomato elem = toAutomato();

		elem.determinizarAutomato();

		Vector<Operacao> operacoes = new Vector<Operacao>();
		operacoes.add(new Operacao("Determinimização",returnToOrigin(elem),true));
		return operacoes;
	}

	public Vector<Operacao> minimizar() {
		ElemLexAutomato elem = this.toAutomato();
		// TODO Auto-generated method stub
		System.out.println("não implementado");
		return null;
	}

	public boolean reconhecerSentenca(String s) {
		// TODO Auto-generated method stub
		System.out.println("não implementado");
		return false;
	}

	public String[] gerarSentencas(int n) {
		// TODO Auto-generated method stub
		System.out.println("não implementado");
		return null;
	}

	public ElemLex uniao(ElemLex outroElem) {
		// TODO Auto-generated method stub
		System.out.println("não implementado");
		return null;
	}

	public ElemLex complemento(ElemLex outroElem) {
		// TODO Auto-generated method stub
		System.out.println("não implementado");
		return null;
	}

	protected ElemLex returnToOrigin(ElemLex elemGerado) {
		if (this instanceof ElemLexAutomato) {
			return elemGerado.toAutomato();
		} else {
			return elemGerado.toGR();
		}
	}

	public abstract ElemLexGR toGR();

	public abstract ElemLexAutomato toAutomato();
}