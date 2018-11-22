package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Database implements DatabaseI {
	static Connection con;

	static Timer timer = null;
	
	private static Database dbInstance;

	private Database() {

	}

	public static synchronized Database getInstance() {
		if (dbInstance == null) {
			try {
			con = Connect.getInstance();
			}catch(Exception e) {
				e.printStackTrace();
				handleException(e);
			}
			dbInstance = new Database();
		}
		return dbInstance;
	}

	public boolean addCredentials(String ip, String status, String errorMessage, String securityName,
			String securityLevel, String authType, String privType, String authPassphrase, String privPassphrase,
			String community, String port, String version) {
		try {
			String sqlCommand = "insert into UserCredentials values('" + ip + "','" + status + "','" + errorMessage
					+ "','" + securityName + "','" + securityLevel + "','" + authType + "','" + privType + "','"
					+ authPassphrase + "','" + privPassphrase + "','" + community + "','" + port + "','" + version
					+ "')";
			int success;
			Statement st = con.createStatement();
			success = st.executeUpdate(sqlCommand);
			if (success == 1) {
				System.out.println("Inserted successfully : " + sqlCommand);
				return true;
			} else
				System.out.println("Insertion failed");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return false;
	}

	public boolean deleteCredential(String[] ip) {
		try {
			for (String element : ip) {
				String sqlCommand = "DELETE from UserCredentials where ip='" + element + "'";
				Statement st = con.createStatement();
				int success = st.executeUpdate(sqlCommand);
				if (success == 1) {
					System.out.println("Deleted successfully : " + sqlCommand);
					return true;
				} else {
					System.out.println("Delete failed");
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return false;
	}

	public boolean updateCredentials(String ip, String status, String errorMessage, String securityName,
			String securityLevel, String authType, String privType, String authPassphrase, String privPassphrase,
			String community, String port, String version) {
		try {
			String sqlCommand = "update UserCredentials set status = '" + status + "', errorMessage = '" + errorMessage
					+ "',SecurityName = '" + securityName + "',securityLevel = '" + securityLevel + "',authType = '"
					+ authType + "',privType = '" + privType + "',authPassphrase = '" + authPassphrase
					+ "',privPassphrase = '" + privPassphrase + "',community = '" + community + "',port = '" + port
					+ "',version = '" + version + "' where ip = '" + ip + "'";
			Statement st = con.createStatement();
			int success = st.executeUpdate(sqlCommand);
			if (success == 1) {
				System.out.println("Updated successfully : " + sqlCommand);
				return true;
			} else {
				System.out.println("Updated failed");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
			return false;
		}
	}

	public ArrayList<Credentials> getCredentials(int limit, int offsetForCredentials) {
		ArrayList<Credentials> arrayList = new ArrayList<Credentials>();
		try {
			String sqlCommand = "select * from UserCredentials limit " + limit + " offset " + offsetForCredentials + "";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlCommand);
			while (rs.next()) {
				Credentials cred = new Credentials();
				cred.setIp(rs.getString("ip"));
				cred.setStatus(rs.getString("status"));
				cred.setErrorMessage(rs.getString("errorMessage"));
				cred.setSecurityName(rs.getString("securityName"));
				cred.setSecurityLevel(rs.getString("SecurityLevel"));
				cred.setAuthType(rs.getString("authType"));
				cred.setPrivType(rs.getString("privType"));
				cred.setPrivPassphrase(rs.getString("privPassphrase"));
				cred.setAuthPassphrase(rs.getString("authPassphrase"));
				cred.setCommunity(rs.getString("community"));
				cred.setPort(rs.getString("port"));
				cred.setVersion(rs.getString("version"));
				arrayList.add(cred);
			}
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return arrayList;
	}

	public ArrayList<Credentials> getPollDetails(int limit, int offsetForCredentials) {
		ArrayList<Credentials> arrayList = new ArrayList<Credentials>();
		try {
			String sqlCommand = "select ip,community,port,version from UserCredentials limit " + limit + " offset "
					+ offsetForCredentials + "";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlCommand);
			while (rs.next()) {
				Credentials cred = new Credentials();
				cred.setIp(rs.getString("ip"));
				cred.setCommunity(rs.getString("community"));
				cred.setPort(rs.getString("port"));
				cred.setVersion(rs.getString("version"));
				arrayList.add(cred);
			}
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return arrayList;
	}

	public void addInOutBytes(String ip, String interFace, long time, long inBytes, long outBytes) {
		try {
			String sqlCommand = "insert into IOBytes values('" + ip + "','" + interFace + "','" + time + "','" + inBytes
					+ "','" + outBytes + "')";
			Statement st = con.createStatement();
			int success = st.executeUpdate(sqlCommand);
			if (success == 1)
				System.out.println("Inserted successfully : " + sqlCommand);
			else
				System.out.println("Insert failed");
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
	}

	public void updateIOBytesEntries(String ip, String interFace, long time, long inBytes, long outBytes) {
		try {
			String sqlCommand = "update IOBytes set time = '" + time + "', inBytes = '" + outBytes + "' where ip = '"
					+ ip + "' and interFace = '" + interFace + "'";
			Statement st = con.createStatement();
			int success = st.executeUpdate(sqlCommand);
			if (success == 1)
				System.out.println("Updated successfully : " + sqlCommand);
			else
				System.out.println("Update failed");
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
	}

	public ArrayList<IOBytes> getIOByteRow(String ip, String interFace) {
		ArrayList<IOBytes> arrayList = new ArrayList<IOBytes>();
		try {
			String sqlCommand = " SELECT time,inBytes,outBytes FROM IOBytes where ip = '" + ip + "' and interFace = '"
					+ interFace + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlCommand);
			while (rs.next()) {
				IOBytes iob = new IOBytes();
				iob.setTime(rs.getLong("time"));
				iob.setInByte(rs.getLong("inBytes"));
				iob.setOutByte(rs.getLong("outBytes"));
				arrayList.add(iob);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return arrayList;
	}

	public void addBandwidthDetails(String ip, String interFace, long time, float inBytesPerSec, float outBytesPerSec) {
		try {
			String sqlCommand = "insert into Bandwidth values('" + ip + "','" + interFace + "','" + time + "','"
					+ inBytesPerSec + "','" + outBytesPerSec + "')";
			Statement st = con.createStatement();
			int success = st.executeUpdate(sqlCommand);
			if (success == 1)
				System.out.println("Inserted successfully : " + sqlCommand);
			else
				System.out.println("Insert failed");
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
	}

	public ArrayList<PlottingDetails> getBandwidthDetails(String ip, String interFace, long startTime, long endTime,
			int limit, int offsetForBandwidth) {
		ArrayList<PlottingDetails> arrayList = new ArrayList<PlottingDetails>();
		try {
			String sqlCommand = "select time,inBytesPerSec,outBytesPerSec from Bandwidth where ip = '" + ip
					+ "' and interFace = '" + interFace + "' and time > '" + startTime + "' and time <'" + endTime
					+ "'order by time asc limit " + limit + " offset " + offsetForBandwidth + "";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlCommand);
			while (rs.next()) {
				PlottingDetails plot = new PlottingDetails();
				plot.setTime(rs.getLong("time"));
				plot.setInBytesPerSecond(rs.getInt("inBytesPerSec"));
				plot.setOutBytesPerSecond(rs.getInt("outBytesPerSec"));
				arrayList.add(plot);
			}
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return arrayList;
	}

	public ArrayList<String> getInterfaces(String ip) {
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			String sqlCommand = "select distinct interFace from Bandwidth where ip='" + ip + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlCommand);
			while (rs.next()) {
				arrayList.add(rs.getString(1));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return arrayList;
	}

	public int getBandwidthRowCount(String ip, String interFace, long startTime, long endTime) {
		int rowCount = 0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"select count(*) as rowcount from Bandwidth where ip = '" + ip + "' and interFace = '" + interFace
							+ "' and time > '" + startTime + "' and time <'" + endTime + "' order by time asc ");
			rs.next();
			rowCount = rs.getInt("rowcount");
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return rowCount;
	}

	public ArrayList<Credentials> getIpMatches(String matchIpString) {
		ArrayList<Credentials> arrayList = new ArrayList<Credentials>();
		try {
			String sqlCommand = "select ip from UserCredentials where ip like '" + matchIpString + "%" + "' limit 10";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlCommand);

			while (rs.next()) {
				Credentials plot = new Credentials();
				plot.setIp(rs.getString("ip"));
				arrayList.add(plot);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return arrayList;
	}

	public void updateIpStatus(String ip, String status, String errorMessage) {
		try {
			String sqlCommand = "update UserCredentials set status ='" + status + "',errorMessage='" + errorMessage
					+ "' where ip = '" + ip + "'";
			Statement st = con.createStatement();
			int success = st.executeUpdate(sqlCommand);
			if (success == 1)
				System.out.println("Updated successfully : " + sqlCommand);
			else
				System.out.println("Updated failed");
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
	}
	public int getIpCount() {
		int ipCount = 0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select count(*) as rowcount from UserCredentials");
			rs.next();
			ipCount = rs.getInt("rowcount");
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		return ipCount;
	}

	private static void handleException(Exception e) {
		if (e instanceof com.mysql.jdbc.exceptions.jdbc4.CommunicationsException) {
			reconnect();
		}
		if (e instanceof java.lang.NullPointerException) {
			if (con == null) {
				reconnect();
			}
		}
	}

	private static void reconnect() {
		if (con == null) {
			if (timer == null) {
				timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						con = Connect.getInstance();
						System.out.println("Reconnecting");
						if (con != null) {
							System.out.println("Connection Re-established");
						}
					}
				}, 0, 30000);
			}
		}
	}

}