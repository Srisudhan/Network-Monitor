<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.2/css/materialize.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<title>Network Monitor</title>
<style>
#productName{
    position : relative;
    bottom : 0;
    left : 0;
    margin : 10px;
}
#banner{
	background:#90ee90;
}
#selectionPane, #pagination{
	display: inline-block;
}
#overlay {
    position: fixed;
    display: none;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0,0,0,0.5);
    z-index: 2;
    cursor: pointer;back
}
#overlayText{
    position: absolute;
    top: 50%;
    left: 50%;
    font-size: 50px;
    color: white;
    transform: translate(-50%,-50%);
    -ms-transform: translate(-50%,-50%);
}
</style>
</head>
<body>
<body>
<div id="overlay" onclick="ManagerUI.removeBanner()">
<div id="overlayText"></div>
</div>
  <div class="progress">
      <div class="indeterminate"></div>
  </div>
   <nav>
    <div class="nav-wrapper">
    <div class="col" id="product">
      <a href="http://rishoncomm.in/" class="brand-logo teal">Network Monitor</a>
       </div>
		<form>
        <div class="input-field right">
          <input id="search" type="search">
          <label class="label-icon" for="search"><i class="material-icons">search</i></label>
          <i class="material-icons">close</i>
        </div>
      </form>
    </div>
  </nav>
<div class=".container-1">
     <table id = "table" class="highlight">
               <thead>
                <tr class='rows'>
                    <th>
                        <div class="md-checkbox">
                            <input id="checkAll" type="checkbox" class="checkbox">
                            <label for="checkAll"> </label>
                          </div>
                    </th>
                    <th>IP</th>
                    <th>Status</th>
                    <th>Error</th>
                    <th>Security Name</th>
                    <th>Security Level</th>
                    <th>Authentication Type</th>
                    <th>Privilege Type</th>
                    <th>Authentication Passphrase</th>
                    <th>Privilege Passphrase</th>
                    <th>Community String</th>
                    <th>Port</th>
                    <th>Version</th>
                </tr>
            </thead>
            <tbody id='body'>
                </tbody>
        </table>
<div class="fixed-action-btn" id="Fbutton">
  <a class="btn-floating btn-large red">
    <i class="large material-icons">dehaze</i>
  </a>
  <ul>
    <li><a class="btn-floating red modal-trigger" data-target="modal1"><i class="material-icons">add</i></a></li>
    <li><a class="btn-floating yellow darken-1"><i class="material-icons" id="delete">delete</i></a></li>
    <li><a class="btn-floating green modal-trigger" data-target="modal1"><i class="material-icons" id="edit">edit</i></a></li>
  </ul>
</div>

<div id="modal1" class="modal modal-fixed-footer" role="dialog">
<div class="modal-content">
<form class="col s12">
  <div class="input-field col s12">
    <select id="snmpVersion">
      <option value="" disabled selected>Choose your option</option>
      <option value="1">SNMPv1</option>
      <option value="2">SNMPv2</option>
      <option value="3">SNMPv3</option>
    </select>
    <label>SNMP Version</label>
    </div>
        <div class="row">
            <div class="input-field col s6" class="validate">
              <input id="ip" type="text" required="required" oninput="ManagerUI.removeLeadingZeros()">
              <label for="ip">IP*</label>
            </div>
			<div class="input-field col s6">
              <input id="communityString" type="text" class="validate">
              <label for="communityString">Community String*</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
              <input id="port" type="number" class="validate">
              <label for="port">Port*</label>
            </div>
              <div class="input-field col s6">
              <select id="securityLevel">
                <option value="" disabled selected>Choose your option</option>
                <option>authPriv</option>
                <option>authNoPriv</option>
                <option>noAuthNoPriv</option>
              </select>
                <label>Security Level</label>
              </div>
        </div>
        <div class="row">
                  <div class="input-field col s6">
                  <select id="privilegeType">
                    <option value="" disabled selected>Choose your option</option>
                    <option>DES</option>
                    <option>3DES</option>
                    <option>AES128</option>
                    <option>AES192</option>
                    <option>AES256</option>
              </select>
                <label>Privilege Type</label>
              </div>
              <div class="input-field col s6">
              <input id="authenticationPassphrase" type="text" class="validate">
              <label for="authenticationPassphrase">Authentication Passphrase</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
              <input id="privilegePassphrase" type="text" class="validate">
              <label for="privilegePassphrase">Privilege Passphrase</label>
            </div>
            <div class="input-field col s6">
              <input id="securityName" type="text" class="validate">
              <label for="securityName">Security Name</label>
            </div>
        </div>
        <div class="row">
             <div class="input-field col s6">
                  <select id="authenticationType">
                    <option value="" disabled selected>Choose your option</option>
                    <option>MD5</option>
                    <option>SHA</option>
              </select>
                <label>Authentication Type</label>
              </div>
        </div>
            <div class="row">
                <a class="waves-effect waves-light btn" id="addData">Add</a>
                <a class="waves-effect waves-light btn" id="update">Update</a>
            </div>
            <div class="row">
                <div id="banner"></div>
            </div>
            </form>
         </div>
    <div class="modal-footer">
        <a href="#!" class=" modal-action modal-close waves-effect waves-green btn-flat">Close</a>
    </div>
