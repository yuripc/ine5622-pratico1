package UI;

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

	private JFrame frame;
	private JSplitPane splitPane;
	private Control.Arquivo arquivo = new Control.Arquivo();
	private JMenuItem mntmUniao;
	private JMenuItem mntmComplemento;
	private JMenuItem mntmA1Determinizar;
	private JMenuItem mntmA1Minimizar;
	private JMenuItem mntmA1Reconhecer;
	private JMenuItem mntmA1GerarSentencas;
	private JMenuItem mntmA1Converter;
	private JMenuItem mntmA2Determinizar;
	private JMenuItem mntmA2Minimizar;
	private JMenuItem mntmA2GerarSentencas;
	private JMenuItem mntmA2Reconhecer;
	private JMenuItem mntmA2Converter;
	private JMenuItem mntmNovo;

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

		mntmA1Reconhecer = new JMenuItem("Reconhecer Senten\u00E7a");
		mnA1.add(mntmA1Reconhecer);

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

		mntmA2Reconhecer = new JMenuItem("Reconhecer Senten\u00E7a");
		mnA2.add(mntmA2Reconhecer);

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
				// TODO
				try {
					JPanel[] panels = arquivo.abrir();
					addComponent(true, panels[0]);
					addComponent(false, panels[1]);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		mntmSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					arquivo.salvar(getPanels());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		mntmSalvarComo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					arquivo.salvarComo(getPanels());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				// TODO
				System.out.println("Ação não configurada");
			}
		});

		mntmA1Minimizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");
			}
		});

		mntmA1Reconhecer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");
			}
		});

		mntmA1GerarSentencas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");
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
				// TODO
				System.out.println("Ação não configurada");
			}
		});

		mntmA2Minimizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");
			}
		});

		mntmA2Reconhecer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");
			}
		});

		mntmA2GerarSentencas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Ação não configurada");
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

	private void addComponent(boolean isLeft, JPanel component) {
		boolean habilitarMenuElemento = component instanceof ElemLex;
		if (isLeft) {
			splitPane.setLeftComponent(component);

			mntmA1Determinizar.setEnabled(habilitarMenuElemento);
			mntmA1Minimizar.setEnabled(habilitarMenuElemento);
			mntmA1Reconhecer.setEnabled(habilitarMenuElemento);
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
			mntmA2Reconhecer.setEnabled(habilitarMenuElemento);
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

	private JPanel[] getPanels() {
		return new JPanel[] { (JPanel) splitPane.getLeftComponent(), (JPanel) splitPane.getRightComponent() };
	}

}
