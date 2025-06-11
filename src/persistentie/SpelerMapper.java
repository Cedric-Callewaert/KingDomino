package persistentie;

import domein.Speler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpelerMapper {
	
	private static Connectie ssh;

    private static final String INSERT_SPELER = "INSERT INTO ID429845_g82.Speler (gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld)"
            + "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SPELER = "UPDATE ID429845_g82.Speler " + "SET aantalGewonnen = ?, aantalGespeeld = ? " + "WHERE gebruikersnaam = ?";
            
    public void voegToe(Speler speler) 
    {
    	Connectie ssh = new Connectie();
    	try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
                PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) 
        {
            query.setString(1, speler.getGebruikersnaam());
            query.setInt(2, speler.getGeboortejaar());
            query.setInt(3, speler.getAantalGewonnen());
            query.setInt(4, speler.getAantalGespeeld());
            
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    	ssh.closeConnection();
    }
    
    public boolean spelerIsInDatabase(String gbNaam) {
        Connectie ssh = new Connectie();
        try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID429845_g82.Speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gbNaam);
            try (ResultSet rs = query.executeQuery()) {
                return rs.next();

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Speler geefSpeler(String gebruikersnaam) {
        Speler speler = null;
        Connectie ssh = new Connectie();
        try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID429845_g82.Speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) 
                {
                    int geboortejaar = rs.getInt("geboortejaar");
                    int aantalGewonnen = rs.getInt("aantalGewonnen");
                    int aantalGespeeld = rs.getInt("aantalGespeeld");

                    speler = new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);               
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        ssh.closeConnection();
        return speler;
    }
    
    public void updateSpelerScore(String naam, boolean wonGame) {
    	
    	Speler speler = geefSpeler(naam);
    	
    	Connectie ssh = new Connectie();
    	try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
                PreparedStatement query = conn.prepareStatement(UPDATE_SPELER)) 
        {
    		
    		int nieuweAantalGewonnen = speler.getAantalGewonnen() + (wonGame ? 1 : 0);
            int nieuweAantalGespeeld = speler.getAantalGespeeld() + 1;
    		
            query.setInt(1, nieuweAantalGewonnen);
            query.setInt(2, nieuweAantalGespeeld);
            query.setString(3, speler.getGebruikersnaam());
            
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    	ssh.closeConnection();
    }
    
    public static void startConnectie() {
 	   ssh = new Connectie();
    }
    public static void endConnectie() {
 	   ssh.closeConnection(); 
    }

}
