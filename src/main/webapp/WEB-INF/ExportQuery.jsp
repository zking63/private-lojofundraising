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
	<title>Export</title>
	
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
<link rel="stylesheet" href="/css/main.css"/>
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
	    <form:form method="POST" action="/committees/select" class="p-4">
	    <input type="hidden" name="page" value="${page}">
	        <p>
		        <label for="committee"></label>
				<select onchange="this.form.submit()" id="committee" name="committee">
					<option class="currentcommittee" value="${ committee.id }">${ committee.getCommitteeName() }</option>
				  	<c:forEach items="${ committees }" var="e">
			        	<option value="${ e.id }">${ e.getCommitteeName() }</option>
			        </c:forEach>
				</select>
	        </p>
	    </form:form>
        <ul class="navbarmenu">
            <li>
            <button class="btn btn-secondary main">
			<a href="/home">Home</a>
			</button>
            </li>
            <li>
           		<div class="dropdown">
				  <button class="btn btn-secondary dropdown-toggle" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    Donations
				  </button>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				  	<a class="dropdown-item" href="/home">Donations</a>
				    <a class="dropdown-item" href="/newdonation">New donation</a>
				    <a class="dropdown-item" href="/import/donations">Import donations</a>
				    <a class="dropdown-item" href="/export">Export donations</a>
				 	</div>
				</div>
            </li>
            <li>
           		<div class="dropdown">
				  <button class="btn btn-secondary dropdown-toggle" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    Donors
				  </button>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				  	<a class="dropdown-item" href="/donors">Donors page</a>
				    <a class="dropdown-item" href="/newdonor">New donor</a>
				    <a class="dropdown-item" href="/import/donations">Import donors by donations</a>
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
				 	<a class="dropdown-item" href="/emails">Emails</a>
				    <a class="dropdown-item" href="/newemail">New email</a>
				    <a class="dropdown-item" href="/import/emails">Import emails</a>
				    <a class="dropdown-item" href="/export">Export</a>
				 	</div>
				</div>
            </li>
            <li>
           		<div class="dropdown">
				  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    <img src="/images/usericon.png" alt="User">
				  </button>
				  <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
				  	<p>${ user.firstName } ${ user.lastName }</p>
					<form:form method="POST" action="/committees/select" class="dropdown-item formclass">
				    <input type="hidden" name="page" value="${page}">
				        <p>
					        <label for="committee"></label>
							<select onchange="this.form.submit()" id="committee" name="committee">
								<option class="currentcommittee" value="${ committee.id }">${ committee.getCommitteeName() }</option>
							  	<c:forEach items="${ committees }" var="e">
						        	<option value="${ e.id }">${ e.getCommitteeName() }</option>
						        </c:forEach>
							</select>
				        </p>
				    </form:form>
				    <div class="dropdown-divider"></div>
				    <a class="dropdown-item" href="/logout">Logout</a>
				 </div>
				</div>
            </li>
        </ul>
        </div>
    <form method="get" class="date-form" id="input-form" action="/export/query/options">
   	<p>
        <label for="field">${message}</label>
		<select onchange="this.form.submit()" id="field" name="field">
		<c:choose>
			<c:when test="${ field == 0}">
				<option name="field" value="0">Email groups</option>
				<option name="field" value="1">Emails</option>
		        <option name="field" value="2">Donations</option>
		        <option name="field" value="3">Donors</option>
		        <option name="field" value="5">Tests</option>
			</c:when>
			<c:when test="${ field == 1}">
				<option name="field" value="1">Emails</option>
		        <option name="field" value="2">Donations</option>
		        <option name="field" value="3">Donors</option>
		        <option name="field" value="5">Tests</option>
		        <option name="field" value="0">Email groups</option>
			</c:when>
			<c:when test="${ field == 2}">
				<option name="field" value="2">Donations</option>
				<option name="field" value="1">Emails</option>
				<option name="field" value="0">Email groups</option>
		        <option name="field" value="3">Donors</option>
		        <option name="field" value="5">Tests</option>
			</c:when>
			<c:when test="${ field == 3}">
				<option name="field" value="3">Donors</option>
				<option name="field" value="0">Email groups</option>
				<option name="field" value="1">Emails</option>
				<option name="field" value="2">Donations</option>
				<option name="field" value="5">Tests</option>
			</c:when>
			<c:when test="${ field == 5}">
				<option name="field" value="5">Tests</option>
				<option name="field" value="0">Email groups</option>
				<option name="field" value="1">Emails</option>
				<option name="field" value="2">Donations</option>
				<option name="field" value="3">Donors</option>
			</c:when>
			<c:otherwise>
				<option name="field" value="4">Select</option>
				<option name="field" value="0">Email groups</option>
				<option name="field" value="1">Emails</option>
		        <option name="field" value="2">Donations</option>
		        <option name="field" value="3">Donors</option>
		        <option name="field" value="5">Tests</option>
			</c:otherwise>
		</c:choose>
		</select>
    </p>
    </form>
    <c:choose>
    <c:when test="${ field != 4}">
			<div id="export-choices">
           		<div class="dropdown">
					<form:form method="get" action="/export/query/options/range" class="date-form" id="input-form">
				    <input type="hidden" name="page" value="${page}">
				    <input type="hidden" name="field" value="${field}">
				        <p>
				        <label for="range">Select time period</label>
					        <label for="range"></label>
							<select onchange="this.form.submit()" id="range" name="range">
									<option name="range" value="0">Select</option>
							  		<option name="range" value="1">Within range</option>
							  		<option name="range" value="2">All time</option>
							</select>
				        </p>
				    </form:form>
				</div>	
				</div>
							</c:when>
			</c:choose>
