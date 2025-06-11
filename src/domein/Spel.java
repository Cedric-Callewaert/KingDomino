package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparators.SortSpelSpelerByOrder;
import enums.Kleur;
import exceptions.SpelerAantalOngeldigException;
import persistentie.TileMapper;

public class Spel {
	private int aantalSpelers;
	private List<spelerSpel> spelers;
	private ArrayList<TilePair> Stapel;
	private ArrayList<TilePair> startColumn;

	private ArrayList<TilePair> eindColumn;

	/**
	 * Maakt het verloop van het spel en de hoeveelheid spelers in het spel.
	 * 
	 * @param spelers - Lijst van spelers uit spelerSpel
	 */
	public Spel(List<spelerSpel> spelers) {
		setSpelers(spelers);
		setAantalSpelers(spelers.size());
		startSpel();
		shuffelSpelers();
	}

	/**
	 * Kijkt hoeveel ronders er nog zijn in het spel.
	 * 
	 * @return het aantal rondes er nog over zijn voordat het spel gedaan is
	 */
	public int roundsRemaining() {
		return Stapel.size() / aantalSpelers;
	}

	/**
	 * Toont hoeveel spelers er zijn.
	 * 
	 * @return hoeveel spelers er zijn
	 */
	public int getAantalSpelers() {
		return aantalSpelers;
	}

	/**
	 * Kijkt voor hoeveel spelers het spel moet gemaakt worden.
	 * 
	 * @param aantalSpelers - hoeveelheid spelers
	 * @throws SpelerAantalOngeldigException
	 */
	public void setAantalSpelers(int aantalSpelers) {
		if (aantalSpelers < 3 || aantalSpelers > 4) {
			throw new SpelerAantalOngeldigException();
		}
		this.aantalSpelers = aantalSpelers;
	}

	/**
	 * Methode voor de spelers te verkrijgen.
	 * 
	 * @return lijst met spelers
	 */
	public List<spelerSpel> getSpelers() {
		return spelers;
	}

	/**
	 * Methode voor de spelers te linken aan een spel.
	 * 
	 * @param Lijst met spelers van spelerSpel
	 */
	public void setSpelers(List<spelerSpel> spelers) {
		this.spelers = spelers;
	}

	/**
	 * Maakt de volgorde van spelers random.
	 */
	public void shuffelSpelers() {
		Collections.shuffle(spelers);
	}

	/**
	 * Start het spel, importeert de tegels uit de database en steekt deze in een
	 * lijst en shuffled ze daarna en heeft de juiste hoeveelheid kaarten die mogen
	 * gebruikt worden in het spel door.
	 */
	private void startSpel() {
		Stapel = new ArrayList<>();
		TileMapper.startConnectie();

		List<TilePair> tilesPairs = new TileMapper().geefTegels();
		for (TilePair t : tilesPairs) {
			Stapel.add(t);
		}

		TileMapper.endConnectie();

		Collections.shuffle(Stapel);

		if (aantalSpelers < 4) {
			for (int i = 0; i < 12; i++) {
				Stapel.remove(0);
			}
		}

		setStartColumn();
		setEindColumn();
	}

	/**
	 * Sorteert de spelers volgens de volgorde van de gekozen tegels.
	 */
	public void sortByOrder() {
		Collections.sort(spelers, new SortSpelSpelerByOrder());
	}

	/**
	 * Geeft de kolom weer waarin de tegels liggen die de spelers kunnen leggen.
	 * 
	 * @return startColumn - als een ArrayList van TilePair
	 */
	public ArrayList<TilePair> getStartColumn() {
		return startColumn;
	}

	/**
	 * Kiest welke tegels er in de start kolom geraken en verwijderd de al
	 * geplaatste tegels.
	 */
	public void setStartColumn() {
		this.startColumn = new ArrayList<>();
		for (int i = 0; i < aantalSpelers; i++) {
			this.startColumn.add(Stapel.remove(0));
		}

		Collections.sort(startColumn);
	}

	/**
	 * Maakt de eind kolom van de vorige ronde de start kolom van de huidige ronde.
	 * 
	 * @param eindColumn - als een ArrayList van TilePair
	 */
	public void setStartColumn(ArrayList<TilePair> eindColumn) {
		this.startColumn = eindColumn;
	}

	/**
	 * Geeft de kolom weer waarin de tegels liggen die de spelers de volgende ronde
	 * kunnen leggen.
	 * 
	 * @return eindColumn - als een ArrayList van TilePair
	 */
	public ArrayList<TilePair> getEindColumn() {
		return eindColumn;
	}

	/**
	 * Kiest welke tegels er in de eind kolom geraken en verwijderd de gekozen
	 * tegels.
	 */
	public void setEindColumn() {
		this.eindColumn = new ArrayList<>();
		for (int i = 0; i < aantalSpelers; i++) {
			this.eindColumn.add(Stapel.remove(0));
		}

		Collections.sort(eindColumn);
	}

	/**
	 * Start een nieuwe ronde, maakt van de eind kolom de start kolom en maakt een
	 * nieuwe eindkolom aan.
	 */
	public void startNewRound() {
		setStartColumn(eindColumn);
		setEindColumn();
	}

	/**
	 * Geeft het moment aan wanneer de laatste ronde begint en maakt van de eind
	 * kolom de start kolom en laat de eind kolom leeg.
	 */
	public void startLaatsteRonde() {
		setStartColumn(eindColumn);
	}

	/**
	 * Houd de volgorde van de spelers met de hoogste scores bij.
	 */
	public void updateSpelers() {
		int highest = 0;
		for (int i = 0; i < spelers.size(); i++) {
			highest = spelers.get(i).getScore() > highest ? spelers.get(i).getScore() : highest;

		}
	}

	/**
	 * Methode om te kiezen welke tegel je wilt plaatsen in de ronde.
	 * 
	 * @param kleur  - een Kleur enum
	 * @param num    - nummer van tegel te kiezen in start kolom
	 * @param column - een ArrayList van TilePair
	 */
	public void claimTilePair(Kleur kleur, int num, ArrayList<TilePair> column) {
		num--;
		switch (num) { // min 1 omdat we irl tellen van 1 tot 4 maar lijst gaat van 0 tot 3
		case 0 -> column.get(num).setEigenaarKleur(kleur);
		case 1 -> column.get(num).setEigenaarKleur(kleur);
		case 2 -> column.get(num).setEigenaarKleur(kleur);
		case 3 -> column.get(num).setEigenaarKleur(kleur);

		default -> System.err.println("Onverwachte waarden: " + num + "moet tussen 1 en 4 zijn");
		}
		for (spelerSpel s : spelers) {
			if (s.getKleur() == kleur) {
				s.setOrder(num);
				break;
			}
		}
	}

}
