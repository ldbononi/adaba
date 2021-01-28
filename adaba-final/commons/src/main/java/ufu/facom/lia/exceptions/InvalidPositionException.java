package ufu.facom.lia.exceptions;

public class InvalidPositionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidPositionException(){
	}
	
	public InvalidPositionException (String message){
		super(message);
	}

}
