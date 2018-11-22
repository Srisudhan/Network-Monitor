package manager;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import database.*;

public class Manager {

	Database obj = Database.getInstance();
	int ipCount, num = 0;
	FutureTask<Boolean>[] ipAddressTasks;

	/*
	 * public Manager(String iP, String community, String port) {
	 * 
	 * this.ipAddress = iP; this.community = community; this.port = port; }
	 */

	public void execute() throws SQLException {

		while (true) {
			Calendar calendar = Calendar.getInstance();
			System.out.println("UnRounded Time" + calendar.getTime());
			int round = calendar.get(Calendar.SECOND) % 5;
			boolean isComplete = pollScheduler();
			if (isComplete)
				continue;
		}

	}

	private boolean pollScheduler() throws SQLException {
		while (true) {
			ipCount = obj.getIpCount();
			System.out.println(ipCount);
			if (ipCount == 0)
				continue;
			ipAddressTasks = new FutureTask[ipCount];
			int limit = 10, offset = 0, ipNum = ipCount;
			do {
			List<Credentials> arrayList = obj.getPollDetails(limit,offset);
				Iterator<Credentials> iterationObj = arrayList.iterator();
				while (iterationObj.hasNext()) {
					Credentials credentialsObj = iterationObj.next();
					startThread(credentialsObj);
				}
				ipNum = ipNum - 10;
				offset = offset + 10;
			}
			while(ipNum > 0);
			for (int count = 0; count < ipAddressTasks.length; count++) {
				try {
					if ((ipAddressTasks[count].get()) != null) {
						if (count == (ipCount - 1)) {
							num = 0;
							return true;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void startThread(Credentials credentialsObj) throws SQLException {

		Callable<Boolean> callable = new SnmpPoller(credentialsObj.getIp(), credentialsObj.getCommunity(),
				credentialsObj.getPort(), credentialsObj.getVersion());
		ipAddressTasks[num] = new FutureTask<Boolean>(callable);
		if (num == ipCount)
			num = 0;
		Thread poll = new Thread(ipAddressTasks[num]);
		// Date executeTime = calendar.getTime();
		poll.start();
		num++;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		new Manager().execute();
	}
}
