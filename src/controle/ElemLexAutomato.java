package controle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

public class ElemLexAutomato extends ElemLex {
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

				throw new InvalidInputException("Apenas '->' ou '*' são válidos", linha, 0);
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
								throw new InvalidInputException(vazio + " não pode ser usado junto a outros estados", linha, coluna);
							} else if (transicao.trim().length() > 0) {
								throw new InvalidInputException("Estado " + transicao + " não definido", linha, coluna);
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


	protected void minimizarAutomato(){
		eliminarInalcancaveis();
		eliminarMortos();
		eliminarEquivalentes();
	}


	private void adicionarEstadoDeErro() {
		estados.add(vazio); //adiciona um estado de erro ao final do automato.
		Vector<String> vetorOpercoesVazio = new Vector<String>(); //operacoes do estado de erro
		for (int i = 0; i < alfabeto.size(); i++) {
			vetorOpercoesVazio.add(vazio);
		}
		operacoes.add(vetorOpercoesVazio); //adiciona ao final(ultima posicao) do vetor de operacoes, sincronizando com o ultimo estado adicionado(de erro) na primeira linha do método.
	}


	/**
	 * adicionar estado de erro. ( assumindo que seja "-" - vazio. )
	 * trocar derivações vazias para derivações para estados de erro
	 * agrupar estados finais/agrupar estados não finais(incluindo estado de erro)
	 * pegar todos os estados de um grupo, um de cada vez, e definir:
	 * *se seus destinos pertencem ao mesmo grupo: deixar no mesmo grupo.
	 * *se seus destinos pertencerem a grupos diferentes: coloca-los em grupos diferentes.
	 * aplicar recursivamente o passo 4 5 e 6.
	 */
	private void eliminarEquivalentes() {
		adicionarEstadoDeErro();
		@SuppressWarnings("unchecked")
		Vector<String> grupoDeEstadosNFLocais = (Vector<String>)estados.clone(); //cria vetor de strings representando grupo de estados de não finais
		grupoDeEstadosNFLocais.removeAll(estadosFinais);
		@SuppressWarnings("unchecked")
		Vector<String> grupoDeEstadosFinaisLocais = (Vector<String>)estadosFinais.clone(); //cria vetor de strings representando grupo de estados de finais
		Vector<Vector<String>> gruposDaPrimeiraInteracao = new Vector<Vector<String>>(); //cria vetor armazenando os grupos de vetores de strings
		gruposDaPrimeiraInteracao.add(grupoDeEstadosFinaisLocais);
		gruposDaPrimeiraInteracao.add(grupoDeEstadosNFLocais);
		Vector<Vector<Vector<String>>> interacoesDeGrupos = new Vector<Vector<Vector<String>>>(); //cria vetor de armazenamento de interações.
		interacoesDeGrupos.add(gruposDaPrimeiraInteracao);

		do {

			Vector<Vector<String>> gruposDaInteracaoCorrente = new Vector<Vector<String>>(); //representando o conjunto de grupos da interação corrente.
			Vector<String> estadosJaEmGrupo = new Vector<String>(); //vetor a nível de facilitar verificações posteriores.

			for (int i = 0; i < interacoesDeGrupos.get(interacoesDeGrupos.size()-1).size(); i++) { //pega a quantidade de grupos da ultima interação registrada.
				for (int j = 0; j < interacoesDeGrupos.get(interacoesDeGrupos.size()-1).get(i).size(); j++) { //pega um grupo da ultima interação registrada;
					for (int k = 0; k < interacoesDeGrupos.get(interacoesDeGrupos.size()-1).get(i).size(); k++) { //pega um dos estados
						for (int o = 0; o < interacoesDeGrupos.get(interacoesDeGrupos.size()-1).get(i).size(); o++) { //pega outro estado
							String estadoCorrenteAComparar = interacoesDeGrupos.get(interacoesDeGrupos.size()-1).get(i).get(o);
							String outroEstadoDoGrupo = interacoesDeGrupos.get(interacoesDeGrupos.size()-1).get(i).get(k);

							if (interacoesDeGrupos.get(interacoesDeGrupos.size()-1).get(i).size() == 1) { //já que o grupo contém somente ele mesmo, não haverá o que comprar. então, adicione-o
								Vector<String> umGrupo = new Vector<String>(); //criar um novo grupo
								umGrupo.add(estadoCorrenteAComparar); //colocar o estado corrente que não pertencia a grupos, nesse grupo criado
								estadosJaEmGrupo.add(estadoCorrenteAComparar); //dizer que esse estado, agora, pertence a algum grupo
								gruposDaInteracaoCorrente.add(umGrupo); //adicionar o grupo criado na lista de grupos da interação corrente
							} if (!outroEstadoDoGrupo.equals(estadoCorrenteAComparar)) {//permanesce no laço se não for o estado corrente a comparar

								Vector<String> operacoesDoCorrente = operacoes.get(estados.indexOf(estadoCorrenteAComparar)); //pega operacoes do estado corrente a comparar
								Vector<String> operacoesDoOutro = operacoes.get(estados.indexOf(outroEstadoDoGrupo)); //pega operacoes de outroEstadoDoGrupo

								boolean estadosEquivalentes = true;
								boolean contemUm = false;
								boolean contemOutro = false;
								for (int l = 0; l < interacoesDeGrupos.get(interacoesDeGrupos.size()-1).size(); l++) { //pega a quantidade de grupos da ultima interação registrada.
									for (int m = 0; m < operacoesDoCorrente.size(); m++) { //varre cada estado das operacoes do corrente(e do outro estado comparado)
										if (interacoesDeGrupos.get(interacoesDeGrupos.size()-1).get(l).contains(operacoesDoCorrente.get(m))) {
											contemUm = true;
										}
										if(interacoesDeGrupos.get(interacoesDeGrupos.size()-1).get(l).contains(operacoesDoOutro.get(m))) {
											contemOutro = true;
										}
										if (contemUm != contemOutro) {
											estadosEquivalentes = false;
										}
									}
								}

								boolean correnteJaEmGrupo = estadosJaEmGrupo.contains(estadoCorrenteAComparar);
								boolean outroJaEmGrupo = estadosJaEmGrupo.contains(outroEstadoDoGrupo);

								if (estadosEquivalentes) { //caso forem estados equivalentes
									if (!correnteJaEmGrupo && outroJaEmGrupo) {
										for (int l = 0; l < gruposDaInteracaoCorrente.size(); l++) { //achar o grupo do estado que ja pertence, e adicionar o outro.
											if (gruposDaInteracaoCorrente.get(l).contains(outroEstadoDoGrupo)) {
												gruposDaInteracaoCorrente.get(l).add(estadoCorrenteAComparar);
												estadosJaEmGrupo.add(estadoCorrenteAComparar);
											}
										}
									} else if (correnteJaEmGrupo && !outroJaEmGrupo) {
										for (int l = 0; l < gruposDaInteracaoCorrente.size(); l++) { //achar o grupo do estado que ja pertence, e adicionar o outro.
											if (gruposDaInteracaoCorrente.get(l).contains(estadoCorrenteAComparar)) {
												gruposDaInteracaoCorrente.get(l).add(outroEstadoDoGrupo);
												estadosJaEmGrupo.add(outroEstadoDoGrupo);
											}
										}
									} else if (!correnteJaEmGrupo && !outroJaEmGrupo) {
										Vector<String> umGrupo = new Vector<String>(); //criar um novo grupo
										umGrupo.add(estadoCorrenteAComparar); //colocar o estado corrente que não pertencia a grupos, nesse grupo criado
										umGrupo.add(outroEstadoDoGrupo); //já que são equivalentes, fazer o mesmo procedimento.
										estadosJaEmGrupo.add(estadoCorrenteAComparar); //dizer que esse estado, agora, pertence a algum grupo
										estadosJaEmGrupo.add(outroEstadoDoGrupo); //já que são equivalentes, fazer o mesmo procedimento.
										gruposDaInteracaoCorrente.add(umGrupo); //adicionar o grupo criado na lista de grupos da interação corrente
									}
								} else { //estados não equivalentes
									if(!correnteJaEmGrupo) { //estado corrente ainda não pertence a nenhum grupo, e não é equivalente.
										Vector<String> umGrupo = new Vector<String>(); //criar um novo grupo
										umGrupo.add(estadoCorrenteAComparar); //colocar o estado corrente que não pertencia a grupos, nesse grupo criado
										estadosJaEmGrupo.add(estadoCorrenteAComparar); //dizer que esse estado, agora, pertence a algum grupo
										gruposDaInteracaoCorrente.add(umGrupo); //adicionar o grupo criado na lista de grupos da interação corrente
									}
								}
							}
						}
					}
				}
			}
			for (int i = 0; i < gruposDaInteracaoCorrente.size(); i++) {
				Collections.sort(gruposDaInteracaoCorrente.get(i)); //garante que os estados não vão ficar invertendo, e assim, nunca alcançando o fim por nunca serem equivalentes.
			}

			interacoesDeGrupos.add(gruposDaInteracaoCorrente);
		} while (!interacoesDeGrupos.get(interacoesDeGrupos.size()-1).equals(interacoesDeGrupos.get(interacoesDeGrupos.size()-2))); //repete o laço enquanto houverem mudança nos grupos

		eliminarEstadoDeErro();
	}


	private void eliminarEstadoDeErro() {
		estados.remove(vazio); // remove o estado de erro
		Vector<String> vetorOpercoesVazio = new Vector<String>(); //recria o estado de operacoes vazias para sincronizar e remover corretamente as operações
		for (int i = 0; i < alfabeto.size(); i++) {
			vetorOpercoesVazio.add(vazio);
		}
		operacoes.removeElement(vetorOpercoesVazio); //remove as operacoes de erro do vetor de operacoes
	}

	/**
	 * pegar estado inicial e adicionar à lista de alcançaveis.
	 * pegar derivados da lista de alcançaveis e adicionar à lista de alcançaveis.
	 * aplicar recursivamente o passo 2 até que não sejam mais adicionados novos à lista.
	 * remover estados que nao pertencem a lista.
	 */
	@SuppressWarnings("unchecked")
	private void eliminarInalcancaveis() {
		Vector<Vector<String>> interacoesDeAlcancaveis = new Vector<Vector<String>>();
		Vector<String> vetorInicial = new Vector<String>();
		vetorInicial.add(estadoInicial);
		interacoesDeAlcancaveis.add(vetorInicial);

		do {
			Vector<String> novaInteracao = new Vector<String>();
			novaInteracao.addAll((Vector<String>)interacoesDeAlcancaveis.get(interacoesDeAlcancaveis.size()-1).clone());

			for (int i = 0; i < novaInteracao.size(); i++) { //pegue cada um dos membros do vetor da ultima interacao com os considerados alcançaveis
				for (int j = 0; j < estados.size(); j++) { //pegue cada um dos membros do vetor de estados
					if (estados.get(j).equals(novaInteracao.get(i))) { //pegar a posicao do estado, da lista de estados (o 'j')
						for (int k = 0; k < operacoes.get(j).size(); k++) { //pegar a lista de operações que o estado(j) permite derivar
							if (novaInteracao.indexOf(operacoes.get(j).get(k)) == -1) { // se a derivação ainda não pertencer à lista da novaInteracao
								novaInteracao.add(operacoes.get(j).get(k)); //adicionar ao vetor da novaInteracao
							}
						}
					}
				}
			}

			interacoesDeAlcancaveis.add(novaInteracao);

		} while (!interacoesDeAlcancaveis.get(interacoesDeAlcancaveis.size()-1).equals(interacoesDeAlcancaveis.get(interacoesDeAlcancaveis.size()-2))); // continuar enquanto ainda estiverem sendo adicionados estados a cada interação

		removerEstadosEOperacoesForaDaLista(interacoesDeAlcancaveis.get(interacoesDeAlcancaveis.size()-1));
	}

	/**
	 * aplicar a lista de estados finais à primeira posição de interações de não mortos.
	 * a partir dos outros estados, marcar como nao morto os estados que alcançam estados nao mortos da fase anterior
	 * aplicar recursivamente o passo 2 até que nao haja mudanças da penultima interação para a ultima
	 * remover estados que nao pertencem à lista.
	 */
	@SuppressWarnings("unchecked")
	private void eliminarMortos() {
		Vector<Vector<String>> interacoesDeNaoMortos = new Vector<Vector<String>>();
		interacoesDeNaoMortos.add(estadosFinais);

		do { //encontra quais são não mortos.
			Vector<String> novaInteracao = new Vector<String>();
			novaInteracao.addAll((Vector<String>)interacoesDeNaoMortos.get(interacoesDeNaoMortos.size()-1).clone()); //adiciona condição pré-estabelecida da ultima interação, para ser avaliada na interação corrente.
			for (int i = 0; i < operacoes.size(); i++) { //varre as operações e resgata os lados esquerdos que direcionam ao estados finais da ULTIMA interação feita.
				for (int j = 0; j < operacoes.get(i).size(); j++) { //varre as colunas direcionadas dos não terminais
					for (int k = 0; k < novaInteracao.size(); k++) { //pega a nova interacao e varre seus nao mortos
						if (operacoes.get(i).get(j).equals(novaInteracao.get(k))) { //se algum não-terminal direcionar a algum não-terminal que já esteja na lista de não mortos
							if (novaInteracao.indexOf(estados.get(i)) == -1) { //verifica se já está na lista.
								novaInteracao.add(estados.get(i)); //adiciona o não-terminal que deriva para um não morto à nova lista de não terminais da interação.
							}
						}
					}
				}
			}
			interacoesDeNaoMortos.add(novaInteracao);
		} while (!interacoesDeNaoMortos.get(interacoesDeNaoMortos.size()-1).equals(interacoesDeNaoMortos.get(interacoesDeNaoMortos.size()-2))); // verifica se a interação anterior foi igual a corrente. se sim, não houve progresso na avaliação e é considerado FINALIZADO.

		removerEstadosEOperacoesForaDaLista(interacoesDeNaoMortos.get(interacoesDeNaoMortos.size()-1));
	}

	private void removerEstadosEOperacoesForaDaLista(Vector<String> listaPorPreservar) {
		Vector<Integer> posicaoDeForasDaLista = new Vector<Integer>();
		for (int i = 0; i < estados.size(); i++) { //varremos todos os estados
			if (!listaPorPreservar.contains(estados.get(i))) {
				posicaoDeForasDaLista.add(i);
			}
		}
		for (int i = posicaoDeForasDaLista.size()-1; i >= 0; i--) { //remove estados e a lista de opreacoes do maior para o menor para nao tirar da posicao os ainda não verificados.
			String provavelEstadoFinal = estados.get(posicaoDeForasDaLista.get(i));
			estados.removeElementAt(posicaoDeForasDaLista.get(i));
			estadosFinais.remove(provavelEstadoFinal);
			operacoes.removeElementAt(posicaoDeForasDaLista.get(i));
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
		StringBuilder sb = new StringBuilder();

		for (String estado : estados) {
			sb.append(estado).append(" ").append(ElemLexGR.INICIO_DERIVACOES).append(" ");

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
				sb.append(possibilidade).append(" ").append(ElemLexGR.SEPARADOR).append(" ");
			}

			for (int i = 0; i < ElemLexGR.SEPARADOR.length(); i++) {
				sb.deleteCharAt(sb.length() - 1);
			}

			if (estado.equals(estadoInicial) && estadosFinais.contains(estado)) {
				StringBuilder estadoCopia = new StringBuilder(estado);

				for (int anexo = 1; estados.contains(estado); anexo++) {
					estado = estadoCopia.append(anexo).toString();
				}

				String novaLinha = estado + ElemLexGR.INICIO_DERIVACOES + sb.toString().split(ElemLexGR.INICIO_DERIVACOES)[1] + ElemLexGR.SEPARADOR + ElemLexGR.EPSILON + "\n";
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
	public Operacao converter() {
		return new Operacao("Conversão para GR", toGR());
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

	private String gerarStringDiferenteDe(Vector<String> stringsAExcluir) {
		String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String stringDiferente = "";

		for (int j = -1; j < alfabeto.length(); j++) { //tentar gerar uma string de até tamanho 2 com esse for.
			for (int i = 0; i < alfabeto.length(); i++) {
				if (j == -1) { //tentar primeiro gerar string de tamanho 1
					if (!stringsAExcluir.contains(alfabeto.charAt(i)+"")) {
						stringDiferente = alfabeto.charAt(i)+"";
						i = alfabeto.length(); //sair do for
						j = alfabeto.length(); //sair do for
					}
				} else { //se não, tentar de tamanho 2.
					if (!stringsAExcluir.contains(alfabeto.charAt(i) + alfabeto.charAt(j) + "")) {
						stringDiferente = alfabeto.charAt(i) + alfabeto.charAt(j) + "";
						i = alfabeto.length(); //sair do for
						j = alfabeto.length(); //sair do for
					}
				}
			}
		}

		return stringDiferente;
	}

	/**
	 * GERAL:
	 * novo estado inicial
	 * destino do novo estado inicial igual aos destinos dos estados iniciais anteriores.
	 * 
	 * PROGRAMAÇÃO:
	 * criar novo ElemLexAutomato
	 * eliminar estados conflitantes(dir e esq), por meio de geração de novos estados equivalentes.
	 * gerar um estado não conflitante com os demais para ser o estado inicial do novo ElemLexAutomato
	 * tratar conflito do alfabeto.
	 * direcionar esse novo estado inicial, para as derivações das operações dos antigos estados iniciais.
	 * 
	 * @param elemOutro
	 * @return 
	 * @throws CloneNotSupportedException 
	 */
	public ElemLexAutomato unirAutomatos(ElemLexAutomato elemOutro) {
		try {
			ElemLexAutomato elemProduto = (ElemLexAutomato) this.clone();
			Vector<String> estadosDaInteracao = new Vector<String>(); //objeto responsavel por armazenar os estados existentes, para não haver geração de estados novos que já existam
			estadosDaInteracao.addAll(this.estados); 
			estadosDaInteracao.addAll(elemOutro.estados);

			for (int i = 0; i < elemProduto.estados.size(); i++) { //fors responsaveis por retirar estados com o mesmo título
				for (int j = 0; j < elemOutro.estados.size(); j++) {
					if (elemProduto.estados.get(i).equals(elemOutro.estados.get(j))) { // se houver um estado com nome igual a outro em automatos diferentes
						String estadoASubstituir = elemProduto.estados.get(i); //pega o estado a ser substituido
						String estadoSubstituto = gerarStringDiferenteDe(estadosDaInteracao); //gerar um outro símbolo que não seja igual a qualquer estado que já exista.
						elemProduto.estados.setElementAt(estadoSubstituto, i); //coloca o símbolo gerado no lugar do antigo
						if (elemProduto.estadosFinais.indexOf(estadoASubstituir) != -1) {//verifica se o estado a ser substituido está na lista de finais
							elemProduto.estadosFinais.setElementAt(estadoSubstituto, elemProduto.estadosFinais.indexOf(estadoASubstituir)); //se estiver, substituir pelo estado gerado
						}
						estadosDaInteracao.setElementAt(estadoSubstituto, estadosDaInteracao.indexOf(estadoASubstituir)); //atualiza a lista de estados da interacao com o estado novo.
						for (int k = 0; k < elemProduto.operacoes.size(); k++) { //fors responsaveis por atualizar a lista de operacoes com o estado novo.
							for (int l = 0; l < elemProduto.operacoes.get(k).size(); l++) {
								String[] transicoesNaoDeterministicas = elemProduto.operacoes.get(k).get(l).split(","); //garantia de trocar os estados caso nao forem deterministicos.
								for (int m = 0; m < transicoesNaoDeterministicas.length; m++) { //varre as transicoes nao deterministicas.
									if (transicoesNaoDeterministicas[m].equals(estadoASubstituir)) { //se o simbolo for igual ao simboolo a substituir
										transicoesNaoDeterministicas[m] = estadoASubstituir; //coloque o simbolo substituto no lugar do simbolo a substituir									
									}
								}
								String reconvertendoArrayNaoDeterministico = "";
								for (int m = 0; m < transicoesNaoDeterministicas.length; m++) { //retorna o string na formatação que estava antes do split;
									if (transicoesNaoDeterministicas.length-1 == m) {
										reconvertendoArrayNaoDeterministico = reconvertendoArrayNaoDeterministico + transicoesNaoDeterministicas[m];
									} else {
										reconvertendoArrayNaoDeterministico = reconvertendoArrayNaoDeterministico + transicoesNaoDeterministicas[m] + ",";
									}
								}
								elemProduto.operacoes.get(k).setElementAt(reconvertendoArrayNaoDeterministico, l); //colocando a reconversão novamente na posição onde estava.
							}
						}
						if (elemProduto.estadoInicial.equals(estadoASubstituir)) {
							elemProduto.estadoInicial = estadoSubstituto; //troca o simbolo inicial se ele for igual ao estado com choque identificado anteriormente.
						}
					}
				}
			}
			
			for (int i = 0; i < elemOutro.alfabeto.size(); i++) { //for responsavel por adicionar algarismos em elemProduto, de elemOutro, que ainda nao existam.
				if (!elemProduto.alfabeto.contains(elemOutro.alfabeto.get(i))) { //se o algarismo do alfabeto do elemOutro nao existir no elemProduto
					elemProduto.alfabeto.add(elemOutro.alfabeto.get(i)); //adicionar esse algarismo no elemProduto
					for (int j = 0; j < elemProduto.operacoes.size(); j++) { //e adicionar produção vazia para cada transição com esse algarismo
						elemProduto.operacoes.get(j).add(vazio);
					}
				}
			}
			ElemLexAutomato elemOutroEditado = (ElemLexAutomato) elemOutro.clone(); //objeto responsavel por não editar o elemento vindo como parâmetro. ele deve permanescer como chegou.
			for (int i = 0; i < elemProduto.alfabeto.size(); i++) { //for responsavel por adicionar algarismos em elemOutro, de elemProduto, que ainda nao existam.
				if (!elemOutroEditado.alfabeto.contains(elemProduto.alfabeto.get(i))) { //se o algarismo do alfabeto do elemProduto nao existir no elemOutro
					elemOutroEditado.alfabeto.add(elemProduto.alfabeto.get(i)); //adicionar esse algarismo no elemOutro
					for (int j = 0; j < elemOutroEditado.operacoes.size(); j++) { //e adicionar produção vazia para cada transição com esse algarismo
						elemOutroEditado.operacoes.get(j).add(vazio);
					}
				}
			}
			
			do {// responsavel por sincronizar as colunas de alfabeto com as operações.
				for (int i = 0; i < elemProduto.alfabeto.size(); i++) { //assim que encontrar um simbolo do alfabeto fora do lugar, enviar para o final e verificar posições.
					if (!elemProduto.alfabeto.get(i).equals(elemOutroEditado.alfabeto.get(i))) {
						char simbolo = elemProduto.alfabeto.get(i);
						elemProduto.alfabeto.removeElement(simbolo);
						elemProduto.alfabeto.add(simbolo); //reposiciona no final.
						
						for (int j = 0; j < elemProduto.operacoes.size(); j++) {
							String operacao = elemProduto.operacoes.get(j).get(i);
							elemProduto.operacoes.get(j).removeElement(operacao);
							elemProduto.operacoes.get(j).add(operacao); //reposiciona todas as operações ao final.
						}
						i = elemProduto.alfabeto.size(); //finaliza o for para que seja feita a verificação de posições.
					}
				}
			} while (!elemOutroEditado.alfabeto.equals(elemProduto.alfabeto)); //fazer isso enquanto elas nao forem iguais.
			
			String novoInicial = gerarStringDiferenteDe(estadosDaInteracao);
			Vector<String> operacoesEstadoInicialProduto = elemProduto.operacoes.get(elemProduto.estados.indexOf(elemProduto.estadoInicial)); //pega as derivações do estado inicial do produto
			Vector<String> operacoesEstadoInicialOutro = elemOutroEditado.operacoes.get(elemOutroEditado.estados.indexOf(elemOutroEditado.estadoInicial)); //pega as derivações do estado inicial do outro
			Vector<String> operacoesNovoInicial = new Vector<String>();

			for (int i = 0; i < elemProduto.alfabeto.size(); i++) { //for responsavel por sincronizar operações dos antigos estados iniciais para o novo estado inicial
				String[] transicoesNaoDeterministicasProduto = operacoesEstadoInicialProduto.get(i).split(",");
				String[] transicoesNaoDeterministicasOutro = operacoesEstadoInicialOutro.get(i).split(",");
				String novaTransicao = "";
				
				for (int j = 0; j < transicoesNaoDeterministicasProduto.length; j++) {
					if (!novaTransicao.contains(transicoesNaoDeterministicasProduto[j]) && !transicoesNaoDeterministicasProduto[j].equals(vazio)) {
						novaTransicao = novaTransicao + transicoesNaoDeterministicasProduto[j] + ",";
					}
					if (!novaTransicao.contains(transicoesNaoDeterministicasOutro[j]) && !transicoesNaoDeterministicasOutro[j].equals(vazio)) {
						novaTransicao = novaTransicao + transicoesNaoDeterministicasOutro[j] + ",";
					}
				}
				if (novaTransicao.length() == 0) { //se a nova transição cruzada for "", então é vazia.
					novaTransicao = vazio;
				} else {
					novaTransicao = novaTransicao.substring(0, novaTransicao.length()-1); //se nao for "", então retirar a ultima virgula.
				}
				operacoesNovoInicial.add(novaTransicao);
			}
			
			elemProduto.estadoInicial = novoInicial; //atualiza para o novo inicial
			elemProduto.estados.addAll(elemOutroEditado.estados); //adiciona estados do outro ao final
			elemProduto.operacoes.addAll(elemOutroEditado.operacoes); // adiciona operacoes do outro ao final
			elemProduto.estadosFinais.addAll(elemOutroEditado.estadosFinais);
			elemProduto.estados.insertElementAt(novoInicial, 0); //coloca o novo inicial na lista de estados
			elemProduto.operacoes.insertElementAt(operacoesNovoInicial, 0); //coloca operacoes do novo inicial na lista de operacoes.
			
			return elemProduto;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;

	}

	
	/**
	 * inverter estados não finais para finais
	 * adicionar estados de erro caso hajam
	 * estado gerado de erro deve ser final, inclusive.
	 */
	public void complementarAutomato() {
		boolean haVazio = false; //boolean que verificaremos a necessidade de uma transicao de erro incluida no automato
		for (int i = 0; i < operacoes.size(); i++) {
			for (int j = 0; j < operacoes.get(i).size(); j++) {
				if (operacoes.get(i).get(j).equals(vazio)) {
					haVazio = true;
				}
			}
		}
		String estadoDeErro = "";
		if (haVazio) { //se houver vazios, insira uma transição equivalenta à de erro.
			estadoDeErro = gerarStringDiferenteDe(estados);
			for (int i = 0; i < operacoes.size(); i++) {
				for (int j = 0; j < operacoes.get(i).size(); j++) {
					if (operacoes.get(i).get(j).equals(vazio)) {
						operacoes.get(i).setElementAt(estadoDeErro, j);
					}
				}
			}
			estados.add(estadoDeErro);
			
			Vector<String> operacoesDoEstadoDeErro = new Vector<String>();
			for (int i = 0; i < alfabeto.size(); i++) { //adiciona as operacoes do novo estado valido(antigo de erro).
				operacoesDoEstadoDeErro.add(estadoDeErro);
			}
			operacoes.add(operacoesDoEstadoDeErro);
		}
		
		@SuppressWarnings("unchecked")
		Vector<String> novosFinais = (Vector<String>) estados.clone();
		novosFinais.removeAll(estadosFinais); //remove os estados finais da lista de todos os estados
		estadosFinais = novosFinais; //atribui a subtração a antiga lista de finais.
		if (haVazio) { //colocar novo estado de erro também como final caso ele tenha sido gerado.
			estadosFinais.add(estadoDeErro);
		}
	}
}
