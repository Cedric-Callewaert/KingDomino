package domein;

import enums.Kleur;
import enums.landschap;

public class spelerSpel{
	private Kleur kleur;
	private spelGrid spelGrid;
	private final Speler linkedSpeler;
	private int order;
	private int score;
	/**
	 * Maakt een speler voor het spel aan met de opgegeven kleur en spelergegevens.
	 * Het spelraster wordt gecreeerd en een starttegel word toegewezen
	 * @param kleur
	 * @param speler
	 */
	public spelerSpel(Kleur kleur, Speler speler) {
		this.linkedSpeler = speler;
		setKleur(kleur);
		setSpelGrid(new spelGrid(11, 11));
		kiesStartTegel(5, 5);
	}
	/**
	 * Geeft kleur terug van de speler in het spel.
	 * @return
	 */
	public Kleur getKleur() {
		return kleur;
	}
	/**
	 * Stelt de kleur van de speler in.
	 * @param kleur
	 */
	public void setKleur(Kleur kleur) {
		this.kleur = kleur;
	}
	/**
	 * Geeft de grid van het spel terug.
	 * @return De grid van het spel
	 */
	public spelGrid getSpelGrid() {
		return spelGrid;
	}
	/**
	 * Stelt de grid van het spel in.
	 * @param spelGrid
	 */
	public void setSpelGrid(spelGrid spelGrid) {
		this.spelGrid = spelGrid;
	}
	/**
	 * Plaatst de starttegel op de gegeven positie (5,5 hardcoded) in de grid van het spel.
	 * @param x
	 * @param y
	 */
	private void kiesStartTegel(int x, int y) {
		spelGrid.placeStartTile(x, y, new Tile(landschap.START, kleur));
	}
	/**
	 * Controleert op het tegelpaar op de gegeven positie kan geplaatst worden.
	 * @param l1 - x coordinaat tegel die geplaatst moet worden
	 * @param l2 - y coordinaat tegel die geplaatst moet worden
	 * @return of de tegel hier geplaatst kan worden
	 */
	public boolean canPlaceChoosen(landschap l1, landschap l2) {
		return spelGrid.canPlacePair(l1, l2);
	}
	/**
	 * Geeft de score van de speler terug.
	 * @return score van de speler
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Berekent de score van de speler.
	 */
	public void calculateScore() {
		score = spelGrid.calculateScore();
	}
	/**
	 * Geeft de speler terug die gelinked is aan een specifieke kleur.
	 * @return de speler die gelinked is aan de kleur
	 */
	public Speler getLinkedSpeler() {
		return linkedSpeler;
	}
	/**
	 * Geeft de volgorde van de spelers door voor het kiezen van een tegel/plaatsen.
	 * @return de volgorde van de spelers
	 */
	public int getOrder() {
		return order;
	}
	/**
	 * Stelt de volgorde van de spelers in
	 * @param order - volgorde van de spelers
	 */
	public void setOrder(int order) {
		this.order = order;
	}

}
