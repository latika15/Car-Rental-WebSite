import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.text.*;
public class WriteReview extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	
	public void init(){
		//Connect to Mongo DB
		MongoClient mongo = new MongoClient("localhost", 27017);
						
		// if database doesn't exists, MongoDB will create it for you
		DB db = mongo.getDB("CustomerReviews");
		
		DBCollection myReviews = db.getCollection("myReviews");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();

		
		try{
			//Get the values from the form
			
			String carmake=request.getParameter("carmake");
			String cartype=request.getParameter("cartype");
			String carmodel=request.getParameter("carmodel");
			String price=request.getParameter("price");
			String id=request.getParameter("carid");
		
			HttpSession session=request.getSession(true); 
			String user=((String)session.getAttribute("username"));
			
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
					out.println("<li class= 'r'><a href='#'>Manage Your Reservations</a></li>"
					+ "<li><a href='about-us.jsp'>About US</a></li>"
					+ "</ul>"
					+ "</nav>");
										out.println("<p>Write the Review</p>");
			
			
									
			out.println(" <h3>" +carmake+" "+carmodel+"</h3> ");
			out.println("<form method=\"get\" action=\"/midwest/SubmitReview\">");
			out.println("<fieldset>");
			out.println("<legend>Car Information</legend>");
			out.println("<input type=\"hidden\" name= \"id\" readonly value = \""+id+"\">");
			out.println("<table>");
			
			out.println("<tr>");
			out.println("<td style='width:30%'> Car Make: </td>");
			out.println("<td><input type=\"text\" name= \"carmake\" readonly value = \""+carmake+"\"></td>");
			out.println("</tr>");
			
			
			out.println("<tr>");
			out.println("<td style='width:30%'> Car Type: </td>");
			out.println("<td><input type=\"text\" name= \"cartype\" readonly value = \""+cartype+"\"></td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td style='width:30%'> Car Model: </td>");
			out.println("<td><input type=\"text\" name= \"carmodel\" readonly value = \""+carmodel+"\"></td>");
			out.println("</tr>");
			
			out.println("</table>");
			out.println("</fieldset>");
			out.println("<fieldset>");
			out.println("<legend>Reviews:</legend>");
		
			out.println("<table>");
			
			
			
			out.println("<tr>");
			out.println("<td> Price </td>");
			out.println("<td> <input type=\"text\" name= \"price\" readonly value = \""+price+"\">  </td>");
			out.println("</tr>");
			
			
			out.println("<tr>");
			out.println("<td> Car Company</td>");
			out.println("<td> <input type=\"text\" name= \"company\" value = \""+carmake+"\" >  </td>");
			out.println("</tr>");
			
			
			out.println("<tr>");
			out.println("<td> Company Zip</td>");
			out.println("<td> <input type=\"number\" name= \"companyzip\" >  </td>");
			out.println("</tr>");
			
			
			out.println("<tr>");
			out.println("<td> Company City</td>");
			out.println("<td> <input type=\"text\" name= \"companycity\" >  </td>");
			out.println("</tr>");
			
		
			out.println("<tr>");
			out.println("<td> Company State</td>");
			out.println("<td> <input type=\"text\" name= \"companystate\" >  </td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td> Car on Sale </td>");
			out.println("<td>");
			out.println("<select name=\"sale\">");
			out.println("<option value=\"Yes\" selected>Yes</option>");
			out.println("<option value=\"No\">No</option>");
			out.println("</td>");
			out.println("</tr>");
			
			
			out.println("<tr>");
			out.println("<td> User ID </td>");
			out.println("<td> <input type=\"text\" name= \"userid\" readonly value = \""+user+"\">  </td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td> User Age: </td>");
			out.println("<td> <input type=\"text\" name=\"userAge\"> </td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td> User Gender: </td>");
			out.println("<td> <input type='radio' name='userGender' value='male'>Male<input type='radio' name='gender' value='female'>Female </td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td> User Occupation: </td>");
			out.println("<td> <input type=\"text\" name=\"userOccupation\"> </td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td> Review Rating: </td>");
			out.println("<td>");
			out.println("<select name=\"reviewRating\">");
			out.println("<option value=\"1\" selected>1</option>");
			out.println("<option value=\"2\">2</option>");
			out.println("<option value=\"3\">3</option>");
			out.println("<option value=\"4\">4</option>");
			out.println("<option value=\"5\">5</option>");
			out.println("</td>");
			out.println("</tr>");
						
			
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
      		Date currentDate=new Date();
      		String todayDate=df.format(currentDate);
	
			out.println("<tr>");
			out.println("<td> Review Date: </td>");
			out.println("<td> <input type=\"text\" name=\"reviewDate\"> </td>");
			out.println("</tr>");
			out.println("<tr>");
			
			out.println("<td> Review Text: </td>");
			out.println("<td><textarea name=\"reviewText\" rows=\"4\" cols=\"50\"> </textarea></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<br><br>");
			out.println("<input type=\"submit\" value=\"Submit Review\">");
			out.println("</fieldset>");
			out.println("</form>");
			out.println("<footer>"+		
				"<div class='footer-bottom'>"+
				"<p>CSP 595 - Enterprise Web Application -Assignment 1</p>"+
				"</div>"+
				"</footer>");
	
			
						
	    } catch (MongoException e) {
		e.printStackTrace();
	    }

	}
}