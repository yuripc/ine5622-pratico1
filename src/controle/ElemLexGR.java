package controle;

import java.util.Vector;

public class ElemLexGR extends ElemLex {

	public ElemLexGR(String elementoLexico) throws InvalidInputException {
		// TODO Auto-generated method stub

		estados = new Vector<String>();
		operacoes = new Vector<Vector<String>>();

		String[] linhas = elementoLexico.split("\n");

		for (int linha = 0; linha < linhas.length; linha++) {
			String[] partesLinha = linhas[linha].split("->");

			if (partesLinha.length > 2) {
				throw new InvalidInputException("Sequncia '->' s— pode aparecer uma vez por linha", linha, partesLinha[0].length() + partesLinha[1].length()
						+ 4);
			}

			String estado = partesLinha[0].trim();
			try {
				if (isEstadoValido(estado)) {
					estados.add(estado);
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

			parteProcessada.append(partesLinha[0]).append("->");

			for (String operacao : operacoesLinha) {

				if (operacao.trim().length() > 0) {

					String entrada = operacao.trim().substring(0, 1);
					String proxEstado = operacao.trim().substring(1, operacao.trim().length());

					try {
						if (entrada.equals("&") || isEntradaValida(entrada)) {
							String transicao = entrada + proxEstado;
							if (proxEstado.length() > 0) {
								if (estados.contains(proxEstado)) {
									if (!operacoesVector.contains(transicao)) {
										operacoesVector.add(transicao);
									}
								} else {
									throw new InvalidInputException("Estado " + proxEstado + " n‹o definido");
								}
							} else {
								operacoesVector.add(transicao);
							}

							parteProcessada.append(transicao).append("|");

						}

					} catch (InvalidInputException e) {
						throw new InvalidInputException(e.getMessage(), linha, parteProcessada.length());
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

	@Override
	public ElemLexAutomato toAutomato() {
		System.out.println("n‹o implementado");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Operacao> converter() {
		Vector<Operacao> operacoes = new Vector<Operacao>();
		operacoes.add(new Operacao("Convers‹o para Automato", toAutomato(), true));
		return operacoes;
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
