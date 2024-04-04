package finalRobot;

import java.io.File;
import lejos.hardware.Sound;

public class Music {

    public static void playMusic() {
        Sound.playSample(new File("SpinMeMono_v8.wav"), 50);
    }
}