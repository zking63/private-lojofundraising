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
	<script>
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
        <label for="field">What are you exporting?</label>
		<select onchange="this.form.submit()" id="field" name="field">
			<option name="field" value="${field}">${field}</option>
	        <option name="field" value="emails">Emails</option>
	        <option name="field" value="donations">Donations</option>
	        <option name="field" value="donors">Donors</option>
		</select>
		<button>Select</button>
    </p>
    </form>
    <form method="get" class="date-form" action="/export/excel">

		<input type="date" value="${startdateD}" name="startdateD"/>
		<input type="date" value="${enddateD}" name="enddateD"/>
		<c:choose>
			<c:when test="${ field.equals(emails)}">
				<input type="checkbox" id="input" name="input" value="Clicks">
				<label for="input"> Clicks</label><br>
				<input type="checkbox" id="input" name="input" value="Opens">
				<label for="input"> Opens</label><br>
				<input type="checkbox" id="input" name="input" value="Bounces">
				<label for="input"> Bounces</label><br>						
			</c:when>
			<c:when test="${ field == donations}">
			    <input type="checkbox" id="input" name="input" value="Amount">
				<label for="input"> Amount</label><br>
				<input type="checkbox" id="input" name="input" value="Date">
				<label for="input"> Date</label><br>
			</c:when>
			<c:when test="${ field == donors}">
			    <input type="checkbox" id="input" name="input" value="FirstName">
				<label for="input"> Name</label><br>
				<input type="checkbox" id="input" name="input" value="LastName">
				<label for="input"> Last</label><br>
			</c:when>
		</c:choose>
		<button>Download Excel</button>
	</form>
</body>
</html>