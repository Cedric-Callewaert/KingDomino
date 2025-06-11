package exceptions;

public class StartTegelOngeldigeXException extends RuntimeException{
	public StartTegelOngeldigeXException() {
		super("Ongeldige coordinaat x voor Start-tegel.");
	}
	public StartTegelOngeldigeXException(String message) {
		super(message);
	}
}
