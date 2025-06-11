package domein;

import java.util.Arrays;
import enums.landschap;
import exceptions.TegelNietGeldigException;

public class spelGrid {
	private Tile[][] grid;
	private int startX, startY;
	private int highestY, lowestY, highestX, lowestX;
	private boolean[][] visited;
	
	/**
	 * Maakt rooster van het spel aan met de opgegeven dimensies ySize en xSize
	 * @param xSize - x coordinaat voor dimensie
	 * @param ySize - y coordinaat voor dimensie
	 */
	public spelGrid(int xSize, int ySize) {
		grid = new Tile[ySize][xSize];
	}
	/**
	 * Geeft rooster van tegels terug
	 * @return
	 */
	public Tile[][] getGrid() {
		return grid;
	}
	/**
	 * Controleert of de tegel geplaatst kan worden
	 * @param land1 - landschapsType van de eerste helft van de domino
	 * @param land2 - landschapsType van de tweede helft van de domino
	 * @return true als alle voorwaarden voldoen en de tegel geplaatst kan worden, anders false.
	 */
	public boolean canPlacePair(landschap land1, landschap land2) {

		int[][] dRot = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				for (int i = 0; i < 4; i++) {
					if (checkValidTile(y, x) && checkValidTile(dRot[i][0] + y, dRot[i][1] + x)) {
						if (legalNeighbours(y, x, land1) || legalNeighbours(dRot[i][0] + y, dRot[i][1] + x, land2)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * Plaatst een paar (2 tegels = 1 domino) van tegels op het rooster op meegegeven coordinaten en richting
	 * met controle op volgens spelregels
	 * @param x - x coordinaat voor de tegelpositie
	 * @param y - y coordinaat voor de tegelpositie
	 * @param dir - richting van de tegels
	 * @param tile1 - eerste helft van de domino
	 * @param tile2 - tweede helft van de domino
	 * @throws TegelNietGeldigException - wanneer tegel op ongeldige positie geplaatst wil worden.
	 */
	public void placeTilePair(int x, int y, int dir, Tile tile1, Tile tile2) throws TegelNietGeldigException {

		int[] tailCords = switch (dir) { // dir is de richting waarheen de tile wijst (tile1 is de "root") 1: rechts, 2:
											// boven, 3: links, 4: onder
		case 1 -> new int[] { 1, 0 };
		case 2 -> new int[] { 0, -1 };
		case 3 -> new int[] { -1, 0 };
		case 4 -> new int[] { 0, 1 };
		default -> new int[] { 0, 1 };
		};
		if (checkValidTile(y, x) && checkValidTile(tailCords[0] + y, tailCords[1] + x)) {
			if (legalNeighbours(y, x, tile1.getLandschapType()) || legalNeighbours(tailCords[0] + y, tailCords[1] + x, tile2.getLandschapType())) {
				grid[y][x] = tile1;
				grid[tailCords[0] + y][tailCords[1] + x] = tile2;

				highestY = y > highestY ? y : highestY;
				lowestY = y < lowestY ? y : lowestY;
				highestY = y + tailCords[0] > highestY ? y + tailCords[0] : highestY;
				lowestY = y + tailCords[0] < lowestY ? y + tailCords[0] : lowestY;

				highestX = x > highestX ? x : highestX;
				lowestX = x < lowestX ? x : lowestX;
				highestX = x + tailCords[1] > highestX ? x + tailCords[1] : highestX;
				lowestX = x + tailCords[1] < lowestX ? x + tailCords[1] : lowestX;

				tile1.setDir(dir);
				tile2.setDir(dir);

			} else {
				throw new TegelNietGeldigException();
			}

		} else {
			throw new TegelNietGeldigException();
		}
	}
	/**
	 * Plaatst de starttegel op [5,5] (hardcoded voor midden)
	 * @param x - x-coordinaat met waarde 5
	 * @param y - y-coordinaat met waarde 5
	 * @param tile - tegel met landschapsType starttegel
	 */
	public void placeStartTile(int x, int y, Tile tile) {
		grid[y][x] = tile;
		startX = x;
		startY = y;
		highestY = startY;
		lowestY = startY;
		highestX = startX;
		lowestX = startX;
	}
	/**
	 * Controleert op een tegel geplaatst kan worden op meegegeven coordinaten
	 * @param y - y-coordinaat
	 * @param x - x-coordinaat
	 * @return true als tegel geplaatst kan worden, false als niet kan
	 */
	public boolean checkValidTile(int y, int x) {
		int tmpY = (y > highestY ? y : highestY) - (y < lowestY ? y : lowestY);
		int tmpX = (x > highestX ? x : highestX) - (x < lowestX ? x : lowestX);

		return x >= 0 && x < grid.length && y >= 0 && y < grid[x].length && grid[y][x] == null && tmpY <= 4
				&& tmpX <= 4;
	}
	/**
	 * Controleert op de opgegeven coordinaten binnen/buiten de roosterlimieten valt
	 * @param y - meegegeven y-coordinaat
	 * @param x - meegegeven x-coordinaat
	 * @return true als binnen roosterlijnen, anders false
	 */
	public boolean checkInGrid(int y, int x) {
		return x >= 0 && x < grid.length && (y >= 0) && (y < grid.length) && (grid[y][x] != null);
	}
	/**
	 * Controleert op een tegel compatibele aangrenzende tegels heeft
	 * @param y - meegegeven y-coordinaat
	 * @param x - meegegeven x-coordinaat
	 * @param type - landschapsType van de tegel
	 * @return true als tegelbuur compatibel landschapsType heeft, anders false
	 */
	public boolean legalNeighbours(int y, int x, landschap type) { // returns true if tile has a neighbour of same type
		return ( // or startTile
		(y+1<=10?grid[y + 1][x] != null ? grid[y + 1][x].getLandschapType() == type || grid[y + 1][x].getLandschapType() == landschap.START: false: false) ||
		(x+1<=10?grid[y][x + 1] != null ? grid[y][x + 1].getLandschapType() == type || grid[y][x + 1].getLandschapType() == landschap.START: false: false) || 
		(y-1>=0?grid[y - 1][x] != null ? grid[y - 1][x].getLandschapType() == type || grid[y - 1][x].getLandschapType() == landschap.START : false: false)|| 
		(x-1>=0?grid[y][x - 1] != null ? grid[y][x - 1].getLandschapType() == type || grid[y][x - 1].getLandschapType() == landschap.START : false: false));

	}
	/**
	 * Voert deepest-first-search (dfs) uit om score te bepalen voor een groep van verbonden tegels
	 * @param y - meegegeven y-coordinaat
	 * @param x - meegegeven x-coordinaat
	 * @param targetType
	 * @return lijst met de score en aantalkronen
	 */
	public int[] dfsStep(int y, int x, landschap targetType) {
		int score = 1;
		int tileKroonen = grid[y][x].getAantalKronen();
		visited[y][x] = true;

		int[] dx = { 0, 0, 1, -1 };
		int[] dy = { 1, -1, 0, 0 };

		for (int i = 0; i < 4; i++) {
			if (checkInGrid(y + dy[i], x + dx[i])) {
				if (!visited[y + dy[i]][x + dx[i]] && grid[y + dy[i]][x + dx[i]] != null && grid[y + dy[i]][x + dx[i]].getLandschapType() == targetType) {

					int[] data = dfsStep(y + dy[i], x + dx[i], targetType);
					score += data[0];
					tileKroonen += data[1];
				}
			}
		}

		return new int[] { score, tileKroonen };
	}
	/**
	 * Berekent de totaalscore van een speler zijn grid
	 * @return de totaalscore van alle groepjes 
	 */
	public int calculateScore() {

		int totalScore = 0;
		visited = new boolean[grid.length][grid.length];

		visited[startY][startX] = true;

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				if (!visited[y][x] && grid[y][x] != null) {
					int[] data = dfsStep(y, x, grid[y][x].getLandschapType());
					totalScore += data[0] * data[1];
				}
			}
		}

		return totalScore;
	}
	/**
	 * Geeft hoogste Y-waarde terug.
	 * @return - hoogste y-waarde
	 */
	public int getHighestY() {
		return highestY;
	}
	/**
	 * Geeft laagste Y-waarde terug.
	 * @return - laagste y-waarde
	 */
	public int getLowestY() {
		return lowestY;
	}
	/**
	 * Geeft hoogste Y-waarde terug.
	 * @return - hoogste x-waarde
	 */
	public int getHighestX() {
		return highestX;
	}
	/**
	 * Geeft laagste X-waarde terug.
	 * @return - laagste x-waarde
	 */
	public int getLowestX() {
		return lowestX;
	}

}
