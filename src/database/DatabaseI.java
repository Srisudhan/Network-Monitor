package database;

import java.util.ArrayList;

public interface DatabaseI {

	public boolean addCredentials(String ip, String status, String errorMessage, String securityName,
			String securityLevel, String authType, String privType, String authPassphrase, String privPassphrase,
			String community, String port, String version);

	public boolean deleteCredential(String[] ip);

	public boolean updateCredentials(String ip, String status, String errorMessage, String securityName,
			String securityLevel, String authType, String privType, String authPassphrase, String privPassphrase,
			String community, String port, String version);

	public ArrayList<Credentials> getCredentials(int limit, int offsetForCredentials);

	public ArrayList<Credentials> getPollDetails(int limit, int offsetForCredentials);

	public ArrayList<PlottingDetails> getBandwidthDetails(String ip, String interFace, long startTime, long endTime,
			int limit, int offsetForBandwidth);

	public void updateIpStatus(String ip, String status, String errorMessage);

	public ArrayList<Credentials> getIpMatches(String matchIpString);

	public ArrayList<String> getInterfaces(String ip);

	public void addInOutBytes(String ip, String interFace, long time, long inBytes, long outBytes);

	public ArrayList<IOBytes> getIOByteRow(String ip, String interFace);

	public void addBandwidthDetails(String ip, String interFace, long time, float inBytesPerSec, float outBytesPerSec);

	public int getIpCount();

}