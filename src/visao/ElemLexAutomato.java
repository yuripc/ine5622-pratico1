package visao;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ElemLexAutomato extends ElemLex {
	private static final long serialVersionUID = 1L;

	private JPanel tabela;

	private static final Insets insets = new Insets(0, 0, 0, 0);
	private int linhas;
	private int colunas;
	private Vector<Vector<Celula>> mapa;
	private JPanel bottomPanel;

	public ElemLexAutomato() {
		super();
		initialConfig();
	}

	public ElemLexAutomato(String s) throws Exception {
		super(s);
	}

	/**
	 * Create the panel.
	 */

	@Override
	protected void initialize() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		tabela = new JPanel();
		scrollPane.setViewportView(tabela);
		tabela.setLayout(new GridBagLayout());

		bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.anchor = GridBagConstraints.EAST;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		bottomPanel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.EAST);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JLabel lblNewLabel = new JLabel("Linha");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);

		JButton btnNewButton = new JButton("-");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		panel_3.add(btnNewButton, gbc_btnNewButton);

		JButton btnNewButton_1 = new JButton("+");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addLine();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 1;
		panel_3.add(btnNewButton_1, gbc_btnNewButton_1);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 0;
		bottomPanel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.WEST);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel lblNewLabel_1 = new JLabel("Coluna");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_4.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JButton btnNewButton_2 = new JButton("-");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 1;
		panel_4.add(btnNewButton_2, gbc_btnNewButton_2);

		JButton btnNewButton_3 = new JButton("+");
		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addColumn();
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.gridx = 1;
		gbc_btnNewButton_3.gridy = 1;
		panel_4.add(btnNewButton_3, gbc_btnNewButton_3);

		mapa = new Vector<Vector<Celula>>();

		addComponent(null, 0, 0);
		addComponent(null, 0, 1);
	}

	@Override
	public void habilitarEdicao(boolean habilitar) {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Celula celula = mapa.get(linha).get(coluna);
				if (celula != null) {
					celula.setEnabled(false);
				}
			}
		}

		tabela.setEnabled(habilitar);
		bottomPanel.setVisible(habilitar);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {

				if (!(linha == 0 && coluna <= 1)) {
					sb.append(mapa.get(linha).get(coluna).getText());
				} else {
					sb.append(" ");
				}

				sb.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	protected void loadString(String s) throws Exception {
		String[] sLinhas = s.split("\n");
		linhas = sLinhas.length;
		colunas = sLinhas[0].split("\t").length;
		for (int linha = 0; linha < linhas; linha++) {
			String[] sColunas = sLinhas[linha].split("\t");

			if (sColunas.length != colunas) {
				throw new Exception("Número de colunas diferente da primeira linha\n" + "linha0:" + colunas + "linha" + linha + ":" + sColunas.length);
			}
			int coluna = 0;
			if (linha == 0) {
				coluna = 2;
			}
			for (; coluna < colunas; coluna++) {
				addComponent(sColunas[coluna], linha, coluna);
			}
		}
	}

	protected void initialConfig() {
		linhas = 1;
		colunas = 2;

		addLine();
		addColumn();
	}

	private void addColumn() {
		for (int i = 0; i < linhas; i++) {
			addComponent(i, colunas);
		}
		colunas++;
	}

	private void addLine() {
		mapa.add(new Vector<Celula>());
		for (int i = 0; i < colunas; i++) {
			addComponent(linhas, i);
		}
		linhas++;
	}

	private void addComponent(int linha, int coluna) {
		addComponent("", linha, coluna);
	}

	private void addComponent(String s, int linha, int coluna) {
		Celula componente;
		if (coluna <= 1 || linha == 0) {
			componente = new CelulaCabecalho(s);
		} else {
			componente = new CelulaCorpo(s);
		}

		GridBagConstraints gbc = new GridBagConstraints(coluna, linha, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, insets, 0, 0);
		tabela.add(componente, gbc);

		try {
			mapa.get(linha);
		} catch (ArrayIndexOutOfBoundsException e) {
			mapa.add(new Vector<Celula>());
		} finally {
			mapa.get(linha).add(componente);
		}
		this.revalidate();
	}

	@Override
	public void irPara(int linha, int coluna) {
		mapa.get(linha).get(coluna).setFocus();
	}

}
