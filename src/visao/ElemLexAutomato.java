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
	private JButton btnRemoveLine;
	private JButton btnRemoveColumn;

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

		btnRemoveLine = new JButton("-");
		btnRemoveLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeLine();
			}
		});

		GridBagConstraints gbc_btnRemoveLine = new GridBagConstraints();
		gbc_btnRemoveLine.gridx = 0;
		gbc_btnRemoveLine.gridy = 1;
		panel_3.add(btnRemoveLine, gbc_btnRemoveLine);

		JButton btnAddLine = new JButton("+");
		btnAddLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addLine();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 1;
		panel_3.add(btnAddLine, gbc_btnNewButton_1);

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

		btnRemoveColumn = new JButton("-");
		btnRemoveColumn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeColumn();
			}
		});
		GridBagConstraints gbc_btnRemoveColumn = new GridBagConstraints();
		gbc_btnRemoveColumn.gridx = 0;
		gbc_btnRemoveColumn.gridy = 1;
		panel_4.add(btnRemoveColumn, gbc_btnRemoveColumn);

		JButton buttonAddColumn = new JButton("+");
		buttonAddColumn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addColumn();
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.gridx = 1;
		gbc_btnNewButton_3.gridy = 1;
		panel_4.add(buttonAddColumn, gbc_btnNewButton_3);

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
		for (int linha = 0; linha < linhas; linha++) {
			addComponent(linha, colunas);
		}

		colunas++;

		checkRemoveButtons();
	}

	private void removeColumn() {
		colunas--;
		for (int linha = 0; linha < linhas; linha++) {
			Celula celula = mapa.get(linha).get(colunas);
			tabela.remove(celula);
			mapa.get(linha).remove(colunas);
		}

		checkRemoveButtons();
	}

	private void addLine() {
		mapa.add(new Vector<Celula>());
		for (int coluna = 0; coluna < colunas; coluna++) {
			addComponent(linhas, coluna);
		}

		linhas++;

		checkRemoveButtons();
	}

	private void removeLine() {
		linhas--;
		for (int coluna = 0; coluna < colunas; coluna++) {
			Celula celula = mapa.get(linhas).get(coluna);
			tabela.remove(celula);
		}

		mapa.remove(linhas);

		this.revalidate();

		checkRemoveButtons();
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

	protected void checkRemoveButtons(){
		if (colunas <= 3) {
			btnRemoveColumn.setEnabled(false);
		} else {
			btnRemoveColumn.setEnabled(true);
		}

		if (linhas <= 2) {
			btnRemoveLine.setEnabled(false);
		} else {
			btnRemoveLine.setEnabled(true);
		}
	}
}
