package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import domein.Spel;
import domein.Speler;
import domein.Tile;
import domein.spelerSpel;
import domein.TilePair;
import enums.Kleur;

public class SpelTest {
    private Spel spel;
    private List<spelerSpel> spelers;

    @BeforeEach
    void aanmaakSpel() {
        spelers = new ArrayList<>();
        spelers.add(new spelerSpel(Kleur.GEEL, new Speler("Frosty", 2002)));
        spelers.add(new spelerSpel(Kleur.ROOD, new Speler("Frosty2", 2005)));
        spelers.add(new spelerSpel(Kleur.BLAUW, new Speler("loldle", 2006)));
        spelers.add(new spelerSpel(Kleur.GROEN, new Speler("lollll", 2014)));
        spel = new Spel(spelers);
    }

    @Test
    void maakSpel_Volledig_maaktObject() {
        Assertions.assertEquals(4, spel.getAantalSpelers());
    }

    @ParameterizedTest
	@ValueSource(ints = {-1, 5, 6})
    void claimTilePair_numFout_geeftError(int num) {
        ArrayList<TilePair> column = new ArrayList<>();
        column.add(new TilePair(new Tile(), new Tile()));
        column.add(new TilePair(new Tile(), new Tile()));
        column.add(new TilePair(new Tile(), new Tile()));
        column.add(new TilePair(new Tile(), new Tile()));
        Kleur kleur = Kleur.GEEL;

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        spel.claimTilePair(kleur, num, column);

        Assertions.assertFalse(errContent.toString().contains("Onverwachte waarden: " + num + " moet tussen 1 en 4 zijn"));
    }

    @Test
    void claimTilePair_allesJuist_maaktObject() {
        ArrayList<TilePair> column = new ArrayList<>();
        column.add(new TilePair(new Tile(), new Tile()));
        column.add(new TilePair(new Tile(), new Tile()));
        column.add(new TilePair(new Tile(), new Tile()));
        column.add(new TilePair(new Tile(), new Tile()));
        Kleur kleur = Kleur.GEEL;
        int num = 2;

        spel.claimTilePair(kleur, num -1, column);

        assertEquals(kleur, column.get(num -1).getEigenaarKleur());

        for (spelerSpel s : spelers) {
            if (s.getKleur() == kleur) {
                assertEquals(num, s.getOrder());
                break;
            }
        }
    }
}
