package ufu.facom.lia.exceptions;

public class InvalidBoardException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidBoardException(){
		
	}
	
	public InvalidBoardException(String message) {
		super(message);
	}

}
