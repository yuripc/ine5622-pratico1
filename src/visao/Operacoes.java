package visao;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Operacoes extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel nome;
	private visao.ElemLex elemento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Operacoes frame = new Operacoes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	protected Operacoes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
	}

	public Operacoes(controle.Operacao operacao) {
		this();

		try {
			if (operacao.getElemLex() instanceof controle.ElemLexAutomato) {
				elemento = new visao.ElemLexAutomato(operacao.getElemLex().toString());

			} else {
				elemento = new visao.ElemLexGR(operacao.getElemLex().toString());
			}
			try {
				elemento.habilitarEdicao(false);
			} catch (Exception e) {
				e.printStackTrace();
			}

			setLayout(new BorderLayout(0, 0));

			nome = new JLabel();
			nome.setHorizontalAlignment(SwingConstants.CENTER);
			add(nome, BorderLayout.NORTH);

			JPanel panel_1 = new JPanel();
			add(panel_1, BorderLayout.SOUTH);

			JButton btnNewButton = new JButton("Aplicar Esquerda");
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						aplicarComponente(true);
					} catch (Exception e1) {

					}
				}
			});
			panel_1.add(btnNewButton);

			JButton btnAplicarDireita = new JButton("Aplicar Direita");
			btnAplicarDireita.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					aplicarComponente(false);
				}
			});
			panel_1.add(btnAplicarDireita);

			JButton btnSalvar = new JButton("Salvar");
			btnSalvar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Main.salvarComo(new JPanel[] { elemento, new JPanel() });
				}
			});
			panel_1.add(btnSalvar);

			nome.setText(operacao.getOperacao());

			add(elemento, BorderLayout.CENTER);

			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			Main.messageError("Ocorreu um erro durante a criação da visualização da operacao " + operacao + "\n" + e.getMessage());

		}
	}

	protected void aplicarComponente(boolean esquerda) {
		try {
			ElemLex elem;

			if (elemento instanceof ElemLexAutomato) {
				elem = new ElemLexAutomato(elemento.toString());
			} else {
				elem = new ElemLexGR(elemento.toString());
			}

			Main.addComponent(esquerda, elem);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
