package exceptions;

public class StartKolomOngeldigException extends RuntimeException{
	public StartKolomOngeldigException() {
		super("Ongeldige startkolom.");
	}
	public StartKolomOngeldigException(String message) {
		super(message);
	}

}
