package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Speler;
import exceptions.*;

class SpelerTest {
	private Speler speler;
	private static final String GELDIGE_NAAM = "SpelerNaam";
	private static final int GELDIGE_GEBOORTEJAAR = 2005, GELDIGE_GEWONNEN = 4, GELDIGE_GESPEELD = 25;

	@Test
	void maakSpeler_alleGegevensCorrect_maaktObject() {
		speler = new Speler(GELDIGE_NAAM, GELDIGE_GEBOORTEJAAR, GELDIGE_GEWONNEN, GELDIGE_GESPEELD);
		Assertions.assertEquals(GELDIGE_NAAM, speler.getGebruikersnaam());
		Assertions.assertEquals(GELDIGE_GEBOORTEJAAR, speler.getGeboortejaar());
		Assertions.assertEquals(GELDIGE_GEWONNEN, speler.getAantalGewonnen());
		Assertions.assertEquals(GELDIGE_GESPEELD, speler.getAantalGespeeld());
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "avata", "a12" })
	void maakSpeler_fouteGebruikersnaam_werptExceptie(String ongeldigeNaam) {
		Assertions.assertThrows(GebruikersNaamOngeldigException.class,
				() -> new Speler(ongeldigeNaam, GELDIGE_GEBOORTEJAAR, GELDIGE_GEWONNEN, GELDIGE_GESPEELD));
	}

	@ParameterizedTest
	@ValueSource(strings = { "aaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
	void maakSpeler_grenswaardenGebruikersnaam_maaktAan(String geldigeNaam) {
		Speler s = new Speler(geldigeNaam, GELDIGE_GEBOORTEJAAR, GELDIGE_GEWONNEN, GELDIGE_GESPEELD);
		Assertions.assertEquals(geldigeNaam, s.getGebruikersnaam());
		Assertions.assertEquals(GELDIGE_GEBOORTEJAAR, s.getGeboortejaar());
		Assertions.assertEquals(GELDIGE_GEWONNEN, s.getAantalGewonnen());
		Assertions.assertEquals(GELDIGE_GESPEELD, s.getAantalGespeeld());
	}

	@ParameterizedTest
	@ValueSource(ints = { Speler.MAX_GEBOORTEJAAR+1, 2021 })
	void maakSpeler_fouteGeboorteJaar_werptExceptie(int ongeldigeJaar) {
		Assertions.assertThrows(GebruikerTeJongException.class,
				() -> new Speler(GELDIGE_NAAM, ongeldigeJaar, GELDIGE_GEWONNEN, GELDIGE_GESPEELD));
	}

	@ParameterizedTest
	@ValueSource(ints = {Speler.MAX_GEBOORTEJAAR, 2018 })
	void maakSpeler_grenswaardenGeboorteJaar_maaktAan(int geldigeJaar) {
		Speler s = new Speler(GELDIGE_NAAM, geldigeJaar, GELDIGE_GEWONNEN, GELDIGE_GESPEELD);
		Assertions.assertEquals(GELDIGE_NAAM, s.getGebruikersnaam());
		Assertions.assertEquals(geldigeJaar, s.getGeboortejaar());
		Assertions.assertEquals(GELDIGE_GEWONNEN, s.getAantalGewonnen());
		Assertions.assertEquals(GELDIGE_GESPEELD, s.getAantalGespeeld());
	}

	@ParameterizedTest
	@ValueSource(ints = { -5, Speler.MIN_GEWONNEN-1 })
	void maakSpeler_fouteAantalGewonnen_werptExceptie(int ongeldigeAantalGewonnen) {
		Assertions.assertThrows(GewonnenOngeldigException.class,
				() -> new Speler(GELDIGE_NAAM, GELDIGE_GEBOORTEJAAR, ongeldigeAantalGewonnen, GELDIGE_GESPEELD));
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, Speler.MIN_GEWONNEN })
	void maakSpeler_grenswaardenAantalGewonnen_maaktAan(int geldigeAantalGewonnen) {
		Speler s = new Speler(GELDIGE_NAAM, GELDIGE_GEBOORTEJAAR, geldigeAantalGewonnen, GELDIGE_GESPEELD);
		Assertions.assertEquals(GELDIGE_NAAM, s.getGebruikersnaam());
		Assertions.assertEquals(GELDIGE_GEBOORTEJAAR, s.getGeboortejaar());
		Assertions.assertEquals(geldigeAantalGewonnen, s.getAantalGewonnen());
		Assertions.assertEquals(GELDIGE_GESPEELD, s.getAantalGespeeld());
	}

	@ParameterizedTest
	@ValueSource(ints = { -5, Speler.MIN_GESPEELD-1 })
	void maakSpeler_fouteAantalGespeeld_werptExceptie(int ongeldigeAantalGespeeld) {
		Assertions.assertThrows(GespeeldOngeldigException.class,
				() -> new Speler(GELDIGE_NAAM, GELDIGE_GEBOORTEJAAR, GELDIGE_GEWONNEN, ongeldigeAantalGespeeld));
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, Speler.MIN_GESPEELD })
	void maakSpeler_grenswaardenAantalGespeeld_maaktAan(int geldigeAantalGespeeld) {
		Speler s = new Speler(GELDIGE_NAAM, GELDIGE_GEBOORTEJAAR, GELDIGE_GEWONNEN, geldigeAantalGespeeld);
		Assertions.assertEquals(GELDIGE_NAAM, s.getGebruikersnaam());
		Assertions.assertEquals(GELDIGE_GEBOORTEJAAR, s.getGeboortejaar());
		Assertions.assertEquals(GELDIGE_GEWONNEN, s.getAantalGewonnen());
		Assertions.assertEquals(geldigeAantalGespeeld, s.getAantalGespeeld());
	}

}
