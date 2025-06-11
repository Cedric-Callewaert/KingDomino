package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparators.SortSpelSpelerByScore;
import dto.PairDTO;
import dto.TileDTO;
import dto.boundsDTO;
import dto.scoreDTO;
import enums.Kleur;
import exceptions.TegelNietGeldigException;
import persistentie.SpelerMapper;

public class DomeinController {

    private final SpelerRepository spelerRepository;
    private Spel spel;

    public DomeinController() { 
        spelerRepository = new SpelerRepository();
    }
    /**
     * Voor elk gekozenPaar zet samen met kleur speler en voor elke spelSpeler pakt gekozenPaar.
     * @param k - kleurenEnum
     * @return gekozenPaar per spelSpeler hun kleur
     */
    public boolean canPlaceChoosen(Kleur k) {
    	TilePair gekozenPaar = null;
    	
    	for (TilePair pair : spel.getStartColumn()) {
    		if(pair.getEigenaarKleur() == k) {
    			gekozenPaar = pair;
    		}
		}
    	
    	for (spelerSpel spelSpeler : spel.getSpelers()) {
    		if(spelSpeler.getKleur() == k) {
    			return spelSpeler.canPlaceChoosen(gekozenPaar.getTile1().getLandschapType(), gekozenPaar.getTile2().getLandschapType());
    		}
		}
    	return false;
    }
    /**
     * Maakt nieuweSpeler-object aan met gebruikersnaam en geboortejaar en voegt deze toe aan de spelerRepository.
     * @param gebruikersnaam - Gebruikersnaam van de speler
     * @param geboortejaar - Geboortejaar van de speler
     */
    public void registreerSpeler(String gebruikersnaam, int geboortejaar) 
    {
        Speler nieuweSpeler = new Speler(gebruikersnaam, geboortejaar);
        spelerRepository.voegToe(nieuweSpeler);
    }
    /**
     * Maakt lijst van spelerSpel met objecten spelers.
     * Voor elke kleur in spelerKleuren krijg index i van de namen.
     * Zet spelerSpel met huidige kleur en gebruiker en bij spelers (list).
     * Maakt nieuw Spel met lijst van spelers.
     * @param spelerKleuren - kleuren van de huidige spelers
     * @param namen - gebruikersnaam van de speler
     */
    public void startNieuwSpel(ArrayList<Kleur> spelerKleuren, ArrayList<String> namen) {
    	List<spelerSpel> spelers = new ArrayList<spelerSpel>();
        int i = 0;
        for (Kleur kleur : spelerKleuren) {
        	Speler gebruiker = new SpelerMapper().geefSpeler(namen.get(i));
            spelers.add(new spelerSpel(kleur, gebruiker));
            i++;
        }
        spel = new Spel(spelers);
	}
    /**
     * Doorloopt tegelparen in startkolom van het spel en slecteert degene die eigendom zijn van speler.
     * Plaatst geselecteerde paar op spelraster voor elke speler in spelSpeler.
     * @param speler - kleur van de speler
     * @param x - x coordinaat
     * @param y - y coordinaat
     * @param dir - directie van de tegel
     * @throws TegelNietGeldigException - Throwed wanneer een tegel niet geldig geplaatst wordt.
     */
    public void plaatsGekozenTegel(Kleur speler, int x, int y, int dir) throws TegelNietGeldigException {
    	TilePair gekozenPaar = null;
    	
    	for (TilePair pair : spel.getStartColumn()) {
    		if(pair.getEigenaarKleur() == speler) {
    			gekozenPaar = pair;
    		}
		}
    	
    	for (spelerSpel spelSpeler : spel.getSpelers()) {
    		if(spelSpeler.getKleur() == speler) {
    			spelSpeler.getSpelGrid().placeTilePair(x, y, dir, gekozenPaar.getTile1(), gekozenPaar.getTile2());
    		}
		}
    	
    }
    /**
     * Claimed TilePair van de startkolom voor een specifieke speler.
     * @param eigenaar - kleur van de eigenaar
     * @param num - nummer van de gekozen tegel
     */
    public void claimTilePairStart(Kleur eigenaar, int num) {
    	spel.claimTilePair(eigenaar, num, spel.getStartColumn());
    }
    /**
     * Claimed TilePair van de eindkolom voor een specifieke speler.
     * @param eigenaar - kleur van de eigenaar
     * @param num - nummer van de gekozen tegel
     */
    public void claimTilePairNext(Kleur eigenaar, int num) {
    	spel.claimTilePair(eigenaar, num, spel.getEindColumn());
    }
    /**
     * Start een nieuwe ronde.
     */
    public void startNewRound() {
    	spel.startNewRound();
    }
    /**
     * Hoeveel keer nieuwe ronde nog moet gebeuren.
     * @return - aantal rondes nog te spelen.
     */
    public int roundsRemaining() {
    	return spel.roundsRemaining();
    }
    /**
     * Start de laatste ronde van het spel.
     */
    public void startLaasteRonde() {
    	spel.startLaatsteRonde();
    }
    /**
     * Berekent de score voor elke speler.
     * Maakt een gesorteerde lijst gebaseerd op score voor elke speler, updates de spelers hun scores in de database
     * en vult een array met spelerinformatie voor weergave op eindscherm. 
     * @return - DTOLijst met score, gelinkte speler, gebruikersnaam en kleur
     */
    public scoreDTO[] calculateRankingEndOfGame() {
    	SpelerMapper mapper = new SpelerMapper();
    	
    	for (spelerSpel s : spel.getSpelers()) {
			s.calculateScore();
		}
    	
    	scoreDTO[] DTOLijst = new scoreDTO[spel.getAantalSpelers()];
    	Collections.sort(spel.getSpelers(), new SortSpelSpelerByScore());
    	int index = 0;
    	for (spelerSpel speler : spel.getSpelers()) {
			DTOLijst[index] = new scoreDTO(speler.getLinkedSpeler().getGebruikersnaam(), speler.getKleur(), speler.getScore());
			mapper.updateSpelerScore(speler.getLinkedSpeler().getGebruikersnaam(), index==0);
			index++;
    	}

    	return DTOLijst;
    	
    }
    /**
     * Sorteert de elementen in spel op volgorde.
     */
    public void sortByOrder() {
    	spel.sortByOrder();
    }
    /**
     * Haalt de kleuren van alle spelers in het spel op en retourneert deze in een lijst.
     * @return - lijst van kleuren die gebruikt worden in het spel
     */
    public Kleur[] geefSpelers() {
    	List<spelerSpel> spelSpelers = spel.getSpelers();
    	Kleur[] retList = new Kleur[spelSpelers.size()];
    	int index = 0;
    	for (spelerSpel speler : spelSpelers) {
			retList[index] = speler.getKleur();
			index++;
		}
    	return retList;
    }   
    
