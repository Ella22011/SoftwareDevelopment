package finalRobot;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Robot {
	
	private static DataExchange DE;
	private static LineFollower LFObj;
	private static ObstacleDetector OBObj;
	
    public static void main(String[] args) {
    	
    	DE = new DataExchange();
		OBObj = new ObstacleDetector(DE);
		LFObj = new LineFollower(DE);
		OBObj.run();
		LFObj.run();
		
		while(!Button.ESCAPE.isDown()) {
			LCD.drawString("It's over...",0, 7);
			LCD.refresh();
			System.exit(0);
		}
    }
}
