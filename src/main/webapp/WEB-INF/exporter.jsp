<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>   
<head>
	<meta charset="ISO-8859-1">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" 
		integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
		crossorigin="anonymous">
	<link rel="stylesheet" href="/css/main.css"/>
	<title>Import</title>
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
    <form method="get" class="date-form" action="/export/excel">
		<input type="date" value="${startdateD}" name="startdateD"/>
		<input type="date" value="${enddateD}" name="enddateD"/>
		<p>
	        <label for="field">What are you exporting?</label>
			<select id="field" name="field">
		        <option name="field" value="Emails">Emails</option>
		        <option name="field" value="Donations">Donations</option>
		        <option name="field" value="Donors">Donors</option>
			</select>
	    </p>
		<button>Download Excel</button>
	</form>
	<button><a th:href="/@{/export/excel}">Export to Excel</a></button>
	<button><a th:href="/export/excel">Export to Excel</a></button>
	 <form method="get" action="/export/excel">
		<button>Download Excel</button>
	</form>
</body>
</html>