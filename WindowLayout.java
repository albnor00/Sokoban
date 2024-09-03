package sokoban;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.JPanel;
/**
 * Creates a grid of components on a JFrame. Also puts up to 16 components on top of the grid.
 * @author William Thimour, Albin Nordström
 *
 */
public class WindowLayout implements LayoutManager, Serializable {
	private static final long serialVersionUID = -3343367119461879821L;
	private Component[][] components;
	private Component[] gridLessComponents;
	private Container pane;
	private int height;
	private int width;
	private int pos;
	private int row;
	private int posG;
	private int topWidthMargin;
	private int topHeightMargin;
	private int topObjectWidth;
	private int topObjectHeight;
	private int bottomWidthMargin;
	private int bottomHeightMargin;
	private int marginWidth;
	private int marginHeight;
	private int objectHeight;
	private int objectWidth;
	private int marginCenterWidth;
	private int marginCenterHeight;
	public final String ROWONE = "rowone";
	public final String ROWTWO = "rowtwo";
	public final String ROWTHREE = "rowthree";
	public final String ROWFOUR = "rowfour";
	public static final String BUTTON = "button";

	/**
	 * Defines a x by y layout for the tiles components based of the size given.
	 *
	 * @param height
	 * @param width
	 * @param tileHeight
	 * @param tileWidth
	 */
	public WindowLayout(int height, int width, int tileHeight, int tileWidth) {
		int ratio;
		int margin= 0;
		if (tileHeight > tileWidth) {
			ratio = tileHeight;
			margin = 1;
		}
		else {
			ratio = tileWidth;
		}
		this.height = height;
		this.width = width;
		int centerHeight = height / 2;
		int centerWidth = width / 2;
		components = new Component[tileHeight][tileWidth];
		gridLessComponents = new Component[16];
		pos = 0;
		marginWidth = (int) Math.round(width * 0.05);
		marginHeight = (int) Math.round(height * 0.05);
		topWidthMargin = (int) Math.round(width * 0.02);
		topHeightMargin = (int) Math.round(height * 0.02);
		topObjectWidth = (int) Math.round(width * 0.16);
		topObjectHeight = (int) Math.round(height * 0.06);
		objectWidth = (int) Math.round(width * (0.9 /ratio));
		objectHeight = (int) Math.round(height * (0.9 /ratio));
		
		int totalCenterWidth = objectWidth * ratio;
		marginWidth = (width - totalCenterWidth) / 2;
		int totalCenterHeight = objectHeight * ratio;
		marginHeight = (height + topObjectHeight + topHeightMargin *2 - totalCenterHeight) / 2;
	}

	/**
	 * Defines a x by y layout for the tiles components based of the size given.
	 *
	 * @param height
	 * @param width
	 */
	public void setSize(int height, int width) {
		this.height = height;
		this.width = width;
		marginWidth = (int) Math.round(width * 0.05);
		marginHeight = (int) Math.round(height * 0.05);
		topWidthMargin = (int) Math.round(width * 0.02);
		topHeightMargin = (int) Math.round(height * 0.02);
		topObjectWidth = (int) Math.round(width * 0.16);
		topObjectHeight = (int) Math.round(height * 0.06);
		//marginCenterHeight = (int)Math.round(height*0.95*0.1);
		//marginCenterWidth = (int)Math.round(width*0.95*0.05);
		int totalCenterWidth = objectWidth * 4 + marginCenterWidth * 3;
		marginWidth = (width - totalCenterWidth) / 2;
		int totalCenterHeight = objectHeight * 4 + marginCenterHeight * 3;
		marginHeight = (height - totalCenterHeight) / 2;
	}

	/**
	 * Sets the components bounds to the specified position given by the layout.
	 *
	 * @param name
	 * @param comp
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		if (name == "button") {
			comp.setBounds(topWidthMargin + topWidthMargin*posG + topObjectWidth*posG, topHeightMargin, topObjectWidth, topObjectHeight); // Set the position and size of the button
			gridLessComponents[posG] = comp;
			posG++;
		}
		else {
			comp.setBounds(marginWidth + objectWidth * (pos) + marginCenterWidth * (pos), marginHeight + objectHeight * row + marginCenterHeight * row, objectWidth, objectHeight);
			components[pos][row] = comp;
			if (pos > components.length - 2) {
				row++;
				pos = 0;
			} else {
				pos++;
			}
		}
	}


	/**
	 * Removes the component form the layout and sets the position in the area for a new component to that location.
	 * If the array is empty it will set the position to (0,0).
	 *
	 * @param comp
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		if (isEmpty()) {
			pos = 0;
			row = 0;
			return;
		}
		for (int i = 0; i < components.length; i++) {
			int b = 0;
			for (int j = 0; j < components[0].length; j++) {
				if (components[i][j] == comp) {
					components[i][j] = null;
					pos = i;
					row = j;
					b = 1;
					break;
				}
			}
			if (b == 1) {
				break;
			}
		}
		if (isEmptyButton()) {
			posG = 0;
		}
		for (int i = 0; i < gridLessComponents.length; i++) {
			if (gridLessComponents[i] == comp) {
				gridLessComponents[i] = null;
				posG--;
			}
		}
	}

	/**
	 * Returns true if components has null on every index.
	 * @return true or false
	 */
	private boolean isEmpty() {
		for (int i = 0; i < components.length; i++) {
			for (int j = 0; j < components.length; j++) {
				if (components[i][j] != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns true if button is null
	 * @return true or false
	 */
	private boolean isEmptyButton() {
		for (int i = 0; i < gridLessComponents.length; i++) {
			if (gridLessComponents[i] != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the index of the given component in the list if it exists otherwise it will return null
	 *
	 * @param comp
	 * @return int[] or null
	 */
	public int[] getIndexOf(Component comp) {
		for (int i = 0; i < components.length; i++) {
			for (int j = 0; j < components[i].length; j++) {
				if (components[i][j] == comp) {
					return new int[]{i, j};
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the index of the component of the buttons on the top of the JFrame.
	 * @param comp
	 * @return i or -1
	 */
	public int getIndexOfGridLess(Component comp) {
		for (int i = 0; i < components.length; i++) {
			if (gridLessComponents[i] == comp) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the preferred size of the map of tiles. 
	 * @return Dimension
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(marginWidth*2+objectWidth*components.length, marginHeight*2+objectHeight*components[0].length);
	}

	/**
	 * Sets the default size to 500 by 500
	 *
	 * @param parent
	 * @return A new Dimension of 500 by 500
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		layoutContainer(parent);
		return new Dimension(500, 500);
	}

	
	/**
	 * Layouts the containers items according to how the should be displayed.
	 * It checks if it is a tile or not.
	 */
	@Override
	public void layoutContainer(Container parent) {
		for (Component c : parent.getComponents()) {
			if (c instanceof Tile) {
				int[] p = getIndexOf(c);
				if (p != null) {
					c.setBounds(marginWidth + objectWidth * p[0] + marginCenterWidth * p[0], marginHeight + objectHeight * p[1] + marginCenterHeight * p[1], objectWidth, objectHeight);
				}
			}
			else {
				int pG = getIndexOfGridLess(c);
				if (pG != -1) {
					c.setBounds(topWidthMargin + topWidthMargin*pG + topObjectWidth*pG, topHeightMargin, topObjectWidth, topObjectHeight);
				}
			}
		}
	}
}