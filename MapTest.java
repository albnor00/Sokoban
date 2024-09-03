package sokoban;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class MapTest {

	@Test
	void test() {
		File mapFile = new File("app\\gfx\\basicmap.csv");
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
    	Map map = new Map(tiles);
    	int[] a = new int[] {1,3};
    	assertArrayEquals(a,map.getPlayerIndex());
    	map.movePlayer(Map.UP);
    	a = new int[] {1,2};
    	assertArrayEquals(a,map.getPlayerIndex());
    	map.movePlayer(Map.LEFT);
    	a = new int[] {1,2};
    	assertArrayEquals(a,map.getPlayerIndex());
    	assertFalse(map.isBoxesCorrect());
    	map.movePlayer(Map.RIGHT);
    	assertTrue(map.isBoxesCorrect());
    	String s = "w,w,w,w,w,w,w,w\n"
    			+ "w, , , , , , ,w\n"
    			+ "w, ,p,m, , , ,w\n"
    			+ "w, , , , , , ,w\n"
    			+ "w,w,w,w,w,w,w,w\n";
    	assertEquals(map.stringifyTiles(),s);
	}

}
