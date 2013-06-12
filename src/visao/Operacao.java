package visao;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Operacao extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel nome;
	private visao.ElemLex elemento;

	/**
	 * Create the panel.
	 */
	public Operacao(controle.Operacao operacao) throws Exception {
		if (operacao.getElemLex() instanceof controle.ElemLexAutomato) {
			elemento = new visao.ElemLexAutomato(operacao.getElemLex().toString());
		} else {
			elemento = new visao.ElemLexGR(operacao.getElemLex().toString());
		}
		elemento.habilitarEdicao(false);
		setLayout(new BorderLayout(0, 0));

		nome = new JLabel("Descricao");
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
