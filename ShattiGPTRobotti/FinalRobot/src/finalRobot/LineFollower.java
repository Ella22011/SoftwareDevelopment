package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

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
                motorA.setPower(25);
                motorB.setPower(10);
            } else if (redValue > 30 && redValue < 40) {
                motorA.setPower(20);
                motorB.setPower(20);
            } else {
                motorA.setPower(10);
                motorB.setPower(25);
            }

            // Check if obstacle is detected
            if (DEObj.getObstacleDetected()) {
                // Perform avoidance maneuver
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

                // Reset obstacle detection flag
                DEObj.setObstacleDetected(false);
            }
        }
        colorSensor.close();
    }
}