package controle;

public class ElemLexGR extends ElemLex {

	public ElemLexGR(String[] elementoLexico) throws Exception {
		super(elementoLexico);
	}


	@Override
	public ElemLexGR toGR() {
		return this;
	}

	@Override
	public ElemLexAutomato toAutomato() {
		System.out.println("n‹o implementado");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElemLexGR processar(String[] o) {
		System.out.println("n‹o implementado");
		// TODO Auto-generated method stub
		return null;
	}

}
