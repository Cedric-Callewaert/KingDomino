package persistentie;

import domein.Tile;
import domein.TilePair;
import enums.landschap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TileMapper {
	
	private static final String getTiles = "SELECT AantalKronen, Type, idTegels FROM ID429845_g82.Tegels";
	
	private static Connectie ssh;
	
    public List<TilePair> geefTegels() {
    	
    	List<TilePair> retList = new ArrayList<>();
    	
    	try (Connection conn = DriverManager.getConnection(Connectie.MYSQL_JDBC);
    	         PreparedStatement query = conn.prepareStatement(getTiles)) {
    	        try (ResultSet rs = query.executeQuery()) {
    	            for (int i = 0; i < (96 / 2); i++) {
    	            	rs.next();
	                    int kroonen = rs.getInt("AantalKronen");
	                    String type = rs.getString("Type");
	                    int tileId = rs.getInt("idTegels");
	                    Tile t1 = new Tile(landschap.valueOf(type.toUpperCase()), kroonen, tileId);
	                    rs.next();
	                    int kroonen2 = rs.getInt("AantalKronen");
	                    String type2 = rs.getString("Type");
	                    int tileId2 = rs.getInt("idTegels");
	                    Tile t2 = new Tile(landschap.valueOf(type2.toUpperCase()), kroonen2, tileId2);
	                    retList.add(new TilePair(t1, t2));
    	            }
    	        }
    	    } catch (SQLException ex) {
    	        throw new RuntimeException(ex);
    	    }
        
        return retList;
    }
    
   public static void startConnectie() {
	   ssh = new Connectie();
   }
   public static void endConnectie() {
	   ssh.closeConnection(); 
   }
}
