package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Tile;
import domein.spelGrid;
import enums.Kleur;
import enums.landschap;
import exceptions.TegelNietGeldigException;

public class SpelGridTest {

    private spelGrid grid;

    @BeforeEach
    public void setUp() {
        grid = new spelGrid(11, 11); 
    }

    @Test
    public void testCanPlacePair() {
        Assertions.assertFalse(grid.canPlacePair(landschap.AARDE, landschap.AARDE));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0})
    public void testPlaceStartTile() {
        Tile tile = new Tile(); 
        grid.placeStartTile(5, 5, new Tile(landschap.START, Kleur.BLAUW)); 
       
    }

    @Test
    public void testPlaceTilePairInvalid(){
        Tile tile1 = new Tile(); 
        Tile tile2 = new Tile();
       
        Assertions.assertThrows(TegelNietGeldigException.class, () -> grid.placeTilePair(5, 5, 1, tile1, tile2));
    }


}
