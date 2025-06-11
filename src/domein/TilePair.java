package domein;

import enums.Kleur;
import persistentie.TileMapper;

public class TilePair implements Comparable<TilePair> {
	
	private static int max;
	
	private Tile tile1;
	private Tile tile2;
	private final int num;
	private Kleur eigenaarKleur;
	private static TileMapper mapper;
	
	/**
	 * Maakt van twee losse tegels een tegelpaar en voegt deze toe aan een lijst.
	 * @param t1 - eerste tegel van het tegelpaar
	 * @param t2 - tweede tegel van het tegelpaar
	 */
	public TilePair(Tile t1, Tile t2) {
		TilePair.max++;
		this.num = TilePair.max;
		setTile1(t1);
		setTile2(t2);
	}
	
	/**
	 * Geeft alle mogelijke tiles.
	 * @return tile1 - een tile uit de lijst
	 */
	public Tile getTile1() {
		return tile1;
	}

	/**
	 * Kiest uit alle mogelijke tiles een tile en benoemt deze tile1.
	 * @param tile1 - - eerste tegel van het tegelpaar
	 */
	public void setTile1(Tile tile1) {
		this.tile1 = tile1;
	}

	/**
	 * Geeft alle mogelijke tiles.
	 * @return tile2 - een tile uit de lijst
	 */
	public Tile getTile2() {
		return tile2;
	}

	/**
	 * Kiest uit alle mogelijke tiles een tile en benoemt deze tile2.
	 * @param tile2 - tweede tegel van het tegelpaar
	 */
	public void setTile2(Tile tile2) {
		this.tile2 = tile2;
	}

	/**
	 * Heeft alle mogelijke eigenaars
	 * @return eigenaarKleur - een Kleur enum
	 */
	public Kleur getEigenaarKleur() {
		return eigenaarKleur;
	}

	/**
	 * Kijkt als de tegel nog beschikbaar is en geeft deze dan het kleur van de speler die het kiest als hij nog beschikbaar is
	 * @param eigenaarKleur - een waarde uit kleur enum
	 */
	public void setEigenaarKleur(Kleur eigenaarKleur) {
		if(this.eigenaarKleur != null) {
			throw new IllegalArgumentException("TilePair is al geclaimed");
		}
		this.eigenaarKleur = eigenaarKleur;
	}
	
	/**
	 * Neemt het nummer van de tegel
	 * @return num - nummer van de tegel
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Kijkt als het nummer van tile1 en van tile2 overeenkomen
	 */
	@Override
	public int compareTo(TilePair Tile2) {
		return Integer.compare(num, Tile2.getNum());
	}



}
