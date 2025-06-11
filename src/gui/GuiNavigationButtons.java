package gui;

import java.util.ArrayList;
import java.util.List;

import domein.DomeinController;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import dto.scoreDTO;
import enums.Kleur;

public class GuiNavigationButtons {
	private DomeinController dc = mainGui.dc;
	private scoreDTO dto;

	private List<Button> Buttons = new ArrayList<>();

	public GuiNavigationButtons() {
		for (Kleur k : dc.geefSpelers()) {
			Kleur targetK = k;
			Button tmp = new ButtonFX1();
			GuiToolbox.styleButton(tmp);
			tmp.setText(k.toString());
			tmp.setOnAction(e -> {
				new GuiGridViewOnly().start(targetK);
			});
			
			Buttons.add(tmp);
		}	
	}
	
	public List<Button> navButtons(){
		return Buttons;
	}
}