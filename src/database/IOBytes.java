package database;

public class IOBytes {
	private long time;
	private long inByte;
	private long outByte;

	public void setTime(long newTime) {
		time = newTime;
	}

	public void setInByte(long newInByte) {
		inByte = newInByte;
	}

	public void setOutByte(long newOutByte) {
		outByte = newOutByte;
	}

	public long getTime() {
		return time;
	}

	public long getInByte() {
		return inByte;
	}

	public long getOutByte() {
		return outByte;
	}

}
