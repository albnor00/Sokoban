package sokoban;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.Timer;


/**
 * Generic controller for handling inputs by the user for the program.
 * @author William Thimour and Albin Nordström
 *
 */
public interface Controller {
	public void addButton(String name, MouseListener mouse);
	
	/**
	 * Creates a drop down menu based on the maps.txt file
	 */
	public void addDropDownList();
	
	/**
	 * Adds a drop down list based of the savedata file
	 */
	public void addDropDownLoadList();
	
	public KeyListener addKey();
	

	/**
	 * Creates a map according to the mapFile variable.
	 * General layout is like this:
	 * w-wall
	 * p-player
	 * u-button
	 * b-box(crate)
	 * ' '-blank
	 * 
	 * This is is saved in a Tile array that is put in the map class which then is added to the JFrame.
	 * Listeners are added and LayoutManager WindowLayout. 
	 */
    public Map window();

    public void terminal();
    
    /**
     * Resets the application to its initial state.
     * Changes the Layout information map.
     */
    public Map reset();
    
    public void addGUI(View view);

	/**
	 * Gets the current map of the GUI
	 * @return map
	 */
	public Map getMap();
	
	/**
	 * Gets the current maps index
	 * @return currentMapIndex
	 */
	public int mapIndex();
	
	public int saveIndex();
	
	public void silenceAudio();
	
	public void addView(String type);
			
	/*public void actionPerformed(ActionEvent e) {

			if (e.getSource() == button) {
				// Perform the desired action when the button is clicked
				System.out.println("Button clicked!");
			}
		}
	}
	*/
}
