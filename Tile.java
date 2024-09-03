package sokoban;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Creates a tile state with specified file for image.
 * @author William Thimour, Albin Nordström
*/
public class Tile extends JComponent implements Serializable{
	private static final long serialVersionUID = -2491403642757362783L;
	private boolean isPlayerOnTile;
    private boolean isBoxOnTile;
    private boolean isButtonOnTile;
    private boolean isWall;
    private File file;
    transient private BufferedImage image;

    /**
     * Creates a tile with image of the file and boolean values.
     * @param file
     * @param player
     * @param box
     * @param button
     * @param wall
     * @throws IOException
     */
    public Tile(File file, boolean player, boolean box, boolean button, boolean wall) throws IOException{
        image = ImageIO.read(file);
        this.file = file;
        isPlayerOnTile = player;
        isBoxOnTile = box;
        isButtonOnTile = button;
        isWall = wall;
    }

    /**
     * Creates a tile of boolean values.
     * @param player
     * @param box
     * @param button
     * @param wall
     * @throws IOException
     */
    public Tile(boolean player, boolean box, boolean button, boolean wall){
        isPlayerOnTile = player;
        isBoxOnTile = box;
        isButtonOnTile = button;
        isWall = wall;
    }

    /**
     * Returns the state of player on tile.
     * @return true or false
     */
    public boolean isPlayerOnTile(){
        return isPlayerOnTile;
    }

    /**
     * Returns the current state of the box on tile.
     * @return true or false
     */
    public boolean isBoxOnTile(){
        return isBoxOnTile;
    }
    
    /**
     * Returns the current state of the button on tile.
     * @return true or false
     */
    public boolean isButtonOnTile() {
    	return isButtonOnTile;
    }
    
    /**
     * Returns the current state of the wall on the tile.
     * @return true or false 
     */
    public boolean isWall() {
    	return isWall;
    }
    
    /**
     * Removes the box from the tile if it is there.
     * @throws IOException
     */
    public void removeBox() throws IOException {
    	if (isButtonOnTile) {
    		image = ImageIO.read(new File("app\\gfx\\blankmarked.png"));
    	}
    	else {
    		image = ImageIO.read(new File("app\\gfx\\blank.png"));
    	}
    	isBoxOnTile = false;
    }
    
    /**
     * Removes the player from the tile.
     * @throws IOException
     */
    public void removePlayer() throws IOException {
    	if (isButtonOnTile) {
    		image = ImageIO.read(new File("app\\gfx\\blankmarked.png"));
    	}
    	else {
    		image = ImageIO.read(new File("app\\gfx\\blank.png"));
    	}
    	isPlayerOnTile = false;
    }
    
    /**
     * Adds the box on the tile if another box is not on the tile.
     * @throws IOException
     */
    public void addBox() throws IOException {
    	if (isButtonOnTile) {
    		image = ImageIO.read(new File("app\\gfx\\cratemarked.png"));
    	}
    	else {
    		image = ImageIO.read(new File("app\\gfx\\crate.png"));
    	}
    	isBoxOnTile = true;
    }
    
    /**
     * Adds a player to the tile.
     * @throws IOException
     */
    public void addPlayer() throws IOException {
    	image = ImageIO.read(new File("app\\gfx\\player.png"));
    	isPlayerOnTile = true;
    }

    /**
     * Stringifies the tile based on the map layout of p,m,b,u,w and empty.
     * @return String
     */
    public String toStringMap() {
    	if (isPlayerOnTile) {
    		return "p";
    	}
    	else if (isBoxOnTile && isButtonOnTile) {
    		return "m";
    	}
    	else if (isBoxOnTile) {
    		return "b";
    	}
    	else if (isButtonOnTile) {
    		return "u";
    	}
    	else if (isWall) {
    		return "w";
    	}
		return " ";
    }
    
    /**
     * Refactors image to be what is on the Tile
     * @throws IOException
     */
    public void refactor() throws IOException {
    	if (isPlayerOnTile) {
    		addPlayer();
    	}
    	else if (isBoxOnTile){
    		addBox();
    	}
    	else if (isButtonOnTile){
    		image = ImageIO.read(new File("app\\gfx\\blankmarked.png"));
    	}
    	else if (isWall) {
    		image = ImageIO.read(new File("app\\gfx\\wall.png"));
    	}
    	else {
    		image = ImageIO.read(new File("app\\gfx\\blank.png"));
    	}
    }

    /**
     * Paints the current image of the tile.
     */
    @Override
    public void paint(Graphics g){
    	if (image == null) {
    		try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	g.drawImage(image,0,0,this.getWidth(),this.getHeight(),null);
    }
    
    /**
     * Stringifies the tiles variables in a vector format like this (v,v,v).
     * @return output
     */
    @Override
    public String toString() {
    	String output = "";
    	output += "(player=" + isPlayerOnTile + ",box=" + isBoxOnTile + ",button=" + isButtonOnTile + ",Image=" + image + ")";
    	return output;
    }
}