package controle;

import java.util.Vector;

public class ElemLexGR extends ElemLex {

	static final String INICIO_DERIVACOES = "->";
	static final String EPSILON = "&";
	static final String SEPARADOR = "|";

	public ElemLexGR(String elementoLexico) throws InvalidInputException {
		alfabeto = new Vector<Character>();
		estados = new Vector<String>();
		operacoes = new Vector<Vector<String>>();

		String[] linhas = elementoLexico.split("\n");

		for (int linha = 0; linha < linhas.length; linha++) {
			String[] partesLinha = linhas[linha].split(INICIO_DERIVACOES);

			if (partesLinha.length > 2) {
				throw new InvalidInputException("Sequência '" + INICIO_DERIVACOES + "' só pode aparecer uma vez por linha", linha, partesLinha[0].length()
						+ partesLinha[1].length() + 4);
			}

			String estado = partesLinha[0].trim();
			try {
				if (isEstadoValido(estado)) {
					estados.add(estado);
				}

				if (linha == 0) {
					estadoInicial = estado;
				}
			} catch (InvalidInputException e) {
				throw new InvalidInputException(e.getMessage(), linha, 0);
			}
		}

		for (int linha = 0; linha < linhas.length; linha++) {
			operacoes.add(new Vector<String>());

			StringBuilder parteProcessada = new StringBuilder();
			String[] partesLinha = linhas[linha].split("->");
			String[] operacoesLinha = partesLinha[1].split("\\|");

			Vector<String> operacoesVector = operacoes.get(operacoes.size() - 1);

			parteProcessada.append(partesLinha[0]).append(INICIO_DERIVACOES);

			for (String operacao : operacoesLinha) {

				if (operacao.trim().length() > 0) {

					String entrada = operacao.trim().substring(0, 1);
					String proxEstado = operacao.trim().substring(1, operacao.trim().length());

					String transicao = "";
					try {
						transicao = entrada + proxEstado;

						if (!operacoesVector.contains(transicao)) {

							boolean transicaoPossivel = false;

							if (entrada.equals(EPSILON)) {
								if (!proxEstado.equals(proxEstado)) {
									throw new InvalidInputException("'" + EPSILON + "' não pode ser usado junto a uma troca de estado");
								} else if (linha > 0) {
									throw new InvalidInputException("'" + EPSILON + "' só pode estar presente no estado inicial");
								} else {
									transicaoPossivel = true;
								}
							} else if (proxEstado.length() == 0) {
								if (isEntradaValida(entrada)) {
									transicaoPossivel = true;
								}
							} else {
								if (isEntradaValida(entrada) && estados.contains(proxEstado)) {
									if (proxEstado.equals(estadoInicial) && operacoes.get(0).contains(EPSILON)) {
										throw new InvalidInputException("Não pode haver transição para o estado inicial case ele contenha '" + EPSILON + "'");
									}
									transicaoPossivel = true;
								} else {
									throw new InvalidInputException("Estado " + proxEstado + " não definido");
								}
							}

							if (transicaoPossivel) {
								if (!alfabeto.contains(entrada.charAt(0)) && !entrada.equals(EPSILON)) {
									alfabeto.add(entrada.charAt(0));
								}
								if (!operacoesVector.contains(transicao)) {
									operacoesVector.add(transicao);
								}
							} else {
								throw new InvalidInputException("Exceção não tratada");
							}
						}

						parteProcessada.append(transicao).append("|");

						transicao = "";
					} catch (InvalidInputException e) {
						throw new InvalidInputException(e.getMessage(), linha, (parteProcessada.length() + transicao.length()
								- transicao.replace(" ", "").length() + 1));
					}
				}
			}
		}
	}

	@Override
	public ElemLexGR toGR() {
		try {
			return (ElemLexGR) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * pegar cada um do alfabeto e, segundo cada um deles, agrupar as operações.
	 * 
	 * 
	 */
	@Override
	public ElemLexAutomato toAutomato() {
		Vector<Vector<String>> novoVetorDeOperacoes = new Vector<Vector<String>>(); //cria novo vetor vazio

		String estadoFinal = "F";
		String estadoFinalBase = "F";
		for (int i = 1; estados.contains(estadoFinal); i++) {
			estadoFinal = estadoFinalBase + i;
		}

		for (int i = 0; i < operacoes.size(); i++) {//operacoes de um estado do automato
			Vector<String> operacoesDoEstado = new Vector<String>();
			for (int k = 0; k < alfabeto.size(); k++) {
				operacoesDoEstado.add("-");
			}
			for (int j = 0; j < operacoes.get(i).size(); j++) { //cada uma das operações do estado do automato
				int posAlfabeto = alfabeto.indexOf(operacoes.get(i).get(j).charAt(0));
				if (posAlfabeto >= 0) {
					String estado = operacoes.get(i).get(j).substring(1);
					if (estado.equals("")) {
						estado = estadoFinal;
					}
					if (operacoesDoEstado.get(posAlfabeto).equals("-")) {
						operacoesDoEstado.set(posAlfabeto, estado);
					} else {
						operacoesDoEstado.set(posAlfabeto, operacoesDoEstado.get(posAlfabeto) + "," + estado);
					}
				}
			}

			novoVetorDeOperacoes.add(operacoesDoEstado);

		}

		StringBuilder sb = new StringBuilder();
		sb.append("\t\t");
		for (char entrada : alfabeto) {
			sb.append(entrada).append("\t");
		}
		sb.append("\n");

		for (int linha = 0; linha < estados.size(); linha++) {
			if (linha == 0) {
				if (operacoes.get(linha).contains(EPSILON)) {
					sb.append("*");
				}
				sb.append("->");
			}
			sb.append("\t").append(estados.get(linha)).append("\t");
			for (int coluna = 0; coluna < novoVetorDeOperacoes.get(linha).size(); coluna++) {
				sb.append(novoVetorDeOperacoes.get(linha).get(coluna)).append("\t");
			}
			sb.append("\n");
		}

		sb.append("*").append("\t").append(estadoFinal).append("\t");
		for(int coluna = 0;coluna<alfabeto.size();coluna++){
			sb.append("-\t");
		}
		sb.append("\n");

		try {
			return new ElemLexAutomato(sb.toString());
		} catch (InvalidInputException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Operacao converter() {
		return new Operacao("Conversão para Automato", toAutomato());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int linha = 0; linha < estados.size(); linha++) {
			Vector<String> operacoesLinha = operacoes.get(linha);

			sb.append(estados.get(linha)).append(" -> ");
			for (int coluna = 0; coluna < operacoesLinha.size() - 1; coluna++) {
				sb.append(operacoesLinha.get(coluna)).append(" | ");
			}
			sb.append(operacoesLinha.get(operacoesLinha.size() - 1));
			sb.append("\n");
		}

		return sb.toString();
	}
}
