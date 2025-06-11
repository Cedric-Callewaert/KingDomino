package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.PairDTO;
import enums.Kleur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class kiesTegelEindKolomMenu {
	private static ResourceBundle bundle = mainGui.bundle;
	private Stage primaryStage = mainGui.primaryStage;
	private DomeinController dc = mainGui.dc;
	private static int roundsRemaining;
	private static Scene currGridScene;
	
	Circle currSpelerckl = new Circle(20);
	Button goGridBtn = new ButtonFX1();
	
	private List<Kleur> pastSpelers = new ArrayList<Kleur>();
	
	List<Button> eindButtons = new ArrayList<Button>();
	List<HBox> startBoxes = new ArrayList<HBox>();

	public void start(int currPlayerIndex) {
		bundle = mainGui.bundle;
		Pane root = new Pane();
		Scene scene = new Scene(root, 1280, 720);
		GridMenu.setkiesTegelScene(scene);
		GuiGridViewOnly.setMainScene(scene);

		currSpelerckl.setFill(dc.geefSpelers()[currPlayerIndex].getKleur());
		
		VBox navButtons = new VBox();
		navButtons.getChildren().addAll(new GuiNavigationButtons().navButtons());
		navButtons.setPadding(new Insets(20,20,20,20));
		navButtons.setStyle("-fx-spacing: 20;");
		navButtons.setAlignment(Pos.CENTER);
		GuiToolbox.positionElement(navButtons, root, 1.3, 2);
		
		for (int i = 0; i < dc.geefSpelers().length; i++) {
			eindButtons.add(new ButtonFX1());
			startBoxes.add(new HBox());
			if(i <= currPlayerIndex) {
				pastSpelers.add(dc.geefSpelers()[i]);
			}
		}
		
		Image backgroundImage = new Image("file:Afbeeldingen/KingDominoAchtergrondNoText.png");
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
		backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
		
		GuiToolbox.positionElement(goGridBtn, root, 2, 4);
		
		for (int i = 0; i < eindButtons.size(); i++) {
		    int index = i;
		    Button button = eindButtons.get(i);
		    button.getStyleClass().clear();
		    button.setPadding(new Insets(10));
		    button.setOnAction(e -> {
		    	try {
		        dc.claimTilePairNext(dc.geefSpelers()[currPlayerIndex], index + 1);
		        GuiToolbox.setBackground(button, dc.geefSpelers()[currPlayerIndex].getKleur());

	        	currSpelerckl.setFill(Color.GRAY);
	        	GuiToolbox.setBackground(goGridBtn, dc.geefSpelers()[0].getKleur());
	        	GuiToolbox.setHoverEffects(goGridBtn);
	    		goGridBtn.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #cc1212; -fx-font-weight: bold; -fx-background-radius: 20; -fx-font-size: 25");
	        	root.getChildren().add(goGridBtn);
		        for (Button btn : eindButtons) {
					btn.setOnAction(e2 -> {});
				}}catch (Exception excep) {} // it makes sens i swear
		    });
		}
		
//		if(dc.roundsRemaining() <= 4) { play epic dark souls banger if only 4 rounds left
//			BackgroundAudioManager.startEpic();
//		}
		
		goGridBtn.setText(bundle.getString("goGrid"));
		goGridBtn.setOnAction(e -> {
			if(currPlayerIndex + 1 >= dc.geefSpelers().length) {
				dc.sortByOrder();
				if(dc.roundsRemaining() == 0){
					dc.startLaasteRonde();
					new GridMenu().start(dc.geefSpelers()[0], 0, true);
				}else {
					dc.startNewRound();
					new GridMenu().start(dc.geefSpelers()[0], 0, false);
				}
			}else {
				new GridMenu().start(dc.geefSpelers()[currPlayerIndex + 1], currPlayerIndex + 1, false);
			}
			goGridBtn.setOnAction(e2 -> primaryStage.setScene(currGridScene));
		});

		VBox vBox1 = new VBox(startBoxes.toArray(new HBox[0]));
		VBox vBox2 = new VBox(eindButtons.toArray(new Button[0]));
		
		HBox hBox = new HBox(vBox1, vBox2);
		
		GuiToolbox.positionElement(currSpelerckl, root, 2 ,1.5);

		hBox.setPadding(new Insets(20));

		GuiToolbox.setBackground(hBox, Color.AZURE);
		GuiToolbox.centerElement(hBox, root);

		root.getChildren().addAll(backgroundImageView,hBox,currSpelerckl, navButtons);
		updateStartCollumn(dc.geefStartKolom());
		updateEindCollumn(dc.geefEindKolom());
		
		primaryStage.setScene(scene);

	}

	private void updateEindCollumn(PairDTO[] tiles) {
		int index = 0;
		for (Button button : eindButtons) {
			
			int id1 = tiles[index].tile1().id();
			int id2 = tiles[index].tile2().id();
			Kleur eigenaar = tiles[index].eigenaar();
			
			HBox imgBox = new HBox(new ImageView(new Image(String.format("file:Afbeeldingen/tegels/%d.png", id1), 50, 50, false, false)),
								   new ImageView(new Image(String.format("file:Afbeeldingen/tegels/%d.png", id2), 50, 50, false, false)));
			
			button.setGraphic(imgBox);
			if(eigenaar != null) {
				GuiToolbox.setBackground(button, eigenaar.getKleur());
			}
			index++;
		}
		
	}
	private void updateStartCollumn(PairDTO[] tiles) {
		int index = 0;
		for (HBox box : startBoxes) {
			
			int id1 = tiles[index].tile1().id();
			int id2 = tiles[index].tile2().id();
			Kleur eigenaar = tiles[index].eigenaar();
			
			box.getChildren().clear();
			box.getChildren().addAll(new ImageView(new Image(String.format("file:Afbeeldingen/tegels/%d.png", id1), 50, 50, false, false)),
						   			 new ImageView(new Image(String.format("file:Afbeeldingen/tegels/%d.png", id2), 50, 50, false, false)));
			box.setPadding(new Insets(10));
			if(eigenaar != null) {
				GuiToolbox.setBackground(box, eigenaar.getKleur());
				for (Kleur k : pastSpelers) {
					if(eigenaar == k) {
						box.setOpacity(0.5);
					}
				}
			}
			index++;
		}
		
	}

	public static void setBundle(ResourceBundle b) {
		bundle = b;
	}
	
	public static void setGridScene(Scene s) {
		currGridScene = s;
	}
}
