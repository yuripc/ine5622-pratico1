package controle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

public class ElemLexAutomato extends ElemLex {

	protected final String separador = ",";
	protected final String epsilon = "&";

	public ElemLexAutomato(String input) throws InvalidInputException {
		String[] s = input.split("\n");
		alfabeto = new Vector<Character>();
		estados = new Vector<String>();
		estadoInicial = "";
		estadosFinais = new Vector<String>();
		operacoes = new Vector<Vector<String>>();

		String[][] tabela = new String[s.length][s[0].length() - s[0].replaceAll("\\.", "").length()];
		for (int i = 0; i < tabela.length; i++) {
			tabela[i] = s[i].split("\\t");
		}

		for (int linha = 1; linha < tabela.length; linha++) {

			// Valida estados
			String estado = tabela[linha][1];
			if (estado.matches("[A-Z][a-z0-9]{" + (estado.length() - 1) + "}")) {
				if (!estados.contains(estado)) {
					estados.add(estado);
				} else {
					throw new InvalidInputException("Estado " + estado + " repetido", linha, 1);
				}
			} else {
				throw new InvalidInputException("Estado deve conter uma letra maiœscula seguida de 0 ou mais letras e/ou d’gitos", linha, 1);
			}

			// Valida estados iniciais e finais
			String coluna0 = tabela[linha][0];
			if (coluna0.contains("*")) {
				estadosFinais.add(estado);
			}
			if (coluna0.contains("->")) {
				if (estadoInicial.equals("")) {
					estadoInicial = estado;
				} else {
					throw new InvalidInputException("Ja existe estado inicial", linha, 0);
				}
			}
			if (!((coluna0.length() == 3 && coluna0.contains("*") && coluna0.contains("->")) || (coluna0.length() == 2 && coluna0.contains("->"))
					|| (coluna0.length() == 1 && coluna0.contains("*")) || (coluna0.length() == 0))) {

				throw new InvalidInputException("Apenas '->' ou '*' s‹o v‡lidos", linha, 0);
			}
		}

		if (estadoInicial.equals("")) {
			throw new InvalidInputException("Nenhum estado inicial definido");
		} else if (estadosFinais.size() == 0) {
			throw new InvalidInputException("Nenhum estado final definido");
		} else if (estados.size() == 0) {
			throw new InvalidInputException("Nenhum estado definido");
		}

		// Valida alfabeto
		for (int coluna = 2; coluna < tabela[0].length; coluna++) {
			if (tabela[0][coluna].length() > 1) {
				throw new InvalidInputException("Caractere de entrada deve ser um œnico caractere", 0, coluna);
			}
			char caractere = tabela[0][coluna].charAt(0);
			if ((caractere + "").matches("[a-z0-9]")) {
				if (!alfabeto.contains(caractere)) {
					alfabeto.add(caractere);
				} else {
					throw new InvalidInputException("Caractere de entrada ja inserido", 0, coluna);
				}
			} else {
				throw new InvalidInputException("Caractere de entrada s— pode ser letra minuscula ou digito", 0, coluna);
			}
		}

		// Valida transicoes
		for (int linha = 1; linha < tabela.length; linha++) {
			operacoes.add(new Vector<String>());
			for (int coluna = 2; coluna < tabela[0].length; coluna++) {
				String[] transicoes = tabela[linha][coluna].split(separador);

				Vector<String> transicoesValidas = new Vector<String>();

				for (String transicao : transicoes) {
					transicao = transicao.trim();
					if (estados.contains(transicao)) {
						if (!transicoesValidas.contains(transicao)) {
							transicoesValidas.add(transicao);
						}
					} else if (!transicao.matches("^[-&]$") && !transicao.equals("")) {
						throw new InvalidInputException("Estado " + transicao + " n‹o definido", linha, coluna);
					}
				}

				StringBuilder transicao = new StringBuilder();
				if (transicoesValidas.isEmpty()) {
					transicao.append(epsilon);
				} else {
					for (String transicaoValida : transicoesValidas) {
						transicao.append(transicaoValida + separador);
					}

					transicao.deleteCharAt(transicao.length() - 1);
				}

				operacoes.get(linha - 1).add(transicao.toString());
			}
		}

	}