    /**
     * Geeft alle tegel paren in de start kolom terug.
     * @return - lijst van PairDTO met als inhoud de tegelpaaren uit de start kolom.
    */
    public PairDTO[] geefStartKolom() {
    	
    	PairDTO[] retList = new PairDTO[4];
    	
    	int index = 0;
    	for (TilePair pair : spel.getStartColumn()) {
    		TileDTO tile1 = new TileDTO(pair.getTile1().getAantalKronen(), pair.getTile1().getLandschapType(), pair.getTile1().getTileID(), pair.getTile1().getDir());
    		TileDTO tile2 = new TileDTO(pair.getTile2().getAantalKronen(), pair.getTile2().getLandschapType(), pair.getTile2().getTileID(), pair.getTile2().getDir());
    		retList[index] = new PairDTO(tile1, tile2, pair.getEigenaarKleur());
    		index++;
    	}
    	
    	return retList;
    }
    
    /**
     * Geeft alle tegel paren in de eind kolom terug.
     * @return - lijst van PairDTO met als inhoud de tegelpaaren uit de eind kolom.
    */
    public PairDTO[] geefEindKolom() {
    	
    	PairDTO[] retList = new PairDTO[4];
    	
    	int index = 0;
    	for (TilePair pair : spel.getEindColumn()) {
    		TileDTO tile1 = new TileDTO(pair.getTile1().getAantalKronen(), pair.getTile1().getLandschapType(), pair.getTile1().getTileID(), pair.getTile1().getDir());
    		TileDTO tile2 = new TileDTO(pair.getTile2().getAantalKronen(), pair.getTile2().getLandschapType(), pair.getTile2().getTileID(), pair.getTile2().getDir());
    		retList[index] = new PairDTO(tile1, tile2, pair.getEigenaarKleur());
    		index++;
		}
    	
    	return retList;
    }
    
