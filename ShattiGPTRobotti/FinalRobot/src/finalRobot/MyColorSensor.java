package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

public class MyColorSensor implements Runnable {

    DataExchange DEObj;

    public MyColorSensor(DataExchange DE) {
        this.DEObj = DE;

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

            DEObj.setRedvalue(redValue);

        }
        colorSensor.close();
    }

}

