package domein;

import java.util.Random;

import enums.Kleur;
import enums.landschap;

public class Tile {

	private int aantalKronen;
	private landschap landschapType;
	private static Random rand = new Random();
	private static landschap[] landschappen = new landschap[]{landschap.AARDE, landschap.BOS, landschap.GRAS, landschap.MIJN, landschap.WATER, landschap.ZAND};
	private int tileID;

	private int dir;
	/**
	 * Maakt een tegel aan met random landschaptype en 4 kronen en zet tegelID op -1
	 */
	public Tile() {
		setLandschapType(landschappen[rand.nextInt(landschappen.length)]);
		setRandomAantalKronen(4);
		setTileID(-1);
	}
	/**
	 * Maakt een tegel aan met een specifiek landschapstype, 4 kronen en de tegelID word
	 * op basis van de kleur bepaald. Directie van de tegel is basic(horizontaal/1).
	 * @param type - soort landschap uit enum landschap
	 * @param kleur - soort kleur uit enum Kleur
	 */
	public Tile(landschap type, Kleur kleur) {
		setLandschapType(type);
		setRandomAantalKronen(4);
		setTileID(kleur.getStarttegelId());
		setDir(1);
	}
	/**
	 * Maakt tegel aan met specifiek landschapstype, aantal kronen, tegelID en richting is basic.
	 * @param type - soort landschap uit enum landschap
	 * @param kronen - aantal kronen van de tegel
	 * @param id - uniek nummer voor de tegel
	 */
	public Tile(landschap type, int kronen, int id) {
		setLandschapType(type);
		setAantalKronen(kronen);
		setTileID(id);
		setDir(dir);
	}

	/**
	 * Geeft aantal kronen op de tegel terug
	 * @return aantal kronen die op te tegel staan
	 */
	public int getAantalKronen() {
		return aantalKronen;
	}

	/**
	 * Stelt aantal kronen die op de tegel staan in waarbij als 0 kronen is het starttegel is
	 * en anders een willekeurig getal tussen 0 en de maximum word gegenereerd.
	 * @param aantal - max aantal kronen
	 */
	private void setRandomAantalKronen(int aantal) {
		this.aantalKronen = landschapType == landschap.START? 0 : rand.nextInt(aantal);
	}
	/**
	 * Stelt aantal kronen op de tegel in op aantal
	 * @param aantal - meegegeven aantal kronen
	 */
	private void setAantalKronen(int aantal) {
		this.aantalKronen = aantal;
	}

	/**
	 * Geeft type landschap terug
	 * @return het type landschap uit enum landschap
	 */
	public landschap getLandschapType() {
		return landschapType;
	}

	/**
	 * Stelt landschapstype in van de tegel met meegegeven landschapsType
	 * @param landschapType - meegegeven landschapstype
	 */
	private void setLandschapType(landschap landschapType) {
		this.landschapType = landschapType;
	}
	/**
	 * Geeft ID van de tegel terug
	 * @return - ID van de tegel
	 */
	public int getTileID() {
		return tileID;
	}
	/**
	 * Stelt ID in van de tegel met meegegeven tileID
	 * @param tileID - meegegeven ID voor de tegel
	 */
	private void setTileID(int tileID) {
		this.tileID = tileID;
	}
	/**
	 * Geeft de richting van de tegel terug
	 * @return - richting van de tegel
	 */
	public int getDir() {
		return dir;
	}
	/**
	 * Stelt richting van de tegel in op de meegegeven richting
	 * @param dir - meegegeven richting van de tegel
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}
	
}
