package controle;

import java.util.Vector;

public class ElemLexAutomato extends ElemLex {

	public ElemLexAutomato(String input) throws Exception {
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
		// Valida estados, estados iniciais e finais
		for (int linha = 1; linha < tabela.length; linha++) {
			String estado = tabela[linha][1];
			if (estado.matches("[A-Z]")) {
				if (!estados.contains(estado)) {
					estados.add(estado);
				} else {
					throw new Exception("Estado " + estado + " repetido - linha " + linha);
				}
			} else {
				throw new Exception("Caractere inválido no estado " + estado + " - linha " + linha);
			}

			String coluna0 = tabela[linha][0];
			if (coluna0.matches("\\*")) {
				estadosFinais.add(estado);
			}
			if (coluna0.matches("->")) {
				if (estadoInicial.equals("")) {
					estadoInicial = estado;
				} else {
					throw new Exception("Ja existe estado inicial - linha " + linha);
				}
			}
			if (!((coluna0.length() == 3 && coluna0.matches("\\*") && coluna0.matches("->")) || (coluna0.length() == 2 && coluna0.matches("->"))
					|| (coluna0.length() == 1 && coluna0.matches("\\*")) || (coluna0.length() == 0))) {
				throw new Exception("Apenas '->' ou '*' são válidos na coluna 0 - linha " + linha);
			}
		}

		if (estadoInicial.equals("")) {
			throw new Exception("Nenhum estado inicial definido");
		} else if (estadosFinais.size() == 0) {
			throw new Exception("Nenhum estado final definido");
		} else if (estados.size() == 0) {
			throw new Exception("Nenhum estado definido");
		}

		// Valida alfabeto
		for (int coluna = 2; coluna < tabela[0].length; coluna++) {
			if (tabela[0][coluna].length() > 1) {
				throw new Exception("Caractere de entrada contem mais de um simbolo - coluna " + coluna);
			}
			char caractere = tabela[0][coluna].charAt(0);
			if ((caractere + "").matches("[a-z0-9]")) {
				if (!alfabeto.contains(caractere)) {
					alfabeto.add(caractere);
				} else {
					throw new Exception("Caractere de entrada ja inserido - coluna " + coluna);
				}
			} else {
				throw new Exception("Caractere de entrada só pode ser letra minuscula ou digito - coluna " + coluna);
			}

		}

		// TODO Valida operações
	}

	@Override
	public ElemLexGR toGR() {
		// TODO Auto-generated method stub
		System.out.println("não implementado");
		return null;
	}

	@Override
	public ElemLexAutomato toAutomato() {
		return this;
	}
}
