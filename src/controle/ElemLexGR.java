package controle;

public class ElemLexGR extends ElemLex {

	public ElemLexGR(String elementoLexico) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("n‹o implementado");
	}


	@Override
	public ElemLexGR toGR() {
		try {
			return (ElemLexGR) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ElemLexAutomato toAutomato() {
		System.out.println("n‹o implementado");
		// TODO Auto-generated method stub
		return null;
	}
}
