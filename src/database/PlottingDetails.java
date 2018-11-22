package database;

public class PlottingDetails {
	private long time;
	private float inBytesPerSecond;
	private float outBytesPerSecond;

	public void setTime(long newTime) {
		time = newTime;
	}

	public void setInBytesPerSecond(int newInBytesPerSecond) {
		inBytesPerSecond = newInBytesPerSecond;
	}

	public void setOutBytesPerSecond(int newOutBytesPerSecond) {
		outBytesPerSecond = newOutBytesPerSecond;
	}

	public long getTime() {
		return time;
	}

	public float getInBytesPerSecond() {
		return inBytesPerSecond;
	}

	public float getOutBytesPerSecond() {
		return outBytesPerSecond;
	}
}