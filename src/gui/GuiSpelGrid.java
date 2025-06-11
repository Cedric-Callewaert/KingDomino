package gui;

import dto.TileDTO;
import dto.boundsDTO;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GuiSpelGrid {
	private GuiTile[][] tiles;
	private GridPane grid;
	private boundsDTO bounds;
	private GridMenu menu;
	private TileDTO[][] dtoGrid;
	private HBox targetTile;
	private boolean canEdit;

	public GuiSpelGrid(TileDTO[][] dtoGrid, boundsDTO bounds, GridMenu menu, boolean canEdit) {
		setBounds(bounds);
		setMenu(menu);
		setDtoGrid(dtoGrid);
		setCanEdit(canEdit);
		setTiles(convertDtoGrid());
		buildGrid();
	}
	
	private GuiTile[][] convertDtoGrid(){
		GuiTile[][] retList = new GuiTile[dtoGrid.length][dtoGrid.length];
		for (int y = 0; y < dtoGrid.length; y++) {
			for (int x = 0; x < dtoGrid.length; x++) {
				if(dtoGrid[y][x] != null) {
					retList[y][x] = new GuiTile(dtoGrid[y][x].id(), dtoGrid[y][x].dir());
					
				}else {
					if(y < (bounds.highestY() - 4) || y > (bounds.lowestY() + 4) || x < (bounds.highestX() - 4) || x > (bounds.lowestX() + 4)) {
						retList[y][x] = new GuiTile(102);
					}else {
						retList[y][x] = new GuiTile(101);
					}
				}
			}
		}
		
		return retList;
	}
	
	private void buildGrid() {
		this.grid = new GridPane();
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles.length; x++) {
				int xCord = x;
				int yCord = y;
				Button tilebtn = new ButtonFX2();
				tilebtn.getStyleClass().clear();
				tilebtn.setGraphic(tiles[y][x].getImg());
				if(canEdit) {
				tilebtn.setOnAction(e -> {
					menu.plaatsTegel(xCord, yCord);
				});
				tilebtn.setOnMouseEntered(e -> {previewPlacement(targetTile, xCord, yCord);});
				}
				grid.add(tilebtn, y, x);
				
			}
		}
	}

	
	private void previewPlacement(HBox target, int x, int y) {
		target.layoutXProperty().unbind();
		target.layoutYProperty().unbind();
		
		target.setLayoutX(grid.getLayoutX() + (y * GuiTile.getTILESIZE()));
		target.setLayoutY(grid.getLayoutY() + (x * GuiTile.getTILESIZE()));
	}
	
	
	private void setTiles(GuiTile[][] tiles) {
		this.tiles = tiles;
	}
	
	public GridPane getGrid() {
		return grid;
	}
	
	public void setTargetTile(HBox targetTile) {
		this.targetTile = targetTile;
	}
	
	private void setBounds(boundsDTO bounds) {
		this.bounds = bounds;
	}

	private void setMenu(GridMenu menu) {
		this.menu = menu;
	}
	private void setDtoGrid(TileDTO[][] dtoGrid) {
		this.dtoGrid = dtoGrid;
	}
	private void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
}
