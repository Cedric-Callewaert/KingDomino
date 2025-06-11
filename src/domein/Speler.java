package domein;

import java.time.LocalDate;

import exceptions.GebruikerTeJongException;
import exceptions.GebruikersNaamOngeldigException;
import exceptions.GespeeldOngeldigException;
import exceptions.GewonnenOngeldigException;

public class Speler 
{
    private String gebruikersnaam;
    private int geboortejaar;
    private int aantalGewonnen, aantalGespeeld;
    public static final int MAX_GEBOORTEJAAR = 2018, MIN_GEWONNEN = 0, MIN_GESPEELD = 0;
    /**
     * Maakt speler aan met gebruikersnaam en geboortejaar, aantalgewonnen en aantalgespeeld op 0.
     * @param gebruikersnaam - gebruikersnaam van speler
     * @param geboortejaar - geboortejaar van speler
     */
    public Speler(String gebruikersnaam,int  geboortejaar) 
    {
    	this(gebruikersnaam,geboortejaar,0,0);
    }
    /**
     * Maakt speler aan met gebruikersnaam, geboortejaar, aantalgewonnen en aantalgespeeld.
     * @param gebruikersnaam
     * @param geboortejaar
     * @param aantalGewonnen - aantal spelletjes die speler gewonnen heeft
     * @param aantalGespeeld - aantal spelletjes die speler gespeeld heeft
     */
    public Speler(String gebruikersnaam,int  geboortejaar, int aantalGewonnen, int aantalGespeeld) 
    {
    	setGebruikersnaam(gebruikersnaam);
    	setGeboortejaar(geboortejaar);
    	setAantalGewonnen(aantalGewonnen);
    	setAantalGespeeld(aantalGespeeld);
    }
    /**
     * Geeft gebruikersnaam terug
     * @return gebruikersnaam
     */
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}
	/**
	 * Stelt gebruikersnaam van de speler in en werpt Exception als deze ongeldig is.
	 * @param gebruikersnaam
	 * @throws GebruikersNaamOngeldigException
	 */
	private void setGebruikersnaam(String gebruikersnaam) {
		if(gebruikersnaam.isBlank() || gebruikersnaam.isEmpty() || gebruikersnaam == null || gebruikersnaam.length() < 6) {
			throw new GebruikersNaamOngeldigException();
		}
		this.gebruikersnaam = gebruikersnaam;
	}
	/**
	 * Geeft geboortejaar terug.
	 * @return geboortejaar
	 */
	public int getGeboortejaar() {
		return geboortejaar;
	}
	/**
	 * Stelt geboortejaar van de speler in en werpt Exception als deze ongeldig is.
	 * @param geboortejaar
	 * @throws GebruikerTeJongException
	 */
	private void setGeboortejaar(int geboortejaar) {
		if(geboortejaar > LocalDate.now().getYear() - 6) {
			throw new GebruikerTeJongException();
		}
		this.geboortejaar = geboortejaar;
	}
	/**
	 * Geeft aantal gewonnen spelletjes van de speler terug.
	 * @return aantal gewonnen spellen
	 */
	public int getAantalGewonnen() {
		return aantalGewonnen;
	}
	/**
	 * Stelt aantalGewonnen van de speler in en werpt Exception als deze ongeldig is.
	 * @param aantalGewonnen
	 * @throws GewonnenOngeldigException
	 */
	private void setAantalGewonnen(int aantalGewonnen) {
		if(aantalGewonnen<0) 
			throw new GewonnenOngeldigException();
		this.aantalGewonnen = aantalGewonnen;
	}
	/**
	 * Geeft aantal gespeelde spelletjes van de speler terug.
	 * @return aantal gespeelde spellen
	 */
	public int getAantalGespeeld() {
		return aantalGespeeld;
	}
	/**
	 * Stelt aantalGespeeld van de speler in en werpt Exception als deze ongeldig is.
	 * @param aantalGespeeld
	 * @throws GespeeldOngeldigException
	 */
	private void setAantalGespeeld(int aantalGespeeld) {
		if(aantalGespeeld<0) 
			throw new GespeeldOngeldigException();
		this.aantalGespeeld = aantalGespeeld;
	}

}
