package finalRobot;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Stopwatch;

/**
 * Date: April 2024
 * This is a program for lego EV3 robot, which will follow a black line and avoid an obstacle.
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

        /**
         * Initialize the Stopwatch 
         */
        stopwatch = new Stopwatch(); 
 

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
         /**
          *  Get elapsed time in milliseconds
          */
            long elapsedTime = stopwatch.elapsed(); 
            System.out.println("Elapsed Time: " + elapsedTime + " milliseconds");
        }
    }
}
