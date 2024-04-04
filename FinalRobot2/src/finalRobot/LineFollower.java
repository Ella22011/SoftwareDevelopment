package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

/**
 * The LineFollower class implements the behavior of a line-following robot.
 */
public class LineFollower implements Runnable {

    private DataExchange DEObj;
    private UnregulatedMotor motorA;
    private UnregulatedMotor motorB;
    private boolean obstacleAvoided = false; // Flag to track if obstacle has been avoided once
    private boolean obstacleDetectedTwice = false; // Flag to track if obstacle has been detected twice
    private Stopwatch stopwatch;

    /**
     * new LineFollower object.
     *
     * @param DE The DataExchange object for communication with other components.
     * @param motorA The UnregulatedMotor object representing the left motor.
     * @param motorB The UnregulatedMotor object representing the right motor.
     */
    public LineFollower(DataExchange DE, UnregulatedMotor motorA, UnregulatedMotor motorB) {
        this.DEObj = DE;
        this.motorA = motorA;
        this.motorB = motorB;
        this.stopwatch = new Stopwatch();
    }

    @Override
    public void run() {
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
        SampleProvider redMode = colorSensor.getRedMode();
        float[] sample = new float[redMode.sampleSize()];

        colorSensor.setFloodlight(Color.RED);

        while (!Button.ENTER.isDown()) {
            redMode.fetchSample(sample, 0);
            float redValue = sample[0] * 100;

            if (redValue > 40) {
                motorA.setPower(25);
                motorB.setPower(10);
            } else if (redValue > 30 && redValue < 40) {
                motorA.setPower(20);
                motorB.setPower(20);
            } else {
                motorA.setPower(10);
                motorB.setPower(25);
            }

            // Check if obstacle is detected
            if (DEObj.getObstacleDetected()) {
                if (!obstacleAvoided) { // If obstacle hasn't been avoided yet
                    // Avoid obstacle
                    motorA.setPower(0);
                    motorB.setPower(40);
                    Delay.msDelay(750);

                    motorA.setPower(30);
                    motorB.setPower(30);
                    Delay.msDelay(1500);

                    motorA.setPower(40);
                    motorB.setPower(0);
                    Delay.msDelay(1250);

                    motorA.setPower(30);
                    motorB.setPower(30);
                    Delay.msDelay(1500);

                    motorA.setPower(0);
                    motorB.setPower(40);
                    Delay.msDelay(500);

                    // Reset obstacle detection
                    DEObj.setObstacleDetected(false);
                    obstacleAvoided = true; // Set obstacle avoided flag to true
                } else if (!obstacleDetectedTwice) { // Obstacle already avoided once
                    // Stop the stopwatch when obstacle is detected the second time
                	long elapsedTime = stopwatch.elapsed();
                	System.out.println("Time: " + (elapsedTime / 1000.0) + " seconds");
                    obstacleDetectedTwice = true;
                    
                    // Stop motors
                    motorA.stop();
                    motorB.stop();
                    
                    // Pause before playing music
                    Delay.msDelay(10000); //10 seconds, Adjust delay time as needed

                 // Play music
                    playLondonBridgeMusic();
                  
                    return;
                }
            }
            colorSensor.close();
        }
    }

    /**
     * Method to play "London Bridge Is Falling Down"
     */
    private void playLondonBridgeMusic() {
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

