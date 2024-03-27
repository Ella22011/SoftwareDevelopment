package finalRobot;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ObstacleDetector implements Runnable{
	
	DataExchange DEObj;
	
	public ObstacleDetector(DataExchange DE) {
		DEObj = DE;
	}
	
	public void run() {
		
		EV3UltrasonicSensor ultraSonic = new EV3UltrasonicSensor(SensorPort.S4);
        SampleProvider distanceMode = ultraSonic.getDistanceMode();
        float[] ultrasonicSample = new float[distanceMode.sampleSize()];
        
        UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
        UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);
        
        distanceMode.fetchSample(ultrasonicSample, 0);
        float distance = ultrasonicSample[0] * 100;

        if (distance < 10) {
        	
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
        }
	}
}
