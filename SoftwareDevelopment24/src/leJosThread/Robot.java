package leJosThread;

import lejos.hardware.Button;

public class Robot {
	
		private static DataExchange DE = new DataExchange();
		private static LineFollower LFObj = new LineFollower(DE);
		private static ObstacleDetector ODObj = new ObstacleDetector(DE);
		private static ColorSensor CSObj = new ColorSensor(DE);

		public static void main(String[] args) {
			Button.waitForAnyPress();
			ODObj.start();
			LFObj.start();
			CSObj.start();
			
			
			while(!(Button.getButtons()!=0)) {
				break;
			}

	}

}