import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.mongodb.*;
import java.util.*;

public class UpdateCarServlet extends HttpServlet {

private static final long serialVersionUID = 1L;
	MongoClient mongo;
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        HttpSession session = request.getSession();
		String username=((String)session.getAttribute("username")); 
		PrintWriter out = response.getWriter();
		if(username.equals("admin")){
			String carid = request.getParameter("carid");
			String message = "";

			if(request.getParameter("update_car") != null ){
				updateCarForm(request, response, carid);
			}
			if(request.getParameter("execute_update") != null ){
				updateCar(request, response, carid);
				message = "Car details for Car ID: "+carid+" updated successfully";
				session.setAttribute("message",message);
				response.sendRedirect("AdminServlet");
			}
			if(request.getParameter("delete_car") != null ){
				deleteCar(carid);
				message = "Car successfully removed";
				session.setAttribute("message",message);
				response.sendRedirect("AdminServlet");
			}

		}else {
				out.println("<head><title>Mid-West Car Rentals</title><link rel='stylesheet' href='styles.css' type='text/css' /></head><body><div id='container'><header>"+
				"<h1><a href='/'>MID-WEST<span>CAR RENTALS</span></a></h1></header><nav><ul><li class='start'><a href='index.jsp'>Home</a></li><li class='r'>"+
				"<a href='login.jsp'>Login</a></li><li class='r'></li><li class= 'r'></li>"+
				"<li><a href='about-us.jsp'>About US</a></li></ul></nav><hr>");
			
				out.println("<div id=\"body\"><article>");
				out.println("<P> You don not have permission for this page </p></article>");
				out.println("<div class='clear'></div></div>"+
				"<footer><div class='footer-content'> "+
				"<div class='clear'></div></div><div class='footer-bottom'>"+
				"<p>	FALL 2015 - CSP595 Project by TEAM - 5</p></div></footer>"+
				"</div></body></html>");
			}	
		
    }
	//Delete car method
	private void deleteCar(String carid){
		
		try{
			
	
			DB db = mongo.getDB("CarRentals");
			DBCollection carDetails = db.getCollection("car_details");
			
			BasicDBObject query = new BasicDBObject("carid",carid);
			
			carDetails.remove(query);
			
		}catch(MongoException e){
			e.printStackTrace();
		}
		
		
	}
	
	//update car method
	private void updateCarForm(HttpServletRequest request, HttpServletResponse response, String carid) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		String contentTitle = "Update Car Details";
		out.println("<head><title>Mid-West Car Rentals</title><link rel='stylesheet' href='styles.css' type='text/css' /></head><body><div id='container'><header>"+
		"<h1><a href='index.jsp'><img src='images/12.png'></a><h1>"+ 
		"</header><nav><ul><li class='start'><a href='index.jsp'>Home</a></li><li class='r'>"+
		"<li class='r'></li><li class= 'r'>"+
		"<li><a href='#'>About US</a></li></ul></nav><hr>"+
		"<center><h2>"+contentTitle+"</h2></center>");
		try{
			
	
			DB db = mongo.getDB("CarRentals");
			DBCollection carDetails = db.getCollection("car_details");
			
			BasicDBObject query = new BasicDBObject("carid",carid);
			
			DBCursor cursor = carDetails.find(query);

			if(cursor.count() != 0){

				while(cursor.hasNext()){
					BasicDBObject car = (BasicDBObject) cursor.next();	
					out.println("<form action='' method='POST'><table><tr><td>Car Id</td>"+
							"<td><input type='text' name='carid' value='"+car.getString("carid") +"' readonly></td></tr><tr><td>Car Make</td>"+
							"<td><input type='text' name='carmake' value='"+car.getString("carmake") +"' ></td></tr><tr><td>Car Type</td>"+
							"<td><input type='text' name='cartype' value='"+car.getString("cartype") +"' ></td></tr><tr><td>Car Model</td>"+
							"<td><input type='text' name='carmodel' value='"+car.getString("carmodel") +"' ></td></tr><tr><td>Car Number</td>"+
							"<td><input type='text' name='carnumber' value='"+car.getString("carnumber") +"' ></td></tr><tr><td>Rental Amount($)</td>"+
							"<td><input type='text' name='rentalamount' value='"+car.getString("rentalamount") +"' ></td></tr><tr><td>Discount</td>"+	
							"<td><input type='text' name='discount' value='"+car.getString("discount") +"' ></td></tr><tr><td>Location</td>"+
							"<td><input type='text' name='location' value='"+car.getString("location") +"' ></td></tr>"+						
							"<td><input class='formbutton' type='submit' name='execute_update' value='Update'></td></tr></table></form>");
				}
			}
			
		}catch(MongoException e){
			e.printStackTrace();
		}
		String myPageBottom = "<footer>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>FALL 2015 - CSP595 Project by TEAM - 5</p>"
					+ "</div>"
					+ "</footer>"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		
		out.println(myPageBottom);
	}

	private void updateCar(HttpServletRequest request, HttpServletResponse response, String carid) throws ServletException, IOException{
		
		DB db = mongo.getDB("CarRentals");
	
	
		PrintWriter out= response.getWriter();
		ServletContext sc=request.getSession().getServletContext();
		HttpSession session=request.getSession(); 
		
			
		String carmake = request.getParameter("carmake");
		String cartype= request.getParameter("cartype");
		String carmodel= request.getParameter("carmodel");
		String carnumber= request.getParameter("carnumber");
		double rentalamount= Double.parseDouble(request.getParameter("rentalamount"));
		double discount= Double.parseDouble(request.getParameter("discount"));
		String location= request.getParameter("location");
		String message = "";
		try{
			DBCollection carDetails = db.getCollection("car_details");
			BasicDBObject query = new BasicDBObject("carid",carid);
			
			BasicDBObject carUpdates = new BasicDBObject("carmake",carmake)
							.append("cartype",cartype)
							.append("carmodel",carmodel)
							.append("carnumber",carnumber)
							.append("rentalamount",rentalamount)
							.append("discount",discount)
							.append("location",location.toLowerCase());

			BasicDBObject car = new BasicDBObject("$set", carUpdates);
			
			carDetails.update(query ,car);
			
	
		}
		catch(MongoException e){
			e.printStackTrace();
		}
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);
	}
}