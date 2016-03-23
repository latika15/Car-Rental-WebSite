
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Mid-West Car Rentals</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>
<body>
<div id="container">
    <header>
    	<%@include file="global/header.jsp"%>
    </header>
    <nav>
		<%@include file="global/nav.jsp"%>
    </nav>
<div id="body">		
	<section id="content">
	    <article>	
		<h2>Add Cars</h2>
				  <form method="post" action="/midwest/AddCarServlet">
				  
				  <table cellpadding='2' cellspacing='1'>
					<tr>
						<td>Car Id</td>
						<td><input type="TEXT" size="15" name="carId"></input></td>
					</tr>
					<tr>
						<td>Car Make</td>
						<td><input type="text" size="15" name="carMake"/></td>
					</tr>
					 <tr>
						<td>Car Type</td>
						<td><select name="carType">
				  <option value="SUV">SUV</option>
				  <option value="Standard">Standard</option>
				  <option value="Economy">Economy</option>
				  <option value="Luxury">Luxury</option>  
				</select></td>
					</tr>
					 <tr>
						<td>Car Model</td>
						<td><input type="TEXT" size="15" name="carModel"></input></td>
					</tr>
					<tr>
						<td>Car Number</td>
						<td><input type="TEXT" size="15" name="carNumber"></input></td>
					</tr>
					<tr>
						<td>Rental Amount</td>
						<td><input type="TEXT" size="15" name="rentalAmount"></input></td>
					</tr>
					<tr>
						<td>Discount</td>
						 <td><input type = "text" name = "discount"></td>
					</tr>
					<tr>
						<td>Location</td>
						 <td><input type = "text" name = "location"></td>
					</tr>
					 <tr>
						<td colspan='2'>
							<center><input type="submit" class ="formbutton" value="Add Car" /></center>
							
						</td>
					</tr>
					</table>
				</form >
		</article>
    </section>    
    <aside class="sidebar">
				<ul>
				<li> <h4>Quick Links</h4><ul><li><a href="AdminServlet">Home</a></li>
				<li><a href='AddCar.jsp'>Add Car</a></li>
				<li><a href='#'>Add/Manage Reservations</a></li>
				</li>
				</ul>
    </aside>
	<div class="clear"></div>
	</div>
	<footer>
       
        <div class="footer-bottom">
           <p>FALL 2015 - CSP595 Project by TEAM - 5</p>
        </div>
    </footer>
</div>
</body>
</html>