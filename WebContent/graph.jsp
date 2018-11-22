<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/jquery-1.11.3.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery.jqplot.css" />
<script
	src="js/jquery.jqplot.min.js"></script>
<script type="text/javascript"
	src="js/plugins/jqplot.dateAxisRenderer.js"></script>
<script type="text/javascript"
	src="js/plugins/jqplot.canvasAxisLabelRenderer.js"></script>
<script type="text/javascript"
	src="js/plugins/jqplot.canvasAxisTickRenderer.js"></script>
<script type="text/javascript"
	src="js/plugins/jqplot.canvasTextRenderer.js"></script>
<script type="text/javascript" src="js/plugins/jqplot.cursor.js"></script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Network Monitor</title>
<style>
#in {
	color: #32cd32;
	font-weight: 800;
	width: 2px;
}

#out {
	color: #1000ff;
	font-weight: 800;
}

table, td, th {
	border: 0 solid #ddd;
	text-align: left;
}

td {
	width: 150px;
}

.jqplot-yaxis {
	padding-left: 25px;
}

.jqplot-yaxis-label {
	padding-left: 0 !important;
}

ul.breadcrumb {
	padding: 5px 8px;
	list-style: none;
	background-color: #eee;
}

ul.breadcrumb li {
	display: inline;
	font-size: 14px;
}

ul.breadcrumb li+li:before {
	padding: 4px;
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
</style>
</head>
<body>
	<div>
		<ul class="breadcrumb">
			<li><a href="index.jsp">Home</a></li>
			<li><a href="details.jsp">Details</a></li>
			<li>Graph</li>
		</ul>
	</div>
	<div>
		<fieldset class="ipAndInterFace"></fieldset>
	</div>
	<div id="graphChart"
		style="height: 250px; width: 1000px; position: relative; margin-left: 50px; margin-top: 80px"
		class="jqplot-target"></div>
	<div>
		<table id="table">
		</table>
	</div>
</body>
<script type="text/javascript">
	var dataIn = [];
	var dataOut = [];
	var maxOut = 0;
	var x;
	function loadPlottingDetails() {
		var index = document.URL.indexOf("?");
		var details = document.URL.substring(index + 1, document.URL.length);
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				getData(this.responseText);
			}
		}
		xhttp.open("POST", "GetPlottingDetails", true);
		xhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xhttp.send("command=get&" + details);
	}

	function getData(responseText) {
		var responseTextSplit = responseText.split("//");
		for (var i = 1; i < responseTextSplit.length; i++) {
			var datum = responseTextSplit[i].split("/");
			x = datum[0];
			console.log(x);
			if (x.startsWith("stop")) {
				performCalculation();
				plotGraph();
			} else {
				var inDatum = parseFloat(datum[1]);
				var outDatum = parseFloat(datum[2]);
				dataIn.push([ x, inDatum ]);
				dataOut.push([ x, outDatum ]);
			}
		}
		function performCalculation() {
			var maxIn = 0;

			var sumIn = 0, sumOut = 0;
			for (var i = 0; i < dataIn.length; i++) {
				sumIn += dataIn[i][1];
				maxIn = maxIn > dataIn[i][1] ? maxIn : dataIn[i][1];
			}
			var avgIn = sumIn / dataIn.length;
			for (var i = 0; i < dataOut.length; i++) {
				sumOut += dataOut[i][1];
				maxOut = maxOut > dataOut[i][1] ? maxOut : dataOut[i][1];
			}
			if (dataIn.length > 0) {
				var avgOut = (sumOut / dataOut.length).toFixed(2);
				var avgIn = (sumIn / dataIn.length).toFixed(2);
				var currentIn = dataIn[dataIn.length - 1][1];
				var currentOut = dataOut[dataOut.length - 1][1];
				$("#table")
						.append(
								'<tr><th> </th><th>Max</th><th>Average</th><th>Current</th></tr><tr><td id="in">In</td><td>'
										+ maxIn
										+ ' B/s</td><td>'
										+ avgIn
										+ ' B/s</td><td>'
										+ currentIn
										+ ' B/s</td></tr><tr><td id="out">Out</td><td>'
										+ maxOut
										+ ' B/s</td><td>'
										+ avgOut
										+ ' B/s</td><td>'
										+ currentOut
										+ ' B/s</td></tr>');
				console.log(dataIn);
				console.log(dataOut);
			} else {
				alert("No data");
			}
		}
	}
	function plotGraph() {
		var grid = {
			gridLineWidth : 1.5,
			gridLineColor : 'rgb(235,235,235)',
			drawGridlines : true
		};
		var plotGraph = $.jqplot('graphChart', [ dataIn, dataOut ], {
			title : "Network Monitor",
			seriesDefaults : {
				showMarker : false,
				rendererOptions : {
					smooth : true,
					animation : {
						show : true
					}
				},
			},
			series : [ {
				color : "#32cd32",
				fill : true
			}, {
				color : "#1000ff",
				lineWidth : 1
			} ],
			axes : {
				xaxis : {
					renderer : $.jqplot.DateAxisRenderer,
					label : 'Time',
					labelRenderer : $.jqplot.CanvasAxisLabelRenderer,

					tickRenderer : $.jqplot.CanvasAxisTickRenderer,
					tickOptions : {
						angle : -30,
						fontFamily : 'Helvetica',
						fontSize : '12pt',
						formatString : '%F %R'
					},
					labelOptions : {
						fontFamily : 'Helvetica',
						fontSize : '12pt',
						textColor : '#1000ff',
						fontWeight : 'bold'
					},
					min : dataIn[0][0]
				},
				yaxis : {
					label : 'Bytes per second',
					labelOptions : {
						fontFamily : 'Helvetica',
						fontSize : '12pt',
						textColor : '#1000ff',
						fontWeight : 'bold'
					},
					labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
					tickRenderer : $.jqplot.CanvasAxisTickRenderer,
					min : 0
				},
				y2axis : {
					label : 'Bytes per second',
					min : 0
				}
			},
			cursor : {
				show : true,
				zoom : true,
				tooltipLocation : 'ne'
			},
			grid : grid

		});
	}
	loadPlottingDetails();
	var urlIndex = document.URL.indexOf("?");
	var content = document.URL.substring(urlIndex + 1, document.URL.length);
	var ipAndInterface = content.split("&");
	$(".ipAndInterFace").append(
			'<legend>IP and Interface:</legend><h4>' + ipAndInterface[0] + ' '
					+ ipAndInterface[1] + '</h4>');
</script>
</html>