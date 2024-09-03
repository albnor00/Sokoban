package sokoban;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * Creates a view.
 * @author William Thimour, Albin Nordström
 *
 */
public interface View {
    
	/**
     * Adds a controller to the view.
     */
    public void addControl(Controller c);
    
    /**
     * Adds the view to the current context.
     * @param view
     */
    public void addView(String view);
    
    /**
     * Removes the view.
     * @param view
     */
    public void removeView(String view);
	

	/**
	 * Creates a window. 
	 */
    public void window();
    
    /**
	 * Removes the current window. 
	 */
    public void removeWindow();
    
    /**
     * Resets the application to its initial state.
     * Changes the Layout information map.
     */
    public void reset();
    
    /**
     * Repaints the view.
     */
    public void repaint();
    
    /**
     * Sets the current ContentPane.
     * @param c
     */
    public void setContentPane(Container c);
    
	
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
	
	/**
	 * Is called by controller when in terminal view.
	 */
	public void draw();
	
	/**
	 * Stringifies the current context in the format:
	 * 
	 */
	public String toString();
}
