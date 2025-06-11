package exceptions;

public class KleurOngeldigException extends RuntimeException{
	public KleurOngeldigException() {
		super("Kleur ongeldig.");
	}
	public KleurOngeldigException(String message) {
		super(message);
	}
	
}
