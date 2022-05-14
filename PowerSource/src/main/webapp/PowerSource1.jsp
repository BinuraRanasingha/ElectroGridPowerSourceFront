<%@page import="model.PowerSource"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.1.min.js"></script>
<script src="Components/powersource1.js"></script>
</head>
<body>
<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Power Source Management</h1>

				<form id="formPower" name="formPower" method="post" action="PowerSource1.jsp">


						Power Source Name: <input id="pName" name="pName" type="text"
						class="form-control form-control-sm"> 
						
						<br>PowerSource Address: <input id=address name="address" type="text"
						class="form-control form-control-sm"> 
						
						<br>Province: <input id=province name="province" type="text"
						class="form-control form-control-sm"> 
						
						<br>Type of power source: <input id="powerSourceType" name="powerSourceType" type="text" class="form-control form-control-sm">
					
						
						
						<br> Power generated: <input id="power" name="power" type="text"
						class="form-control form-control-sm">
						
						<br> Maintenance Day: <input id="mDay" name="mDay" type="text"
						class="form-control form-control-sm">
						
						<br> Employee ID of head engineer: <input id="engID" name="engID" type="text"
						class="form-control form-control-sm">
						
						
						
						<br> <input id="btnSave" name="btnSave" type="button" value="Save" 
						class="btn btn-primary"> <input type="hidden"
						id="hidPowerIDSubmit" name="hidPowerIDSubmit" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divPowerSourceGrid">
					<%
						PowerSource ps = new PowerSource();
						out.print(ps.readPowerSource());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>