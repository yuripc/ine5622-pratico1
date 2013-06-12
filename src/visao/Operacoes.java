package visao;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controle.Operacao;

public class Operacoes extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
	public Operacoes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
	}

	public Operacoes(Vector<controle.Operacao> operacoes) {
		this();

		for (Operacao operacao : operacoes) {
			try {
				contentPane.add(new visao.Operacao(operacao));
			} catch (Exception e) {
				Main.messageError("Ocorreu um erro durante a criação da visualização da operacao " + operacao + "\n" + e.getMessage());
			}
		}
		this.setVisible(true);

	}
}
