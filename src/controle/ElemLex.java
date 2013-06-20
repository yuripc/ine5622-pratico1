package controle;

import java.util.Vector;

public abstract class ElemLex implements Cloneable {

	Vector<Character> alfabeto;
	Vector<String> estados;
	Vector<Vector<String>> operacoes;

	String estadoInicial;

	public Operacao determinizar() {
		ElemLexAutomato elem = toAutomato();

		elem.determinizarAutomato();

		return new Operacao("Determinimização", returnToOrigin(elem));
	}

	public Operacao minimizar() {
		ElemLexAutomato elem = this.toAutomato();

		elem.minimizarAutomato();

		return new Operacao("Minimização", returnToOrigin(elem));
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

	public Operacao uniao(ElemLex elemLexDir) {
		ElemLexAutomato elemEsq = this.toAutomato();
		ElemLexAutomato elemDir = elemLexDir.toAutomato();

		ElemLexAutomato elemProduto = elemEsq.unirAutomatos(elemDir);

		return new Operacao("União", returnToOrigin(elemProduto));
	}

	public Operacao complemento() {
		ElemLexAutomato elem = toAutomato();

		elem.complementarAutomato();

		return new Operacao("Complemento", returnToOrigin(elem));
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

	public abstract Operacao converter();

}