package sokoban;

import java.awt.Graphics;
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
import java.io.Serializable;
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
 * It is the default controller implementation. It handles basic user input on the JFrame and terminal.
 * @author William Thimour and Albin Nordström
 *
 */
public class DefaultController implements Controller, Serializable {
	
	private static final long serialVersionUID = 8738714308782267800L;
	private Map map;
	private JButton button;
	private File[] mapFiles;
	private File[] saveFiles;
	private JComboBox<String> list;
	private JComboBox<String> savelist;
	private int currentMapIndex;
	private int currentSaveIndex;
	private Save saver;
	private boolean isTerminal;
	private boolean isWindow;
	private View gui;

	/**
	 * Adds the GUI class to the current context and sets the current view state.
	 */
	public void addGUI(View view) {
		this.gui =view;
		isWindow = false;
		isTerminal = false;
	}
	
	/**
	 * Adds a view state to the controller
	 */
	public void addView(String type) {
		if(type.equals("terminal")) {
			isTerminal = true;
		}
		else if(type.equals("window")) {
			isWindow = true;
		}
	}
	
	/**
	 * Silences the audio
	 */
	public void silenceAudio() {
		map.refactorListener();
	}
	
    /**
     * Adds button to top of JFrame
     */
	public void addButton(String name, MouseListener mouse) {
		button = new JButton(name);
		map.add(WindowLayout.BUTTON,button);
		button.addMouseListener(mouse);
		button.setFocusable(false);
	}
	
	/**
	 * Creates a drop down menu based on the maps.txt file
	 */
	public void addDropDownList() {
		File mapInfo = new File("app\\levelinfo\\maps.txt");
		Scanner scan = null;
    	try {
			scan = new Scanner(mapInfo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String line = scan.nextLine();
    	String[] names = new String[Integer.parseInt(line)];
    	File[] files = new File[Integer.parseInt(line)];
    	int i = 0;
    	while(scan.hasNextLine()) {
    		line = scan.nextLine();
    		names[i] = line.split("-")[0];
    		files[i] = new File("app\\gfx\\" + line.split("-")[1]);
    		i++;
    	}
    	list = new JComboBox<String>(names);
    	list.addItemListener(comboAction);
    	if (currentMapIndex > 0) {
    		list.setSelectedIndex(currentMapIndex);
    	}
    	list.setFocusable(false);
    	map.add(WindowLayout.BUTTON,list);
    	mapFiles = files;
    	//System.out.println(files[0]);
	}
	
	/**
	 * Adds three SoundMapListeners to the map.
	 */
	public void addAudio() {
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("app\\sound\\Walking.wav"));
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		MapListener soundListener = null;
		try {
			soundListener = new SoundMapListener(audioInputStream, 0.9, 0, 0, 3000000);
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (LineUnavailableException e) {
			throw new RuntimeException(e);
		}
		map.addMapListener(soundListener);
		
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("app\\sound\\Main-track.wav"));
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			soundListener = new SoundMapListener(audioInputStream, 0.8, 0, 2500000, 0);
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (LineUnavailableException e) {
			throw new RuntimeException(e);
		}
		map.addMapListener(soundListener);
		
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("app\\sound\\Box.wav"));
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			soundListener = new SoundMapListener(audioInputStream, 0.88, 0, 2500000, 0);
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (LineUnavailableException e) {
			throw new RuntimeException(e);
		}
		map.addMapListener(soundListener);
	}
	
