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
	<h2>Donors</h2>
	<form method="post" class="date-form" action="/donors">
		<input type="date" value="${startdateD}" name="startdateD"/>
		<input type="date" value="${enddateD}" name="enddateD"/>
		<button>Set</button>
	</form>
	<table class="table table-hover">
	    <thead>
	        <tr>
	            <th>Name</th>
	            <th>Email address</th>
	            <th>Most recent donation in range amount</br>
	            	<form class="pointer" method="post" action="/donors/sortdown">
						<input type="hidden" name="field" value="mostrecentamount">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/donors/sortup">
						<input type="hidden" name="field" value="mostrecentamount">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>v</button>
					</form>
	            </th>
	            <th>Most recent donation in range date</br>
	            	<form class="pointer" method="post" action="/donors/sortdown">
						<input type="hidden" name="field" value="latestdonation">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/donors/sortup">
						<input type="hidden" name="field" value="latestdonation">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>v</button>
					</form>
	            </th>
	            <th>Times given</br>
	            	<form class="pointer" method="post" action="/donors/sortdown">
						<input type="hidden" name="field" value="donationcount">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/donors/sortup">
						<input type="hidden" name="field" value="donationcount">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>v</button>
					</form>
	            </th>
	            <th>Times given within range</th>
	            <th>Total given</br>
	            <form class="pointer" method="post" action="/donors/sortdown">
						<input type="hidden" name="field" value="donorsum">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/donors/sortup">
						<input type="hidden" name="field" value="donorsum">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>v</button>
					</form>
	            </th>
	            <th>Total within range</th>
	            <th>Average donation amount</br>
	            	<form class="pointer" method="post" action="/donors/sortdown">
						<input type="hidden" name="field" value="donoraverage">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>^</button>
					</form>
					<form class="pointer" method="post" action="/donors/sortup">
						<input type="hidden" name="field" value="donoraverage">
						<input type="hidden" name="startdateD" value="${startdateD}">
						<input type="hidden" name="enddateD" value="${enddateD}">
						<button>v</button>
					</form>
	            </th>
	            <th>Average within range</th>
	            <th>Action</th>
	        </tr>
	    </thead>
		<tbody>
			<c:forEach items="${ donor }" var="d">
				<tr>
					<td><a href="/donors/${d.id}">${ d.donorFirstName } ${d.donorLastName}</a></td>
					<td>${d.donorEmail}</td>
					<td>$${d.getMostrecentInrangeAmount()}</td>
					<td>${d.getMostRecentDateinRange()}</td>
					<td>${d.getDonordata().getDonor_contributioncount()}</td>
					<td>${d.countwithinrange}</td>
					<td>$${d.getDonordata().getDonorSumFormatted() }</td>
					<td>$${d.sumwithinrange}</td>
					<td>$${d.getDonordata().getDonorAverageFormatted() }</td>
					<td>$${d.averagewithinrange}</td>
					<td>
					<p><a href="/donors/edit/${d.id}">Edit</a></p>
					<p><a href="/donors/delete/${d.id}">Delete</a></p>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>