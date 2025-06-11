package gui;


import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GuiToolbox {
   
	public static String toRGBCode(Color color) {
		return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
	}
	
	public static void setBackground(Region object, Color color) {
		object.setStyle("-fx-background-color: " + GuiToolbox.toRGBCode(color) + ";");
	}
	
	public static void setBackgroundColor(Region object, int r, int g, int b, double a) {
		object.setStyle(String.format("-fx-background-color: rgba(%d, %d, %d, %s);", r, g, b, a));
	}


	public static void centerElement(Region object, Region root) {
		object.layoutXProperty().bind(root.widthProperty().subtract(object.widthProperty()).divide(2));
		object.layoutYProperty().bind(root.heightProperty().subtract(object.heightProperty()).divide(2));
	}

	public static void positionElement(Region object, Region root, double factorX, double factorY) {
		object.layoutXProperty().bind(root.widthProperty().subtract(object.widthProperty()).divide(factorX));
		object.layoutYProperty().bind(root.heightProperty().subtract(object.heightProperty()).divide(factorY));
	}
	
	public static void moveElementRelative(Region object, Region root, double relativeX, double relativeY) {
	    object.layoutXProperty().unbind();
	    object.layoutYProperty().unbind();
		
	    double targetX = root.getWidth() * relativeX - object.getWidth() / 2;
	    double targetY = root.getHeight() * relativeY - object.getHeight() / 2;
	    
	    object.setLayoutX(targetX);
	    object.setLayoutY(targetY);
	}

	
	public static void positionElement2(Region object, Region root, double factorX, double factorY) {
	    object.layoutXProperty().bind(root.layoutXProperty().add(root.widthProperty().subtract(object.widthProperty()).divide(factorX)));
	    object.layoutYProperty().bind(root.layoutYProperty().add(root.heightProperty().subtract(object.heightProperty()).divide(factorY)));
	}

	
    public static void positionElement(Circle object, Region root, double factorX, double factorY) {
        object.centerXProperty().bind(root.widthProperty().divide(factorX));
        object.centerYProperty().bind(root.heightProperty().divide(factorY));
    }
	
	public static void styleButton(Region button) {
		button.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #cc1212; -fx-font-weight: bold; -fx-background-radius: 20; -fx-font-size: 15");
	}
	public static void roundedBackground(Region object, int roundness) {
		object.setStyle(String.format("-fx-background-radius: %d", roundness));
	}

	public static void setHoverEffects(Region button) {
		button.setOnMouseEntered(event -> {button.setScaleX(1.2); button.setScaleY(1.2);});
		button.setOnMouseExited(event -> {button.setScaleX(1.0); button.setScaleY(1.0);});
	}
}
