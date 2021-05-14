<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" 
		integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
		crossorigin="anonymous">
	<link rel="stylesheet" href="/css/main.css"/>
    <title>Update email page</title>
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
    <div class="buttons1">
    <button><a href="/newdonor">Upload a new donor</a></button>
	<button><a href="/newdonation">Upload a new donation</a></button>
	<button><a href="/newemail">Upload a new email</a></button>
	</div>
	<div class="user-form">
	<h1>Edit an email</h1>
	    <form:form method="POST" action="/emails/edit/${email.id}" modelAttribute="email">
	    	<form:hidden value="${ user.id }" path="email_uploader"/>
	    	<p>
	            <form:label path="emailName">Name:</form:label>
	            <form:errors path="emailName"></form:errors>
	            <form:input type="emailName" path="emailName"/>
	        </p>
	        <p>
	            <form:label path="emailRefcode">Refcode:</form:label>
	            <form:errors path="emailRefcode"></form:errors>
	            <form:input type="emailRefcode" path="emailRefcode"/>
	        </p>
	        <p>
	            <form:label path="Emaildate">Date:</form:label>
	            <form:errors path="Emaildate"></form:errors>
	            <form:input type="Emaildate" path="Emaildate"/>
	        </p>
	        <input type="submit" value="Upload!"/>
	    </form:form>
	</div> 
</body>
</html>