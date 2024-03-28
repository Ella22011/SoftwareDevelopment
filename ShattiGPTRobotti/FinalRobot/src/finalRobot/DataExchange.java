package finalRobot;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lejos.hardware.motor.UnregulatedMotor;

public class DataExchange {
    private boolean obstacleDetected = false;
    private final Lock lock = new ReentrantLock();

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
        lock.lock();
        try {
            obstacleDetected = status;
        } finally {
            lock.unlock();
        }
    }

    public boolean getObstacleDetected() {
        lock.lock();
        try {
            return obstacleDetected;
        } finally {
            lock.unlock();
        }
    }

    public void setMotorSpeeds(UnregulatedMotor motorA, UnregulatedMotor motorB, int speedA, int speedB) {
        lock.lock();
        try {
            motorA.setPower(speedA);
            motorB.setPower(speedB);
        } finally {
            lock.unlock();
        }
    }
}