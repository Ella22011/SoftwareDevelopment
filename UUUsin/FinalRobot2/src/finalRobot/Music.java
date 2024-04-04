package finalRobot;

import java.io.File;
import lejos.hardware.Sound;

public class Music {

    public static void playMusic() {
        Sound.playSample(new File("SpinMeMono_v8.wav"), 50);
    }

    /**
     * Method to play a part of "London Bridge Is Falling Down"
     */
    public static void playLondonBridgeMusic() {
        int[] notes = { 392, 392, 440, 392, 349, 330, 294 };
        int[] durations = { 200, 200, 400, 200, 200, 400, 400 };
        // Play the song
        for (int i = 0; i < notes.length; i++) {
            Sound.playTone(notes[i], durations[i]);
            try {
                Thread.sleep(durations[i] + 50); // Add a small delay between notes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
