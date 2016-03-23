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


public class CancelReservation extends HttpServlet {
 
	MongoClient mongo;
	
	public void init() throws ServletException
	{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}

 public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
	try{
	
			
			HttpSession session=request.getSession(true); 
			boolean isadmin = false;
			String user=((String)session.getAttribute("username"));
			String confirmationNumber = request.getParameter("confirmation_number");
			
			DB db = mongo.getDB("CarRentals");
			
			DBCollection reservation_details = db.getCollection("reservation_details");
			
			BasicDBObject query = new BasicDBObject();
		    query.put("confirmation_number",confirmationNumber);
		    DBCursor cursor = reservation_details.find(query);
		    BasicDBObject obj = new BasicDBObject();
			PrintWriter out = response.getWriter();
			String pickupDate="";
			String pick = "";
			long datediff=0;
						    
			
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
				
				
				"</header>"+
					 "<nav>"
					+ "<ul>"
					+ "<li class='start'><a href='index.jsp'>Home</a></li>");
					
					if(user == null){					
					out.println("<li class='r'><a href='login.jsp'>Login</a></li>");
					}
					else{
					out.println("<li class='r'><a href='Logout'>Logout</a></li>");
					}
					out.println("<li><a href='about-us.jsp'>About US</a></li>"
					+ "</ul>"
					+ "</nav>");
			
			
			
			
			out.println("<br><br><hr>");
			out.println("<h1>Cancel Reservations</h1>");
			
			if(cursor.count() == 0){
						      out.println("<h2>Reservation not found </h2>");
						      
						    }
						    else{

							      //check difference between today's date and delivery date is more than 5
							      while(cursor.hasNext()){
							        obj = (BasicDBObject) cursor.next();
							        pick = obj.getString("pick");
							       }
							       pickupDate = pick.split(" ")[0];

							      System.out.println(pickupDate);
							      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							      Date currentDate=new Date();
							      String todayDate=df.format(currentDate);

							      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
							      try{
							        Date pickup_date = sdf.parse(pickupDate);
							        Date today_date = sdf.parse(todayDate);

							        long dateDifference=pickup_date.getTime()-today_date.getTime();
							        datediff=dateDifference/(24 * 60 * 60 * 1000);
							        String message = "";
							        if(datediff > 1 ){
							            reservation_details.remove(obj);
							            	
							            		response.sendRedirect("ViewReservations");
							            	
							                
							            
							        }
							        else{
							          out.println("<h4> Reservation cannot be cancelled </h4>");
							          
							        }

							      }
							      catch(Exception e){e.printStackTrace();
						  			}   
						  		}
						    
						  	
	String backURL;
			if(user.equals("admin")){
				backURL =
          response.encodeURL("/midwest/AdminServlet");
			}	
			else{	
				backURL =
          response.encodeURL("/midwest/index.jsp");
       }
        // "Proceed to Checkout" button below table
		 out.println
          ("</TABLE>\n" +
           "<FORM ACTION=\"" + backURL + "\" METHOD=\"post\">\n" +
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
		   
				
				out.println("</body>");
				out.println("</html>");
			
		} catch (MongoException e) {
				e.printStackTrace();
		}
	}	
		public void destroy(){
      // do nothing.
	}
}











