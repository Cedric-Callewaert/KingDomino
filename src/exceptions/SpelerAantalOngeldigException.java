package exceptions;

public class SpelerAantalOngeldigException extends RuntimeException{
	public SpelerAantalOngeldigException() {
		super("Ongeldig aantal spelers.");
	}
	public SpelerAantalOngeldigException(String message) {
		super(message);
	}
}
