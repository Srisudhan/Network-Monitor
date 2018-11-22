package managerUI;

import database.Credentials;
import database.Database;
import java.util.*;
import java.io.*;
import java.sql.SQLException;

import javax.servlet.http.*;
import javax.servlet.*;

public class ManagerUI extends HttpServlet {
    private Database databases = Database.getInstance();
    private Credentials credentials = new Credentials();
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            res.setContentType("text/plain");
            PrintWriter pr = res.getWriter();
            var command = req.getParameter("command");
            switch (command) {
            case "ipCount": {
                pr.write(this.getIPCount());
                break;
            }
            case "show": {
            	var limit = Integer.parseInt(req.getParameter("limit"));
            	var offset = Integer.parseInt(req.getParameter("offset"));
                String output = this.showCredentials(limit, offset);
                pr.write(output);
                break;
            }
            case "addCredential": {
                this.setDetails(req);
                pr.write(this.addCredentials()+"");
                break;
            }
            case "deleteCredentials": {
                System.out.println(req);
                pr.write(this.deleteCredentials(req) + "");
                break;
            }
            case "updateCredential": {
                this.setDetails(req);
                pr.write(this.updateCredential() + "");
                break;
            }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String showCredentials(int limit, int offset) throws ClassNotFoundException, SQLException {
        var template = "";
        ArrayList<Credentials> credentailsList = new ArrayList<Credentials>();
        credentailsList = databases.getCredentials(limit, offset);
        for (Credentials cr : credentailsList) {
        	StringBuilder rows = new StringBuilder("<tr class='rows'>");
        	rows.append("<td><div  class=\"input-field col s6\"><input id='" + cr.getIp() + "' type='checkbox' class='checkbox'><label for= '" + cr.getIp() + "'> </label><div></td>");
        	rows.append("<td> <a href='http://192.168.1.100:8080/Network_Monitor/details.jsp?ip=" + cr.getIp() + "'>" + cr.getIp() + "</a></td>");
        	rows.append("<td>" + cr.getStatus() + "</td>");
        	rows.append("<td>" + cr.getErrorMessage() + "</td>");
        	rows.append("<td>" + cr.getSecurityName() + "</td>");
        	rows.append("<td>" + cr.getSecurityLevel() + "</td>");
        	rows.append("<td>" + cr.getAuthType() + "</td>");
        	rows.append("<td>" + cr.getPrivType() + "</td>");
        	rows.append("<td>" + cr.getAuthPassphrase() + "</td>");
        	rows.append("<td>" + cr.getPrivPassphrase() + "</td>");
        	rows.append("<td>" + cr.getCommunity() + "</td>");
        	rows.append("<td>" + cr.getPort() + "</td>");
        	rows.append("<td>" + cr.getVersion() + "</td>");
        	rows.append("</tr>");
            template += rows;
        }
        return template;
    }
    private boolean addCredentials() throws SQLException {
        return databases.addCredentials(credentials.getIp(), credentials.getStatus(), credentials.getErrorMessage(),
                credentials.getSecurityName(), credentials.getSecurityLevel(), credentials.getAuthType(),
                credentials.getPrivType(), credentials.getAuthPassphrase(), credentials.getPrivPassphrase(),
                credentials.getCommunity(), credentials.getPort(), credentials.getVersion());
    }

    private boolean deleteCredentials(HttpServletRequest req) throws SQLException {
        var ipList = req.getParameter("ipList").trim().split(",");
        return databases.deleteCredential(ipList);
    }

    private boolean updateCredential() throws SQLException {
        return databases.updateCredentials(credentials.getIp(), credentials.getStatus(), credentials.getErrorMessage(),
                credentials.getSecurityName(), credentials.getSecurityLevel(), credentials.getAuthType(),
                credentials.getPrivType(), credentials.getAuthPassphrase(), credentials.getPrivPassphrase(),
                credentials.getCommunity(), credentials.getPort(), credentials.getVersion());
    }

    private void setDetails(HttpServletRequest req) {
    	credentials.setVersion(req.getParameter("version"));
        credentials.setIp(req.getParameter("ip"));
        credentials.setStatus(req.getParameter("status"));
        credentials.setErrorMessage(req.getParameter("errorMessage"));
        credentials.setSecurityName(req.getParameter("securityName"));
        credentials.setSecurityLevel(req.getParameter("securityLevel"));
        credentials.setAuthType(req.getParameter("authType"));
        credentials.setPrivType(req.getParameter("privType"));
        credentials.setAuthPassphrase(req.getParameter("authPassphrase"));
        credentials.setPrivPassphrase(req.getParameter("privPassphrase"));
        credentials.setCommunity(req.getParameter("communityString"));
        credentials.setPort(req.getParameter("port"));
    }

    private String getIPCount() throws SQLException {
        return "" + databases.getIpCount();
    }
}