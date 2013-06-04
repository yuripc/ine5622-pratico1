package Control;
import java.util.Arrays;
import java.util.Vector;

import UI.Main;

public abstract class ElemLex {

	Vector<Character> alfabeto;
	Vector<String> estados;
	String estadoInicial;
	Vector<String> estadosFinais;
	Vector<Vector<String>> operacoes;
	String arquivo;

	protected ElemLex(String[] elementoLexico) throws Exception {
		ElemLex elem = processar(elementoLexico);

		this.alfabeto = elem.alfabeto;
		this.estados = elem.estados;
		this.estadoInicial = elem.estadoInicial;
		this.estadosFinais = elem.estadosFinais;
		this.operacoes = elem.operacoes;
	}

	public ElemLex determinizar() {
		ElemLexAutomato elem = toAutomato();

		Vector<String> producoes = new Vector<String>();

		boolean isFinal = false;

		Vector<String> estadosPendentes = new Vector<String>();
		estadosPendentes.add(elem.estadoInicial);

		while (estadosPendentes.size() > 0) {
			String estado = estadosPendentes.get(0);

			// Loop para cada entrada possivel
			for (char entrada : elem.alfabeto) {
				String proximoEstado = "";

				// Loop para cada letra do estado global
				for (char estadoSub : estado.toCharArray()) {
					String[] proximosEstadosSub = elem.proximoEstado(estadoSub, entrada);
					for (String proximoEstadoSub : proximosEstadosSub) {
						if (!proximoEstado.contains(proximoEstadoSub) && !proximoEstadoSub.equals("&")) {
							proximoEstado += proximoEstadoSub;
						}
					}

					if (!isFinal && elem.estadosFinais.contains(estadoSub)) {
						isFinal = true;
					}
				}

				if (proximoEstado.length() == 0) {
					proximoEstado = "&";
				} else if (proximoEstado.length() > 1) {
					char[] caracteres = proximoEstado.toCharArray();
					Arrays.sort(caracteres);
					proximoEstado = new String(caracteres);
				}

				if (!estadosPendentes.contains(proximoEstado) && !elem.estados.contains(proximoEstado)) {
					estadosPendentes.add(proximoEstado);
				}

				producoes.add(proximoEstado);
			}

			elem.estados.add(estado);
			if (isFinal) {
				elem.estadosFinais.add(estado);
			}
			elem.operacoes.add(producoes);

			estadosPendentes.remove(estado);
		}

		return returnToOrigin(elem);
	}

	public ElemLex minimizar(ElemLex vElem) {
		ElemLexAutomato elem = vElem.toAutomato();
		// TODO Auto-generated method stub
		Main.debug("n‹o implementado");
		return null;
	}

	public boolean sentencaValida(ElemLex elem, String s) {
		// TODO Auto-generated method stub
		Main.debug("n‹o implementado");
		return false;
	}

	public String[] sentencasPossiveis(ElemLex elem, int n) {
		// TODO Auto-generated method stub
		Main.debug("n‹o implementado");
		return null;
	}

	public ElemLex uniao(ElemLex outroElem) {
		// TODO Auto-generated method stub
		Main.debug("n‹o implementado");
		return null;
	}

	public ElemLex complemento(ElemLex elem1, ElemLex elem2) {
		// TODO Auto-generated method stub
		Main.debug("n‹o implementado");
		return null;
	}

	protected String[] proximoEstado(String estadoAtual, char entrada) {
		try {
			Vector<String> linhaTransicoes = operacoes.get(estados.indexOf(estadoAtual));
			return linhaTransicoes.get(alfabeto.indexOf(entrada)).split(",");
		} catch (ArrayIndexOutOfBoundsException e) {
			return new String[] { "&" };
		}
	}

	public String[] proximoEstado(char estadoAtual, char entrada) {
		return proximoEstado(estadoAtual, entrada);
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

	public abstract ElemLex processar(String[] o) throws Exception;

}