package cui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.Locale;

import domein.DomeinController;

import dto.PairDTO;
import dto.TileDTO;
import dto.scoreDTO;
import enums.Kleur;
import enums.landschap;
import exceptions.GebruikerTeJongException;
import exceptions.GebruikersNaamOngeldigException;
import exceptions.KleurOngeldigException;
import exceptions.SpelerAantalOngeldigException;
import exceptions.StartKolomOngeldigException;

public class SpelApplicatie {

	private DomeinController dc;
	private Scanner input = new Scanner(System.in);
	private ResourceBundle bundle;
		

	public SpelApplicatie() {
		dc = new DomeinController();
	}
	
	
	public void keuzeTaal() {
	    String[] languageChoices = { "English", "Francais", "Nederlands", "Turkce", "Quit" };
	    int choice = 0;
	    
	    do {
	        choice = maakMenuKeuze(languageChoices, "Choose your preferred language.");
	        switch (choice) {
	            case 1:
	                bundle = ResourceBundle.getBundle("ApplicationResources_en", new Locale("en", "EN"));
	                break;
	            case 2:
	                bundle = ResourceBundle.getBundle("ApplicationResources_fr", new Locale("fr", "FR"));
	                break;
	            case 3:
	                bundle = ResourceBundle.getBundle("ApplicationResources_nl", new Locale("nl", "NL"));
	                break;
	            case 4:
	                bundle = ResourceBundle.getBundle("ApplicationResources_tr", new Locale("tr", "TR"));
	                break;
	            case 5:
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	        Kleur.bundle = bundle;
	        landschap.bundle = bundle;

	       
	        if (choice >= 1 && choice <= 4) {
	            ToonHoofdMenu();
	        }
	    } while (choice != 5);
	}

	
	
	public void ToonHoofdMenu() {
			String Choice1 = bundle.getString("mainMenuChoice1");
			String Choice2 = bundle.getString("mainMenuChoice2");
			String Choice3 = bundle.getString("mainMenuChoice3");
			String MainMenuPrompt = bundle.getString("mainMenuPrompt");
			String[] menuKeuzes = { Choice1, Choice2, Choice3 };
			int keuze;
			do {
				keuze = maakMenuKeuze(menuKeuzes, MainMenuPrompt);
				switch (keuze) {
				case 1 -> maakNieuweSpeler();
				case 2 -> startNieuwSpel();
				}
			} while (keuze != 3);
	
	}

	private int maakMenuKeuze(String[] keuzes, String hoofding) {
//		String menuChoice = bundle.getString("mainMenuChoice");
		int gemaakteKeuze;
		do {
			System.out.println(hoofding);
			int index = 1;
			for (String text : keuzes) {
				System.out.printf("%d: %s%n", index, text);
				index++;
			}
		//	System.out.print(menuChoice);
			gemaakteKeuze = input.nextInt();
		} while (gemaakteKeuze > 5 || gemaakteKeuze < 1);
		return gemaakteKeuze;
	}

	private void maakNieuweSpeler() {
		String username = bundle.getString("inputUsername");
		String birthyear = bundle.getString("inputBirthyear");
		String invalidusername = bundle.getString("invalidUsernameMessage");
		String invalidbirthyear = bundle.getString("invalidBirthYearMessage");
		String invalidInput = bundle.getString("invalidInputMessage");
		System.out.print(username);
		String gebruikersNaam = input.next();
		System.out.print("birthyear");
		int geboorteJaar = input.nextInt();
		try {
			dc.registreerSpeler(gebruikersNaam, geboorteJaar);
		} catch (Exception e) {
			if (e.getClass() == GebruikerTeJongException.class) {
				System.err.println(invalidbirthyear);
			}
			if (e.getClass() == GebruikersNaamOngeldigException.class) {
				System.err.println(invalidusername);
			}
			System.err.println(invalidInput);
			maakNieuweSpeler();
		}
	}
	
	private void maakNieuweSpeler(String naam) {
		
		String birthyear = bundle.getString("inputBirthyear");
		String invalidusername = bundle.getString("invalidUsernameMessage");
		String invalidbirthyear = bundle.getString("invalidBirthYearMessage");
		String invalidInput = bundle.getString("invalidInputMessage");
		
		System.out.print(birthyear);
		int geboorteJaar = input.nextInt();
		try {
			dc.registreerSpeler(naam, geboorteJaar);
		} catch (Exception e) {
			if (e.getClass() == GebruikerTeJongException.class) {
				System.err.println(invalidbirthyear);
			}
			if (e.getClass() == GebruikersNaamOngeldigException.class) {
				System.err.println(invalidusername);
			}
			System.err.println(invalidInput);
			maakNieuweSpeler();
		}
	}

	private void startNieuwSpel() {
		String amountplayersPrompt = bundle.getString("amountOfPlayersPrompt");
		String disclaimer = bundle.getString("disclaimerAmountOP");
		String amountplayersInput = bundle.getString("amountOfPlayersInput");
		String invalidInput = bundle.getString("invalidInputMessage");
		String playerColor = bundle.getString("colourOfPlayerPrompt");
		String chooseColor = bundle.getString("chooseNumberOfColour");
		String InvalidchooseColor = bundle.getString("invalidColorChoiceMessage");
		String playerClrScn = bundle.getString("playerClrScn");
		String amountOfRoundsToGo = bundle.getString("amountOfRoundsToGo");
		String gameEndMessage = bundle.getString("gameEndMessage");
		String scoreMessage = bundle.getString("scoreMessage");
		ArrayList<Kleur> kleuren = new ArrayList<>(Arrays.asList(Kleur.values()));
		ArrayList<Kleur> spelerKleuren = new ArrayList<>();
		ArrayList<String> namen = new ArrayList<>();
		int aantal = 0;
		while (true) {
            System.out.print(amountplayersPrompt);
            try {
                aantal = input.nextInt();
                if (aantal == 3 || aantal == 4) {
                    break;
                } else {
                    System.err.println(disclaimer);
                }
            } catch (Exception e) {
                if(e.getClass() == SpelerAantalOngeldigException.class) {
                	System.err.println(invalidInput);
                }
                input.nextLine();
            }
        }
		System.out.println(amountplayersInput + aantal);
		for (int i = 1; i <= aantal; i++) {
			String naam = kiesGebruiker();
			namen.add(naam);
			
			System.out.printf(playerColor, naam);
			for (int j = 0; j < kleuren.size(); j++) {
				System.out.printf("\t%d : %s%n", j, kleuren.get(j).toString());
			}
			int kleurNum = 0;
			while (true) {
				System.out.print(chooseColor);
				try {
					kleurNum = input.nextInt();
					if (kleurNum >= 0 && kleurNum < kleuren.size()) {
						break;
					}else {
	                    System.err.println(InvalidchooseColor);
					}
				}catch (Exception e) {
					if(e.getClass() == KleurOngeldigException.class){
						System.err.println(InvalidchooseColor);
					}
					input.nextLine();
				}
			}
			spelerKleuren.add(kleuren.remove(kleurNum));
			System.out.println("");
		}

		System.out.println("");

		int speler = 0;
		for (Kleur kleur : spelerKleuren) {
			System.out.printf(playerClrScn, speler, kleur.toString());
			speler++;
		}

		System.out.println("");

		dc.startNieuwSpel(spelerKleuren, namen);

		startClaimStartKolom();
		plaatsGekozenTegel();
		startClaimEindKolom();
		dc.startNewRound();
		int rondes = dc.roundsRemaining();
		while (rondes > 1) {
			
			System.out.printf(amountOfRoundsToGo, rondes);
			
			plaatsGekozenTegel();
			startClaimEindKolom();
			dc.startNewRound();
			rondes = dc.roundsRemaining();
		}
		dc.startLaasteRonde();
		
		plaatsGekozenTegel();
		
		System.out.println("--------------------------------");
		System.out.println(gameEndMessage);
		System.out.println("--------------------------------");
		
		scoreDTO[] uitkomst = dc.calculateRankingEndOfGame();
		
		for (int k = 0; k < uitkomst.length; k++) {
			toonSpelerGrid(uitkomst[k].kleur());
			System.out.printf(scoreMessage,uitkomst[k].gebruikersnaam(), uitkomst[k].kleur().toString(), uitkomst[k].score());
		}
	}

private void toonKolomen() {
		String startColumn = bundle.getString("startColumn");
		String endColumn = bundle.getString("endColumn");
		
		PairDTO[] startKolom = dc.geefStartKolom();
		PairDTO[] eindKolom = dc.geefEindKolom();
		Kleur[] spelSpelers = dc.geefSpelers();
		System.out.println(startColumn);
		for (int i = 0; i < spelSpelers.length; i++) {
			System.out.printf("%d\t %s %d %s\t%d %s%n", i + 1, startKolom[i].eigenaar(),
					startKolom[i].tile1().aantalKronen(), startKolom[i].tile1().landschapType().toString(),
					startKolom[i].tile2().aantalKronen(), startKolom[i].tile2().landschapType().toString());
		}

		System.out.println(endColumn);
		for (int i = 0; i < spelSpelers.length; i++) {
			System.out.printf("%d\t %s %d %s\t%d %s%n", i + 1, eindKolom[i].eigenaar(),
					eindKolom[i].tile1().aantalKronen(), eindKolom[i].tile1().landschapType().toString(),
					eindKolom[i].tile2().aantalKronen(), eindKolom[i].tile2().landschapType().toString());
		}

	}

	private void startClaimStartKolom() {
		String chooseTileSC = bundle.getString("chooseTileSC");
		String tileAlreadyChosenMessage = bundle.getString("tileAlreadyChosenMessage");
		String invalidStartTile = bundle.getString("invalidInputMessage");
		
		Kleur[] spelSpelers = dc.geefSpelers();
		Set<Integer> claimedTiles = new HashSet<>();

		for (int i = 0; i < spelSpelers.length; i++) {
			toonKolomen();
			System.out.println("");
			System.out.printf(chooseTileSC, spelSpelers[i]);
			int keuze;
	        boolean validKeuze = false;
			while (!validKeuze) {
	            try {
	                keuze = input.nextInt();
	                if (!claimedTiles.contains(keuze)) {
	                    dc.claimTilePairStart(spelSpelers[i], keuze);
	                    claimedTiles.add(keuze); 
	                    validKeuze = true;
	                } else {
	                    System.err.println(tileAlreadyChosenMessage);
	                }
	            } catch (Exception e) {
	            	if(e.getClass() == StartKolomOngeldigException.class) {
	            		System.err.println(invalidStartTile);
	            	}
	            }
	        }
	    }
	    toonKolomen();

	}

	private void startClaimEindKolom() {
		String chooseTileEC = bundle.getString("chooseTileEC");
		String tileAlreadyChosenMessage = bundle.getString("tileAlreadyChosenMessage");
		String invalidEndTile = bundle.getString("invalidInputMessage");
		
		Kleur[] spelSpelers = dc.geefSpelers();	
		Set<Integer> claimedTiles = new HashSet<>();

		for (int i = 0; i < spelSpelers.length; i++) {
			toonKolomen();
			System.out.println("");
			System.out.printf(chooseTileEC, spelSpelers[i]);
			int keuze;
			boolean validKeuze = false;
			while (!validKeuze) {
				try {
					keuze = input.nextInt();
					if (!claimedTiles.contains(keuze)) {
						dc.claimTilePairNext(spelSpelers[i], keuze);
						claimedTiles.add(keuze);
						validKeuze = true;
					}else {
						System.err.println(tileAlreadyChosenMessage);
					}
				}catch (Exception e) {
					if(e.getClass() == StartKolomOngeldigException.class) {
	            		System.err.println(invalidEndTile);
	            	}
				}
			}
			
		}
		toonKolomen();
	}

	private void plaatsGekozenTegel() {
		
		String chooseXCoord = bundle.getString("rootXCoordPrompt");
		String invalidXCoord = bundle.getString("invalidRootXCoordMessage");
		String chooseYCoord = bundle.getString("rootYCoordPrompt");
		String invalidYCoord = bundle.getString("invalidRootYCoordMessage");
		String chooseTailDirection = bundle.getString("tailDirectionPrompt");
		String invalidTailDirection = bundle.getString("invalidTailDirectionMessage");
		
		Kleur[] spelSpelers = dc.geefSpelers();

		for (int i = 0; i < spelSpelers.length; i++) {
		    TileDTO[] tiles = dc.geefGekozenTegel(spelSpelers[i]);

		    System.out.println("");
		    System.out.printf("Veld van speler %d, %s%n", i, spelSpelers[i]);
		    toonSpelerGrid(spelSpelers[i]);
		    System.out.println("Plaats volgende tegel: ");
		    System.out.printf("\tROOT: %d %s\tTAIL:%d %s%n", tiles[0].aantalKronen(), tiles[0].landschapType().toString(),
		            tiles[1].aantalKronen(), tiles[1].landschapType().toString().toString());

		    int x = 0;
		    int y = 0;
		    int dir = 0;

		    while (true) {
		        try {
		            System.out.printf(chooseXCoord, i, spelSpelers[i]);
		            x = input.nextInt();
		            if (x >= 0 && x <= 10) {
		                break;
		            } else {
		                System.err.println(invalidXCoord);
		            }
		        } catch (Exception e) {
		            System.err.println(invalidXCoord);
		        }
		    }

		    while (true) {
		        try {
		            System.out.printf(chooseYCoord, i, spelSpelers[i]);
		            y = input.nextInt();
		            if (y >= 0 && y <= 10) {
		                break;
		            } else {
		                System.err.println(invalidYCoord);
		            }
		        } catch (Exception e) {
		            System.err.println(invalidYCoord);
		        }
		    }

		    while (true) {
		        try {
		            System.out.printf(chooseTailDirection, i, spelSpelers[i]);
		            System.out.println("\t  2  ");
		            System.out.println("\t3   1");
		            System.out.println("\t  4  ");
		            System.out.print("Keuze: ");
		            dir = input.nextInt();
		            if (dir >= 1 && dir <= 4) {
		                break;
		            } else {
		                System.err.println(invalidTailDirection);
		            }
		        } catch (Exception e) {
		            System.err.println("Ongeldige invoer voor richting.");
		        }
		    }
		    
		    try {
		    dc.plaatsGekozenTegel(spelSpelers[i], x, y, dir);
		    }catch (Exception e) {
				break;
			}
		    System.out.println("Updated grid: ");
		    toonSpelerGrid(spelSpelers[i]);
		}
	}

	private void toonSpelerGrid(Kleur speler) {
		String empty = bundle.getString("empty");
		
		TileDTO[][] grid = dc.geefBord(speler);

		for (TileDTO[] row : grid) {
			for (TileDTO tile : row) {
				if (tile == null) {
					System.out.printf(empty);

				} else {
					System.out.printf("%d %s\t", tile.aantalKronen(), tile.landschapType().toString());
				}
			}
			System.out.println("");
		}
	}
	
	private String kiesGebruiker() {
		String playerNotFound = bundle.getString("playerNotFoundMessage");
		String yesOrNo = bundle.getString("yesornoFail");
		
		System.out.print("Geef je gebruikersnaam: ");
		String naam = input.next();
		if(dc.getGebruiker(naam)) {
			return naam;
		} else {
			while(true) {
				System.out.println(playerNotFound);
				String antw = input.next();
				if(antw.equals("y")) {
					maakNieuweSpeler(naam);
					return naam;
				}else if(antw.equals("n")) {
					return kiesGebruiker();
				}else {
					System.err.println(yesOrNo);
				}		
			}
		}
	}
}