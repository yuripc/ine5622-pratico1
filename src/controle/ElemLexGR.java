package controle;

public class ElemLexGR extends ElemLex {

	public ElemLexGR(String elementoLexico) throws InvalidInputException {
		// TODO Auto-generated method stub
		throw new InvalidInputException("n‹o implementado");
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

	@Override
	public ElemLex converter(){
		return toAutomato();
	}
}
