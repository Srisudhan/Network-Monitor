package userInterface;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;

public class SetInfoUI extends HttpServlet{
/**
* 
*/
private static final long serialVersionUID = 1L;

public void doPost(HttpServletRequest request, HttpServletResponse response) 
throws ServletException, IOException { 

try {
String command = request.getParameter("command");
response.setContentType("text/html"); 
PrintWriter out = response.getWriter(); 
Database db=Database.getInstance();
if(command.startsWith("getInterface")) {
String ip = request.getParameter("ip");
ArrayList<String> al = new ArrayList<String>(); 
al=db.getInterfaces(ip);
for(String temp:al) {
out.write(temp+"/");
}
}
} catch (Exception e) {
e.printStackTrace();
}
}
}