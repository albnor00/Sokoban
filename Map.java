package sokoban;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
/**
 * Creates a grid of tiles.
 * @author William Thimour, Albin Nordström
 */
public class Map extends Container implements Serializable{
	private static final long serialVersionUID = 1847194905745771452L;
	private Tile[][] mapTiles;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = 3;
    public static final int DOWN = 4;
	transient private List<MapListener> listeners;

	/**
	 * Adds listener to listeners
	 * @param listener
	 */
	public void addMapListener(MapListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes listener form listeners
	 * @param listener
	 */
	public void removeMapListener(MapListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Creates a map of width and height. Has been usurped by the tiles constructor.
	 * @deprecated
	 * @param width
	 * @param height
	 */
    public Map(int width, int height){
        mapTiles = new Tile[width][height];
        listeners = new ArrayList<>();
    }
    
    /**
     * Refactors listener after saving.
     */
    public void refactorListener() {
    	listeners = new ArrayList<>();
    }
    
    /**
     * Creates a map of size of tiles and initializes listeners.
     * @param tiles
     */
    public Map(Tile[][] tiles) {
    	mapTiles = tiles;
    	for (int j = 0; j < tiles[0].length; j++) {
    		for (int i = 0; i < tiles.length; i++) {
    			add("",tiles[i][j]);
    		}
    	}
    	listeners = new ArrayList<>();
    }
    
    /**
     * Adds the map to the specified layout.
     */
    @Override
    public void setLayout(LayoutManager l) {
    	super.setLayout(l);
    	for (int j = 0; j < mapTiles[0].length; j++) {
    		for (int i = 0; i < mapTiles.length; i++) {
    			l.addLayoutComponent("",mapTiles[i][j]);
    		}
    	}
    }
    
    /**
     * Gets index of player if exists
     * @return int[] or null
     */
    public int[] getPlayerIndex() {
    	for (int i = 0; i < mapTiles.length; i++) {
    		for (int j = 0; j < mapTiles[0].length; j++) {
    			if (mapTiles[i][j].isPlayerOnTile()) {
    				return new int[] {i,j};
    			}
    		}
    	}
    	return null;
    }
    /**
     * Gets index of specified tile if exists
     * @param t
     * @return int[] or null
     */
    public int[] getIndex(Tile t) {
    	for (int i = 0; i < mapTiles.length; i++) {
    		for (int j = 0; j < mapTiles[0].length; j++) {
    			if (mapTiles[i][j].equals(t)) {
    				return new int[] {i,j};
    			}
    		}
    	}
    	return null;
    }

    /**
     * Stops all listeners
     */
   private void stop(){
	   for (MapListener listener : listeners) {
		   listener.stop();
	   }

   }
   
   /**
    * Notifies all listeners.
    */
	private void notifyListeners() {
		for (MapListener listener : listeners) {
			listener.onMove();
		}
	}
	
	/**
	 * Notifies index in listeners with or without continuous command.
	 * @param index
	 * @param continuous
	 */
	public void notifyListenersIndex(int index, boolean continuous) {
		if (continuous) {
			listeners.get(index).continuous();
		}
		else {
			listeners.get(index).onMove();
		}
	}
	
	/**
	 * Moves player to new tile. Calls movePlayer
	 * @param t
	 */
	public void movePlayerToTile(Tile t) {
		int[] pos = getIndex(t);
		if (mapTiles[pos[0]][pos[1]+1].isPlayerOnTile()) {
			movePlayer(Map.UP);
		}
		else if (mapTiles[pos[0]][pos[1]-1].isPlayerOnTile()) {
			movePlayer(Map.DOWN);
		}
		else if (mapTiles[pos[0]+1][pos[1]].isPlayerOnTile()) {
			movePlayer(Map.RIGHT);
		}
		else if(mapTiles[pos[0]-1][pos[1]].isPlayerOnTile()) {
			movePlayer(Map.LEFT);
		}
	}
    
	/**
	 * Moves player according to movement. If next tile is not a wall or two boxes next to each other then player moves to next tile.
	 * @param movement
	 */
    public void movePlayer(int movement) {


    	int[] playerPos = getPlayerIndex();


    	if (movement == 1) {
    		Tile currentTile = mapTiles[playerPos[0]][playerPos[1]];
    		Tile nextTile = mapTiles[playerPos[0]][playerPos[1]-1];
    		if (mapTiles[playerPos[0]][playerPos[1]-1].isWall()) {
    			return;
    		}
    		if (nextTile.isBoxOnTile()) {
    			boolean ans = moveBox(nextTile,movement);
    			if (!ans) {
    				return;
    			}
    		}
    		else {
    			if (listeners.size() > 0) {
    				notifyListenersIndex(0, false);
    			}
    		}

    		try {
    			currentTile.removePlayer();
				nextTile.addPlayer();
				//notifyListenersIndex(0, false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if (movement == 2) {
    		Tile currentTile = mapTiles[playerPos[0]][playerPos[1]];
    		Tile nextTile = mapTiles[playerPos[0]+1][playerPos[1]];
    		if (nextTile.isWall()) {
    			return;
    		}
    		if (nextTile.isBoxOnTile()) {
    			boolean ans = moveBox(nextTile,movement);
    			if (!ans) {
    				return;
    			}

    		}
    		else {
    			if (listeners.size() > 0) {
    				notifyListenersIndex(0, false);
    			}
    		}
    		try {
    			currentTile.removePlayer();
				//notifyListenersIndex(0, false);
				nextTile.addPlayer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if (movement == 3) {
    		Tile currentTile = mapTiles[playerPos[0]][playerPos[1]];
    		Tile nextTile = mapTiles[playerPos[0]-1][playerPos[1]];
    		if (nextTile.isWall()) {
    			return;
    		}
    		if (nextTile.isBoxOnTile()) {
    			boolean ans = moveBox(nextTile,movement);
    			if (!ans) {
    				return;
    			}
    		}
    		else {
    			if (listeners.size() > 0) {
    				notifyListenersIndex(0, false);
    			}
    		}
    		try {
    			currentTile.removePlayer();
				nextTile.addPlayer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if (movement == 4) {
    		Tile currentTile = mapTiles[playerPos[0]][playerPos[1]];
    		Tile nextTile = mapTiles[playerPos[0]][playerPos[1]+1];
    		if (nextTile.isWall()) {
    			return;
    		}
    		if (nextTile.isBoxOnTile()) {
    			boolean ans = moveBox(nextTile,movement);
    			if (!ans) {
    				return;
    			}
    		}
    		else {
    			if (listeners.size() > 0) {
    				notifyListenersIndex(0, false);
    			}
    		}
    		try {
    			currentTile.removePlayer();
				nextTile.addPlayer();
				//notifyListenersIndex(0, false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}


    }

    /**
     * Moves box according to the movement. If the position is wall or box then it will return false and not move it.
     * @param box
     * @param movement
     * @return true or false
     */
	private boolean moveBox(Tile box,int movement) {

		int[] boxPos = getIndex(box);
		if (movement == 1) {
			Tile currentTile = mapTiles[boxPos[0]][boxPos[1]];
    		Tile nextTile = mapTiles[boxPos[0]][boxPos[1]-1];

    		if (nextTile.isWall()) {
    			return false;
    		}
    		if (nextTile.isBoxOnTile()) {
    			return false;
    		}
    		try {
    			currentTile.removeBox();
				nextTile.addBox();
				if (listeners.size() > 0) {
    				notifyListenersIndex(2, false);
    			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (movement == 2) {
			Tile currentTile = mapTiles[boxPos[0]][boxPos[1]];
    		Tile nextTile = mapTiles[boxPos[0]+1][boxPos[1]];
    		if (nextTile.isWall()) {
    			return false;
    		}
    		if (nextTile.isBoxOnTile()) {
    			return false;
    		}
    		try {
    			currentTile.removeBox();
				nextTile.addBox();
				if (listeners.size() > 0) {
    				notifyListenersIndex(2, false);
    			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (movement == 3) {
			Tile currentTile = mapTiles[boxPos[0]][boxPos[1]];
    		Tile nextTile = mapTiles[boxPos[0]-1][boxPos[1]];
    		if (nextTile.isWall()) {
    			return false;
    		}
    		if (nextTile.isBoxOnTile()) {
    			return false;
    		}
    		try {
    			currentTile.removeBox();
				nextTile.addBox();
				if (listeners.size() > 0) {
    				notifyListenersIndex(2, false);
    			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (movement == 4) {
			Tile currentTile = mapTiles[boxPos[0]][boxPos[1]];
    		Tile nextTile = mapTiles[boxPos[0]][boxPos[1]+1];
    		if (nextTile.isWall()) {
    			return false;
    		}
    		if (nextTile.isBoxOnTile()) {
    			return false;
    		}
    		try {
    			currentTile.removeBox();
				nextTile.addBox();
				if (listeners.size() > 0) {
    				notifyListenersIndex(2, false);
    			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * Checks if all boxes are on the correct position.
	 */
	public boolean isBoxesCorrect() {
		boolean ans = false;
		for(Tile[] tA : mapTiles) {
			for (Tile t : tA) {
				if (t.isBoxOnTile() && t.isButtonOnTile()) {
					ans = true;
				}
				else if (t.isBoxOnTile()) {
					return false;
				}
			}
		}
		return ans;
	}
	
	/**
	 * Removes all menu elements from the container Map
	 */
	public void removeAllMenuItems() {
		for (Component c : this.getComponents()) {
			if (c instanceof JButton || c instanceof JComboBox) {
				remove(c);
			}
		}
	}
	
	/**
	 * Fixes image issues of Tiles in Map because BufferedImage can't be saved.
	 */
	public void refactorTiles() {
		for (int i = 0; i < mapTiles.length; i++) {
    		for (int j = 0; j < mapTiles[0].length; j++) {
    			try {
					mapTiles[i][j].refactor();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
	}
	
	/**
	 * Sets output to the stringified version of the map according to how the are displayed in the level files.
	 * @return output
	 */
	public String stringifyTiles() {
		String output = "";
		for (int i = 0; i < mapTiles[0].length; i++) {
    		for (int g = 0; g < mapTiles.length; g++) {
    			output += mapTiles[g][i].toStringMap();
    			if (g < mapTiles.length - 1) {
    				output += ",";
    			}
    		}
    		output += "\n";
    	}
		return output;
	}
	
	/**
	 * Creates a message on the view if it is instance of GUI.
	 * @param gui
	 * @param text
	 */
	public void message(View gui, String text) {
		if (gui instanceof GUI) {
			JOptionPane.showMessageDialog((GUI)gui, text);
		}
	}
	
	/**
	 * Stringifies the current context of the map.
	 */
	public String toString() {
		String output = "";
		output += "Map:\n";
		output += " 	Tile double array:\n";
		for (Tile[] tiles : mapTiles) {
			output += " 		[";
			for (Tile tile : tiles) {
				output += tile + ",";
			}
			output += "]\n";
		}
		return output;
	}
}
