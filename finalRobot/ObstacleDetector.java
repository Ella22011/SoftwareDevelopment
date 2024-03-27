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
		/** 
		 *Constructor for ObstacleDetector class
		 **/
		DEObj = DE;
	}
	
	/**
	 * Thread for avoiding the obstacle
	 **/
	public void run() {
		/** Initialize ultrasonic sensor */
		EV3UltrasonicSensor ultraSonic = new EV3UltrasonicSensor(SensorPort.S4);
		/** Get the distance mode sample provider */
		SampleProvider distanceMode = ultraSonic.getDistanceMode();
		/** Create an array to store ultrasonic sensor readings */
		float[] ultrasonicSample = new float[distanceMode.sampleSize()];
		
		/** Initialize motors */
		UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
		UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);
		
		/** Fetch the sample from the ultrasonic sensor */
		distanceMode.fetchSample(ultrasonicSample, 0);
		/** Convert the sample to distance in centimeters */
		float distance = ultrasonicSample[0] * 100;

		/** If an obstacle is detected within 10 centimeters */
		if (distance < 10) {
			/** Stop motor A and move motor B forward */
			motorA.setPower(0);
			motorB.setPower(40);
			/** Delay to allow the robot to adjust its direction */
			Delay.msDelay(750);

			/** Move both motors forward */
			motorA.setPower(30);
			motorB.setPower(30);
			/** Delay to allow the robot to move forward */
			Delay.msDelay(1500);

			/** Move motor A forward and stop motor B */
			motorA.setPower(40);
			motorB.setPower(0);
			/** Delay to allow the robot to adjust its direction */
			Delay.msDelay(1250);

			/** Move both motors forward */
			motorA.setPower(30);
			motorB.setPower(30);
			/** Delay to allow the robot to move forward */
			Delay.msDelay(1500);

			/** Stop motor A and move motor B forward */
			motorA.setPower(0);
			motorB.setPower(40);
			/** Delay to allow the robot to adjust its direction */
			Delay.msDelay(500);
		}
	}
}
