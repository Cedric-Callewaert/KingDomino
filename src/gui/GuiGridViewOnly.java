package gui;

import java.io.File;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.TileDTO;
import enums.Kleur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class GuiGridViewOnly {
	private Stage primaryStage = mainGui.primaryStage;
	private DomeinController dc = mainGui.dc;
	private static ResourceBundle bundle;
	private static Scene mainScene;
	private GuiSpelGrid spelGrid;
	private GridPane grid;

	private Kleur spelerKleur;

	private GuiTile[] imgBoxTiles = new GuiTile[2];
	private Button goBack = new ButtonFX1();
	ImageView backgroundImageView;

	public void start(Kleur spelerKleur) {
		
		
		Image backgroundImage = new Image("file:Afbeeldingen/KingDominoAchtergrondNoText.png");
		backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
		backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
		bundle = mainGui.bundle;
		setSpelerKleur(spelerKleur);
		Pane root = new Pane();
		Scene scene = new Scene(root, 1280, 720);
		GuiToolbox.setBackground(root, Color.RED);
		
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
		
		spelGrid = new GuiSpelGrid(dc.geefBord(spelerKleur), dc.geefBounds(spelerKleur), null, false);
		grid = spelGrid.getGrid();

		
		GuiToolbox.positionElement(goBack, root, 2, 15);
		GuiToolbox.setHoverEffects(goBack);
		goBack.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #cc1212; -fx-font-weight: bold; -fx-background-radius: 20; -fx-font-size: 20");
		goBack.setText(bundle.getString("goBack"));
		goBack.setOnAction(e -> {
			primaryStage.setScene(mainScene);	
		});

		grid.setAlignment(Pos.CENTER);
		GuiToolbox.positionElement(grid, root, 2, 1.8);
		root.getChildren().addAll(backgroundImageView,goBack,grid, navButtons);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ViewOnly");
	}


	public void setSpelerKleur(Kleur spelerKleur) {
		this.spelerKleur = spelerKleur;
	}

	public static void setBundle(ResourceBundle b) {
		bundle = b;
	}

	public static void setMainScene(Scene s) {
		mainScene = s;
	}

}
