package controle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

public class ElemLexAutomato extends ElemLex {

	String estadoInicial;
	Vector<String> estadosFinais;

	protected final String separador = ",";
	protected final String vazio = "-";

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
			String estado = tabela[linha][1].trim();
			try {
				if (isEstadoValido(estado)) {
					if (!estados.contains(estado)) {
						estados.add(estado);
					} else {
						throw new InvalidInputException("Estado " + estado + " repetido");
					}
				}
			} catch (InvalidInputException e) {
				throw new InvalidInputException(e.getMessage(), linha, 1);
			}

			// Valida estados iniciais e finais
			String coluna0 = tabela[linha][0].trim();
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

			String entrada = tabela[0][coluna].trim();

			try {
				if (isEntradaValida(entrada)) {

					char caractere = entrada.charAt(0);
					if (!alfabeto.contains(caractere)) {
						alfabeto.add(caractere);
					} else {
						throw new InvalidInputException("Caractere de entrada ja inserido");
					}

				}
			} catch (InvalidInputException e) {
				throw new InvalidInputException(e.getMessage(), 0, coluna);
			}
		}
		// Valida transicoes
		for (int linha = 1; linha < tabela.length; linha++) {
			operacoes.add(new Vector<String>());

			for (int coluna = 2; coluna < tabela[0].length; coluna++) {
				String[] transicoes = tabela[linha][coluna].split(separador);

				StringBuilder transicaoFinal = new StringBuilder();

				if (transicoes[0].equals(vazio) || transicoes[0].trim().length() == 0) {
					transicaoFinal.append(vazio);
				} else {
					Vector<String> transicoesValidas = new Vector<String>();
					for (String transicao : transicoes) {
						transicao = transicao.trim();
						if (estados.contains(transicao)) {
							if (!transicoesValidas.contains(transicao)) {
								transicoesValidas.add(transicao);
							}
						} else {
							if (transicao.equals(vazio)) {
								throw new InvalidInputException(vazio + " n‹o pode ser usado junto a outros estados", linha, coluna);
							} else if (transicao.trim().length() > 0) {
								throw new InvalidInputException("Estado " + transicao + " n‹o definido", linha, coluna);
							}
						}
					}
					for (String transicaoValida : transicoesValidas) {
						transicaoFinal.append(transicaoValida + separador);
					}
					transicaoFinal.deleteCharAt(transicaoFinal.length() - 1);
				}

				operacoes.get(linha - 1).add(transicaoFinal.toString());
			}
		}

		// Passa estado inicial para o topo
		if (estados.get(0) != estadoInicial) {
			int pos = estados.indexOf(estadoInicial);

			estados.set(pos, estados.get(0));
			estados.set(0, estadoInicial);

			Vector<String> operacoesInicial = operacoes.get(pos);

			operacoes.set(pos, operacoes.get(0));
			operacoes.set(0, operacoesInicial);
		}
	}

	protected void determinizarAutomato() {

		Vector<String> estadosPendentes = new Vector<String>();

		for (String estado : estados) {
			for (char entrada : alfabeto) {
				String proxEstado = proximoEstado(estado, entrada);

				if (!estados.contains(proxEstado) && !estadosPendentes.contains(proxEstado) && !proxEstado.equals(vazio)) {
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

				if (!estados.contains(proxEstado) && !estadosPendentes.contains(proxEstado) && !proxEstado.equals(vazio)) {
					estadosPendentes.add(proxEstado);
				}

				setOperacao(estado, entrada, proxEstado);
			}

			String[] subEstados = estadoNaoDeterminizado.split(separador);

			for (int i = 0; i < subEstados.length && !estadosFinais.contains(estado); i++) {
				if (estadosFinais.contains(subEstados[i])) {
					estadosFinais.add(estado);
				}
			}

			estadosPendentes.remove(0);
		}

		Vector<String> estadosNormalizados = new Vector<String>();

		for (String estado : estados) {
			estado = normalizarEstado(estado);

			if (estadosNormalizados.contains(estado)) {
				StringBuilder estadoCopia = new StringBuilder(estado);

				for (int anexo = 1; estadosNormalizados.contains(estado); anexo++) {
					estado = estadoCopia.append(anexo).toString();
				}
			}
			estadosNormalizados.add(estado);
		}

		for (int linha = 0; linha < estados.size(); linha++) {
			Vector<String> vectorLinha = operacoes.get(linha);
			for (int coluna = 0; coluna < alfabeto.size(); coluna++) {
				String operacao = vectorLinha.get(coluna);
				if (!operacao.equals(vazio)) {
					int pos = estados.indexOf(operacao);
					vectorLinha.set(coluna, estadosNormalizados.get(pos));
				}
			}
		}

		for (int linha = 0; linha < estados.size(); linha++) {
			estados.set(linha, estadosNormalizados.get(linha));
		}
	}

	protected String proximoEstado(String estado, char entrada) {
		Vector<String> proximosEstados = new Vector<String>();

		for (String subEstado : estado.split(separador)) {
			if (!subEstado.equals(vazio)) {
				int linha = estados.indexOf(subEstado);
				int coluna = alfabeto.indexOf(entrada);
				String proxEstado = operacoes.get(linha).get(coluna);

				for (String subProxEstado : proxEstado.split(separador)) {
					if (!subProxEstado.equals(vazio) && !proximosEstados.contains(subProxEstado)) {
						proximosEstados.add(subProxEstado);
					}
				}
			}
		}

		if (proximosEstados.size() == 0) {
			proximosEstados.add(vazio);
		} else {
			removerDuplicatas(proximosEstados);
		}

		return StringUtils.join(proximosEstados, separador);
	}

	protected String normalizarEstado(String s) {
		return StringUtils.capitalize(s.toLowerCase());
	}

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
		String separadorGR = " | ";
		StringBuilder sb = new StringBuilder();

		for (String estado : estados) {
			sb.append(estado).append(" -> ");

			Vector<String> possibilidades = new Vector<String>();

			for (char entrada : alfabeto) {
				String proxEstado = proximoEstado(estado, entrada);

				if (!proxEstado.equals(vazio)) {

					String[] subEstados = proxEstado.split(separador);
					for (String subEstado : subEstados) {
						possibilidades.add(entrada + subEstado);

						if (estadosFinais.contains(subEstado)) {
							possibilidades.add(entrada + "");
						}
					}
				}
			}

			removerDuplicatas(possibilidades);

			for (String possibilidade : possibilidades) {
				sb.append(possibilidade).append(separadorGR);
			}

			for (int i = 0; i < separadorGR.length(); i++) {
				sb.deleteCharAt(sb.length() - 1);
			}

			if (estado.equals(estadoInicial) && estadosFinais.contains(estado)) {
				StringBuilder estadoCopia = new StringBuilder(estado);

				for (int anexo = 1; estados.contains(estado); anexo++) {
					estado = estadoCopia.append(anexo).toString();
				}

				String novaLinha = estado + "->" + sb.toString().split("->")[1] + separadorGR + "&" + "\n";
				sb.insert(0, novaLinha);
			}

			sb.append("\n");
		}

		try {
			return new ElemLexGR(sb.toString());
		} catch (InvalidInputException e) {
			e.printStackTrace();
			return null;
		}
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
	public Vector<Operacao> converter() {
		Vector<Operacao> operacoes = new Vector<Operacao>();
		operacoes.add(new Operacao("Convers‹o para GR", toGR(), true));
		return operacoes;
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
