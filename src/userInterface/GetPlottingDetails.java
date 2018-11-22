package userInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import database.PlottingDetails;

public class GetPlottingDetails extends HttpServlet {
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
response.setContentType("text");
PrintWriter pw = response.getWriter();
Database db = Database.getInstance();
ArrayList<PlottingDetails> arrayList = new ArrayList<PlottingDetails>();
System.out.println("GetPlottingDetails");
try {

String ip = request.getParameter("ip");
System.out.println(ip);
String interFace = request.getParameter("Interface");
System.out.println(interFace);
String sDate = request.getParameter("startDate");
String sTime = request.getParameter("startTime");
String eDate = request.getParameter("endDate");
String eTime = request.getParameter("endTime");
SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
String fromTime = sTime + ":00.500";
String toTime = eTime + ":00.500";
Date start = startSdf.parse(sDate + " " + fromTime);
Date end = endSdf.parse(eDate + " " + toTime);
System.out.println(start);
System.out.println(end);
long startTime = (start.getTime()) / 1000;
long endTime = (end.getTime()) / 1000;
int rowCount = db.getBandwidthRowCount(ip, interFace, startTime, endTime);
int row = rowCount / 100;
int remainingCount = rowCount % 100;
System.out.println(row);
int limit = 100;
if (remainingCount != 0) {
row++;
}
int i = row;
while (row != 0) {
System.out.println(row);
arrayList = db.getBandwidthDetails(ip, interFace, startTime, endTime, limit, limit * (i - row));
System.out.println("limit=" + limit + " offset" + (limit*(i-row)));
for(PlottingDetails inData : arrayList) {
long time = inData.getTime();
Date date = new Date(time * 1000);
SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm yyyy-MM-dd");
String times = dateformat.format(date);
float inBytesPerSecond = inData.getInBytesPerSecond();
float outBytesPerSecond = inData.getOutBytesPerSecond();
pw.write(times + "/" + inBytesPerSecond + "/" + outBytesPerSecond + "//");
}
row--;
}
pw.write("stop");
} catch (Exception e) {
pw.write("stop");
e.printStackTrace();
}

}
}