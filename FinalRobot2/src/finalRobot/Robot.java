package finalRobot;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Stopwatch;

/**
 * This program controls an EV3 robot to follow a black line and avoid obstacles.
 * 
 * Date: April 2024
 * Team 16 - Error 404
 * 
 * Components:
 * - DataExchange: Manages data exchange between different threads.
 * - LineFollower: Controls the robot using two unregulated motors.
 * - ObstacleDetector: Detects obstacles using an ultrasonic sensor.
 * - MyColorSensot: Detects the black line using a red light.
 * 
 */
public class Robot {

    private static DataExchange DE;
    private static LineFollower LFObj;
    private static ObstacleDetector OBObj;
    private static Stopwatch stopwatch; // Using Stopwatch for timing

    public static void main(String[] args) {

        DE = new DataExchange();
        UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
        UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);

        OBObj = new ObstacleDetector(DE);
        LFObj = new LineFollower(DE, motorA, motorB);

        Thread obstacleThread = new Thread(OBObj);
        Thread lineFollowerThread = new Thread(LFObj);

        // Initialize the Stopwatch 
        stopwatch = new Stopwatch(); 

        lineFollowerThread.start();
        obstacleThread.start();

        try {
            // Wait for the robot to complete the track and detect the obstacle for the second time
            while (!DE.getObstacleDetectedTwice()) {
                Thread.sleep(100); // Sleep for 100 milliseconds
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            motorA.close();
            motorB.close();
            
            // Get elapsed time in milliseconds
            long elapsedTime = stopwatch.elapsed(); 
            System.out.println("Elapsed Time: " + elapsedTime + " milliseconds");

            // Wait for 40 seconds
            try {
                Thread.sleep(40000); // Sleep for 40 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Play music here
 
        }
    }
}