package gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import enums.Kleur;
import enums.landschap;

public class HoofdMenu  {
	private ResourceBundle bundle;
	private static final String BUNDLE_BASE_NAME = "ApplicationResources";
	private Button registreerSpelerButton;
	private Button startSpelButton;
	private Button afsluitenButton;

	private Stage primaryStage = mainGui.primaryStage;

	public HoofdMenu() {
		registreerSpelerButton = new ButtonFX1();
		startSpelButton = new ButtonFX1();
		afsluitenButton = new ButtonFX1();

		onLanguageChange("en");
	}

	public void start() {
		StartGameMenu startGameMenu = new StartGameMenu();
		HoofdMenu hoofdMenu = new HoofdMenu();

		Pane root = new Pane();
		Scene scene = new Scene(root, 1280, 720);
		
		ScoreMenu.setMainScene(scene);
		
		Image luid = new Image("file:Afbeeldingen/luidInvert.png");
		ImageView luidImageView = new ImageView(luid);
		luidImageView.setFitWidth(10);
		luidImageView.setFitHeight(10);
		
		Image stil = new Image("file:Afbeeldingen/stilInvert.png");
		ImageView stilImageView = new ImageView(stil);
		stilImageView.setFitWidth(10);
		stilImageView.setFitHeight(10); 
		
		Slider soundSlider = new Slider(0, 100, 50);
		
		soundSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            BackgroundAudioManager.volume = newValue.doubleValue()/100.0;
            BackgroundAudioManager.updateVolume();
        });
		
		HBox sound = new HBox(5);
		sound.setPadding(new Insets(10));
		sound.getChildren().addAll(stilImageView, soundSlider, luidImageView);
		sound.setAlignment(Pos.CENTER);

		Image backgroundImage = new Image("file:Afbeeldingen/KingDominoMenuAchtergrond.jpg");
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
		backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());

		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll("English", "Français", "Nederlands", "Türkçe");
		choiceBox.setValue("English");
		choiceBox.setStyle("-fx-background-color: #ffffff;" );
	
		
		GuiToolbox.roundedBackground(choiceBox, 20);
		updateUI();
		choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				switch (newValue) {
				case "English":
					changeLanguage("en");
					break;
				case "Français":
					changeLanguage("fr");
					break;
				case "Nederlands":
					changeLanguage("nl");
					break;
				case "Türkçe":
					changeLanguage("tr");
					break;
				}
				updateUI();
			}
		});

		VBox vboxButtons = new VBox();
		vboxButtons.setSpacing(20);
		vboxButtons.setAlignment(Pos.CENTER);

		GuiToolbox.styleButton(registreerSpelerButton);
		GuiToolbox.styleButton(startSpelButton);
		GuiToolbox.styleButton(afsluitenButton);

		GuiToolbox.setHoverEffects(registreerSpelerButton);
		registreerSpelerButton.setOnAction(event -> {
		    new RegistreerSpeler().start(scene);
		});

		GuiToolbox.setHoverEffects(startSpelButton);
		startSpelButton.setOnAction(event -> {
			new StartGameMenu().start(scene);
		});
		
		GuiToolbox.setHoverEffects(afsluitenButton);
		afsluitenButton.setOnAction(event -> Platform.exit());

		vboxButtons.getChildren().addAll(registreerSpelerButton, startSpelButton, choiceBox, afsluitenButton, sound);
		root.getChildren().addAll(backgroundImageView, vboxButtons);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Hoofdmenu");
		primaryStage.show();

		GuiToolbox.centerElement(vboxButtons, root);
	}

	private void updateUI() {

		registreerSpelerButton.setText(bundle.getString("mainMenuChoice1"));
		startSpelButton.setText(bundle.getString("mainMenuChoice2"));
		afsluitenButton.setText(bundle.getString("mainMenuChoice3"));
	}

	public void onLanguageChange(String languageCode) {
		mainGui.bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, new Locale(languageCode));
		bundle = mainGui.bundle;
		Kleur.bundle = mainGui.bundle;
		landschap.bundle = mainGui.bundle;
	}
	
	private void changeLanguage(String languageCode) {
		onLanguageChange(languageCode);

	}
}
