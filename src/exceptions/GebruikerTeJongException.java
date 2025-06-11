package exceptions;

public class GebruikerTeJongException extends RuntimeException{
	public GebruikerTeJongException() {
		super("Ongeldige geboorte datum.");
	}
	public GebruikerTeJongException(String message) {
		super(message);
	}
	
}
