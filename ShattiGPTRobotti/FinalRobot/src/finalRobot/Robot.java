package finalRobot;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;

public class Robot {
    
    private static DataExchange DE;
    private static LineFollower LFObj;
    private static ObstacleDetector OBObj;
    
    public static void main(String[] args) {
        
        DE = new DataExchange();
        UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
        UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);
        
        OBObj = new ObstacleDetector(DE, motorA, motorB);
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