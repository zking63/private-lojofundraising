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
	<title>Donors</title>
	

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
<link rel="stylesheet" href="/css/main.css"/>
</head>

<body>
     <div class="navbar">
        <ul class="navbarmenu">
            <li class="main"><a href="/home">Home</a>
            </li>
            <li>
           		<div class="dropdown">
				  <p class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    Donors
				  </p>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				    <a class="dropdown-item" href="/newdonor">New donor</a>
				    <a class="dropdown-item" href="/donors">Donors page</a>
				    <a class="dropdown-item" href="/export">Export donors</a>
				 	</div>
				</div>
            </li>
            <li>
           		<div class="dropdown">
				  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    Emails
				  </button>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				    <a class="dropdown-item" href="/newemail">New email</a>
				    <a class="dropdown-item" href="/emails">Emails page</a>
				    <a class="dropdown-item" href="/export">Export donors</a>
				 	</div>
				</div>
            </li>
            <li><a href="/logout">Logout</a></li>
        </ul>
</body>
</html>