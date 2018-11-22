package model.exceptions;

public class DaoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DaoException(String mensagem , Throwable causa) {
		super(mensagem,causa);
	}
	public DaoException(Throwable causa){
		super(causa);
	}

}
