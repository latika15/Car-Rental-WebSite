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
		
		
	
		String carid=request.getParameter("carid");
		String carmake=request.getParameter("carmake");
		String cartype=request.getParameter("cartype");
		String carmodel=request.getParameter("carmodel");
		double rentalamount=Double.parseDouble(request.getParameter("rentalamount"));
		//double price=Integer.parseInt(rentalamount);
		String location=request.getParameter("location");
		String pick=request.getParameter("pick");
		String drop= request.getParameter("drop");
		
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String address=request.getParameter("address");
		String phoneNumber=request.getParameter("phoneNumber");
		String creditcard=request.getParameter("creditcard");
		
		String insurance=request.getParameter("insurance");
		
		
	%>
	
	
	<h1>Review Reservation</h1>
			
			<form method="get" action="ReserveCar">
			<div>
				<div style="float:left;width: 469px; margin-right: 75px;">
					<fieldset>
					<legend>Rental information:</legend>
					
					<table>
			
			
					<tr>
					<td>Car Make</td>
					<td><p><%= carmake%></p> </td>
					<input type="hidden" name="carmake" value="<%= carmake%>" ></input>
					<input type="hidden" name="carid" value="<%= carid%>" >
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
					<td>Total Price </td>
					<% if(insurance.equals("yes")) { 
						rentalamount=rentalamount+9;
						}
					
					%> 
					<td><p>$<%= rentalamount%></p></td>
					<input type="hidden" name="rentalamount" value="<%= rentalamount%>" ></input>
					</tr>
					
					<tr>
					<td>Pick Up Location  </td>
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
				
				<div style="float:left;width:300px;">				
					<div>
				
						<form method="get" action="ReviewReservation.jsp">
						<fieldset>
						<legend>Personal information:</legend>
						<table>
						<tr>
						<td> First name: </td>
						<td><p><%= firstName%></p></td>
						<input type="hidden" name="firstName" value="<%= firstName%>" ></input>
						</tr>				
						<tr>
						<td> Last name: </td>
						<td><p><%= lastName%></p></td>
						<input type="hidden" name="lastName" value="<%= lastName%>" ></input>
						</tr>				
						<tr>
						<td> Address: </td>
						<td><p><%= address%></p></td>
						<input type="hidden" name="address" value="<%= address%>" ></input>
						</tr>
						<tr>
						<tr>
						<td> Phone: </td>
						<td><p><%= phoneNumber%></p></td>
						<input type="hidden" name="phoneNumber" value="<%= phoneNumber%>" ></input>
						</tr>
						<td> Credit Card Information: </td>
						<td><p><%= creditcard%></p></td>
						<input type="hidden" name="creditcard" value="<%= creditcard%>" ></input>
						</tr>
						</table>
						<br>
									
						</fieldset>		
						</form>
					</div>
					<br><br>
					<div>
						<fieldset>
						<legend>Collision Damage Coverage (recommended)</legend>
						<% if(insurance.equals("yes")) { %>
						<p> You have opted for insurance and insurance charges have been added to your total cost</p>
						
						<% } else { %>
						
						<p>You have not selected the insurance</p>
						
						<% } %>
						<input type="hidden" name="insurance" value="<%= insurance%>" ></input>
						<input style="margin-bottom: 24px;" type="submit" class="blue-button" value="Reserve">
						<br>
						</fieldset>
						
					</div>
				</div>	
					
					
			</div>
			</form>
			<footer style="clear:both;"> 
			<div class='footer-bottom'>
            <p>FALL 2015 - CSP595 Project by TEAM - 5</p>
			</div>
			</footer>			
			</body>
			</html>
			
			