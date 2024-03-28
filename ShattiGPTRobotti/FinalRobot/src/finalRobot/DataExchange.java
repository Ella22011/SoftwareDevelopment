package finalRobot;

public class DataExchange {
    private boolean obstacleDetected = false;

    private float redvalue;
    private float distance;

    public float getRedvalue() {
        return redvalue;
    }

    public void setRedvalue(float redvalue) {
        this.redvalue = redvalue;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public DataExchange() {}

    public void setObstacleDetected(boolean status) {
        obstacleDetected = status;
    }

    public boolean getObstacleDetected() {
        return obstacleDetected;
    }
}