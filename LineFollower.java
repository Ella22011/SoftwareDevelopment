package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

public class LineFollower implements Runnable{
	
	DataExchange DEObj;
	
	public LineFollower(DataExchange DE) {
		DEObj = DE;
	}

	public void run() {

	  System.out.println("Color Sensor\n");
      System.out.println("Press any key to start");
      Button.LEDPattern(2);
      Sound.beepSequenceUp();
      Button.waitForAnyPress();

      UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
      UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);

      EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
      SampleProvider redMode = colorSensor.getRedMode();
      float[] sample = new float[redMode.sampleSize()];

      colorSensor.setFloodlight(Color.RED);
      
      while(!Button.ENTER.isDown()) {
          redMode.fetchSample(sample, 0);
          float redValue = sample[0] * 100;
          System.out.println("Red Value: " + redValue);
          
          if (redValue > 40) {
              motorA.setPower(25);
              motorB.setPower(10);
          }
          else if (redValue > 30 && redValue < 40) {
              motorA.setPower(20);
              motorB.setPower(20);
          }
          else {
              motorA.setPower(10);
              motorB.setPower(25);
          }
	}
}
}
