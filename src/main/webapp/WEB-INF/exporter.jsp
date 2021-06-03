<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>   
<head>
	<meta charset="ISO-8859-1">
	<script src="jquery-1.7.1.min.js"></script>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" 
		integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
		crossorigin="anonymous">
	<link rel="stylesheet" href="/css/main.css"/>
	<title>Import</title>
	<script type="text/javascript">
		$(document).ready(function(){
		  $("#input-form").change(function() {
		     $("#field").submit();
		  });
		});
	</script>
</head>
<body>
     <div class="navbar">
     	<h1 class="titles"><a href="/home">LoJo Fundraising</a></h1>
        <ul class="navbarmenu">
            <li class="main"><a href="/home">Home</a>
            </li>
            <li><a href="/donors">Donors</a></li>
            <li><a href="/emails">Emails</a></li>
            <li><a href="/logout">Logout</a></li>
        </ul>
    </div>
    <form method="get" class="date-form" id="input-form" action="/export/select">
   	<p>
        <label for="field">${message}</label>
		<select onchange="this.form.submit()" id="field" name="field">
		<c:choose>
			<c:when test="${ field == 1}">
				<option name="field" value="1">Emails</option>
		        <option name="field" value="2">Donations</option>
		        <option name="field" value="3">Donors</option>
			</c:when>
			<c:when test="${ field == 2}">
				<option name="field" value="2">Donations</option>
				<option name="field" value="1">Emails</option>
		        <option name="field" value="3">Donors</option>
			</c:when>
			<c:when test="${ field == 3}">
				<option name="field" value="3">Donors</option>
				<option name="field" value="2">Donations</option>
				<option name="field" value="1">Emails</option>
			</c:when>
			<c:otherwise>
				<option name="field" value="4">Select</option>
				<option name="field" value="1">Emails</option>
		        <option name="field" value="2">Donations</option>
		        <option name="field" value="3">Donors</option>
			</c:otherwise>
		</c:choose>
		</select>
    </p>
    </form>
    <div id="export-form">
    <form method="get" class="date-form" id="export-form" action="/export/excel">
		<input type="hidden" value="${field}" name="field"/>
		<input type="date" value="${startdateD}" name="startdateD"/>
		<input type="date" value="${enddateD}" name="enddateD"/>
		<p>
		<c:choose>
			<c:when test="${ field == 1}">
				<input type="checkbox" id="input" name="input" value="Clicks">
				<label for="input"> Clicks</label><br>
				<input type="checkbox" id="input" name="input" value="Opens">
				<label for="input"> Opens</label><br>
				<input type="checkbox" id="input" name="input" value="Bounces">
				<label for="input"> Bounces</label><br>	
				<input type="checkbox" id="input" name="input" value="Unsubscribes">
				<label for="input"> Unsubscribes</label><br>	
				<input type="checkbox" id="input" name="input" value="Open rate">
				<label for="input"> Open rate</label><br>	
				<input type="checkbox" id="input" name="input" value="Click rate">
				<label for="input"> Click rate</label><br>	
				<input type="checkbox" id="input" name="input" value="Unsubscribe rate">	
				<label for="input"> Unsubscribe rate</label><br>	
				<input type="checkbox" id="input" name="input" value="Bounce rate">
				<label for="input"> Bounce rate</label><br>	
				<input type="checkbox" id="input" name="input" value="Clicks/opens">	
				<label for="input"> Clicks per open</label><br>	
				<input type="checkbox" id="input" name="input" value="Revenue">
				<label for="input"> Revenue</label><br>		
				<input type="checkbox" id="input" name="input" value="Donations">	
				<label for="input"> Donations</label><br>	
				<input type="checkbox" id="input" name="input" value="Donors">
				<label for="input"> Donors</label><br>	
				<input type="checkbox" id="input" name="input" value="Average donation">
				<label for="input"> Average donation</label><br>	
				<input type="checkbox" id="input" name="input" value="Donations/open">
				<label for="input"> Donations per open</label><br>
				<input type="checkbox" id="input" name="input" value="Donations/click">
				<label for="input"> Donations per click</label><br>
				<input type="checkbox" id="input" name="input" value="Donors/open">
				<label for="input"> Donors per open</label><br>
				<input type="checkbox" id="input" name="input" value="Donors/click">
				<label for="input"> Donors per click</label><br>
				<input type="checkbox" id="input" name="input" value="Recurring donations">
				<label for="input"> Recurring donations</label><br>
				<input type="checkbox" id="input" name="input" value="Recurring donors">
				<label for="input"> Recurring donors</label><br>
				<input type="checkbox" id="input" name="input" value="Recurring revenue">
				<label for="input"> Recurring revenue</label><br>
			</c:when>
			<c:when test="${ field == 2}">
			    <input type="checkbox" id="input" name="input" value="Amount">
				<label for="input"> Amount</label><br>
				<input type="checkbox" id="input" name="input" value="Date">
				<label for="input"> Date</label><br>
			</c:when>
			<c:when test="${ field == 3}">
			    <input type="checkbox" id="input" name="input" value="FirstName">
				<label for="input"> Name</label><br>
				<input type="checkbox" id="input" name="input" value="LastName">
				<label for="input"> Last</label><br>
			</c:when>
		</c:choose>
		</p>
		<button>Download Excel</button>
	</form>
	</div>
</body>
</html>