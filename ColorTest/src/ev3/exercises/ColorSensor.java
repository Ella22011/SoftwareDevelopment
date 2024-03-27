package ev3.exercises;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ColorSensor {
    
    public static void main(String[] args) {
        
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
        
        EV3UltrasonicSensor ultraSonic = new EV3UltrasonicSensor(SensorPort.S4);
        SampleProvider distanceMode = ultraSonic.getDistanceMode();
        float[] ultrasonicSample = new float[distanceMode.sampleSize()];
        
        colorSensor.setFloodlight(Color.RED);
        motorA.setPower(20);
        motorB.setPower(20);
        
        boolean obstacleDetected = false;
        
        while(!Button.ENTER.isDown()) {
            redMode.fetchSample(sample, 0);
            float redValue = sample[0] * 100;
            
            distanceMode.fetchSample(ultrasonicSample, 0);
            float distance = ultrasonicSample[0] * 100;
            
            if (distance < 10) {
            	obstacleDetected = true;
            	
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
            } else {
            	if (obstacleDetected) {
            		motorA.setPower(20);
                    motorB.setPower(20);
                    obstacleDetected = false;
            	} else {
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
            Delay.msDelay(100);
        }
        colorSensor.close();
        ultraSonic.close();
        motorA.close(); 
        motorB.close();
    }
}