</div>
</div>
<div id="productName">
<div>© Network Monitor.All rights reserved</div>
</div>
	<div class="row">
	<center>
<div id="pagination">
</div>
<div class="input-field col s1" id="selectionPane">
			<select id="rowsPerPage" class="browser-default ">
				<option value="" disabled selected>Choose your option</option>
				<option>All</option>
				<option selected="selected">5</option>
				<option>10</option>
				<option>15</option>
				<option>20</option>
				<option>25</option>
			</select>
		</div>
	</center>
	</div>
</body>
<script src=" https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/js/materialize.min.js"></script>
    <script type="text/javascript">
        var previousIPCount;
        var ipCount;
        var param;
        var ip = document.querySelector("#ip");
        var addButton = document.querySelector("#addData");
        var table = document.querySelector("#body");
        var deleteButton = document.querySelector("#delete");
        var editButton = document.querySelector("#edit");
        var updateButton = document.querySelector("#update");
        var ipCountTextArea = document.querySelector("#ipCount");
        var rowsPerPage = document.querySelector("#rowsPerPage");
        var search = document.querySelector("#search");
        var pageLimit = 5, offset;
        var index = [];
        var ManagerUI = {};
        ManagerUI.addCredential = function(){
        	if(ManagerUI.doValidation){
	            ManagerUI.setDetails();
	            param = "command=addCredential" + ManagerUI.getDetails();
	            ManagerUI.doAjax();
        	}
        	else{
        		//failure case;
        	}
        }