	/**
	 * Adds a drop down list based of the savedata file
	 */
	public void addDropDownLoadList() {
		File saveInfo = new File("app\\save games\\savedata.txt");
		if (saveInfo.exists() == false) {
			try {
				saveInfo.createNewFile();
				FileWriter writer = new FileWriter(saveInfo);
				writer.write("1\n");
				writer.write("default-default");
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Scanner scan = null;
    	try {
			scan = new Scanner(saveInfo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String line = scan.nextLine();
    	String[] names = new String[Integer.parseInt(line)];
    	File[] files = new File[Integer.parseInt(line)];
    	int i = 0;
    	while(scan.hasNextLine()) {
    		line = scan.nextLine();
    		names[i] = line.split("-")[0];
    		files[i] = new File("app\\save games\\" + line.split("-")[1]);
    		i++;
    	}
    	savelist = new JComboBox<String>(names);
    	savelist.addItemListener(comboActionLoad);
    	if (currentSaveIndex > 0) {
    		savelist.setSelectedIndex(currentSaveIndex);
    	}
    	savelist.setFocusable(false);
    	map.add(WindowLayout.BUTTON,savelist);
    	saveFiles = files;
    	//System.out.println(files[0]);
	}
	
	private void readInTiles(File mapFile) {
		Scanner scan = null;
    	try {
			scan = new Scanner(mapFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String line = scan.nextLine();
    	Tile[][] tiles = new Tile[Integer.parseInt(line.split(",")[0])][Integer.parseInt(line.split(",")[1])];
    	int j = 0;
    	while(scan.hasNextLine()) {
    		line = scan.nextLine();
    		String[] chars = line.split(",");
    		for (int i = 0; i < chars.length; i++) {
    			if (chars[i].equals("p")) {
    				try {
						tiles[i][j] = new Tile(new File("app\\gfx\\player.png"),true,false,false,false);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			else if (chars[i].equals("w")) {
    				try {
						tiles[i][j] = new Tile(new File("app\\gfx\\wall.png"),false,false,false,true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			else if (chars[i].equals("u")) {
    				try {
						tiles[i][j] = new Tile(new File("app\\gfx\\blankmarked.png"),false,false,true,false);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			else if (chars[i].equals("b")) {
    				try {
						tiles[i][j] = new Tile(new File("app\\gfx\\crate.png"),false,true,false,false);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			else {
    				try {
						tiles[i][j] = new Tile(new File("app\\gfx\\blank.png"),false,false,false,false);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}
    		j++;
    	}
    	map = new Map(tiles);
    	map.setLayout(new WindowLayout(800,800,tiles.length,tiles[0].length));
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
	 * 
	 * Returns this map to the view handler.
	 * @return Map
	 */
    public Map window(){
    	File mapFile = new File("app\\gfx\\basicmap.csv");
    	readInTiles(mapFile);


    	currentMapIndex = 0;
    	currentSaveIndex = 0;
    	saver = new Save();


		addAudio();


    	addButton("Reset", mouse);
    	addDropDownList();
    	addButton("Save", buttonSave);
    	addDropDownLoadList();
    	addButton("Load", buttonLoad);
		map.notifyListenersIndex(1, true);
		return map;
    }

    /**
     * Creates the terminal context and starts the game loop.
     */
    public void terminal(){
    
    	isTerminal = true;
    	if (!isWindow) {
    		File mapFile = new File("app\\gfx\\basicmap.csv");

        	readInTiles(mapFile);
        	currentMapIndex = 0;
        	currentSaveIndex = 0;
        	saver = new Save();
        	
        	addButton("Reset", mouse);
        	addDropDownList();
        	addButton("Save", buttonSave);
        	addDropDownLoadList();
        	addButton("Load", buttonLoad);
    		t.start();
    	}
    }
    
    /**
     * Resets the application to its initial state.
     * Changes the Layout information map.
     */
    public Map reset() {
    	File mapFile = mapFiles[currentMapIndex];
    	readInTiles(mapFile);
    	
    	if (isWindow) {
    		addAudio();
    	}
    	
    	
    	map.addMouseListener(tileMouse);
    	//addKeyListener(key);
    	addButton("Reset", mouse);
    	addDropDownList();
    	addButton("Save", buttonSave);
    	addDropDownLoadList();
    	addButton("Load", buttonLoad);
    	return map;
    }
    
    
    /**
     * Is called when load is pressed for refactoring of map;
     * @param m
     */
    private void resetMap(Map m) {
    	File mapFile = mapFiles[currentMapIndex];
    	map = m;
    	map.refactorListener();
    	addAudio();
    	m.removeAllMenuItems();
    	m.refactorTiles();
    	//addKeyListener(key);
    	addButton("Reset", mouse);
    	addDropDownList();
    	addButton("Save", buttonSave);
    	addDropDownLoadList();
    	addButton("Load", buttonLoad);
    	gui.setContentPane(map);
    	gui.repaint();
    }
    
    /**
     * Adds key to the view.
     */
    public KeyListener addKey() {
    	return key;
    }
    
    /**
     * Simple controller for the keyboard. 
     * It looks for arrow inputs and if the map has been cleared.
     */
    transient KeyListener key = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				map.movePlayer(Map.UP);
				gui.repaint();
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				map.movePlayer(Map.RIGHT);
				gui.repaint();
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				map.movePlayer(Map.LEFT);
				gui.repaint();
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				map.movePlayer(Map.DOWN);
				gui.repaint();
			}
			else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				gui.reset();
			}
			if (map.isBoxesCorrect()) {
				if (currentMapIndex == 9) {
					map.message(gui, "You're Winner");
				}
				else if (currentMapIndex < list.getItemCount() - 1) {
					map.message(gui, "Level complete");
					currentMapIndex++;
				}
				gui.reset();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

    };


    /**
     * Resets the context
     */
    transient MouseListener mouse = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			//System.out.println("Button clicked!");
			gui.reset();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	/**
	 * Gets the selected item of the level drop down list.
	 */
	transient ItemListener comboAction = new ItemListener() {

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox<String> cd = (JComboBox<String>)e.getSource();
			if (currentMapIndex == cd.getSelectedIndex()) {
				return;
			}
			currentMapIndex = cd.getSelectedIndex();
			//System.out.println(currentMapIndex);
		}
		
	}; 
	
	/**
	 * Gets the selected item of the save drop down list.
	 */
	transient ItemListener comboActionLoad = new ItemListener() {

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox<String> cd = (JComboBox<String>)e.getSource();
			if (currentSaveIndex == cd.getSelectedIndex()) {
				return;
			}
			currentSaveIndex = cd.getSelectedIndex();
		}
		
	}; 
	
	/**
	 * Gets the mouse click on the tile.
	 */
	transient MouseListener tileMouse = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getComponent() instanceof Tile) {
				map.movePlayerToTile((Tile)e.getComponent());
				gui.repaint();
			}
			if (map.isBoxesCorrect()) {
				if (currentMapIndex < list.getItemCount() - 1) {
					currentMapIndex++;
				}
				reset();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	/**
	 * Converts GUI to object.
	 * @return
	 */
	private View convertToObject() {
		return gui;
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
	 * Gets saveindex
	 * @deprecated
	 * @return currentSaveIndex
	 */
	public int saveIndex() {
		return currentSaveIndex;
	}
	
	/**
	 * Refactors the GUI after a save has been loaded
	 * @param o
	 */
	private void refactorGUI(GUI o) {
		currentMapIndex = o.mapIndex();
		gui.addControl(this);
		resetMap(o.getMap());
	}
	
	/**
	 * Saves the current context when save button is pressed.
	 * Calls save.
	 */
	transient MouseListener buttonSave = new MouseListener(){

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			try {
				int index = (saveFiles.length);
				String filename = "save" + (saveFiles.length) + ".ser";
				File newSave;
				if (savelist.getSelectedItem().equals("default")) {
					newSave = new File("app\\save games\\save0.ser");
				}
				else {
					newSave = new File("app\\save games\\save" + (saveFiles.length) + ".ser");
				}
				newSave.createNewFile();
				View o = gui;
				saver.saveObject(o, newSave);
				File savedata = new File("app\\save games\\savedata.txt");
				Scanner scan = new Scanner(savedata);
				String input = "";
				scan.nextLine();
				while (scan.hasNextLine()) {
					input+=scan.nextLine()+ "\n";
				}
				FileWriter writer = new FileWriter(savedata);
				if (savelist.getSelectedItem().equals("default")) {
					writer.write(String.valueOf(index) + "\n");
					writer.write("Save Data0-save0.ser");
				}
				else {
					writer.write(String.valueOf(index+1) + "\n");
					writer.write(input);
					writer.write("Save Data" + index + "-" + filename);
				}
				writer.close();
				gui.reset();
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};

	/**
	 * Gets current save selected when load button is pressed.
	 * Calls save.
	 */
	transient MouseListener buttonLoad = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			try {
				GUI o = (GUI)saver.getObject(saveFiles[currentSaveIndex]);
				refactorGUI(o);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	/**
	 * Triggers every millisecond. Checks if input has occurred in terminal.
	 */
	transient Timer t = new Timer(1,new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Scanner scan = new Scanner(System.in);
			boolean exit = true;
			switch(scan.nextLine().toLowerCase()) {
				case "up": map.movePlayer(Map.UP); break;
				case "down": map.movePlayer(Map.DOWN); break;
				case "right": map.movePlayer(Map.RIGHT); break;
				case "left": map.movePlayer(Map.LEFT); break;
			}
			if (map.isBoxesCorrect()) {
				if (currentMapIndex < list.getItemCount() - 1) {
					currentMapIndex++;
				}
				reset();
			}
			gui.draw();
			//String ans = "";
		}
		
	});
	
	/**
	 * Converts the current context to string.
	 */
	public String toString() {
		String output = "";
		output += map + "\n";
		output += "Current Map Files:\n";
		for (File file : mapFiles) {
			output += " " + file.toString() + "\n";
		}
		output += "Current map selected: " + currentMapIndex + "\n";
		output += "Current Save Files:\n";
		for (File file : saveFiles) {
			output += "	" + file.toString() + "\n";
		}
		output += "Current save selected: " + currentSaveIndex + "\n";
		output += "Save class value: " + saver + "\n";
		if (!isWindow) {
			output += map.stringifyTiles();
		}
		return output;
	}

}
