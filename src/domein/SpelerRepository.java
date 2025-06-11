package domein;

import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {

    private final SpelerMapper mapper;
    /**
     * Maakt spelerMapper aan voor de interactie met spelergegevens.
     */
    public SpelerRepository() 
    {
        mapper = new SpelerMapper();
    }
    /**
     * Controle van gebruikersnaam of deze wel of niet in database is.
     * @param gbNaam - opgegeven gebruikersnaam
     * @return of de opgegeven gebruikersnaam wel of niet in de database is
     */
    public boolean spelerInDatabase(String gbNaam) {
    	return mapper.spelerIsInDatabase(gbNaam);
    }
    /**
     * Voegt nieuwe spelers toe of werpt als de speler al bestaat.
     * @param speler
     * @throws GebruikersnaamInGebruikException
     */
    public void voegToe(Speler speler) {
       if (bestaatSpeler(speler.getGebruikersnaam()))
            throw new GebruikersnaamInGebruikException();
       
       mapper.voegToe(speler);
    }
    /**
     * Controleert op speler met opgegeven gebruikersnaam al bestaat in de repository.
     * @param gebruikersnaam
     * @return
     */
    public boolean bestaatSpeler(String gebruikersnaam){
        return mapper.geefSpeler(gebruikersnaam)!=null;
    }  
}
