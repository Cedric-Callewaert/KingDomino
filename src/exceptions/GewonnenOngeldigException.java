package exceptions;

public class GewonnenOngeldigException extends RuntimeException {
	
	public GewonnenOngeldigException() {
		super("GebruikersNaam ongeldig.");
	}
	public GewonnenOngeldigException(String message) {
		super(message);
	}
}
