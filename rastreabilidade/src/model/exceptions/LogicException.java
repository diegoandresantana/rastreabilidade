package model.exceptions;

public class LogicException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public LogicException(String mensagem , Throwable causa) {
		super(mensagem,causa);
	}
}
