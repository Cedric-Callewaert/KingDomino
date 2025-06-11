package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import dto.scoreDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScoreMenu {
	private Button mainMenubtn;
	private static Scene mainScene;
	
	private Stage primaryStage = mainGui.primaryStage;
	private DomeinController dc = mainGui.dc;
	private static ResourceBundle bundle;
	private VBox scoreLabels = new VBox();

	public void start() {
		bundle = mainGui.bundle;
		mainMenubtn = new Button(bundle.getString("hoofdMenu"));
		scoreLabels.setAlignment(Pos.CENTER_LEFT);
		GuiToolbox.setHoverEffects(mainMenubtn);		
		mainMenubtn.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #cc1212; -fx-font-weight: bold; -fx-background-radius: 20; -fx-font-size: 31");
		Pane root = new Pane();
		Scene scene = new Scene(root, 1280, 720);
		VBox labelBox = new VBox(scoreLabels);
		Image backgroundImage = new Image("file:Afbeeldingen/KingDominoMenuAchtergrond.jpg");
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
		backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
		scoreLabels.setStyle("-fx-font-size: 25; -fx-font-weight: bold;");
		scoreLabels.setPadding(new Insets(20,20,20,20));
		buildScores();
		
		mainMenubtn.setOnAction(e -> {primaryStage.setScene(mainScene);});
		GuiToolbox.positionElement(mainMenubtn, root, 2, 1.3);
		labelBox.setAlignment(Pos.CENTER);
		GuiToolbox.centerElement(labelBox, root);
		GuiToolbox.setBackgroundColor(labelBox, 255, 255, 255, 0.8);
		root.getChildren().addAll(backgroundImageView , labelBox, mainMenubtn);

		primaryStage.setScene(scene);
		
	}
	
	private void buildScores() {
		scoreLabels.getChildren().clear();
		int index = 1;
		for(scoreDTO s : dc.calculateRankingEndOfGame()) {
			scoreLabels.getChildren().add(new Label(String.format("%d: %s, %s %n"+ bundle.getString("score")+": %d", index, s.kleur().toString(), s.gebruikersnaam(), s.score())));
			index++;
		}
	}
	
	public static void setMainScene(Scene main) {
		mainScene = main;
	}
	
}
