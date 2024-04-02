package finalRobot;

/**
 * The DataExchange class represents a data exchange mechanism
 * for sharing information between different components of the robot.
 */
public class DataExchange {
    
    private boolean obstacleDetected = false; // Flag to indicate if an obstacle is detected
    private int obstacleCount = 0; // Counter for detecting obstacle twice
    private float redvalue; // Value representing red color detected by the sensor
    private float distance; // Distance measured by the sensor
    
    /**
     * Get the red value detected by the sensor.
     * 
     * @return The red value detected by the sensor.
     */
    public float getRedvalue() {
        return redvalue;
    }

    /**
     * Set the red value detected by the sensor.
     * 
     * @param redvalue The red value detected by the sensor to be set.
     */
    public void setRedvalue(float redvalue) {
        this.redvalue = redvalue;
    }

    /**
     * Get the distance measured by the sensor.
     * 
     * @return The distance measured by the sensor.
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Set the distance measured by the sensor.
     * 
     * @param distance The distance measured by the sensor to be set.
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * Default constructor for the DataExchange class.
     */
    public DataExchange() {}

    /**
     * Set the status of obstacle detection.
     * 
     * @param status The status of obstacle detection to be set.
     */
    public void setObstacleDetected(boolean status) {
        obstacleDetected = status;
        if (status) {
            obstacleCount++;
        }
    }

    /**
     * Get the status of obstacle detection.
     * 
     * @return The status of obstacle detection.
     */
    public boolean getObstacleDetected() {
        return obstacleDetected;
    }
    
    /**
     * Check if obstacle has been detected twice.
     * 
     * @return True if obstacle has been detected twice, otherwise false.
     */
    public boolean getObstacleDetectedTwice() {
        return obstacleCount >= 2;
    }
}
