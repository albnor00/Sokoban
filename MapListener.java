package sokoban;

import java.io.Serializable;

/**
 * Creates a listener for the map.
 * @author William Thimour, Albin Nordström
 *
 */
public interface MapListener {
	/**
	 * Starts the listener.
	 */
    void onMove();

    /**
     * Stops the listener.
     */
    void stop();
    
    /**
     * Makes the listener continuous.
     */
    void continuous();
}