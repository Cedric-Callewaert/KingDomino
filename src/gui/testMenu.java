package gui;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class testMenu{
	
	private Stage primaryStage = mainGui.primaryStage;
	private DomeinController dc = mainGui.dc;
	
	public void start(Stage primaryStage, Scene prevScene){

		Button goBack = new Button("back");
		
		goBack.setOnAction(event -> {
			primaryStage.setScene(prevScene);
        });
		
		Pane root = new Pane();
		
		root.setStyle("-fx-background-color: " + GuiToolbox.toRGBCode(Color.LIGHTBLUE) + ";");
		root.getChildren().addAll(goBack);		
		Scene scene = new Scene(root, 1280, 720);
		
		primaryStage.setScene(scene);
	}

}
