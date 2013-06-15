package controle;

public class InvalidInputException extends Exception {
	protected final int linha, coluna;

	public InvalidInputException(String message) {
		super(message);
		this.linha = -1;
		this.coluna = -1;

	}

	public InvalidInputException(String message, int linha, int coluna) {
		super(message + "\nLinha: " + linha + " Coluna:" + coluna);
		this.linha = linha;
		this.coluna = coluna;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

}