	protected void determinizarAutomato() {

		Vector<String> estadosPendentes = new Vector<String>();

		for (String estado : estados) {
			for (char entrada : alfabeto) {
				String proxEstado = proximoEstado(estado, entrada);

				if (!estados.contains(proxEstado) && !estadosPendentes.contains(proxEstado) && !proxEstado.equals(epsilon)) {
					estadosPendentes.add(proxEstado);
				}
				setOperacao(estado, entrada, proxEstado.replace(separador, ""));
			}
		}

		while (estadosPendentes.size() > 0) {
			String estado = estadosPendentes.get(0).replace(separador, "");
			String estadoNaoDeterminizado = estadosPendentes.get(0);

			estados.add(estado);
			operacoes.add(new Vector<String>());

			for (char entrada : alfabeto) {
				String proxEstado = proximoEstado(estadoNaoDeterminizado, entrada);

				if (!estados.contains(proxEstado) && !estadosPendentes.contains(proxEstado) && !proxEstado.equals(epsilon)) {
					estadosPendentes.add(proxEstado);
				}

				setOperacao(estado, entrada, proxEstado);
			}

			boolean estadoFinal = false;
			String[] subEstados = estadoNaoDeterminizado.split(separador);

			for (int i = 0; i < subEstados.length && !estadoFinal; i++) {
				if (estadosFinais.contains(subEstados[i])) {
					estadosFinais.add(estado);
					estadoFinal = true;
				}
			}

			estadosPendentes.remove(0);
		}

		//TODO NORMALIZAR ESTADOS
	}

	protected String proximoEstado(String estado, char entrada) {
		Vector<String> proximosEstados = new Vector<String>();

		for (String subEstado : estado.split(separador)) {
			if (!subEstado.equals(epsilon)) {
				int linha = estados.indexOf(subEstado);
				int coluna = alfabeto.indexOf(entrada);
				String proxEstado = operacoes.get(linha).get(coluna);

				for (String subProxEstado : proxEstado.split(separador)) {
					if (!subProxEstado.equals(epsilon) && !proximosEstados.contains(subProxEstado)) {
						proximosEstados.add(subProxEstado);
					}
				}
			}
		}

		if (proximosEstados.size() == 0) {
			proximosEstados.add(epsilon);
		} else {
			removerDuplicatas(proximosEstados);
		}

		return StringUtils.join(proximosEstados, separador);
	}

	// TODO COLOCAR DE VOLTA
	//	protected String normalizarEstado(String s) {
	//		return StringUtils.capitalize(s.toLowerCase()).replace(separador, "");
	//	}

	protected void setOperacao(String estado, char entrada, String proximoEstado) {
		int linha = estados.indexOf(estado);
		int coluna = alfabeto.indexOf(entrada);
		if (operacoes.get(linha).size() > coluna) {
			operacoes.get(linha).set(coluna, proximoEstado);
		} else if (operacoes.get(linha).size() == coluna) {
			operacoes.get(linha).add(proximoEstado);
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	@Override
	public ElemLexGR toGR() {
		// TODO Auto-generated method stub
		System.out.println("n‹o implementado");
		return null;
	}

	@Override
	public ElemLexAutomato toAutomato() {
		try {
			return (ElemLexAutomato) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\t\t");
		for (char entrada : alfabeto) {
			sb.append(entrada + "\t");
		}

		sb.append("\n");

		for (int linha = 0; linha < estados.size(); linha++) {
			String estado = estados.get(linha);
			if (estadoInicial.equals(estado)) {
				sb.append("->");
			}
			if (estadosFinais.contains(estado)) {
				sb.append("*");
			}
			sb.append("\t");

			sb.append(estado).append("\t");
			for (int coluna = 0; coluna < alfabeto.size(); coluna++) {
				String operacao = operacoes.get(linha).get(coluna);
				if (operacao.equals("")) {
					operacao = " ";
				}
				sb.append(operacao).append("\t");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	protected void removerDuplicatas(Vector<String> vetor) {
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(vetor);
		vetor.clear();
		vetor.addAll(hs);
		Collections.sort(vetor);
	}
}
