package exceptions;

public class GebruikersNaamOngeldigException extends RuntimeException{
	public GebruikersNaamOngeldigException() {
		super("GebruikersNaam ongeldig.");
	}
	public GebruikersNaamOngeldigException(String message) {
		super(message);
	}
	
}
