package leJosThread;

public class DataExchange {

	private boolean obstacleDetected = false;
	private int count = 1;
	private int lineChecker;
	
	
	private int CMD = 1;
	public DataExchange() {
		
	}
	
	public void setObstacleDetected(boolean status) {
		obstacleDetected = status;
	}
	
	public boolean getObstacleDetected() {
		return obstacleDetected;
	}
	
	public void setCMD(int command) {
		CMD = command;
	}
	
	public int getCMD() {
		return CMD;
	}

	public int getCycle() {
		return count;
	}

	public void setCycle(int cycle) {
		this.count = this.count + cycle;
	}
	
	public int getLineChecker() {
		return lineChecker;
	}

	public void setLineChecker(int lineChecker) {
		this.lineChecker = lineChecker;
	}



}