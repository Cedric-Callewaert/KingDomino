package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class GuiTile {

	private int id;
	private ImageView img;
	private Rotate rot = new Rotate();
	private int deg = 0;

	private static int TILESIZE = 50;

	public GuiTile(int tileId, int dir) {
		setId(tileId);
		setImg();
		rot.setAngle((dir - 1) * -90);
	}
	
	public GuiTile(int tileId) {
		setId(tileId);
		setImg();
	}

	private void setImg() {
		rot.setAngle(0);
		rot.setPivotX(TILESIZE / 2);
		rot.setPivotY(TILESIZE / 2);
		img = new ImageView(new Image(String.format("file:Afbeeldingen/tegels/%d.png", id))); // 1-96 tiles, 97-100
																								// starttegel, 101-102
																								// wolken
		// img = new Image("file:Afbeeldingen/tegels/101.png"); //1-96 tiles, 97-100
		// starttegel, 101-102 wolken

		img.setFitWidth(TILESIZE);
		img.setFitHeight(TILESIZE);
		img.getTransforms().add(rot);
	}

	private void setId(int id) {
		this.id = id;
	}

	public ImageView getImg() {
		return img;
	}

	public static int getTILESIZE() {
		return TILESIZE;
	}
}