/*          function showTable(){
            do{
                param = "command=show&limit=100"  + "&offset=" + offset;
                ManagerUI.doAjax();
                ipCount -= 100;
            }while(ipCount/100>1);
            M.AutoInit();
        } */
        ManagerUI.removeLoadingBar = function(){
        	var LoadingBar = $(".progress");
        	LoadingBar.remove();
        }
        ManagerUI.setActionStatus = function(response, successMessage, failureMessage){
            var banner = document.querySelector('#overlayText');
            var modalBanner = document.querySelector('#banner');
            banner.innerText = "";
            if(response == "true"){
                banner.insertAdjacentHTML("beforeend", successMessage);
                ManagerUI.showBanner();
                return true;
            }
            else
         	   modalBanner.insertAdjacentHTML("beforeend", failureMessage);
            return false;
        }
        ManagerUI.generateRows = function(){
                var tableTemplate = "<tr class='rows'>" +
	                                	"<th><div  class=\"input-field col s6\"back><input id='" + ManagerUI.ip + "' type='checkbox' class='checkbox'><label for= '" + ManagerUI.ip + "'> </label><div></td>" +
	                                  	"<th><a href='http://192.168.1.100:8080/Network_Monitor/details.jsp?ip=" + ManagerUI.ip + "'>" + ManagerUI.ip + "</a></th>" +
	                                	"<th>" + "-" + "</th>" +
	                                    "<th>" + "-" + "</th>" +
	                                   	"<th>" + ManagerUI.securityName + "</th>" +
	                                   	"<th>" + ManagerUI.securityLevel + "</th>" +
	                                 	"<th>" + ManagerUI.authenticationType + "</th>" +
	                                 	"<th>" + ManagerUI.privilegeType + "</th>" +
	                                	"<th>" + ManagerUI.authenticationPassphrase + "</th>" +
	                                 	"<th>" + ManagerUI.privilegePassphrase + "</th>" +
	                                	"<th>" + ManagerUI.commnyString + "</th>" +
	                                   	"<th>" + ManagerUI.port + "</th>" +
	                                   	"<th>" + ManagerUI.version + "</th>" +
                                 	"</tr>";
                  table.insertAdjacentHTML('beforeend',tableTemplate);
                  M.AutoInit();
        }
        ManagerUI.setDetails = function(){
        	var target = document.querySelector("#snmpVersion");
        	ManagerUI.version = target.options[target.selectedIndex].text.trim();
			ManagerUI.ip = ManagerUI.removeLeadingZeros();
            ManagerUI.port = document.querySelector("#port").value.trim();
            ManagerUI.communityString = document.querySelector("#communityString").value.trim();
        	if(ManagerUI.version == "SNMPv3"){
	            ManagerUI.securityName = document.querySelector("#securityName").value.trim();
	            target = document.querySelector("#securityLevel");
	            ManagerUI.securityLevel = target.options[target.selectedIndex].text.trim();
	            target = document.querySelector("#authenticationType");
	            ManagerUI.authenticationType = target.options[target.selectedIndex].text.trim();
	            target = document.querySelector("#privilegeType");
	            ManagerUI.privilegeType = target.options[target.selectedIndex].text;
	            ManagerUI.authenticationPassphrase = document.querySelector("#authenticationPassphrase").value.trim();
	            ManagerUI.privilegePassphrase = document.querySelector("#privilegePassphrase").value.trim();
	            ManagerUI.communityString = document.querySelector("#communityString").value.trim();
	            ManagerUI.port = document.querySelector("#port").value.trim();
        	}
        	else{
	            ManagerUI.securityName = "-";
	            ManagerUI.securityLevel = "-";
	            ManagerUI.authenticationType = "-";
	            ManagerUI.privilegeType = "-";
	            ManagerUI.authenticationPassphrase = "-";
	            ManagerUI.privilegePassphrase = "-";
        	}
        }
        ManagerUI.getDetails = function(){
            var details = "&version=" + ManagerUI.version + 
	            			"&ip=" + ManagerUI.ip +
	                            "&status=" + ManagerUI.status +
	                                "&errorMessage=" + ManagerUI.error +
	                                    "&securityName=" + ManagerUI.securityName +
	                                        "&securityLevel=" + ManagerUI.securityLevel +
	                                            "&authType=" + ManagerUI.authenticationType +
	                                                "&privType=" + ManagerUI.privilegeType +
	                                                    "&authPassphrase=" + ManagerUI.authenticationPassphrase +
	                                                        "&privPassphrase=" + ManagerUI.privilegePassphrase +
	                                                            "&communityString=" + ManagerUI.communityString +
	                                                                "&port=" + ManagerUI.port;
            return details;
        }
        ManagerUI.deleteCredentials = function(){
			var ipList = ManagerUI.deleteSelectedRow();
            param = "command=deleteCredentials&ipList=" + ipList;
            ManagerUI.doAjax();
        }
        ManagerUI.deleteSelectedRow = function(){
            var checkboxes = document.querySelectorAll('.checkbox');
            var rows = document.querySelectorAll('.rows');
            var ipList = "";
            for (var i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked == true){
                    if(i != 0){
                        ipList += rows[i].cells[1].textContent + ',';
                        rows[i].remove();
                    }
                    else{
                        checkboxes[i].checked = false;
                    }
                }     
            }
            return ipList;
        }
        ManagerUI.editCredentials = function(){
            var checkboxes = document.querySelectorAll('.checkbox');
            var rows = document.querySelectorAll('.rows');
            for (var i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked == true){
                    if(i != 0){
                        document.querySelector("#ip").readOnly = true;
                        console.log(rows[i].cells);
                        ManagerUI.setTextBoxValue("ip",i,1,rows);
                        ManagerUI.setTextBoxValue("securityName",i,4,rows);
                        ManagerUI.setTextBoxValue("authenticationPassphrase",i,8,rows);
                        ManagerUI.setTextBoxValue("privilegePassphrase",i,9,rows);
                        ManagerUI.setTextBoxValue("communityString",i,10,rows);
                        ManagerUI.setTextBoxValue("port",i,11,rows);
                        M.updateTextFields();
                        break;
                    }
                }     
            }
        }
        ManagerUI.setTextBoxValue = function(id,i,cellIndex,rows){
            document.querySelector("#"+id).value = rows[i].cells[cellIndex].textContent;
        }
        ManagerUI.updateCredentials = function(){
            ManagerUI.setDetails();
            param = "command=updateCredential" + ManagerUI.getDetails();
            ManagerUI.generateRows();
            ManagerUI.doAjax();
            document.querySelector("#ip").readOnly = false;
        }
        ManagerUI.validateIP = function(){
            if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(ip.value)){
                   ip.classList.remove("invalid");
                   ip.classList.add("valid");                
                }
            else{
                ip.classList.remove("invalid");
                ip.classList.add("invalid");
            }
        }
        ManagerUI.removeLeadingZeros = function(){
        	var inIP = document.querySelector("#ip").value.trim().split('.');
            for (var i = 0; i < 4; i++) {
                if (inIP[i].startsWith('0')) {
                    if (inIP[i].startsWith('00'))
                        inIP[i] = inIP[i].substring(2, 3);
                    else
                        inIP[i] = inIP[i].substring(1, 3);
                }
            }
            return inIP[0] + '.' + inIP[1] + '.' + inIP[2] + '.' + inIP[3];
        }
        ManagerUI.searchIP = function(event){
        	for(var i = 0; i < table.childNodes.length; i++){
        		if(table.childNodes[i].childNodes[1].innerText.trim().startsWith(search.value)){
					table.childNodes[i].hidden = false;
        		}
        		else{
        			table.childNodes[i].hidden = true;
        		}
        	}
        	M.AutoInit();
        }
        ManagerUI.showBanner = function() {
            document.getElementById("overlay").style.display = "block";
        }
        ManagerUI.removeBanner = function() {
            document.getElementById("overlay").style.display = "none";
        }
        ManagerUI.doValidation = function(){
        	return (((ManagerUI.port!=undefined) && (ManagerUI.communityString!=undefined))? true : false);
        }
        $(document).ready(function () {
            $('.modal-trigger').modal();
        });
        $("#checkAll").click(function(){
            $('input:checkbox').not(this).prop('checked', this.checked);
        });
        ManagerUI.displayContents = function(e){
            offset = e.target.textContent;
            offset = (offset - 1) * pageLimit;
            //remove the wave effect when clicking amd adding active page color
            var pagination = document.querySelector(".pagination");
            for(var i = 0; i < pagination.childNodes.length; i++){
                if(pagination.childNodes[i].classList == "active"){
                    pagination.childNodes[i].classList.remove("active");
                    pagination.childNodes[i].classList.add("waves-effect");
                }
            }
            e.target.classList.remove("waves-effect");
            e.target.classList.add("active");
            M.AutoInit();
            param = "command=show&limit=" + pageLimit + "&offset=" + offset;
            ManagerUI.doAjax();
        }
        /* ManagerUI.displayContents = function(e){
        	offset = e.target.textContent;
        	var index;
        	offset = (offset - 1) * pageLimit;
        	//remove the wave effect when clicking amd adding active page color
        	var pagination = document.querySelector(".pagination");
        	for(var i = 0; i < pagination.childNodes.length; i++){
        		if(pagination.childNodes[i].classList == "active"){
        			index = i;
        			pagination.childNodes[i].classList.remove("active");
        		}
        	}
        	if(offset == "chevron_left"){
        		offset = pagination.childNodes[index].value - 1;
        		pagination.childNodes[index-1].classList.add("active");
        	}
        	else{
        	e.target.classList.add("active");
        	}
        	M.AutoInit();
        	param = "command=show&limit=" + pageLimit + "&offset=" + offset;
        	ManagerUI.doAjax();
        } */
        ManagerUI.loadPagination = function(){
        	limit = ((ipCount % pageLimit) == 0 ) ? (ipCount/pageLimit) : ((ipCount/pageLimit) + 1);
        	var pagination = document.querySelector("#pagination");
        	pagination.innerText = "";
        	var template = "<ul class='pagination'><li class='waves-effect' onclick='ManagerUI.displayContents(event)'><a href='#!''><i class='material-icons'>chevron_left</i></a></li>";
        	template += "<li id='firstPage' class='active' onclick='ManagerUI.displayContents(event)'><a href='#!'>1</a></li>";
        	for(var i = 2; i <= limit; i++){
        		template += "<li class='waves-effect' onclick='ManagerUI.displayContents(event)'><a href='#!'>" + i + "</a></li>";
        	}
            template += "<li class='waves-effect' onclick='ManagerUI.displayContents(event)'><a href='#!'><i class='material-icons'>chevron_right</i></a></li></ul>";
            pagination.insertAdjacentHTML("beforeend", template);
            M.AutoInit();
            $("#firstPage").click();
        }
        ManagerUI.displayActionStatus = function(response){
            if(param.startsWith("command=addCredential")){
            	if(ManagerUI.setActionStatus(response,"<span>Credentials Added Successfully</span>","<span>IP already exists</span>"))
            	ManagerUI.generateRows();
            }
            else if(param.startsWith("command=deleteCredential")){
            	ManagerUI.setActionStatus(response,"<span>Deleted Successfully</span>","<span>Deleted Failed</span>");
            }
            else if(param.startsWith("command=updateCredential")){
            	if(ManagerUI.setActionStatus(response, "<span>Updated Successfully</span>", "<span>Update Failed</span>"));
                ManagerUI.deleteSelectedRow();
            }
            else if(param.startsWith("command=show") && response.startsWith("<tr class='rows'>")){
			table.innerText = "";
            table.insertAdjacentHTML("beforeend", response);
            ManagerUI.removeLoadingBar();
            }
            else{
               ipCount = response;
               ManagerUI.loadPagination();
            }
       }
        ManagerUI.doAjax = function(){
            var xhr = new XMLHttpRequest;
            xhr.open('POST','ManagerUI',true);
            xhr.onreadystatechange = function()
            {
                if(xhr.readyState==4 && this.status==200)
                {
                    var response = this.responseText;
                    ManagerUI.displayActionStatus(response);
                    }
                }
            xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            xhr.send(param);
        }
        ManagerUI.setIPCount = function(){
            param = "command=ipCount";
            ManagerUI.doAjax();
        }
        ManagerUI.changeRowsPerPage = function(){
        	pageLimit = rowsPerPage.value;
        	if(pageLimit == "All")
        		pageLimit = ipCount;
        	ManagerUI.loadPagination();
        }
        ManagerUI.hasDatabaseChanged = function(){
        	var modal = $(".modal");
        	if(!modal[0].M_Modal.isOpen){
        	previousIPCount = ipCount;
        	ManagerUI.setIPCount();
        	if(previousIPCount != ipCount)
        		ManagerUI.loadPagination();
        	else
        		$("#firstPage").click();
        	}
        }
        setInterval(ManagerUI.hasDatabaseChanged, 5000);
        ManagerUI.setIPCount();
        //showTable();
        rowsPerPage.addEventListener('change', ManagerUI.changeRowsPerPage);
        ip.addEventListener("focusout", ManagerUI.validateIP);
        addButton.addEventListener("click", ManagerUI.addCredential);
        deleteButton.addEventListener("click", ManagerUI.deleteCredentials);
        editButton.addEventListener("click", ManagerUI.editCredentials);
        updateButton.addEventListener("click", ManagerUI.updateCredentials);
        search.addEventListener('keyup', ManagerUI.searchIP)
    </script>
</html>
