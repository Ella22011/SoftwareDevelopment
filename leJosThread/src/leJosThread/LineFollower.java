package leJosThread;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class LineFollower extends Thread {
	DataExchange DEObj;

	private RegulatedMotor rightWheel = new EV3LargeRegulatedMotor(MotorPort.A);
	private RegulatedMotor leftWheel = new EV3LargeRegulatedMotor(MotorPort.B);

	public LineFollower(DataExchange DE) {
		DEObj = DE;
	}
	private final int lineColor = 15; 
	
	public void run() {
		// Infinite Task
		while (true) {
			//Get value from the ColorSensor Thread
			int colorDetected = DEObj.getLineChecker();
			int count = DEObj.getCycle();

			if (DEObj.getCMD() == 1) {
				//No obstacle is detected				
				int baseSpeed = 250;

				int error = (lineColor - colorDetected) * 8;
				LCD.drawInt(error, 1, 1);
				LCD.drawInt(colorDetected, 5, 1);
				//Change wheel spade based on error
				leftWheel.setSpeed(baseSpeed + error);
				rightWheel.setSpeed(baseSpeed - error);
				 
				leftWheel.forward();
				
				rightWheel.forward();
				
			} else {
				// OBSTACLE DETECTED 
				DEObj.setCycle(1);
				
				//If this is the first cycle. 
				if(count <= 1) {
					System.out.println("Cycle: " + count);
					//Turn right
					leftWheel.setSpeed(320);
					rightWheel.setSpeed(180);

					rightWheel.forward();
					leftWheel.forward();
	 
					Delay.msDelay(1000);
					Sound.buzz();
					
					//Turn left
					leftWheel.setSpeed(120);
					rightWheel.setSpeed(250);

					leftWheel.forward();
					rightWheel.forward();
					
					Delay.msDelay(3000);
					Sound.buzz();
					
					//Turn right
					leftWheel.setSpeed(300);
					rightWheel.setSpeed(90);

					rightWheel.forward();
					leftWheel.forward();

					Delay.msDelay(750);
					Sound.buzz();

				} else if(count > 1){
					//System.out.println("Cycle: " + count);
					
					leftWheel.stop();
					rightWheel.stop();
					
					leftWheel.setSpeed(90);
					rightWheel.setSpeed(90);
					
					leftWheel.forward();
					rightWheel.backward();
					
//Sound.playSample(new File (""), Sound.VOL_MAX);
					
					leftWheel.stop();
					rightWheel.stop();
					
					System.out.println("Yippee");
					Sound.twoBeeps();
					Delay.msDelay(20000);
					
				}
				

			}
			if (Button.getButtons() != 0) {
				break;
			}
		}
	}

}