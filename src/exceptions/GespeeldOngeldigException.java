package exceptions;

public class GespeeldOngeldigException extends RuntimeException{

	public GespeeldOngeldigException() {
		super("Aantal gespeeld ongeldig.");
	}
	public GespeeldOngeldigException(String message) {
		super(message);
	}
}
