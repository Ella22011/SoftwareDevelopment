package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

/**
 * The ObstacleDetector class detects obstacles using an ultrasonic sensor.
 */
public class ObstacleDetector implements Runnable {

    private DataExchange DEObj;
    private EV3UltrasonicSensor ultraSonic;

    /**
     * New ObstacleDetector object.
     *
     * @param DE The DataExchange object for communication.
     */
    public ObstacleDetector(DataExchange DE) {
        this.DEObj = DE;
        this.ultraSonic = new EV3UltrasonicSensor(SensorPort.S4);
    }

    @Override
    public void run() {
        SampleProvider distanceMode = ultraSonic.getDistanceMode();
        float[] ultrasonicSample = new float[distanceMode.sampleSize()];

        while (!Button.ENTER.isDown()) {
            distanceMode.fetchSample(ultrasonicSample, 0);
            float distance = ultrasonicSample[0] * 100;

            // Updates distance in DataExchange
            DEObj.setDistance(distance);

            // Checks if obstacle is detected
            if (distance < 15) {
                DEObj.setObstacleDetected(true);
            } else {
                DEObj.setObstacleDetected(false);
            }

            try {
                Thread.sleep(100); // Adjust sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ultraSonic.close();
    }
}
