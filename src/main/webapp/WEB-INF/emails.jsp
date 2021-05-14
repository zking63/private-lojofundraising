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
	<title>Emails</title>
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
	<div class="wrapper">
	<h2>Emails</h2>
	<form method="post" class="date-form" action="/emails">
		<input type="date" value="${startdateE}" name="startdateE"/>
		<input type="date" value="${enddateE}" name="enddateE"/>
		<button>Set</button>
	</form>
	<table class="table table-hover">
	    <thead>
	        <tr>
	            <th>Name</th>
	            <th>Send date</br> 		
		            <form class="pointer" method="post" action="/emails/sortdown">
						<input type="hidden" name="field" value="datetime">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/emails/sortup">
						<input type="hidden" name="field" value="datetime">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>v</button>
					</form>
				</th>
	            <th>Refcode</th>
	            <th>Total revenue</br> 
	            	 <form class="pointer" method="post" action="/emails/sortdown">
						<input type="hidden" name="field" value="sum">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/emails/sortup">
						<input type="hidden" name="field" value="sum">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>v</button>
					</form>
	            </th>
	            <th>Average Donation</br> 
	            	<form class="pointer" method="post" action="/emails/sortdown">
						<input type="hidden" name="field" value="average">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/emails/sortup">
						<input type="hidden" name="field" value="average">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>v</button>
					</form>
	            </th>
	            <th>Number of donations</br> 
	                <form class="pointer" method="post" action="/emails/sortdown">
						<input type="hidden" name="field" value="donationscount">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/emails/sortup">
						<input type="hidden" name="field" value="donationscount">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>v</button>
					</form>
	            </th>
	            <th>Number of donors</br>
	               <form class="pointer" method="post" action="/emails/sortdown">
						<input type="hidden" name="field" value="donorcount">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/emails/sortup">
						<input type="hidden" name="field" value="donorcount">
						<input type="hidden" name="startdateE" value="${ startdateE}">
						<input type="hidden" name="enddateE" value="${ enddateE}">
						<button>v</button>
					</form>
	            </th>
	            <th>Action</th>
	        </tr>
	    </thead>
		<tbody>
			<c:forEach items="${ email }" var="e">
				<tr>
					<td><a href="/emails/${e.id}">${ e.emailName }</a></td>
					<td>${e.getEmailDateFormatted()}</td>
					<td>${e.emailRefcode}</td>
					<td>$${e.emaildata.getEmailsum()}</td>
					<td>$${e.emaildata.getEmailAverageFormatted()}</td>
					<td>${e.emaildata.getDonationcount()}</td>
					<td>${e.emaildata.getDonorcount()}</td>
					<td>
						<p><a href="/emails/edit/${e.id}">Edit</a></p>
						<p><a href="/emails/delete/${e.id}">Delete</a></p>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>