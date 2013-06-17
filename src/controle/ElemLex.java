package controle;

import java.util.Vector;

public abstract class ElemLex implements Cloneable {

	Vector<Character> alfabeto;
	Vector<String> estados;
	Vector<Vector<String>> operacoes;

	String estadoInicial;

	public Vector<Operacao> determinizar() {
		ElemLexAutomato elem = toAutomato();

		elem.determinizarAutomato();

		Vector<Operacao> operacoes = new Vector<Operacao>();
		operacoes.add(new Operacao("Determinimização", returnToOrigin(elem), true));
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

	protected boolean isEstadoValido(String estado) throws InvalidInputException {
		if (!estado.matches("[A-Z][a-z0-9]{" + (estado.length() - 1) + "}")) {
			throw new InvalidInputException("Estado deve conter uma letra maiúscula seguida de 0 ou mais letras e/ou dígitos");
		}
		return true;
	}

	protected boolean isEntradaValida(String entrada) throws InvalidInputException {
		if (entrada.length() > 1) {
			throw new InvalidInputException("Caractere de entrada deve ser um único caractere");
		}
		if (!entrada.matches("[a-z0-9]")) {
			throw new InvalidInputException("Caractere de entrada só pode ser letra minuscula ou digito");
		}
		return true;
	}

	public abstract ElemLexGR toGR();

	public abstract ElemLexAutomato toAutomato();

	public abstract Vector<Operacao> converter();

}