<c:choose>
    <c:when test="${ range == 1}">
			<div id="export-choices">
           		<div class="dropdown">
					<form:form method="get" action="/export/query/options/rangeset" class="date-form" id="input-form">
					    	<div id="date-choices">
					      	<input type="hidden" name="page" value="${page}">
				   			 <input type="hidden" name="field" value="${field}">
				  		    <input type="hidden" name="type" value="${range}">
							<input type="date" value="${startdateD}" name="startdateD"/>
							<input type="date" value="${enddateD}" name="enddateD"/>
							</div>
				    </form:form>
				</div>	
				</div>
			</c:when>
			</c:choose>
    <c:choose>
    <c:when test="${ range > 0}">
			<div id="export-choices">
           		<div class="dropdown">
					<form:form method="get" action="/export/query/options/type" class="date-form" id="input-form">
				    <input type="hidden" name="page" value="${page}">
				    <input type="hidden" name="field" value="${field}">
				        <p>
				        <label for="type">${message}</label>
					        <label for="type"></label>
							<select onchange="this.form.submit()" id="type" name="type">
							<c:choose>
							  	<c:when test="${ field == 1}">
							  		<option name="type" value="0">${ typelabel }</option>
							  		<option name="type" value="7">All emails</option>
									<option name="type" value="1">Refcode 1</option>
									<option name="type" value="2">Refcode 2</option>
									<option name="type" value="4">Title</option>
									<option name="type" value="5">Category</option>
									<option name="type" value="5">Subject line</option>
									<option name="type" value="6">Sender</option>
						        </c:when>
						        </c:choose>
							</select>
				        </p>
				    </form:form>
				</div>	
				</div>
			</c:when>
			</c:choose>
    <div id="export-form">
    <form method="get" class="date-form" action="/export/excel">
		<c:choose>
			<c:when test="${ type == 1}">
			<div id="export-choices">
           		<div class="dropdown">
					<form:form method="POST" action="/export/query/excel" class="dropdown-item formclass">
					    	<input type="hidden" name="page" value="${page}">
				   			<input type="hidden" name="field" value="${field}">
				  		    <input type="hidden" name="type" value="${range}">
				    <input type="hidden" name="startdateD" value="${startdateD}">
				    <input type="hidden" name="enddateD" value="${enddateD}">
				        <p>
				        <label for="type">${message}</label>
					        <label for="parameter"></label>
							<select>
							<c:choose>
							  	<c:when test="${ type == 1}">
							  		<option name="parameter" value="0">Select</option>
									<option name="parameter" value="1">Equals</option>
									<option name="parameter" value="2">Contains</option>
						        </c:when>
						        </c:choose>
							</select>
				        </p>
				       <p>
				        <label for="type">${message}</label>
					        <label for="option"></label>
							<c:choose>
							  	<c:when test="${ type == 1}">
									<textarea name="option" placeholder="Final parameter"></textarea>
					            </c:when>
						       </c:choose>
				        </p>
				    </form:form>
				</div>	
				</div>
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
			<c:when test="${ field == 5}">
			<div id="export-choices">
				<input type="checkbox" id="input" name="input" value="Clicks">
				<label for="input"> Clicks</label><br>
				<input type="checkbox" id="input" name="input" value="Opens">
				<label for="input"> Opens</label><br>
				<input type="checkbox" id="input" name="input" value="Open rate">
				<label for="input"> Open rate</label><br>	
				<input type="checkbox" id="input" name="input" value="Click rate">
				<label for="input"> Click rate</label><br>	
				<input type="checkbox" id="input" name="input" value="Clicks/opens">	
				<label for="input"> Clicks per open</label><br>	
				<input type="checkbox" id="input" name="input" value="Revenue">
				<label for="input"> Revenue</label><br>	
				<input type="checkbox" id="input" name="input" value="Donations">	
				<label for="input"> Donations</label><br>		
				</div>
				<div id="export-choices">	
				<input type="checkbox" id="input" name="input" value="Average donation">
				<label for="input"> Average donation</label><br>
				<input type="checkbox" id="input" name="input" value="Donations/open">
				<label for="input"> Donations per open</label><br>
				<input type="checkbox" id="input" name="input" value="Donations/click">
				<label for="input"> Donations per click</label><br>
				<input type="checkbox" id="input" name="input" value="Average email revenue">
				<label for="input"> Average email revenue</label><br>
				<input type="checkbox" id="input" name="input" value="emailcount">
				<label for="input"> Number of emails tested</label><br>
				<input type="checkbox" id="input" name="input" value="testcategory">
				<label for="input"> Test category</label><br>
				<input type="checkbox" id="input" name="input" value="Recipients">
				<label for="input"> Recipients</label><br>
				</div>
			</c:when>
		</c:choose>
		<button>Download Excel</button>
	</form>
	</div>
</body>
</html>