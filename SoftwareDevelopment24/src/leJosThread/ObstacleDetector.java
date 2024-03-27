package leJosThread;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class ObstacleDetector extends Thread {
	private DataExchange DEObj;
	
	private EV3UltrasonicSensor us; 
	int distanceValue;
	private final int securityDistance = 25;
	
	
	public ObstacleDetector(DataExchange DE) {
		DEObj = DE;
		us = new EV3UltrasonicSensor(SensorPort.S4);
	}
	
	
	public void run() {
		
		while(true) {
			
			final SampleProvider distance = us.getDistanceMode();
			
			
			float[] sample = new float[distance.sampleSize()];
			distance.fetchSample(sample, 0);
			float distanceValue = (int)(sample[0]*100);
			
			if(distanceValue > securityDistance){
				DEObj.setCMD(1);
			}else {
				
				DEObj.setCMD(0);
			}
			
		}
		
	}

}