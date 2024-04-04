package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

/**
 * The MyColorSensor class detects the black line using a red light.
 */
public class MyColorSensor implements Runnable {

    private DataExchange DEObj;

    /**
     * New MyColorSensor object.
     *
     * @param DE The DataExchange object for communication with other components.
     */
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

            // Set red value in DataExchange
            DEObj.setRedvalue(redValue);
        }
        colorSensor.close();
    }
}

