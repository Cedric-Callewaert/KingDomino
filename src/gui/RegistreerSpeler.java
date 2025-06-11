package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.GebruikerTeJongException;
import exceptions.GebruikersNaamOngeldigException;

public class RegistreerSpeler {

	private Label lblUsername = new Label();
	private Label lblUsernameError = new Label();
	private TextField txtUsername = new TextField();
	private DatePicker kiesGeboorte = new DatePicker();
	private Label lblGeboorte = new Label();
	private Label lblGeboorteError = new Label();
	
	private Button registreerSpeler = new ButtonFX1();
	private Button terugNaarHoofdMenu =  new ButtonFX1();
	private static ResourceBundle bundle = mainGui.bundle;
	private Stage primaryStage = mainGui.primaryStage;
	private DomeinController dc = mainGui.dc; 
	
    public void start(Scene prevScene) {
    	
		bundle = mainGui.bundle;
		lblGeboorte.setText(bundle.getString("geboorteDatum") + ":");
		lblUsername.setText(bundle.getString("username") + ":");
        Image backgroundImage = new Image("file:Afbeeldingen/KingDominoMenuAchtergrond.jpg");

        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
        
        lblUsername.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        lblUsernameError.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        lblGeboorte.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        lblGeboorteError.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        
        
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);
        
        GuiToolbox.setBackgroundColor(grid, 168, 129, 50, 0.7);
        
        GridPane.setConstraints(lblUsername, 0, 0);
        GridPane.setConstraints(txtUsername, 1, 0);
        GridPane.setConstraints(lblUsernameError, 2, 0);

        GridPane.setConstraints(lblGeboorte, 0, 1);
        kiesGeboorte.setValue(LocalDate.now());
        GridPane.setConstraints(kiesGeboorte, 1, 1);
        GridPane.setConstraints(lblGeboorteError, 2, 1);

        registreerSpeler.setText(bundle.getString("registreer"));
        registreerSpeler.setStyle("-fx-background-radius: 20");
        GridPane.setConstraints(registreerSpeler, 1, 2);
        registreerSpeler.setOnAction(e -> {
            String username = txtUsername.getText();
            int geboorte = kiesGeboorte.getValue().getYear();
            try {
            	dc.registreerSpeler(username, geboorte);
            	if(prevScene != null) {
            		primaryStage.setScene(prevScene);
            	}
            }catch (Exception ex) {
    			if (ex.getClass() == GebruikerTeJongException.class) {
    				lblGeboorte.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
    				lblUsername.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
    				lblGeboorteError.setText(bundle.getString("invalidBirthYearMessage"));
    				lblUsernameError.setText("");
    				
    			}
    			if (ex.getClass() == GebruikersNaamOngeldigException.class) {
    				lblUsername.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
    				lblGeboorte.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
    				lblUsernameError.setText(bundle.getString("invalidUsernameMessage"));
    				lblGeboorteError.setText("");
    			}
    		}
        });
        
        terugNaarHoofdMenu.setText(bundle.getString("hoofdMenu"));
        terugNaarHoofdMenu.setStyle("-fx-background-radius: 20");
        GridPane.setConstraints(terugNaarHoofdMenu, 0, 2);
        terugNaarHoofdMenu.setOnAction(e -> {
        	primaryStage.setScene(prevScene);
        });
		
        Pane root =  new Pane();
        
        grid.getChildren().addAll(lblUsername, txtUsername, lblGeboorte, kiesGeboorte, lblUsernameError, lblGeboorteError, registreerSpeler, terugNaarHoofdMenu);
        root.getChildren().addAll(backgroundImageView, grid);

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Background Image Example");
        primaryStage.show();
        
        GuiToolbox.setHoverEffects(registreerSpeler);
		GuiToolbox.setHoverEffects(terugNaarHoofdMenu);
		terugNaarHoofdMenu.setOnAction(event -> {
			primaryStage.setScene(prevScene);
		});
		
        GuiToolbox.centerElement(grid, root);
    }
    
    public static void setBundle(ResourceBundle b) {
    	bundle = b;
    }
}
