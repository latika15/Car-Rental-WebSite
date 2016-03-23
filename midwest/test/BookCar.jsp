<html>

<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Mid-West Car Rentals</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>

<body>
<% String username=((String)session.getAttribute("username")); %>
<div id="container">
    <header>
    	<%@include file="global/header.jsp"%>
		<p>Welcome <%= username %>
    </header>

	<nav>
		<%@include file="global/nav.jsp"%>
    </nav>
		
	<%@ page import="java.util.*,java.text.*, java.io.*,com.mongodb.*" %>
	
	
	<% 
		
		
		int price = 0;
		
		String carmake=request.getParameter("carmake");
		String cartype=request.getParameter("cartype");
		String carmodel=request.getParameter("carmodel");
		double rentalamount=Double.parseDouble(request.getParameter("rentalamount"));
		String pickupdate= request.getParameter("pickupdate");
		String pickuptime= request.getParameter("pickuptime");
		String pick= pickupdate+' '+pickuptime;
		String dropoffdate= request.getParameter("dropoffdate");
		String dropofftime= request.getParameter("dropofftime");
		String drop= dropoffdate+' '+dropofftime;
		String location= request.getParameter("pickuplocation");
		String age= request.getParameter("age");
	%>
		
			<h1>Book Car</h1>							
			<br> 
			<form method="get" action="ReviewReservation.jsp">
			<div>
				<div style="float:left;width: 469px; margin-right: 75px;">
					<fieldset>
					<legend>Rental information:</legend>
					
					<table>
			
			
					<tr>
					<td>Car Make</td>
					<td><p><%= carmake%></p> </td>
					<input type="hidden" name="carmake" value="<%= carmake%>" ></input>
					</tr>
					
					<tr>
					<td>Car Type </td>
					<td><p><%= cartype%></p></td>
					<input type="hidden" name="cartype" value="<%= cartype%>" ></input>
					</tr>
					
					<tr>
					<td>Car Model </td>
					<td><p><%= carmodel%></p></td>
					<input type="hidden" name="carmodel" value="<%= carmodel%>" ></input>
					</tr>
					
					<tr>
					<td>Car Price </td>
					<td><p><%= rentalamount%></p></td>
					<input type="hidden" name="rentalamount" value="<%= rentalamount%>" ></input>
					</tr>
					
					<tr>
					<td>Pick Up Location</td>
					<td><p><%= location%></p></td>
					<input type="hidden" name="location" value="<%= location%>" ></input>
					</tr>

					<tr>
					<td>Pick Up Date and Time  </td>
					<td><p><%= pick%></p></td>
					<input type="hidden" name="pick" value="<%= pick%>" ></input>
					</tr>
					
					<tr>
					<td>Drop Off Date and Time  </td>
					<td><p><%= drop%></p></td>
					<input type="hidden" name="drop" value="<%= drop%>" ></input>
					</tr>
					
							
					
					
					</table>
					</fieldset>
				</div>
				
				<div>				
					<div style="float:left;">
				
						
						<fieldset>
						<legend>Personal information:</legend>
						<table>
						<tr>
						<td> First name: </td>
						<td> <input type="text" name="firstName"> </td>
						</tr>				
						<tr>
						<td> Last name: </td>
						<td> <input type="text" name="lastName"> </td>
						</tr>				
						<tr>
						<td> Address: </td>
						<td> <input type="text" name="address"> </td>
						</tr>
						<tr>
						<tr>
						<td> Phone: </td>
						<td> <input type="text" name="phoneNumber"> </td>
						</tr>
						<td> Credit Card Information: </td>
						<td> <input type="text" name="creditcard"> </td>
						</tr>
						</table>
						<br>
									
						</fieldset>		
						
					</div>
					<br><br>
					<div>
						<fieldset>
						<legend>Collision Damage Coverage (recommended)</legend>
						<p style="margin-bottom: 4px;">Pays up to $40,000. $0 Deductible.</p>
						<p style="margin-bottom: 4px;">Receive primary coverage without having to go through your insurance.</p>
						<p style="margin-bottom: 4px;">Covers damage to vehicle due to collision, theft, vandalism, fire, or hail.</p>
						<p style="margin-bottom: 4px;">Covers damage to vehicle due to collision, theft, vandalism, fire, or hail.</p>
						<input type="radio" name="insurance" value="yes" checked>Yes, add coverage to my rental for $9/day
						<br>
						<input type="radio" name="insurance" value="no">No thank you, I want to travel without coverage
						<br><br>
						<input type="submit" class="button-primary" value="Review Reservation">
						</fieldset>
						
					</div>
					
				</div>	
					
			</div>
			
			</form>
			<form method="get" action="ApplyPromo">
					<div>
					<table>
					<tr>
					<td style="width: 108px;"><b>PROMO CODE</b></td>
					<td><input type = "text" name="promo" value=""></input></td>
					<td style="width: 100px;"><input type="submit" name="applypromo" value="APPLY"></input></td>
					<tr>
					</table>
					<input type="hidden" name="carmake" value="<%= carmake%>" ></input>
					<input type="hidden" name="cartype" value="<%= cartype%>" ></input>
					<input type="hidden" name="carmodel" value="<%= carmodel%>" ></input>
					<input type="hidden" name="rentalamount" value="<%= rentalamount%>" ></input>
					<input type="hidden" name="location" value="<%= location%>" ></input>
					<input type="hidden" name="pick" value="<%= pick%>" ></input>
					<input type="hidden" name="drop" value="<%= drop%>" ></input>
					<input type="hidden" name="age" value="<%= age%>" ></input>
					
					
					
					
					
					</div>
			</form>		
					
			<footer style="clear:both;"> 
			<div class='footer-bottom'>
            <p>CSP 595 - Enterprise Web Application - Assignment 1</p>
			</div>
			</footer>			
			</body>
			</html>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
