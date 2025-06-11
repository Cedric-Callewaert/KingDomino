package enums;

import java.util.ResourceBundle;

public enum landschap {
	AARDE,
	BOS,
	GRAS,
	MIJN,
	WATER,
	ZAND,
	START;
	
	public static ResourceBundle bundle;

	@Override
	public String toString() {
		switch(this) {
		case AARDE: return bundle.getString("land0");
		case BOS: return bundle.getString("land1");
		case GRAS: return bundle.getString("land2");
		case MIJN: return bundle.getString("land3");
		case WATER: return bundle.getString("land4");
		case ZAND: return bundle.getString("land5");
		case START: return bundle.getString("land6");
		default: return super.toString();
		}
		
	}
}
	
