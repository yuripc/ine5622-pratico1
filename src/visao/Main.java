package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;

public class Main {

	private static JFrame frame;
	private static JSplitPane splitPane;
	private static controle.Arquivo arquivo = new controle.Arquivo();
	private static JMenuItem mntmUniao;
	private static JMenuItem mntmComplemento;
	private static JMenuItem mntmA1Determinizar;
	private static JMenuItem mntmA1Minimizar;
	private static JMenuItem mntmA1ReconhecerSentenca;
	private static JMenuItem mntmA1GerarSentencas;
	private static JMenuItem mntmA1Converter;
	private static JMenuItem mntmA2Determinizar;
	private static JMenuItem mntmA2Minimizar;
	private static JMenuItem mntmA2GerarSentencas;
	private static JMenuItem mntmA2ReconhecerSentenca;
	private static JMenuItem mntmA2Converter;
	private static JMenuItem mntmNovo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main window = new Main();
					window.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(650, 200));

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);

		mntmNovo = new JMenuItem("Novo");
		mntmNovo.setEnabled(false);
		mnArquivo.add(mntmNovo);

		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mnArquivo.add(mntmAbrir);

		JSeparator separator = new JSeparator();
		mnArquivo.add(separator);

		JMenuItem mntmSalvar = new JMenuItem("Salvar");
		mnArquivo.add(mntmSalvar);

		JMenuItem mntmSalvarComo = new JMenuItem("Salvar Como");
		mnArquivo.add(mntmSalvarComo);

		JSeparator separator_1 = new JSeparator();
		mnArquivo.add(separator_1);

		JMenuItem mntmSair = new JMenuItem("Sair");
		mnArquivo.add(mntmSair);

		JMenu mnAcoes = new JMenu("Ações");
		menuBar.add(mnAcoes);

		mntmUniao = new JMenuItem("União");
		mnAcoes.add(mntmUniao);

		mntmComplemento = new JMenuItem("Complemento");
		mnAcoes.add(mntmComplemento);

		JSeparator separator_4 = new JSeparator();
		mnAcoes.add(separator_4);

		JMenuItem mntmDebug = new JMenuItem("System.out.println");
		mnAcoes.add(mntmDebug);

		JMenu mnA1 = new JMenu("Elemento Esquerda");
		menuBar.add(mnA1);

		JMenu mntmA1Novo = new JMenu("Novo");
		mnA1.add(mntmA1Novo);

		JMenuItem mntmA1NovoAutomato = new JMenuItem("Automato");
		mntmA1Novo.add(mntmA1NovoAutomato);

		JMenuItem mntmA1NovoGR = new JMenuItem("Gramatica Regular");
		mntmA1Novo.add(mntmA1NovoGR);

		JSeparator sepA11 = new JSeparator();
		mnA1.add(sepA11);

		mntmA1Determinizar = new JMenuItem("Determinizar");
		mnA1.add(mntmA1Determinizar);

		mntmA1Minimizar = new JMenuItem("Minimizar");
		mnA1.add(mntmA1Minimizar);

		JSeparator SepA12 = new JSeparator();
		mnA1.add(SepA12);

		mntmA1ReconhecerSentenca = new JMenuItem("Reconhecer Senten\u00E7a");
		mnA1.add(mntmA1ReconhecerSentenca);

		mntmA1GerarSentencas = new JMenuItem("Senten\u00E7as Reconhecidas");
		mnA1.add(mntmA1GerarSentencas);

		JSeparator SepA13 = new JSeparator();
		mnA1.add(SepA13);

		mntmA1Converter = new JMenuItem("Converter");
		mnA1.add(mntmA1Converter);

		JMenu mnA2 = new JMenu("Elemento Direita");
		menuBar.add(mnA2);

		JMenu mntmA2Novo = new JMenu("Novo");
		mnA2.add(mntmA2Novo);

		JMenuItem mntmA2NovoAutomato = new JMenuItem("Automato");
		mntmA2Novo.add(mntmA2NovoAutomato);

		JMenuItem mntmA2NovoGR = new JMenuItem("Gramatica Regular");
		mntmA2Novo.add(mntmA2NovoGR);

		JSeparator sepA21 = new JSeparator();
		mnA2.add(sepA21);

		mntmA2Determinizar = new JMenuItem("Determinizar");
		mnA2.add(mntmA2Determinizar);

		mntmA2Minimizar = new JMenuItem("Minimizar");
		mnA2.add(mntmA2Minimizar);

		JSeparator SepA22 = new JSeparator();
		mnA2.add(SepA22);

		mntmA2ReconhecerSentenca = new JMenuItem("Reconhecer Senten\u00E7a");
		mnA2.add(mntmA2ReconhecerSentenca);

		mntmA2GerarSentencas = new JMenuItem("Senten\u00E7as Reconhecidas");
		mnA2.add(mntmA2GerarSentencas);

		JSeparator SepA23 = new JSeparator();
		mnA2.add(SepA23);

		mntmA2Converter = new JMenuItem("Converter");
		mnA2.add(mntmA2Converter);

		splitPane = new JSplitPane();
		addComponent(true, new JPanel());
		addComponent(false, new JPanel());

		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		mntmNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Todas as alterações não salvas serão perdidas,\nContinuar?", "Confirmação", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					arquivo.resetCurrentFile();
					addComponent(true, new JPanel());
					addComponent(false, new JPanel());
					mntmNovo.setEnabled(false);
				}
			}
		});

		mntmAbrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel[] panels;

				boolean erroEsquerda = false;
				boolean erroDireita = false;

				try {
					panels = arquivo.abrir();

					try {
						addComponent(true, panels[0]);
						erroEsquerda = false;
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					try {
						addComponent(false, panels[1]);
						erroDireita = false;
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					if (erroEsquerda || erroDireita) {
						if (erroEsquerda && erroDireita) {
							messageError("Os dois elementos léxicos estão corrompidos e não puderam ser lidos");
						} else if (erroEsquerda) {
							messageError("O elemento léxico da esquerda está corrompido e não pôde ser lido");
						} else {
							messageError("O elemento léxico da direita está corrompido e não pôde ser lido");
						}
					}
				} catch (Exception e1) {
					messageError("Arquivo está corrompido e não pôde ser lido");
				}

			}
		});

		mntmSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					arquivo.salvar(getPanels());
				} catch (IOException e1) {
					e1.printStackTrace();
					messageError("O arquivo não pode ser salvo");
				}
			}
		});

		mntmSalvarComo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarComo(getPanels());
			}
		});

		mntmSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Qualquer arquivo não salvo será perdido.\nDeseja continuar?", "Confirmação",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		mntmUniao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");

			}
		});

		mntmComplemento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");
			}
		});

		mntmDebug.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, splitPane.getLeftComponent().toString());
			}
		});

		mntmA1NovoAutomato.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addComponent(true, new ElemLexAutomato());
			}
		});

		mntmA1NovoGR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addComponent(true, new ElemLexGR());
			}
		});

		mntmA1Determinizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controle.ElemLex elem = criarElem(true);
				if (elem != null) {
					new Operacoes(elem.determinizar());
				}
			}
		});

		mntmA1Minimizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controle.ElemLex elem = criarElem(true);
				if (elem != null) {
					elem.minimizar();
				}
			}
		});

		mntmA1ReconhecerSentenca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reconhecerSentenca(true);
			}
		});

		mntmA1GerarSentencas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gerarSentenca(true);
			}
		});

		mntmA1Converter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");
			}
		});

		mntmA2NovoAutomato.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addComponent(false, new ElemLexAutomato());
			}
		});

		mntmA2NovoGR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addComponent(false, new ElemLexGR());
			}
		});

		mntmA2Determinizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controle.ElemLex elem = criarElem(false);
				if (elem != null) {
					elem.determinizar();
				}
			}
		});

		mntmA2Minimizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controle.ElemLex elem = criarElem(false);
				if (elem != null) {
					elem.minimizar();
				}
			}
		});

		mntmA2ReconhecerSentenca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reconhecerSentenca(false);
			}
		});

		mntmA2GerarSentencas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gerarSentenca(false);
			}
		});

		mntmA2Converter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");
			}
		});
	}

	public static void addComponent(boolean isLeft, JPanel component) {
		boolean habilitarMenuElemento = component instanceof ElemLex;
		if (isLeft) {
			splitPane.setLeftComponent(component);

			mntmA1Determinizar.setEnabled(habilitarMenuElemento);
			mntmA1Minimizar.setEnabled(habilitarMenuElemento);
			mntmA1ReconhecerSentenca.setEnabled(habilitarMenuElemento);
			mntmA1GerarSentencas.setEnabled(habilitarMenuElemento);
			mntmA1Converter.setEnabled(habilitarMenuElemento);

			if (component instanceof ElemLexAutomato) {
				mntmA1Converter.setText("Converter para GR");
			} else if (component instanceof ElemLexGR) {
				mntmA1Converter.setText("Converter para Automato");
			} else {
				mntmA1Converter.setText("Converter para");
			}

		} else {
			splitPane.setRightComponent(component);

			mntmA2Determinizar.setEnabled(habilitarMenuElemento);
			mntmA2Minimizar.setEnabled(habilitarMenuElemento);
			mntmA2ReconhecerSentenca.setEnabled(habilitarMenuElemento);
			mntmA2GerarSentencas.setEnabled(habilitarMenuElemento);
			mntmA2Converter.setEnabled(habilitarMenuElemento);
			if (component instanceof ElemLexAutomato) {
				mntmA2Converter.setText("Converter para GR");
			} else if (component instanceof ElemLexGR) {
				mntmA2Converter.setText("Converter para Automato");
			} else {
				mntmA2Converter.setText("Converter para");
			}
		}

		if (habilitarMenuElemento) {
			mntmNovo.setEnabled(true);
		}

		boolean habilitarMenuAcao = splitPane.getLeftComponent() instanceof ElemLex && splitPane.getRightComponent() instanceof ElemLex;

		mntmUniao.setEnabled(habilitarMenuAcao);
		mntmComplemento.setEnabled(habilitarMenuAcao);

	}

	private static controle.ElemLex criarElem(boolean esquerda) {
		try {
			JPanel panel;
			if (esquerda) {
				panel = (JPanel) splitPane.getLeftComponent();
			} else {
				panel = (JPanel) splitPane.getRightComponent();
			}
			if (panel instanceof ElemLexAutomato) {
				return new controle.ElemLexAutomato(panel.toString());
			} else {
				return new controle.ElemLexGR(panel.toString());
			}
		} catch (Exception e) {
			String pos;
			if (esquerda) {
				pos = "esquerda";
			} else {
				pos = "direita";
			}
			messageError("Elemento da " + pos + " não é válido:\n" + e.getMessage() + "\nConsulte a 'Ajuda' para obter mais informações");
			e.printStackTrace();
			return null;
		}
	}

	public static String dialogInput(String mensagem) {
		return JOptionPane.showInputDialog(null, mensagem).trim();
	}

	public static void messageInformation(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void messageError(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "", JOptionPane.ERROR_MESSAGE);
	}

	private static void reconhecerSentenca(boolean esquerda) {
		controle.ElemLex elem = criarElem(true);
		if (elem != null) {
			String input = dialogInput("Qual sentença deseja verificar?");

			if (elem.reconhecerSentenca(input)) {
				messageInformation("Sentença faz parte da linguagem!");
			} else {
				messageError("Sentença não faz parte da linguagem!");
			}
		}
	}

	private static void gerarSentenca(boolean esquerda) {
		controle.ElemLex elem = criarElem(esquerda);
		if (elem != null) {
			int input = Integer.parseInt(dialogInput("Qual o tamanho das sentença?"));

			String[] sentencas = elem.gerarSentencas(input);
			if (sentencas.length == 0) {
				messageError("Não existe sentença com esse tamanho");
			} else {
				String s = "Sentenças de tamanho " + input + ": " + sentencas.length + "\n";
				for (int i = 0; i < sentencas.length; i++) {
					s += "• " + sentencas[i] + "\n";
				}
				messageInformation(s);
			}
		}
	}

	private static JPanel[] getPanels() {
		return new JPanel[] { (JPanel) splitPane.getLeftComponent(), (JPanel) splitPane.getRightComponent() };
	}

	public static void salvarComo(JPanel[] panels){
		try {
			arquivo.salvarComo(panels);
		} catch (IOException e1) {
			e1.printStackTrace();
			messageError("O arquivo não pode ser salvo");
		}
	}
}
