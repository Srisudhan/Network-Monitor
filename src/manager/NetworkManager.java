package manager;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class NetworkManager extends HttpServlet implements Runnable {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		Thread thread = new Thread();
		thread.start();
	}

	public void run() {
		try {
			new Manager().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
