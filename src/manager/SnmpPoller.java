package manager;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Callable;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import database.*;

public class SnmpPoller implements Callable<Boolean> {

	private String ipAddress, community, port, version;
	Database dbObj = Database.getInstance();

	public SnmpPoller(String iP, String community, String port, String version) {

		this.ipAddress = iP;
		this.community = community;
		this.port = port;
		this.version = version;
	}

	public Boolean call() throws InterruptedException, UnknownHostException {

		boolean status;
		Calendar calendar = Calendar.getInstance();
		System.out.println("UnRounded Time" + calendar.getTime());
		int round = calendar.get(Calendar.SECOND) % 5;
		/*
		 * if (round > 0) { calendar.add(Calendar.SECOND, (5 - round)); }
		 */
		int remTime = 5 - round;
		// System.out.println("Rounded Time" + calendar.getTime());
		Thread.sleep(remTime * 1000);
		int i = 0;
		while (true) {

			status = getInandOutBytes("1.3.6.1.2.1.31.1.1.1.1." + i, "1.3.6.1.2.1.2.2.1.10." + i, "1.3.6.1.2.1.2.2.1.16." + i);
			i++;
			if (status)
				continue;
			else 
				break;
		}
		return true;
	}

	private boolean getInandOutBytes(String interFace, String inOID, String outOID) {

		String[] OID = { interFace, inOID, outOID };
		String strInByte, strOutByte, strInterfaceName, interfaceName = "";
		long inByte = 0, outByte = 0;
		// boolean ipStatus;
		long time = System.currentTimeMillis() / 1000;

		try {
			TransportMapping transport = null;
			for (int x = 1; x < 4; x++) {
				try {
					
					OctetString community1 = new OctetString(community);
					String pollAddress = ipAddress + "/" + port;
					Address targetaddress = new UdpAddress(pollAddress);
					transport = new DefaultUdpTransportMapping();
					transport.listen();
					CommunityTarget comtarget = new CommunityTarget();
					comtarget.setCommunity(community1);
					switch(version) {
					case "SNMPv1":{
						comtarget.setVersion(SnmpConstants.version1);
						break;
					}
					case "SNMPv2":{
						comtarget.setVersion(SnmpConstants.version2c);
						break;
					}
					}
					//comtarget.setVersion(SnmpConstants.version2c);
					comtarget.setAddress(targetaddress);
					comtarget.setRetries(2);
					comtarget.setTimeout(500);
					PDU pdu = new PDU();
					ResponseEvent response;
					Snmp snmp;
					pdu.add(new VariableBinding(new OID(OID[x-1])));
					pdu.setType(PDU.GETNEXT);
					snmp = new Snmp(transport);
					response = snmp.getNext(pdu, comtarget);
					if (response.getResponse() != null) {
						
						if (response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
							
							PDU pduresponse = response.getResponse();
							switch (x) {
							
							case 1:
								strInterfaceName = pduresponse.getVariableBindings().firstElement().toString();
								if (strInterfaceName.contains("=")) {
									
									int len = strInterfaceName.indexOf("=");
									System.out.println(strInterfaceName + "\t" + len); 
									interfaceName = strInterfaceName.substring(len + 2, strInterfaceName.length());
									String oidPrefix = strInterfaceName.substring(21, 22);
									System.out.println(oidPrefix);
									if(!(oidPrefix.equals("1"))) {
										return false;
									}
								}
								break;
								
							case 2:
								strInByte = pduresponse.getVariableBindings().firstElement().toString();
								if (strInByte.contains("=")) {
									
									int len = strInByte.indexOf("=");
									strInByte = strInByte.substring(len + 2, strInByte.length());
									inByte = Long.parseLong(strInByte);
								}
								break;
								
							default:
								strOutByte = pduresponse.getVariableBindings().firstElement().toString();
								if (strOutByte.contains("=")) {
									
									int len = strOutByte.indexOf("=");
									strOutByte = strOutByte.substring(len + 2, strOutByte.length());
									outByte = Long.parseLong(strOutByte);
								}
							}
							
						}
						
						else {
							
							dbObj.updateIpStatus(ipAddress, "Offline", "Not reachable");
							return true;
						}
					}
					
					else {
						
						/*
						 * InetAddress inet = InetAddress.getByName(ipAddress); ipStatus =
						 * inet.isReachable(500) ? true : false; if(ipStatus)
						 * obj.updateIpStatus(ipAddress, "Offline", "Incorrect details"); else
						 */
						dbObj.updateIpStatus(ipAddress, "Offline", "Host Unreachable");
						return false;
					}
				}finally {
					if(transport != null)
						transport.close();
				}
			}
			int rowCount = dbObj.getIOBytesRowCount(ipAddress, interfaceName);
			if(rowCount == 0) {
				dbObj.addInOutBytes(ipAddress, interfaceName, time, inByte, outByte);
			}
			else {
				long prevTime,prevInByte,prevOutByte;
				ArrayList<IOBytes> arrayList = new ArrayList<IOBytes>();
				arrayList = dbObj.getIOByteRow(ipAddress, interfaceName);
				IOBytes iob = new IOBytes();
				iob = arrayList.get(0);
				prevTime = iob.getTime();
				prevInByte = iob.getInByte();
				prevOutByte = iob.getOutByte();
				dbObj.updateIOByteRow(ipAddress, interfaceName, time, inByte, outByte);
				calculateBandwidth(time,prevTime,inByte,prevInByte,outByte,prevOutByte,interfaceName,ipAddress);
			}
			
			dbObj.updateIpStatus(ipAddress, "Online", "");
		}

		catch (Exception e) {
			e.printStackTrace();
			return true;

		}
		
		return true;
	}
	
