package sokoban;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class TileTest {

	/**
	 * Creates a player tile and checks if the variables are changed to correct state.
	 */
	@Test
	void playerTest() {
		try {
			Tile t = new Tile(new File("app\\gfx\\player.png"),true,false,false,false);
			assertFalse(t.isBoxOnTile());
			assertFalse(t.isButtonOnTile());
			assertFalse(t.isWall());
			assertTrue(t.isPlayerOnTile());
			assertEquals(t.toStringMap(),"p");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a wall tile and checks if the variables are changed to correct state.
	 */
	@Test
	void wallTest() {
		try {
			Tile t = new Tile(new File("app\\gfx\\wall.png"),false,false,false,true);
			assertFalse(t.isBoxOnTile());
			assertFalse(t.isButtonOnTile());
			assertTrue(t.isWall());
			assertFalse(t.isPlayerOnTile());
			assertEquals(t.toStringMap(),"w");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void markedTest() {
		try {
			Tile t = new Tile(new File("app\\gfx\\blankmarked.png"),false,false,true,false);
			assertFalse(t.isBoxOnTile());
			assertTrue(t.isButtonOnTile());
			assertFalse(t.isWall());
			assertFalse(t.isPlayerOnTile());
			assertEquals(t.toStringMap(),"u");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a box tile and checks if the variables are changed to correct state.
	 */
	@Test
	void boxTest() {
		try {
			Tile t = new Tile(new File("app\\gfx\\crate.png"),false,true,false,false);
			assertTrue(t.isBoxOnTile());
			assertFalse(t.isButtonOnTile());
			assertFalse(t.isWall());
			assertFalse(t.isPlayerOnTile());
			assertEquals(t.toStringMap(),"b");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a marked box tile and checks if the variables are changed to correct state.
	 */
	@Test
	void markedboxTest() {
		try {
			Tile t = new Tile(new File("app\\gfx\\cratemarked.png"),false,true,true,false);
			assertTrue(t.isBoxOnTile());
			assertTrue(t.isButtonOnTile());
			assertFalse(t.isWall());
			assertFalse(t.isPlayerOnTile());
			assertEquals(t.toStringMap(),"m");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a blank tile and checks if the variables are changed to correct state.
	 */
	@Test
	void empty() {
		try {
			Tile t = new Tile(new File("app\\gfx\\blank.png"),false,false,false,false);
			assertFalse(t.isBoxOnTile());
			assertFalse(t.isButtonOnTile());
			assertFalse(t.isWall());
			assertFalse(t.isPlayerOnTile());
			assertEquals(t.toStringMap()," ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
