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
public class Robot2 {

    private static DataExchange DE;
    private static LineFollower LFObj;
    private static ObstacleDetector OBObj;
    public static Stopwatch stopwatch; // Using Stopwatch for timing

    public static void main(String[] args) {
        DE = new DataExchange();
        UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
        UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);

        OBObj = new ObstacleDetector(DE);
        LFObj = new LineFollower(DE, motorA, motorB);

        Thread obstacleThread = new Thread(OBObj);
        Thread lineFollowerThread = new Thread(LFObj);

        lineFollowerThread.start();
        obstacleThread.start();

        try {
            lineFollowerThread.join();
            obstacleThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            motorA.close();
            motorB.close();
        }
    }

}