	/*private void getLastTwoIOBytesValue(String ipAddress, String interFace) {
		
		int bytesRow = 0; 
		long firstTime = 0, secondTime = 0, firstInByte = 0, secondInByte = 0, firstOutByte = 0, secondOutByte = 0;
		try {
			bytesRow = dbObj.getIOBytesRowCount(ipAddress, interFace);
			int deletedCount = bytesRow - 5;
			System.out.println(deletedCount);
			 if(deletedCount > 0) 
				 dbObj.deleteIOBytesEntries(ipAddress, interFace, deletedCount);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 if (bytesRow > 1) {
			 ArrayList<IOBytes> interfaceModule = new ArrayList<IOBytes>(); 
			 interfaceModule = dbObj.getLastTwoIOBytes(ipAddress, interFace);
			 int index=0;
			 while(index<2) {
				 IOBytes ioBytesObj = interfaceModule.get(index); 
				 if (index == 0) {
					 firstInByte = ioBytesObj.getInBytes(); // getting inbytes from IOBytes class
					 firstOutByte = ioBytesObj.getOutBytes(); // getting outbytes from IOBytesclass 
					 firstTime = ioBytesObj.getTime(); // getting time from IOBytes class
					 index++; 
				} 
				 else {
			 
					 secondInByte = ioBytesObj.getInBytes(); // getting inbytes from IOBytes class
					 secondOutByte = ioBytesObj.getOutBytes(); // getting outbytes from IOBytesclass 
					 secondTime = ioBytesObj.getTime(); 
					 index++;
				} 
			}
			 //calculating bandwidth for 
		 try {
			 calculateBandwidth(firstTime, secondTime, firstInByte, secondInByte,firstOutByte, secondOutByte, interFace, ipAddress); 
		} 
		 catch(SQLException e) { 
			 e.printStackTrace(); 
		 	} 
		 } 
	}*/

	private void calculateBandwidth(long startTime, long endTime, long startInByte, long endInByte, long startOutByte,
			long endOutByte, String interFace, String ipAddress) throws SQLException {

		long timeDiff, inByteDiff, outByteDiff;
		float inBytesPerSec = 0, outBytesPerSec = 0;
		timeDiff = startTime - endTime;
		inByteDiff = startInByte - endInByte;
		outByteDiff = startOutByte - endOutByte;
		System.out.print(ipAddress + "\t" + timeDiff + "\t" + inByteDiff + "\t" + outByteDiff);
		inBytesPerSec = inByteDiff / timeDiff;
		outBytesPerSec = outByteDiff / timeDiff;
		if(inBytesPerSec >= 0 && outBytesPerSec >= 0)
			dbObj.addBandwidthDetails(ipAddress, interFace, startTime, inBytesPerSec, outBytesPerSec);
		else
			System.out.println("Bandwidth value is neglected");

	}
}
