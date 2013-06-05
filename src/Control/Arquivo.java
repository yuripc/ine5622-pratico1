package Control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Arquivo {

	protected String currentFile = "";
	protected JFileChooser fileChooser = new JFileChooser();
	protected String extensao;
//teste
	protected static Arquivo instance;

	public Arquivo() {
		currentFile = "";
		extensao = ".afgr";
		fileChooser = new JFileChooser() {
			private static final long serialVersionUID = 1L;

			@Override
			public void approveSelection() {
				File f = new File(getSelectedFile().getAbsolutePath() + extensao);
				if (f.exists() && getDialogType() == SAVE_DIALOG) {
					int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE);
					switch (result) {
					case JOptionPane.YES_OPTION:
						super.approveSelection();
						return;
					case JOptionPane.NO_OPTION:
						return;
					case JOptionPane.CLOSED_OPTION:
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					}
				}
				super.approveSelection();
			}
		};

		fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(extensao) || f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "Arquivos " + extensao;
			}
		});
		fileChooser.setDialogTitle("Selecione o arquivo");

	}

	public void salvar(JPanel[] paineis) throws IOException {
		if (currentFile.equals("")) {
			salvarComo(paineis);
		} else {
			gravar(paineis, currentFile);
		}
	}

	public void salvarComo(JPanel[] paineis) throws IOException {
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			currentFile = fileChooser.getSelectedFile().getAbsolutePath();
			gravar(paineis, currentFile);
		}
	}

	private void gravar(JPanel[] paineis, String localizacao) throws IOException {
		if (!localizacao.substring(localizacao.length() - 5).equals(extensao)) {
			localizacao += extensao;
		}
		BufferedWriter escritor = new BufferedWriter(new FileWriter(localizacao));
		escritor.write(converterParaTexto(paineis));
		escritor.close();
		currentFile = localizacao;
	}

	private String converterParaTexto(JPanel[] paineis) {
		String s = "";
		for (int i = 0; i < paineis.length; i++) {
			try {
				UI.ElemLex painel = (UI.ElemLex) paineis[i];
				s += "#" + painel.tipo();
				s += "\n" + painel.toString();
			} catch (Exception e) {
				s += "#null";
			}
			s += "\n";
		}
		return s;
	}

	public JPanel[] abrir() throws Exception {
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				BufferedReader leitor = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
				StringBuilder sb = new StringBuilder();
				String linha = leitor.readLine();

				while (linha != null) {
					sb.append(linha);
					sb.append("\n");
					linha = leitor.readLine();
				}

				leitor.close();

				currentFile = fileChooser.getSelectedFile().getAbsolutePath();

				return converterParaPaineis(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Erro durante a leitura");

			}
		} else {
			return null;
		}
	}

	private JPanel[] converterParaPaineis(String s) throws Exception {
		String[] elementos = s.split("#");

		JPanel[] paneis = new JPanel[] { new JPanel(), new JPanel() };

		for (int i = 0; i <= 1; i++) {
			String entrada = elementos[i + 1];
			String tipoPanel = entrada.substring(0, entrada.indexOf("\n"));
			String elemento = entrada.substring(entrada.indexOf("\n")+1,entrada.lastIndexOf('\n'));
			if (tipoPanel.equals("Automato")) {
				paneis[i] = new UI.ElemLexAutomato(elemento);
			} else if (tipoPanel.equals("GR")) {
				paneis[i] = new UI.ElemLexGR(elemento);
			}
		}
		return paneis;
	}

	public void resetCurrentFile() {
		currentFile = "";
	}

}
