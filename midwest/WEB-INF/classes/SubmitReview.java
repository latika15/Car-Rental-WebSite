import java.io.*;
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

public class SubmitReview extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			//Get the values from the form
			String id = request.getParameter("id");
			String carmake = request.getParameter("carmake");
			String cartype = request.getParameter("cartype");
			String carmodel = request.getParameter("carmodel");
			String price = request.getParameter("price").trim();
			//int price1= Integer.parseInt(price);
			String company = request.getParameter("company");
			Integer companyzip = Integer.parseInt(request.getParameter("companyzip"));
			String companycity = request.getParameter("companycity");
			String companystate = request.getParameter("companystate");
			String sale = request.getParameter("sale");
			String user = request.getParameter("userid");
			String userage = request.getParameter("userAge");
			String usergender = request.getParameter("userGender");
			String userOccupation = request.getParameter("userOccupation");
			int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));	
			String reviewDate = request.getParameter("reviewDate");
			String reviewText = request.getParameter("reviewText");
			
			
										
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CarRentals");
				
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myReviews = db.getCollection("Reviews");
			System.out.println("Collection myReviews selected successfully");
				
			BasicDBObject doc = new BasicDBObject("title", "myReviews").
				append("carid", id).
				append("carmake", carmake).
				append("cartype", cartype).
				append("carmodel", carmodel).
				append("price", price).
				append("company", company).
				append("companyzip", companyzip).
				append("companycity", companycity).
				append("companystate", companystate).
				append("sale", sale).
				append("userid", user).
				append("userage", userage).
				append("usergender", usergender).
				append("userOccupation",userOccupation).
				append("reviewRating", reviewRating).
				append("reviewDate", reviewDate).
				append("reviewText", reviewText);
									
			myReviews.insert(doc);
				
			System.out.println("Document inserted successfully");
			
			//Send the response back to the JSP
			PrintWriter out = response.getWriter();
			
				
			String docType =
					"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
					"Transitional//EN\">\n";
					
					String title = "Your Review has been Saved Successfully";
					
				out.println(docType+"<html>"+
				"<head>"+
				"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
				"<title>Game Center </title>"+
				"<link rel='stylesheet' href='styles.css' type='text/css' />"+
				"</head>"+
				"<body>"+
				"<div id='container'>"+
				"<header>"+
				"<h1><a href='index.jsp'><img src='images/12.png'></a><h1>"+ 
				"<h2>Project- Midwest Car Rentals</h2>"+
			
				
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


			
			
			out.println("<h1> Review submitted for: "+ carmake + carmodel +"</h1>");
			
			
	
			
			String backURL =
          response.encodeURL("/midwest/index.jsp");
       
        // "Proceed to Checkout" button below table
		 out.println
          ("</TABLE>\n" +
           "<FORM ACTION=\"" + backURL + "\">\n" +
           "<BIG><CENTER>\n" +
		   "<p>Thank you for using MidWest Car Rentals...</p>"+
           "<INPUT TYPE=\"SUBMIT\"\n" +
           "       VALUE=\"Go to Home Page\">\n" +
		   
           "</CENTER></BIG></FORM>");
			out.println("</body>");
			out.println("</html>");
		
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	
}