package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ElemLexAutomato extends JPanel implements ElemLex {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panel;

	private static final Insets insets = new Insets(0, 0, 0, 0);
	private int linhas;
	private int colunas;
	private LinkedHashMap<Point, Component> mapa;

	/**
	 * Create the panel.
	 */
	public ElemLexAutomato() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridBagLayout());

		JPanel bottomPanel = new JPanel();
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

		mapa = new LinkedHashMap<Point, Component>();
		initialConfig();
	}

	@Override
	public String toString() {
		String s = "";
		for (int y = 0; y < linhas; y++) {
			for (int x = 0; x < colunas; x++) {
				String textoCelula = "";
				if (y == 0 || x <= 1) {
					if (!(y == 0 && x <= 1)) {
						textoCelula = ((JTextField) mapa.get(new Point(x, y))).getText();
					}
				} else {
					textoCelula = ((JTextArea) ((JScrollPane) mapa.get(new Point(x, y))).getViewport().getView()).getText();
				}
				if (textoCelula.equals("")) {
					textoCelula = " ";
				}
				s += textoCelula + "\t";
			}
			s += "\n";
		}
		return s;
	}

	@Override
	public void loadString(String s) throws Exception {
		Main.debug("lading");
		String[] sLinhas = s.split("\n");
		linhas = sLinhas.length;
		colunas = sLinhas[0].split("\t").length;
		for (int linha = 0; linha < linhas; linha++) {
			String[] sColunas = sLinhas[linha].split("\t");

			if (sColunas.length != colunas) {
				throw new Exception("N�mero de colunas diferente da primeira linha\n" + "linha0:" + colunas + "linha" + linha + ":" + sColunas.length);
			}
			int coluna = 0;
			if (linha == 0) {
				coluna = 2;
			}
			for (; coluna < colunas; coluna++) {
				addComponent(sColunas[coluna], coluna, linha);
			}
		}
	}

	@Override
	public String tipo() {
		return "Automato";
	}

	private void initialConfig() {
		addComponent(2, 0);
		addComponent(0, 1);
		addComponent(1, 1);
		addComponent(2, 1);

		linhas = 2;
		colunas = 3;
	}

	private JPanel genComponent(boolean ehCabecalho, String string) {
		if (string.equals(" ")) {
			string = "";
		}
		String s = new String(string);
		JPanel panelBase = new JPanel();
		panelBase.setLayout(new BorderLayout(0, 0));
		if (ehCabecalho) {
			JTextField textField = new JTextField();
			textField.setPreferredSize(new Dimension(30, 20));
			textField.setBorder(new LineBorder(new Color(0, 0, 0)));
			textField.setHorizontalAlignment(JTextField.CENTER);

			Main.debug("cab"+s);
			textField.setText("aa"+s);
			Main.debug(s+"_"+textField.getText());
			textField.repaint();
			panelBase.add(textField, BorderLayout.CENTER);
			panelBase.repaint();
		} else {
			JScrollPane scrollPane = new JScrollPane();
			JTextArea textArea = new JTextArea();
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(false);
			textArea.setMargin(new Insets(5, 5, 5, 5));

			Main.debug("not cab"+s);
			textArea.setText("a"+s);
			Main.debug(s+"_"+textArea.getText());

			scrollPane.setViewportView(textArea);
			scrollPane.setPreferredSize(new Dimension(60, 45));
			scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			panelBase.add(scrollPane, BorderLayout.CENTER);
		}
		panelBase.revalidate();
		return panelBase;
	}

	private void addColumn() {
		for (int i = 0; i < linhas; i++) {
			addComponent(colunas, i);
		}
		colunas++;
	}

	private void addLine() {
		for (int i = 0; i < colunas; i++) {
			addComponent(i, linhas);
		}
		linhas++;
	}

	private void addComponent(int coluna, int linha) {
		addComponent("", coluna, linha);
	}

	private void addComponent(String s, int coluna, int linha) {
		boolean ehCabecalho = coluna <= 1 || linha == 0;
		JPanel component = genComponent(ehCabecalho, s);

		GridBagConstraints gbc = new GridBagConstraints(coluna, linha, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, insets, 0, 0);
		panel.add(component, gbc);
		panel.revalidate();

		mapa.put(new Point(coluna, linha), component.getComponent(0));

	}

}
