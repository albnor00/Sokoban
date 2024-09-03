package sokoban;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import javax.sound.sampled.FloatControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;

/**
 * Creates a listener from a given audio stream
 * @author William Thimour, Albin NordStröm
 *
 */
public class SoundMapListener implements MapListener, Serializable {
	private static final long serialVersionUID = -3492378601121583275L;
	private final Clip clip;
    private boolean onMove;
    private int start;
    private int end;
    private long pos;
    
    /**
     * Stops the clip after 3 seconds.
     */
    Timer t = new Timer(3000,new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			stop();
		}
    	
    });

    /**
     * Creates AudioClip from the audioInputStream with volume and start, end and position pos.
     * @param audioInputStream
     * @param volume
     * @param start
     * @param end
     * @param pos
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public SoundMapListener(AudioInputStream audioInputStream, double volume, int start, int end, long pos) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        clip = AudioSystem.getClip();
        try {
            clip.open(audioInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        onMove = false;
        FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (float) ((range * volume) + gainControl.getMinimum());
        gainControl.setValue(gain);
        this.start = start;
        this.end = end;
        this.pos = pos;
    }

    /**
     * Starts the move sound if not on move.
     */
    @Override
    public void onMove() {
    	if (!onMove) {
	        clip.setMicrosecondPosition(pos);
	        clip.start();
	        t.start();
	        onMove = true;
    	}
    }
    
    /**
     * Starts a continuous loop of the listener.
     */
    public void continuous() {
    	clip.setLoopPoints(start, end);
    	//System.out.println(clip.getFrameLength());
    	clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Turns of the clip.
     */
    public void stop(){
        clip.stop();
        t.stop();
        onMove = false;
    }


}