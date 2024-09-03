package sokoban;
import java.util.Scanner;

public class App {
	/**
	 * Creates a program based on the what view is selected.
	 */
	public App() {
		DefaultController control = new DefaultController();
		GUI gui = new GUI(control);
				
		
		gui.addView("window");
		gui.addView("terminal");
	}
	
	public static void main(String[] args) {
		App app = new App();
	}
}
