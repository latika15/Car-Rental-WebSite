
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import javax.servlet.http.*;
import java.util.*;

public class AddCarServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}



  protected void register(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
	
	DB db = mongo.getDB("CarRentals");
	
	
	PrintWriter out= response.getWriter();
	ServletContext sc=request.getSession().getServletContext();
	HttpSession session=request.getSession(); 
	String username=((String)session.getAttribute("username")); 
	
	if(username.equals("admin")){
		
		String carid = request.getParameter("carId");
        String carmake = request.getParameter("carMake");
		String cartype= request.getParameter("carType");
		String carmodel= request.getParameter("carModel");
		String carnumber= request.getParameter("carNumber");
		double rentalamount= Double.parseDouble(request.getParameter("rentalAmount"));
		double discount= Double.parseDouble(request.getParameter("discount"));
		String location= request.getParameter("location");
		String message = "";
		DBCollection carDetails = db.getCollection("car_details");
		BasicDBObject query = new BasicDBObject("carid",carid);
		DBCursor cursor = carDetails.find(query);

		if(cursor.count() == 0){
			BasicDBObject car = new BasicDBObject("carid",carid)
							.append("carmake",carmake)
							.append("cartype",cartype)
							.append("carmodel",carmodel)
							.append("carnumber",carnumber)
							.append("rentalamount",rentalamount)
							.append("discount",discount)
							.append("location",location.toLowerCase());
			carDetails.insert(car);
			
			message = "Car successfully added";
		}else{
			message = "Car id already exist";
		}
		session.setAttribute("message",message);
		response.sendRedirect("AdminServlet");
	  
	 }else 
	 
	 {
	 
	 
			String docType ="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
				"Transitional//EN\">\n";
				out.println(docType+"<html>"+
				"<head>"+
				"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
				"<h1><a href='index.jsp'><img src='images/12.png'></a><h1>"+ 	
				"<link rel='stylesheet' href='styles.css' type='text/css' />"+
				"</head>"+
				"<body>"+
				"<div id='container'>"+
				"<header>"+
				"<p>Welcome: "+username+"</p>"+
				"<a href='/csj/LogoutServlet'>Logout</a>"+
				"</header>"+
				"<P> You don not have permission for this page </p>");
					
				
				out.println("<footer>"+		
				"<div class='footer-bottom'>"+
				"<p>FALL 2015 - CSP595 Project by TEAM - 5</p>"+
				"</div>"+
				"</footer>"+
				"</div>"+
				"</body>"+
				"</html>");
			
		
		
	 
	 
	 }
	 
	 
    } 


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        register(request, response);
    } 

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        register(request, response);
    }
}