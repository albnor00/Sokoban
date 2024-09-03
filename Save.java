package sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Saves an object to a file or loads an object from a file.
 * @author William Thimour, Albin Nordström
 *
 */
public class Save implements Serializable{
	public Save() {
		
	}
	
	/**
	 * Saves the object in the specified file.
	 * @param o
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void saveObject(Object o, File file) throws IOException, FileNotFoundException {
		if (o instanceof Serializable) {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream output = new ObjectOutputStream(fileOut);
			output.writeObject(o);
			
			output.close();
			fileOut.close();
		}
	}
	
	/**
	 * Gets the object from the serialized file. 
	 * @param file
	 * @return object
	 * @throws Exception
	 */
	public Object getObject(File file) throws Exception {
		FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream input = new ObjectInputStream(fileIn);
        
        Object o = input.readObject();
        
        return o;
	}
	
	/**
	 * Returns string representation of the class.
	 */
	public String toString() {
		return "is initilazed";
	}
}
