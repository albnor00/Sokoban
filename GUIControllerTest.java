package sokoban;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GUIControllerTest {

	/**
	 * Tests if the DefaultController and GUI work correctly by using the terminal view. 
	 */
	@Test
	void test() {
		DefaultController control = new DefaultController();
		GUI gui = new GUI(control);
				
		
		gui.addView("window");
		gui.addView("terminal");
		String[] sd = gui.toString().split("\n");
		assertEquals(" app\\gfx\\eightmap.csv",sd[19]);
		assertEquals("Save class value: is initilazed",sd[26]);
		assertEquals("Window is true",sd[27]);
		assertEquals("Terminal is true",sd[28]);
	}

}
