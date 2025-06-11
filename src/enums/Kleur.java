package enums;

import java.util.ResourceBundle;

import javafx.scene.paint.Color;

public enum Kleur {
	GROEN,
	BLAUW,
	ROOD,
	GEEL;
	
	public static ResourceBundle bundle;

	@Override
	public String toString() {
		switch(this) {
		case GROEN: return bundle.getString("colour0");
		case BLAUW: return bundle.getString("colour1");
		case ROOD: return bundle.getString("colour2");
		case GEEL: return bundle.getString("colour3");
		default: return super.toString();
		}
	}
	
	public Color getKleur() {
		switch(this) {
		case GROEN: return Color.GREEN;
		case BLAUW: return Color.BLUE;
		case ROOD: return Color.RED;
		case GEEL: return Color.YELLOW;
		default: return Color.VIOLET;
		}
	}
	
	public int getStarttegelId(){
		switch(this) {
		case GROEN: return 99;
		case BLAUW: return 97;
		case ROOD: return 100;
		case GEEL: return 98;
		default: return 102;
		}
	}
}
