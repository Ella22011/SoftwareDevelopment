package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.motor.UnregulatedMotor;
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
        while (!Button.ENTER.isDown()) {
            float redValue = DEObj.getRedvalue();

            if (redValue > 40) {
                motorA.setPower(50);
                motorB.setPower(20);
            } else if (redValue > 30 && redValue < 40) {
                motorA.setPower(60);
                motorB.setPower(60);
            } else {
                motorA.setPower(20);
                motorB.setPower(50);
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
                    Delay.msDelay(2000);

                    motorA.setPower(40);
                    motorB.setPower(0);
                    Delay.msDelay(1250);

                    motorA.setPower(30);
                    motorB.setPower(30);
                    Delay.msDelay(2000);

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
                    Delay.msDelay(5000);
                    Music.playMusic();
                    Music.playLondonBridgeMusic(); // Play music
                    return;
                }
            }
        }
    }
}
