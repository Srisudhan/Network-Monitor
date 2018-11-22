package database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Connect {

	private static Connection con;

	private Connect() {
		
	}

	public static synchronized Connection getInstance() {
		try {
			if (con == null) {
				Properties props = new Properties();
				InputStream in =  new URL("http://192.168.1.100:8080/Network_Monitor/resources/db.properties").openStream();
				props.load(in);
				in.close();

				String driver = props.getProperty("jdbc.driver");
				if (driver != null) {
					Class.forName(driver);
				}

				String url = props.getProperty("jdbc.connectionUrl");
				String username = props.getProperty("jdbc.userName");
				String password = props.getProperty("jdbc.password");

				con = DriverManager.getConnection(url, username, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
