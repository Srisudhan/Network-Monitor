<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.11.3.min.js"></script>
<style>
body {
	background: #fff;
}

.form {
	width: 450px;
	height: 200px;
	font-size: 16px;
	max-width: 500px;
	padding: 25px 20px 75px;
	background: #edecec;
	border: 2px solid #D3D3D3;
	border-radius: 5px;
	margin-left: 400px;
	margin-top: 100px;
}

ul.breadcrumb {
	padding: 10px 16px;
	list-style: none;
	background-color: #eee;
}

ul.breadcrumb li {
	display: inline;
	font-size: 18px;
}

ul.breadcrumb li+li:before {
	padding: 8px;
	color: black;
	content: "/\00a0";
}

ul.breadcrumb li a {
	color: #0275d8;
	text-decoration: none;
}

ul.breadcrumb li a:hover {
	color: #01447e;
	text-decoration: underline;
}

#dispErr {
	border: 2px Roundest red;
	border-radius: 5px;
	padding-top: 10px;
}
</style>
<title>Details</title>
</head>
<body>
	<div>
		<ul class="breadcrumb">
			<li><a href="index.jsp">Home</a></li>
			<li>details</li>
		</ul>
	</div>
	<div class="form" autocomplete="on">
		<div id="dispErr"></div>
		<br> <label for="ip">IP:</label> <input type="text" id="ip"
			onfocusout="setIP(event)"> <br> Interface: <select
			name="interFace" id="interFace">
			<option>select</option>
		</select> <br> From Date and Time: <input type="date" name="startDate"
			id="startDate" required> <input type="time" name="startTime"
			id="startTime" required> <br> End Date and Time: <input
			type="date" name="endDate" id="endDate" required> <input
			type="time" name="endTime" id="endTime" required> <br>
		<button id="submit" type="submit" onclick="setInfo()">Plot</button>
	</div>
	<script>
		var dateControler = {
			currentDate : null
		}
		$(document).on("change", "#startDate , #endDate", function(event) {
			var now = new Date();
			var selectedDate = new Date($(this).val());
			if (selectedDate > now) {
				$(this).val(dateControler.currentDate);
				alert("Future dates cannot be monitored");
			} else {
				dateControler.currentDate = $(this).val();
			}
		});
		function setInfo() {
			var ip = document.querySelector("#ip").value;
			var interFace = document.querySelector("#interFace").value;
			var startDate = document.querySelector("#startDate").value;
			var startTime = document.querySelector("#startTime").value;
			var endDate = document.querySelector("#endDate").value;
			var endTime = document.querySelector("#endTime").value;
			/* var today = new Date();
			 var todayMillisec = today.getTime();
			 var startDateMillisec = startDate.getTime();
			 if(startDateMillisec > todayMillisec)
			 alert("Future dates cannot be monitored"); 
			 console.log(todayMillisec);
			 console.log(startDateMillisec); */
			if (startDate == "" || startTime == "" || endDate == ""
					|| endTime == "") {

				$('#dispErr').empty().prepend('Please Specify all the details');
			} else {

				window.location = "http://192.168.1.100:8080/Network_Monitor/graph.jsp?ip="
						+ ip
						+ "&Interface="
						+ interFace
						+ "&startDate="
						+ startDate
						+ "&startTime="
						+ startTime
						+ "&endDate="
						+ endDate + "&endTime=" + endTime;
			}
		}
		function setIP() {
			setDetails();
		}
		function setInterfaces(responseText) {
			var splitInterFace = responseText.split("/");
			var interface_option = document.querySelector("#interFace");
			var displayErr = document.querySelector("#dispErr");
			while (interface_option.hasChildNodes()) {
				interface_option.removeChild(interface_option.firstChild);
			}
			if (responseText == "") {
				displayErr.style.backgroundColor = "#ffacaa";
				$("#interFace").append('<option>select</option>');
				$('#dispErr')
						.empty()
						.prepend(
								'IP Status : Unregistered or invalid IP! Please register to monitor');
				$("#startDate").prop('disabled', true);
				$("#startTime").prop('disabled', true);
				$("#endDate").prop('disabled', true);
				$("#endTime").prop('disabled', true);
				$("#submit").prop('disabled', true);
			} else {
				displayErr.style.backgroundColor = "#90ee90";
				for (var i = 0; i < (splitInterFace.length - 1); i++)
					$("#interFace").append(
							'<option>' + splitInterFace[i] + '</option>');
				$('#dispErr')
						.empty()
						.prepend(
								'IP Status : IP validated successfully... Specify other details');
				$("#startDate").prop('disabled', false);
				$("#startTime").prop('disabled', false);
				$("#endDate").prop('disabled', false);
				$("#endTime").prop('disabled', false);
				$("#submit").prop('disabled', false);
			}
		}
		function setDetails() {
			var inIP = document.getElementById("ip").value.split('.');
			for (var i = 0; i < 4; i++) {
				if (inIP[i].startsWith('0')) {
					if (inIP[i].startsWith('00'))
						inIP[i] = inIP[i].substring(2, 3);
					else
						inIP[i] = inIP[i].substring(1, 3);
				}
			}
			var ip = inIP[0] + '.' + inIP[1] + '.' + inIP[2] + '.' + inIP[3];
			console.log(ip);
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					setInterfaces(this.responseText);
				}
			}
			xhttp.open("POST", "SetInfoUI", true);
			xhttp.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			xhttp.send("command=getInterface&ip=" + ip);
		}
		function getIpFromInd() {
			var index = document.URL.indexOf("=");
			if (index != -1) {
				var ipValue = document.URL.substring(index + 1,
						document.URL.length);
				document.querySelector("#ip").value = ipValue;
			}
		}
		getIpFromInd();
		setIP();
	</script>
</body>
</html>