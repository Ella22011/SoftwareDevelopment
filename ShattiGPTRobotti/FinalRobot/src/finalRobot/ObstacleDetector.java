package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * The ObstacleDetector class detects obstacles using an ultrasonic sensor.
 */
public class ObstacleDetector implements Runnable {

    private DataExchange DEObj;

    /**
     * New ObstacleDetector object.
     *
     * @param DE The DataExchange object for communication.
     */
    public ObstacleDetector(DataExchange DE) {
        this.DEObj = DE;
    }

    @Override
    public void run() {
        EV3UltrasonicSensor ultraSonic = new EV3UltrasonicSensor(SensorPort.S4);
        SampleProvider distanceMode = ultraSonic.getDistanceMode();
        float[] ultrasonicSample = new float[distanceMode.sampleSize()];

        while (!Button.ENTER.isDown()) {
            distanceMode.fetchSample(ultrasonicSample, 0);
            float distance = ultrasonicSample[0] * 100;

            // Updates distance in DataExchange
            DEObj.setDistance(distance);

            // Checks if obstacle is detected
            if (distance < 10) {
                DEObj.setObstacleDetected(true);
            }

            Delay.msDelay(100);
        }
        ultraSonic.close();
    }
}