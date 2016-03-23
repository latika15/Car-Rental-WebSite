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


public class ViewReservations extends HttpServlet {
 
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
			if(user.equals("admin")){
				response.sendRedirect("ViewAllReservations");
			}
			DB db = mongo.getDB("CarRentals");
			
			DBCollection myOrders = db.getCollection("reservation_details");
			
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("user", user);
			DBCursor cursor = myOrders.find(searchQuery);
			PrintWriter out = response.getWriter();
			
			String docType =
					"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
					"Transitional//EN\">\n";
					
					
					
				out.println(docType+"<html>"+
				"<head>"+
				"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
				"<title>Mid-West Car Rentals</title>"+
				"<link rel='stylesheet' href='styles.css' type='text/css' />"+
				"</head>"+
				"<body>"+
				"<div id='container'>"+
				"<header>"+
				"<h1><a href='index.jsp'><img src='images/12.png'></a><h1>"+ 
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
					out.println("<li class= 'r'><a href='ViewReservations'>Manage Your Reservations</a></li>"
					+ "<li><a href='about-us.jsp'>About US</a></li>"
					+ "</ul>"
					+ "</nav>");
			
			
			
			
			out.println("<br><br><hr>");
			out.println("<h1>My Reservations</h1>");
			if(cursor.count() == 0){
				out.println("There are no reservartion for this User.");
			}
			else{
			
				out.println("<table>"+
				"<tr>"+
				"<th>User</th>"+
				"<th>Confirmation Number</th>"+
				"<th>Pick Up Date and Time</th>"+
				"<th>Drop Off Date and Time</th>"+
				"<th>Car Make</th>"+
				"<th>Car Type</th>"+
				"<th>Car Model</th>"+
				"<th>Total Price</th>"+
				"<th>First Name</th>"+
				"<th>Last Name</th>"+
				"<th>Address</th>"+
				"<th>Phone Number</th>"+
				"<th>Status</th>"+
				"</tr>");
				
				
				String userid="";
				String confirmationnumber = "";
				String pick="";
				String drop="";
				String firstName = "";
				String lastName = "";
				String address="";
				String phoneNumber="";
				String carmake="";
				String cartype="";
				String totalprice="";
				String carmodel="";
				String status="";
				
				while (cursor.hasNext()) {
					
					BasicDBObject obj = (BasicDBObject) cursor.next();				
					
					out.println("<tr>");
					
					userid = obj.getString("user");
					out.println("<td>" +userid+ "</td>");

					confirmationnumber = obj.getString("confirmation_number");
					out.println("<td>" +confirmationnumber+ "</td>");

					pick =obj.getString("pick");
					out.println("<td>" +pick+ "</td>");
					
					drop = obj.getString("drop");
					out.println("<td>" +drop+ "</td>");
	
					carmake = obj.getString("carmake");
					out.println("<td>" +carmake+ "</td>");
					
					cartype = obj.getString("cartype");
					out.println("<td>" +cartype+ "</td>");
					
					carmodel = obj.getString("carmodel");
					out.println("<td>" +carmodel+ "</td>");
									
					totalprice = obj.getString("total_price");
					out.println("<td>" +totalprice+ "</td>");
					
					
					firstName = obj.getString("first_name");
					out.println("<td>" +firstName+ "</td>");
					
					lastName = obj.getString("last_name");
					out.println("<td>" +lastName+ "</td>");
					
					address = obj.getString("address");
					out.println("<td>" +address+ "</td>");
					
					phoneNumber = obj.getString("phone_number");
					out.println("<td>" +phoneNumber+ "</td>");
					
					status = obj.getString("status");
					out.println("<td>" +status+ "</td>");
				
					out.println("</tr>");
					
				}
			}	
				
				
				String backURL =
          response.encodeURL("/midwest/index.jsp");
       
        // "Proceed to Checkout" button below table
				   
		   out.println("</table><form method='POST' action='CancelReservation'>"+
			"<p>Enter the Confirmation Number which you want to Cancel<br>reservation cannot be cancelled within 24hr of pickup time</p>"+
			"<input type='text' name='confirmation_number'>"+
			"<input class='delete-button' type='submit' name='submit' value='Cancel'>"+
			"</form>");
			
			 out.println
          ("<FORM ACTION=\"" + backURL + "\" METHOD=\"post\">\n" +
           "<BIG><CENTER>\n" +
		  
           "<INPUT class='blue-button' TYPE=\"SUBMIT\"\n" +
           "       VALUE=\"Go to Home Page\">\n" +
		   
           "</CENTER></BIG></FORM>");
				String myPageBottom = "<footer>"
                    + "<div class=\"footer-bottom\">"
                    + "<p>FALL 2015 - CSP595 Project by TEAM - 5</p>"
                    + "</div>"
                    + "</footer>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
        
        out.println(myPageBottom);
			
		} catch (MongoException e) {
				e.printStackTrace();
		}
	}	
		public void destroy(){
      // do nothing.
	}
}











