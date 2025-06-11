package gui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.TileDTO;
import enums.Kleur;

public class GridMenu {

	private Stage primaryStage = mainGui.primaryStage;
	private DomeinController dc = mainGui.dc;
	private static ResourceBundle bundle;
	private static Scene kiesTegelScene;
	private GuiSpelGrid spelGrid;
	private GridPane grid;
	private int spelerIndex;
	private boolean lastRound;
	private double angle;
	
	private Kleur spelerKleur;
	private Rotate rot = new Rotate();
	private int dir = 1;
	HBox imgBox = new HBox();
	private GuiTile[] imgBoxTiles = new GuiTile[2];
	private Button goBack = new ButtonFX1();
	ImageView backgroundImageView;

	public void start(Kleur spelerKleur, int spelerIndex, boolean lastRound) {
		Image backgroundImage = new Image("file:Afbeeldingen/KingDominoAchtergrondNoText.png");
		backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
		backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
		setLastRound(lastRound);
		bundle = mainGui.bundle;
		setSpelerKleur(spelerKleur);
		this.spelerIndex = spelerIndex;
		Pane root = new Pane();
		Scene scene = new Scene(root, 1280, 720);
		GuiToolbox.setBackground(root, Color.RED);
		GuiGridViewOnly.setMainScene(scene);
		
		VBox navButtons = new VBox();
		navButtons.getChildren().addAll(new GuiNavigationButtons().navButtons());
		navButtons.setPadding(new Insets(20,20,20,20));
		navButtons.setStyle("-fx-spacing: 20;");
		navButtons.setAlignment(Pos.CENTER);
		GuiToolbox.positionElement(navButtons, root, 1.3, 2);
		
		VBox gridVolBox = new VBox();
		gridVolBox.setPadding(new Insets(20,20,20,20));
		gridVolBox.setAlignment(Pos.CENTER);
		GuiToolbox.centerElement(gridVolBox, root);
		GuiToolbox.setBackgroundColor(gridVolBox, 168, 129, 50, 0.7);
		
		kiesTegelEindKolomMenu.setGridScene(scene);
		KiesTegelsMenu.setGridScene(scene);
		
		spelGrid = new GuiSpelGrid(dc.geefBord(spelerKleur), dc.geefBounds(spelerKleur), this, true);
		grid = spelGrid.getGrid();

		updateTileGraphic(dc.geefGekozenTegel(spelerKleur));
		spelGrid.setTargetTile(imgBox);
		
		GuiToolbox.positionElement(imgBox, root, 2, 10);
		
		scene.setOnKeyPressed(e2 -> {
			if(e2.getCode() == KeyCode.R) {
				rot.setAngle((rot.getAngle() - 90) < -360? -90: rot.getAngle() - 90);
				angle = rot.getAngle();
				dir++;
				if(dir >= 5) {dir = 1;}
			}
		});	
		
		GuiToolbox.positionElement(goBack, root, 2, 15);
		GuiToolbox.setHoverEffects(goBack);
		goBack.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #cc1212; -fx-font-weight: bold; -fx-background-radius: 20; -fx-font-size: 20");
		goBack.setText(bundle.getString("goBack"));
		goBack.setOnAction(e -> {
			primaryStage.setScene(kiesTegelScene);
			
		});

		grid.setAlignment(Pos.CENTER);

		GuiToolbox.positionElement(grid, root, 2, 1.8);
		
		Label cantPlacelbl = new Label(bundle.getString("gridVol"));
		
		cantPlacelbl.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 30");
		
		Button cantPlaceBtn = new Button(bundle.getString("volgendeRonde"));
		cantPlaceBtn.setOnAction(e -> nextRound());
		GuiToolbox.styleButton(cantPlaceBtn);
		GuiToolbox.setHoverEffects(cantPlaceBtn);
		gridVolBox.getChildren().addAll(cantPlacelbl, cantPlaceBtn);
		root.getChildren().addAll(backgroundImageView,goBack, navButtons);
		
		if(!dc.canPlaceChoosen(spelerKleur)){
			root.getChildren().add(gridVolBox);
		}else {
			root.getChildren().addAll(grid, imgBox);
		}
		
		primaryStage.setScene(scene);
	}

	private void updateTileGraphic(TileDTO[] tiles) {
		int id1 = tiles[0].id();
		int id2 = tiles[1].id();

		imgBoxTiles[0] = new GuiTile(id1);
		imgBoxTiles[1] = new GuiTile(id2);
		
		imgBox = new HBox(
				imgBoxTiles[0].getImg(),
				imgBoxTiles[1].getImg());
		
		imgBox.getTransforms().add(rot);
		imgBox.setPrefSize(100, 50);
		imgBox.setMouseTransparent(true);
	    Platform.runLater(() -> {
	        rot.setPivotX(imgBox.getWidth() / 4);
	        rot.setPivotY(imgBox.getHeight() / 2);
	    });
	}

	public void setSpelerKleur(Kleur spelerKleur) {
		this.spelerKleur = spelerKleur;
	}

	public void setLastRound(boolean isLast) {
		this.lastRound = isLast;
	}
	
	public static void setBundle(ResourceBundle b) {
		bundle = b;
	}

	public static void setkiesTegelScene(Scene s) {
		kiesTegelScene = s;
	}
	
	public void plaatsTegel(int x, int y) {
		try {
			dc.plaatsGekozenTegel(spelerKleur, x, y, dir);
			nextRound();
		}catch (Exception e) {
			placedWrongEffect();
		}
	}
	
	private void placedWrongEffect() {
	    int stepCount = 40;
	    int totalDurationMillis = 500;
	    int angleDelta = 7;
	    int durationPerStep = totalDurationMillis / stepCount;
	    
	    Media media = new Media(new File("sounds/cantplace2.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        mediaPlayer.setVolume(BackgroundAudioManager.volume * 2);
        mediaPlayer.play();
        

	    
	    rot.setPivotX(imgBox.getWidth() / 2);
	    backgroundImageView.setOpacity(0);
	    
	    Timeline timeline = new Timeline();
	    for (int i = 0; i < stepCount; i++) {
	    	
	        int angleChange = (i % 2 == 0) ? angleDelta : -angleDelta;
	        
	        double nextAngle = angle + angleChange;
	        KeyFrame keyFrame = new KeyFrame(Duration.millis((i + 1) * durationPerStep),
	                new KeyValue(rot.angleProperty(), nextAngle, Interpolator.EASE_BOTH), 
	                new KeyValue(backgroundImageView.opacityProperty(),i<=(stepCount/2)?0.5:1, Interpolator.EASE_BOTH));
	        
	        timeline.getKeyFrames().add(keyFrame);
	    }
	    
	    KeyFrame finalKeyFrame = new KeyFrame(Duration.millis(totalDurationMillis),
	            new KeyValue(rot.angleProperty(), angle, Interpolator.EASE_BOTH),
	            new KeyValue(backgroundImageView.opacityProperty(),1, Interpolator.EASE_BOTH));
	    timeline.getKeyFrames().add(finalKeyFrame);
	    
	    timeline.play();
	    
	    rot.setPivotX(imgBox.getWidth() / 4);
	}
	
	public void nextRound() {
		if(!lastRound) {
			new kiesTegelEindKolomMenu().start(spelerIndex);
		}else {
			if(spelerIndex == dc.geefSpelers().length - 1) {
				new ScoreMenu().start();
			}else {
				new GridMenu().start(dc.geefSpelers()[spelerIndex + 1], spelerIndex + 1, true);;
			}
		}
	}

}
