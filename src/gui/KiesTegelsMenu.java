package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import dto.PairDTO;
import enums.Kleur;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class KiesTegelsMenu {

	private static ResourceBundle bundle = mainGui.bundle;
	private Stage primaryStage = mainGui.primaryStage;
	private DomeinController dc = mainGui.dc;
	
	Circle currSpelerckl = new Circle(20);
	Button goGridBtn = new ButtonFX1();
	
	private Kleur currSpeler;
	private int kiesKleurIndex = 0;
	private int plaatsKleurIndex = 0;

	List<Button> startButtons = new ArrayList<Button>();
	List<HBox> eindBoxes = new ArrayList<HBox>();
	private static Scene currGridScene;

	public void start() {
		bundle = mainGui.bundle;
		Pane root = new Pane();
		Scene scene = new Scene(root, 1280, 720);
		GridMenu.setkiesTegelScene(scene);
		
		currSpelerckl.setFill(dc.geefSpelers()[kiesKleurIndex].getKleur());
		
		for (int i = 0; i < dc.geefSpelers().length; i++) {
			startButtons.add(new ButtonFX1());
			eindBoxes.add(new HBox());
		}
		
		Image backgroundImage = new Image("file:Afbeeldingen/KingDominoAchtergrondNoText.png");
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
		backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
		
		GuiToolbox.positionElement(goGridBtn, root, 2, 4);
		
		for (int i = 0; i < startButtons.size(); i++) {
		    int index = i;
		    Button button = startButtons.get(i);
		    button.getStyleClass().clear();
		    button.setPadding(new Insets(10));
		    button.setOnAction(e -> {
		        dc.claimTilePairStart(dc.geefSpelers()[kiesKleurIndex], index + 1);
		        GuiToolbox.setBackground(button, dc.geefSpelers()[kiesKleurIndex].getKleur());
		        kiesKleurIndex++;
		        if(kiesKleurIndex < startButtons.size()) {
		        	currSpelerckl.setFill(dc.geefSpelers()[kiesKleurIndex].getKleur());
		        }else {
		        	currSpelerckl.setFill(Color.GRAY);
		        	GuiToolbox.setBackground(goGridBtn, dc.geefSpelers()[0].getKleur());
		        	GuiToolbox.setHoverEffects(goGridBtn);
		    		goGridBtn.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #cc1212; -fx-font-weight: bold; -fx-background-radius: 20; -fx-font-size: 25");
		        	root.getChildren().add(goGridBtn);
		        }
		        button.setOnAction(e2 -> {});
		    });
		}
		
		goGridBtn.setText(bundle.getString("goGrid"));
		goGridBtn.setOnAction(e -> {
			dc.sortByOrder();
			new GridMenu().start(dc.geefSpelers()[0], 0, false);
			goGridBtn.setOnAction(e2 -> primaryStage.setScene(currGridScene));
		});

		VBox vBox1 = new VBox(startButtons.toArray(new Button[0]));
		VBox vBox2 = new VBox(eindBoxes.toArray(new HBox[0]));
		
		HBox hBox = new HBox(vBox1, vBox2);
		
		GuiToolbox.positionElement(currSpelerckl, root, 2 ,1.5);

		hBox.setPadding(new Insets(20));

		GuiToolbox.setBackground(hBox, Color.AZURE);
		GuiToolbox.centerElement(hBox, root);

		root.getChildren().addAll(backgroundImageView,hBox,currSpelerckl);
		updateStartCollumn(dc.geefStartKolom());
		updateEindCollumn(dc.geefEindKolom());
		
		primaryStage.setScene(scene);

	}

	private void updateStartCollumn(PairDTO[] tiles) {
		int index = 0;
		for (Button button : startButtons) {
			
			int id1 = tiles[index].tile1().id();
			int id2 = tiles[index].tile2().id();
			
			HBox imgBox = new HBox(new ImageView(new Image(String.format("file:Afbeeldingen/tegels/%d.png", id1), 50, 50, false, false)),
								   new ImageView(new Image(String.format("file:Afbeeldingen/tegels/%d.png", id2), 50, 50, false, false)));
			
			button.setGraphic(imgBox);
			index++;
		}

	}
	private void updateEindCollumn(PairDTO[] tiles) {
		int index = 0;
		for (HBox box : eindBoxes) {
			
			int id1 = tiles[index].tile1().id();
			int id2 = tiles[index].tile2().id();
			
			box.getChildren().clear();
			box.getChildren().addAll(new ImageView(new Image(String.format("file:Afbeeldingen/tegels/%d.png", id1), 50, 50, false, false)),
						   			 new ImageView(new Image(String.format("file:Afbeeldingen/tegels/%d.png", id2), 50, 50, false, false)));
			box.setPadding(new Insets(10));
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
