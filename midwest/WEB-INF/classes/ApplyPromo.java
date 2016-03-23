import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;
import java.util.*;
import java.text.*;

public class ApplyPromo extends HttpServlet {
 
	MongoClient mongo;
	
	public void init() throws ServletException
	{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	 public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
	try{
	
			
			HttpSession session=request.getSession(true);
			
			String user=((String)session.getAttribute("username"));
			String email=((String)session.getAttribute("email"));
			String promocode= request.getParameter("promo");
			
			int price = 0;
			String status="";
			status="<h3 style='color:red'>Promo Code is not valid</h3>";
			String carmake=request.getParameter("carmake");
			String cartype=request.getParameter("cartype");
			String carmodel=request.getParameter("carmodel");
			double rentalamount=Double.parseDouble(request.getParameter("rentalamount"));
			String pick= request.getParameter("pick");
			
			String drop= request.getParameter("drop");
			
			String location= request.getParameter("location");
			String age= request.getParameter("age");
			
			DB db = mongo.getDB("CarRentals");
			
			DBCollection promos = db.getCollection("promo");
			
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("promo", promocode);
			searchQuery.put("email",email);
			DBCursor cursor = promos.find(searchQuery);
			
			if(cursor.hasNext())
			{				
				rentalamount=(rentalamount*(100-15))/100;
				status="<h3 style='color:green'>Promo Code has been applied successfully</h3>";
			}
			PrintWriter out = response.getWriter();
			
			String docType =("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
					"Transitional//EN\">\n");
					
					
					
				out.println(docType+"<html>"+
				"<head>"+
				"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
				"<title>Mid-West Car Rentals</title>"+
				"<link rel='stylesheet' href='styles.css' type='text/css' />"+
				"</head>"+
				"<body>"+
				"<div id='container'>"+
				"<header>"+
				"<h1><a href='/'>MID-WEST<span>CAR RENTALS</span></a></h1>"+
				"<p>Welcome: "+user+"</p>"+
				
				
				"</header>");
				out.println("<nav>"
					+ "<ul>"
					+ "<li class='start'><a href='index.jsp'>Home</a></li>");
					
					if(user == null){					
					out.println("<li class='r'><a href='login.jsp'>Login</a></li>");
					}
					else{
					out.println("<li class='r'><a href='Logout'>Logout</a></li>");
					}
					out.println("<li class= 'r'><a href='#'>Manage Your Reservations</a></li>"
					+ "<li><a href='about-us.jsp'>About US</a></li>"
					+ "</ul>"
					+ "</nav>");
				
			out.println("<h1>Book Car</h1>"+
						status+
						"<br>"+ 
						"<form method='get' action='ReviewReservation.jsp'>"+
						"<div>"+
						"<div style='float:left;width: 469px; margin-right: 75px;'>"+
						"<fieldset>"+
						"<legend>Rental information:</legend>"+
						
						"<table>"+
				
				
						"<tr>"+
						"<td>Car Make</td>"+
						"<td><p>"+carmake+"</p> </td>"+
						"<input type='hidden' name='carmake' value='"+carmake+"' ></input>"+
						"</tr>"+
						
						"<tr>"+
						"<td>Car Type </td>"+
						"<td><p>"+cartype+"</p></td>"+
						"<input type='hidden' name='cartype' value='"+cartype+"' ></input>"+
						"</tr>"+
						
						"<tr>"+
						"<td>Car Model </td>"+
						"<td><p>"+carmodel+"</p></td>"+
						"<input type='hidden' name='carmodel' value='" +carmodel+"' ></input>"+
						"</tr>"+
						
						"<tr>"+
						"<td>Car Price </td>"+
						"<td><p>"+rentalamount+"</p></td>"+
						"<input type='hidden' name='rentalamount' value='" +rentalamount+"' ></input>"+
						"</tr>"+
						
						"<tr>"+
						"<td>Pick Up Location</td>"+
						"<td><p>"+location+"</p></td>"+
						"<input type='hidden' name='location' value='" +location+"' ></input>"+
						"</tr>"+

						"<tr>"+
						"<td>Pick Up Date and Time  </td>"+
						"<td><p>"+pick+"</p></td>"+
						"<input type='hidden' name='pick' value='" +pick+"' ></input>"+
						"</tr>"+
						
						"<tr>"+
						"<td>Drop Off Date and Time  </td>"+
						"<td><p>"+drop+"</p></td>"+
						"<input type='hidden' name='drop' value='" +drop+"' ></input>"+
						"</tr>"+
					
							
					
					
					"</table>"+
					"</fieldset>"+
				"</div>"+
				
				"<div>"+				
					"<div style='float:left;'>"+
				
						
						"<fieldset>"+
						"<legend>Personal information:</legend>"+
						"<table>"+
						"<tr>"+
						"<td> First name: </td>"+
						"<td> <input type='text' name='firstName'> </td>"+
						"</tr>"+				
						"<tr>"+
						"<td> Last name: </td>"+
						"<td><input type='text' name='lastName'> </td>"+
						"</tr>"+				
						"<tr>"+
						"<td> Address: </td>"+
						"<td> <input type='text' name='address'> </td>"+
						"</tr>"+
						"<tr>"+
						"<tr>"+
						"<td> Phone: </td>"+
						"<td> <input type='text' name='phoneNumber'> </td>"+
						"</tr>"+
						"<td> Credit Card Information: </td>"+
						"<td> <input type='text' name='creditcard'> </td>"+
						"</tr>"+
						"</table>"+
						"<br>"+
									
						"</fieldset>"+		
						
					"</div>"+
					"<br><br>"+
					"<div>"+
						"<fieldset>"+
						"<legend>Collision Damage Coverage (recommended)</legend>"+
						"<p style='margin-bottom: 4px;'>Pays up to $40,000. $0 Deductible.</p>"+
						"<p style='margin-bottom: 4px;'>Receive primary coverage without having to go through your insurance.</p>"+
						"<p style='margin-bottom: 4px;'>Covers damage to vehicle due to collision, theft, vandalism, fire, or hail.</p>"+
						"<p style='margin-bottom: 4px;'>Covers damage to vehicle due to collision, theft, vandalism, fire, or hail.</p>"+
						"<input type='radio' name='insurance' value='yes' checked>Yes, add coverage to my rental for $9/day</imput>"+
						"<br>"+
						"<input type='radio' name='insurance' value='no'>No thank you, I want to travel without coverage</input>"+
						"<br><br>"+
						"<input type='submit' class='button-primary' value='Review Reservation'>"+
						"</fieldset>"+
						
					"</div>"+
					
				"</div>"+	
					
			"</div>"+
			
			"</form>"+
			
					
			"<footer style='clear:both;'>"+ 
			"<div class='footer-bottom'>"+
            "<p>CSp 595- Project</p>"+
			"</div>"+
			"</footer>"+			
			"</body>"+
			"</html>");
	
	}
	catch (MongoException e) {
				e.printStackTrace();
		}
 }
} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
