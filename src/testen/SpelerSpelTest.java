package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domein.Speler;
import enums.Kleur;
import enums.landschap;

public class SpelerSpelTest {
	private domein.spelerSpel spelerSpel;
	@BeforeAll
    public void setUp() {
        Speler speler = new Speler("Test Speler", 2005);
        spelerSpel = new domein.spelerSpel(Kleur.ROOD, speler);
    }
	
    @Test
    public void testKleur() {
        assertEquals(Kleur.ROOD, spelerSpel.getKleur());
    }

    @Test
    public void testCanPlaceChoosen_ValidPair_ReturnsTrue() {
        enums.landschap l1 = landschap.AARDE;
        enums.landschap l2 = landschap.AARDE;
        domein.spelerSpel kanGeplaatst = new domein.spelerSpel(Kleur.BLAUW, null);

        boolean result = kanGeplaatst.canPlaceChoosen(l1, l2);

        assertTrue(result);
    }

    @Test
    public void testCanPlaceChoosen_InvalidPair_ReturnsFalse() {
    	 enums.landschap l1 = landschap.AARDE;
         enums.landschap l2 = landschap.AARDE;
         domein.spelerSpel kanNietGeplaatst = new domein.spelerSpel(Kleur.BLAUW, null);

         boolean result = kanNietGeplaatst.canPlaceChoosen(l1, l2);

         assertFalse(result);
     }

    @Test
    public void testCalculateScore_WithValidInput_ReturnsExpectedScore() {
    	domein.spelerSpel berekenScore = new domein.spelerSpel(Kleur.BLAUW, null);
        
        berekenScore.calculateScore();
        int result = berekenScore.getScore();

        assertEquals(15, result);
    }

}
