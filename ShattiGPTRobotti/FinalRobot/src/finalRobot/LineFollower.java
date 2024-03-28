package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

public class LineFollower implements Runnable {
    
    DataExchange DEObj;
    UnregulatedMotor motorA;
    UnregulatedMotor motorB;
    
    public LineFollower(DataExchange DE, UnregulatedMotor motorA, UnregulatedMotor motorB) {
        this.DEObj = DE;
        this.motorA = motorA;
        this.motorB = motorB;
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
                DEObj.setMotorSpeeds(motorA, motorB, 25, 10);
            } else if (redValue > 30 && redValue < 40) {
                DEObj.setMotorSpeeds(motorA, motorB, 20, 20);
            } else {
                DEObj.setMotorSpeeds(motorA, motorB, 10, 25);
            }
        }
        colorSensor.close();
    }
}