package sokoban;


import java.awt.Container;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileWriter;
import java.util.Scanner;
import javax.sound.sampled.*;

import javax.swing.*;

/**
 * Creates a view for either a terminal or a swing window.
 * @author William Thimour, Albin Nordström
 *
 */
public class GUI extends JFrame implements Serializable, View{
	private static final long serialVersionUID = 8738714308782267800L;
	private Map map;
	private int currentMapIndex;
	private boolean isTerminal;
	private boolean isWindow;
	transient private Controller control;

	/**
	 * Sets the GUIs controller and sets the view booleans to false. 
	 * @param control
	 */
    public GUI(Controller control){
    	isWindow = false;
    	isTerminal = false;
    	control.addGUI(this);
    	this.control = control;
    }
    
    /**
     * Adds a controller to the view.
     */
    public void addControl(Controller c) {
    	control = c;
    }
    
    /**
     * Adds the view to the current context.
     * @param view
     */
    public void addView(String view) {
		if (view.equals("terminal")) {
			control.addView("terminal");
			terminal();
		}
		else if(view.equals("window")) {
			if (isTerminal) {
				setVisible(true);
			}
			else {
				window();
			}
			control.addView("window");
			isWindow = true;
		}
	}
    
    /**
     * Removes the view.
     * @param view
     */
    public void removeView(String view) {
    	if (view.equals("terminal")) {
			removeTerminal();
		}
		else if(view.equals("window")) {
			removeWindow();
		}
    }
	
    /**
     * Repaints the window.
     */
    public void repaint() {
    	super.repaint();
    }
    
    /**
     * Sets the contentPane to Container c.
     * @param c
     */
    public void setContentPane(Container c) {
    	super.setContentPane(c);
    }

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
    public void window(){


    	currentMapIndex = 0;
    	map = control.window();



		setTitle("Sokoban");


    	setSize(800,800);
    	setContentPane(map);
    	addKeyListener(control.addKey());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		map.notifyListenersIndex(1, true);
    }
    
    /**
     * Removes the window.
     */
    public void removeWindow() {
    	setVisible(false);
    	revalidate();
    }

    /**
     * Activates a terminal view of the program.
     */
    public void terminal(){
    	control.terminal();
    	isTerminal = true;
    }
    
    /**
     * Removes the terminal view from the GUI.
     */
    public void removeTerminal() {
    	isTerminal = false;
    }
    
    /**
     * Resets the application to its initial state.
     * Changes the Layout information map.
     */
    public void reset() {
    	map = control.reset();
    	setContentPane(map);
    	revalidate();
    	setSize(map.getPreferredSize());
    	revalidate();
    	repaint();
    }
    
    

	/**
	 * Converts the current GUI to an object.
	 * @return
	 */
	private GUI convertToObject() {
		return this;
	}
	
	/**
	 * Gets the current map of the GUI
	 * @return map
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Gets the current maps index
	 * @return currentMapIndex
	 */
	public int mapIndex() {
		return currentMapIndex;
	}
	
	/**
	 * Overrides the def
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (isTerminal) {
			System.out.println(this);
		}
	}
	
	/**
	 * Draws the current context when the terminal view is true
	 */
	public void draw() {
		if (isTerminal) {
			System.out.println(this);
		}
	}
	
	/**
	 * Stringifies the current context in the format:
	 */
	public String toString() {
		String output = control.toString();
		output += "Window is " + isWindow;
		output += "\nTerminal is " + isTerminal +"\n";
		return output;
	}

}