    /**
     * Geeft de gekozen tegel van een bepaalde speler terug.
     * @param speler - de kleur van de speler waarvan je het gekozen tegelpaar wilt.
     * @return - de gekozen tegel van de speler als een TileDTO lijst van 2 groot.
     * @throws IllegalArgumentException als de gegeven speler nog geen tegel heeft gekozen
     */
    public TileDTO[] geefGekozenTegel(Kleur speler) {
    	
    	TileDTO[] retList = new TileDTO[2];
    	
    	for (TilePair pair : spel.getStartColumn()) {
			if(pair.getEigenaarKleur() == speler) {
				retList[0] = new TileDTO(pair.getTile1().getAantalKronen(), pair.getTile1().getLandschapType(), pair.getTile1().getTileID(), pair.getTile1().getDir());
				retList[1] = new TileDTO(pair.getTile2().getAantalKronen(), pair.getTile2().getLandschapType(), pair.getTile2().getTileID(), pair.getTile2().getDir());
				return retList;
			}
			
			
		}
    	
    	throw new IllegalArgumentException("Speler heeft geen tile in startkolom");
    }
    /**
     * Geeft de grenzen van het spelraster voor specifieke speler. De grenzen zijn hoogste en laagste x en y.
     * @param speler - kleur van de specifieke speler
     * @return -boundsDTO met hoogste en laagste y-coordinaten van de grid.
     * @throws IllegalArgumentException als de speler geen bounds heeft.
     */
    public boundsDTO geefBounds(Kleur speler) {   	
    	for(spelerSpel s : spel.getSpelers()) {
    		if(s.getKleur() == speler) {
    			spelGrid g = s.getSpelGrid();
    			return new boundsDTO(g.getHighestY(), g.getLowestY(), g.getHighestX(), g.getLowestX());
    		}
    	}
    	
    	throw new IllegalArgumentException("Speler heeft geen bounds (idk what that means either g)");
    }
    /**
     * Geeft het bord van de speler weer van de gegeven kleur. 
     * @param spelerKleur - kleur van de speler
     * @return - lijst van bordgegevens van de speler
     * @throws IllegalArgumentException als de speler niet gevonden wordt.
     */
    public TileDTO[][] geefBord(Kleur spelerKleur){
    	
    	Tile[][] grid = null;
    	
    	for (spelerSpel s : spel.getSpelers()) {
			if(s.getKleur() == spelerKleur) {
				grid = s.getSpelGrid().getGrid();
			}
		}
    	
    	if(grid == null) { throw new IllegalArgumentException(String.format("Grid van speler met kleur %s niet gevonden", spelerKleur.toString()));}
    	
    	TileDTO[][] returnList = new TileDTO[grid.length][grid[0].length];
    	
    	int y = 0;
    	for (Tile[] row : grid) {
    		int x = 0;
    		
			for (Tile tile : row) {
				
				if(tile != null) {
					
					returnList[y][x] = new TileDTO(tile.getAantalKronen(), tile.getLandschapType(), tile.getTileID(), tile.getDir());
				}else {
					returnList[y][x] = null;
				}
				
				x++;
			}
			y++;
		}
    	
    	return returnList;
    }
    
    /**
     * Geeft een leeg bord met dimensies van dims en returned als een list.
     * @param dims - dimensies van het lege bord
     * @return - bord waarbij elke TileDTO een lege tegel is met 0 kronen en geen landschapstype.
     */
    public TileDTO[][] geefBordLeeg(int dims){
    	
    	TileDTO[][] retList = new TileDTO[dims][dims];
    	
    	for (int i = 0; i < retList.length; i++) {
			for (int j = 0; j < retList.length; j++) {
				retList[i][j] = new TileDTO(0, null, 0, 1);
			}
		}
    	
    	return retList;

    }
    /**
     * Kijkt of de naam van een gebruiker al bestaat in de spelerRepository.
     * @param naam - naam die ingegeven is
     * @return - true als speler bestaat met opgegeven naam in de spelerRepository.
     */
    public boolean getGebruiker(String naam) {
    	return spelerRepository.bestaatSpeler(naam);
    }
    /**
     * Kijkt of de gebruikersnaam van een speler al bestaat in de spelerRepository.
     * @param gbNaam - gebruikersnaam die ingegeven is
     * @return - true als de gebruikersnaam al in de spelerRepository bestaat.
     */
    public boolean checkSpelerInDatabase(String gbNaam) {
		return spelerRepository.bestaatSpeler(gbNaam);
	}
}
