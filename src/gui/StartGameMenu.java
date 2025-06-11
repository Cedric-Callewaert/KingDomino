package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import enums.Kleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartGameMenu{

	private Stage primaryStage = mainGui.primaryStage;
	private DomeinController dc = mainGui.dc;
	private static ResourceBundle bundle = mainGui.bundle;
	private VBox vBox1 = new VBox();
	private VBox vBox2 = new VBox();
	private VBox vBox3 = new VBox();
	private VBox vBox4 = new VBox();

	private final int BUTONSPACING = 100;

	ArrayList<Kleur> kleuren = new ArrayList<>(Arrays.asList(Kleur.values()));
	ArrayList<Kleur> availableKleuren = kleuren;
	private List<String> kleurList = new ArrayList<>();

	ChoiceBox<String> choiceBox1 = new ChoiceBox<>();
	ChoiceBox<String> choiceBox2 = new ChoiceBox<>();
	ChoiceBox<String> choiceBox3 = new ChoiceBox<>();
	ChoiceBox<String> choiceBox4 = new ChoiceBox<>();

	TextField textField1 = new TextField();
	TextField textField2 = new TextField();
	TextField textField3 = new TextField();
	TextField textField4 = new TextField();

	Button goBackbtn = new ButtonFX1();
	Button startGamebtn = new ButtonFX1();

	Label aantalSpelerslbl = new Label();

	private int aantalSpelers = 0;

	private boolean activateListeners = true;
	
	public void start(Scene prevScene) {
		bundle = mainGui.bundle;
		Image backgroundImage = new Image("file:Afbeeldingen/KingDominoMenuAchtergrond.jpg");
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
		backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
		
		goBackbtn.setText(bundle.getString("goBack"));
		startGamebtn.setText(bundle.getString("startGame"));
    
		choiceBox1.setStyle("-fx-background-radius: 11");
		choiceBox2.setStyle("-fx-background-radius: 11");
		choiceBox3.setStyle("-fx-background-radius: 11");
		choiceBox4.setStyle("-fx-background-radius: 11");
		
		Pane root = new Pane();
		Scene scene = new Scene(root, 1280, 720);

		VBox vKleur1 = new VBox();
		VBox vKleur2 = new VBox();
		HBox kleurBox = new HBox();
		
		
		aantalSpelerslbl.setPadding(new Insets(10));
			
		textField1.setPromptText(bundle.getString("usernameSpeler1"));
		textField2.setPromptText(bundle.getString("usernameSpeler2"));
		textField3.setPromptText(bundle.getString("usernameSpeler3"));
		textField4.setPromptText(bundle.getString("usernameSpeler4"));

		textField1.setAlignment(Pos.CENTER);
		textField2.setAlignment(Pos.CENTER);
		textField3.setAlignment(Pos.CENTER);
		textField4.setAlignment(Pos.CENTER);

		aantalSpelerslbl.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 25");


		vBox1.setAlignment(Pos.CENTER);
		vBox1.getChildren().addAll(textField1, choiceBox1);
		vBox1.setSpacing(10);
		vBox2.setAlignment(Pos.CENTER);
		vBox2.getChildren().addAll(textField2, choiceBox2);
		vBox2.setSpacing(10);
		vBox3.setAlignment(Pos.CENTER);
		vBox3.getChildren().addAll(textField3, choiceBox3);
		vBox3.setSpacing(10);
		vBox4.setAlignment(Pos.CENTER);
		vBox4.getChildren().addAll(textField4, choiceBox4);
		vBox4.setSpacing(10);

		aantalSpelerslbl.setText(String.format("%s %d" , bundle.getString("aantalSpelers"), aantalSpelers));

		kleurBox.setSpacing(BUTONSPACING);
		vKleur1.setSpacing(BUTONSPACING);
		vKleur2.setSpacing(BUTONSPACING);

		kleurBox.setPadding(new Insets(10));
		kleurBox.setAlignment(Pos.CENTER);

		changeOptions();

		choiceBox1.getItems().addAll(kleurList);
		choiceBox2.getItems().addAll(kleurList);
		choiceBox3.getItems().addAll(kleurList);
		choiceBox4.getItems().addAll(kleurList);

		choiceBox1.setValue(bundle.getString("choiceBoxNone1"));
		choiceBox2.setValue(bundle.getString("choiceBoxNone1"));
		choiceBox3.setValue(bundle.getString("choiceBoxNone1"));
		choiceBox4.setValue(bundle.getString("choiceBoxNone1"));
		
		goBackbtn.setOnAction(event -> {
			String defaultChoice = bundle.getString("choiceBoxNone1");
		    choiceBox1.setValue(defaultChoice);
		    choiceBox2.setValue(defaultChoice);
		    choiceBox3.setValue(defaultChoice);
		    choiceBox4.setValue(defaultChoice);
		    primaryStage.setScene(prevScene);
		});

		listener(choiceBox1);
		listener(choiceBox2);
		listener(choiceBox3);
		listener(choiceBox4);

		vKleur1.getChildren().addAll(vBox1, vBox3);
		vKleur2.getChildren().addAll(vBox2, vBox4);
		kleurBox.getChildren().addAll(vKleur1, vKleur2);
		GuiToolbox.setBackgroundColor(kleurBox, 128, 113, 22, 0.5);
		//GuiToolbox.roundedBackground(kleurBox, 11);
		
		GuiToolbox.positionElement(startGamebtn, root, 1.64, 1.35);
		GuiToolbox.positionElement(goBackbtn, root, 2.56, 1.35);

		GuiToolbox.centerElement(kleurBox, root);
		GuiToolbox.centerElement(aantalSpelerslbl, root);

		GuiToolbox.styleButton(goBackbtn);
		GuiToolbox.styleButton(startGamebtn);
		GuiToolbox.setHoverEffects(startGamebtn);
		GuiToolbox.setHoverEffects(goBackbtn);
		
		startGamebtn.setOnAction(event -> {
			startGame();
		});

		root.getChildren().addAll(backgroundImageView,kleurBox , goBackbtn, aantalSpelerslbl, startGamebtn);
		
		primaryStage.setScene(scene);

	}

	private void listener(ChoiceBox<String> choiceBox) {
		choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (activateListeners) {
					Kleur selectedKleur = getKleurByTranslation(newValue);
					if (selectedKleur != null) {
						availableKleuren.remove(selectedKleur);

					}

					Kleur oldKleur = getKleurByTranslation(oldValue);
					if (oldKleur != null) {
						availableKleuren.add(oldKleur);
					}
					changeOptions();

					if (newValue == bundle.getString("choiceBoxNone1") && oldValue != null) {
						aantalSpelers--;

					}
					if (newValue != bundle.getString("choiceBoxNone1")
							&& (oldValue == bundle.getString("choiceBoxNone1") || oldValue == null)) {
						aantalSpelers++;
					}

					aantalSpelerslbl.setText(String.format("%s %d", bundle.getString("aantalSpelers"), aantalSpelers));

				}
			}
		});
	}

	private void changeOptions() {
		activateListeners = false;
		List<String> kleurStrings = new ArrayList<>();
		for (Kleur k : availableKleuren) {
			kleurStrings.add(k.toString());
		}
		kleurStrings.add(bundle.getString("choiceBoxNone1"));

		List<String> selectedItems = new ArrayList<String>();

		for (ChoiceBox<String> choiceBox : Arrays.asList(choiceBox1, choiceBox2, choiceBox3, choiceBox4)) {
			selectedItems.add(choiceBox.getValue());
			choiceBox.getItems().clear();
			choiceBox.getItems().addAll(kleurStrings);
		}

		choiceBox1.setValue(selectedItems.get(0));
		choiceBox2.setValue(selectedItems.get(1));
		choiceBox3.setValue(selectedItems.get(2));
		choiceBox4.setValue(selectedItems.get(3));
		activateListeners = true;
	}

	private Kleur getKleurByTranslation(String translatedString) {
		for (Kleur kleur : Kleur.values()) {
			if (kleur.toString().equals(translatedString)) {
				return kleur;
			}
		}
		return null;
	}

	private void startGame() {
		ArrayList<Kleur> spelerKleuren = new ArrayList<>();
		ArrayList<String> spelerNamen = new ArrayList<>();
		
		GuiToolbox.setBackground(textField1, Color.WHITE);
		GuiToolbox.setBackground(textField2, Color.WHITE);
		GuiToolbox.setBackground(textField3, Color.WHITE);
		GuiToolbox.setBackground(textField4, Color.WHITE);
		aantalSpelerslbl.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 25");
		

		
		if (choiceBox1.getValue() != null && !choiceBox1.getValue().equals(bundle.getString("choiceBoxNone1"))) {
			spelerKleuren.add(getKleurByTranslation(choiceBox1.getValue()));
			spelerNamen.add(textField1.getText());
		}
		if (choiceBox2.getValue() != null && !choiceBox2.getValue().equals(bundle.getString("choiceBoxNone1"))) {
			spelerKleuren.add(getKleurByTranslation(choiceBox2.getValue()));
			spelerNamen.add(textField2.getText());
		}
		if (choiceBox3.getValue() != null && !choiceBox3.getValue().equals(bundle.getString("choiceBoxNone1"))) {
			spelerKleuren.add(getKleurByTranslation(choiceBox3.getValue()));
			spelerNamen.add(textField3.getText());
		}
		if (choiceBox4.getValue() != null && !choiceBox4.getValue().equals(bundle.getString("choiceBoxNone1"))) {
			spelerKleuren.add(getKleurByTranslation(choiceBox4.getValue()));
			spelerNamen.add(textField4.getText());
		}
		
		if(!dc.checkSpelerInDatabase(textField1.getText())) {GuiToolbox.setBackgroundColor(textField1, 255,0,0,0.7); return;}
		if(!dc.checkSpelerInDatabase(textField2.getText())) {GuiToolbox.setBackgroundColor(textField2, 255,0,0,0.7); return;}
		if(!dc.checkSpelerInDatabase(textField3.getText())) {GuiToolbox.setBackgroundColor(textField3, 255,0,0,0.7); return;}
		if((!dc.checkSpelerInDatabase(textField4.getText())) && spelerKleuren.size() == 4) {GuiToolbox.setBackgroundColor(textField4, 255,0,0,0.7); return;}
		try {
			dc.startNieuwSpel(spelerKleuren, spelerNamen);
			new KiesTegelsMenu().start();
		}catch (Exception e) {
			aantalSpelerslbl.setStyle("-fx-text-fill: #ff0000; -fx-font-weight: bold; -fx-font-size: 25");
		}
	}

}
