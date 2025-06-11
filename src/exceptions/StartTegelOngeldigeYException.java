package exceptions;

public class StartTegelOngeldigeYException extends RuntimeException{
	public StartTegelOngeldigeYException() {
		super("Ongeldige coordinaat y voor Start-tegel.");
	}
	public StartTegelOngeldigeYException(String message) {
		super(message);
	}
}
