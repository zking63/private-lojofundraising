<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<link rel="stylesheet" href="/css/main.css" />
    <title>Login Page</title>
</head>
<body>
	<div class="container">
			<p><form:errors path="user.*"/></p>
	   	    <form:form class="user-form" method="POST" action="/" modelAttribute="user">
	   	    <h1>Register!</h1>
	    	<p>
	            <form:label path="firstName">First Name:</form:label>
	            <form:input type="firstName" path="firstName"/>
	        </p>
	        <p>
	            <form:label path="lastName">Last Name:</form:label>
	            <form:input type="lastName" path="lastName"/>
	        </p>
	        <p>
	            <form:label path="email">Email:</form:label>
	            <form:input type="email" path="email"/>
	        </p>
	        <p>
	            <form:label path="password">Password:</form:label>
	            <form:password path="password"/>
	        </p>
	        <p>
	            <form:label path="passwordConfirmation">Confirm Password:</form:label>
	            <form:password path="passwordConfirmation"/>
	        </p>
	        <input type="submit" value="Register!"/>
	    </form:form>
	    <form class="user-form" method="post" action="/login">
	    	<p><c:out value="${ error }" /></p>
		    <h1>Login</h1>
	        <p>
	            <label type="email" for="email">Email</label>
	            <input type="text" id="email" name="email"/>
	        </p>
	        <p>
	            <label for="password">Password</label>
	            <input type="password" id="password" name="password"/>
	        </p>
	        <input type="submit" value="Login!"/>
	    </form> 
	</div>   
</body>
</html>