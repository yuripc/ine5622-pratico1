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

	private final static boolean devVersion = true;
	private JFrame frame;
	private JSplitPane splitPane;
	private Control.Arquivo arquivo = new Control.Arquivo();

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

		JMenuItem mntmUniao = new JMenuItem("União");
		mnAcoes.add(mntmUniao);

		JMenuItem mntmComplemento = new JMenuItem("Complemento");
		mnAcoes.add(mntmComplemento);

		JSeparator separator_4 = new JSeparator();
		mnAcoes.add(separator_4);

		JMenuItem mntmDebug = new JMenuItem("Debug");
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

		JMenuItem mntmA1Determinizar = new JMenuItem("Determinizar");
		mnA1.add(mntmA1Determinizar);

		JMenuItem mntmA1Minimizar = new JMenuItem("Minimizar");
		mnA1.add(mntmA1Minimizar);

		JSeparator SepA12 = new JSeparator();
		mnA1.add(SepA12);

		JMenuItem mntmA1Reconhecer = new JMenuItem("Reconhecer Senten\u00E7a");
		mnA1.add(mntmA1Reconhecer);

		JMenuItem mntmA1GerarSentencas = new JMenuItem("Senten\u00E7as Reconhecidas");
		mnA1.add(mntmA1GerarSentencas);

		JSeparator SepA13 = new JSeparator();
		mnA1.add(SepA13);

		final JMenuItem mntmA1Converter = new JMenuItem("Converter");
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

		JMenuItem mntmA2Determinizar = new JMenuItem("Determinizar");
		mnA2.add(mntmA2Determinizar);

		JMenuItem mntmA2Minimizar = new JMenuItem("Minimizar");
		mnA2.add(mntmA2Minimizar);

		JSeparator SepA22 = new JSeparator();
		mnA2.add(SepA22);

		JMenuItem mntmA2Reconhecer = new JMenuItem("Reconhecer Senten\u00E7a");
		mnA2.add(mntmA2Reconhecer);

		JMenuItem mntmA2GerarSentencas = new JMenuItem("Senten\u00E7as Reconhecidas");
		mnA2.add(mntmA2GerarSentencas);

		JSeparator SepA23 = new JSeparator();
		mnA2.add(SepA23);

		final JMenuItem mntmA2Converter = new JMenuItem("Converter");
		mnA2.add(mntmA2Converter);

		splitPane = new JSplitPane();
		splitPane.setLeftComponent(new JPanel());
		splitPane.setRightComponent(new JPanel());

		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		mntmAbrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				try {
					JPanel[] panels = arquivo.abrir();
					splitPane.setLeftComponent(panels[0]);
					splitPane.setRightComponent(panels[1]);
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
				debug("Ação não configurada");
			}
		});

		mntmComplemento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
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
				splitPane.setLeftComponent(new ElemLexAutomato());
				mntmA1Converter.setText("Converter para GR");
			}
		});

		mntmA1NovoGR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				splitPane.setLeftComponent(new ElemLexGR());
				mntmA1Converter.setText("Converter para Automato");
			}
		});

		mntmA1Determinizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});

		mntmA1Minimizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});

		mntmA1Reconhecer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});

		mntmA1GerarSentencas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});

		mntmA1Converter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});

		mntmA2NovoAutomato.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				splitPane.setRightComponent(new ElemLexAutomato());
				mntmA2Converter.setText("Converter para GR");
			}
		});

		mntmA2NovoGR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				splitPane.setRightComponent(new ElemLexGR());
				mntmA2Converter.setText("Converter para Automato");
			}
		});

		mntmA2Determinizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});

		mntmA2Minimizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});

		mntmA2Reconhecer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});

		mntmA2GerarSentencas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});

		mntmA2Converter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				debug("Ação não configurada");
			}
		});
	}

	public static void debug(String s) {
		if (devVersion) {
			System.out.println(s);
		}
	}

	private JPanel[] getPanels() {
		return new JPanel[] { (JPanel) splitPane.getLeftComponent(), (JPanel) splitPane.getRightComponent() };
	}
}
