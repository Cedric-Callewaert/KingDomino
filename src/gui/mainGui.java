package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class mainGui extends Application {

	public static Stage primaryStage;
	public static DomeinController dc;
	public static ResourceBundle bundle;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.dc = new DomeinController();
		this.primaryStage = primaryStage;
		BackgroundAudioManager.primStage = primaryStage;
		BackgroundAudioManager.startCalm();
		new HoofdMenu().start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
