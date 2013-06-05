package controle;
import java.util.Vector;


public class ElemLexAutomato extends ElemLex {

	public ElemLexAutomato(String[] elementoLexico) throws Exception {
		super(elementoLexico);
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

	@Override
	public ElemLexAutomato processar(String[] o) throws Exception {

		Vector<Character> vAlfabeto = new Vector<Character>();
		Vector<String> vEstados = new Vector<String>();
		String vEstadoInicial = "";
		Vector<String> vEstadosFinais = new Vector<String>();
		Vector<Vector<String>> vOperacoes = new Vector<Vector<String>>();

		String[][] tabela = new String[o.length][o[0].length() - o[0].replaceAll("\\.", "").length()];
		for (int i = 0; i < tabela.length; i++) {
			tabela[i] = o[i].split("\\t");
		}
		// Valida estados, estados iniciais e finais
		for (int linha = 1; linha < tabela.length; linha++) {
			String estado = tabela[linha][1];
			if (estado.matches("[A-Z]")) {
				if (!vEstados.contains(estado)) {
					vEstados.add(estado);
				} else {
					throw new Exception("Estado " + estado + " repetido - linha " + linha);
				}
			} else {
				throw new Exception("Caractere inválido no estado " + estado + " - linha " + linha);
			}
			if (tabela[linha][0].matches("\\*")) {
				vEstadosFinais.add(estado);
			}
			if (tabela[linha][0].matches("->")) {
				if (vEstadoInicial == "") {
					vEstadoInicial = estado;
				} else {
					throw new Exception("Ja existe estado inicial - linha " + linha);
				}
			}
		}

		if (vEstadoInicial == "") {
			throw new Exception("Nenhum estado inicial definido");
		} else if (vEstadosFinais.size() == 0) {
			throw new Exception("Nenhum estado final definido");
		} else if (vEstados.size() == 0) {
			throw new Exception("Nenhum estado definido");
		}

		// Valida alfabeto
		for (int coluna = 2; coluna < tabela[0].length; coluna++) {
			if (tabela[0][coluna].length() > 1) {
				throw new Exception("Caractere de entrada contem mais de um simbolo - coluna " + coluna);
			}
			char caractere = tabela[0][coluna].charAt(0);
			if ((caractere + "").matches("a-z0-9")) {
				if (!vAlfabeto.contains(caractere)) {
					vAlfabeto.add(caractere);
				} else {
					throw new Exception("Caractere de entrada ja inserido - coluna " + coluna);
				}
			} else {
				throw new Exception("Caractere de entrada só pode ser letra minuscula ou digito - coluna " + coluna);
			}

		}

		// Valida operações

		// TODO Auto-generated method stub
		System.out.println("não implementado");
		return null;
	}
}
