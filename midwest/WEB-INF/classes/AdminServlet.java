import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.*;

public class AdminServlet extends HttpServlet {

private static final long serialVersionUID = 1L;
	MongoClient mongo;
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}

 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException 
	{
	
	response.setContentType("text/html");
	PrintWriter out= response.getWriter();
	
	DB db = mongo.getDB("CarRentals");
	//ServletContext sc=request.getSession().getServletContext();
	HttpSession session=request.getSession(); 
	
	
	if(session != null){
		String username = "";
		username=((String)session.getAttribute("username"));

		out.println("<head><title>Mid-West Car Rentals</title><link rel='stylesheet' href='styles.css' type='text/css' /></head><body><div id='container'><header>"+
		
				"<h1><a href='index.jsp'><img src='images/12.png'></a><h1>"+ 
				"<h1><b>Welcome  "+ username +"</b></h1>"+
				
				"</header><nav><ul><li class='start'><a href='AdminServlet'>Home</a></li><li class='r'>"+
			
				"<a href='Logout'>Logout</a>"+
				"</li>"+
				
				"<li><a href='about-us.jsp'>About US</a></li></ul></nav><hr>");
			
		out.println("<div id=\"body\"><section id=\"content\"><article>");
		if(username.equals("admin")){
						

				
				DBCollection carDetails = db.getCollection("car_details");
				try
				{
					DBCursor cursor= carDetails.find();
					if(cursor.count()==0)
						out.println("There are no cars to show");
					else
					{	
						String message = (String) session.getAttribute("message");
						if(message != null){
							out.println("<p><b>"+message+"<b></p>");
							message = "";
							session.setAttribute("message",message);
						}
						out.println("<form method='POST' action='UpdateCarServlet'> <table>"+
									"<tr>"+
									"<th></th>"+
									"<th>Car Id</th>"+
									"<th>Car Make</th>"+
									"<th>Car Type</th>"+
									"<th>Car Model</th>"+
									"<th>Car Number</th>"+
									"<th>Rental Amount</th>"+
									"<th>Discount</th>"+
									"<th>Location</th>"+
									"</tr>");
						while(cursor.hasNext())
						{
							BasicDBObject obj= (BasicDBObject)cursor.next();
							out.println("<tr>");
							out.println("<td><input type='radio' name='carid' value='"+obj.getString("carid")+"'></td>");
							out.println("<td>"+obj.getString("carid")+"</td>");
							out.println("<td>"+obj.getString("carmake")+"</td>");
							out.println("<td>"+obj.getString("cartype")+"</td>");
							out.println("<td>"+obj.getString("carmodel")+"</td>");
							out.println("<td>"+obj.getString("carnumber")+"</td>");
							out.println("<td>"+obj.getString("rentalamount")+"</td>");
							out.println("<td>"+obj.getString("discount")+"</td>");
							out.println("<td>"+obj.getString("location")+"</td>");
							out.println("</tr>");
							
						}
						out.println("</table>");
						out.println("<input type='submit' class='blue-button' name='update_car' value='Update'>&nbsp;&nbsp;"+
									"<input class='delete-button' name='delete_car' type='submit' value='Delete'></form>");
					
					}
				}
				catch(MongoException e){
					e.printStackTrace();
				}			
			}
			else {
				out.println("<P> You don not have permission for this page </p>");
			}	

			
			out.println("</article></section>");
			out.println("<aside class='sidebar'><ul><li> <h4>Quick Links</h4><ul><li><a href='AdminServlet'>Home</a></li>"+
			"<li><a href='AddCar.jsp'>Add Car</a></li>"+
			"<li><a href='ViewAllReservations'>Add/Manage Reservations</a></li></li></ul></aside>");
			out.println("<div class='clear'></div></div>"+
			"<footer><div class='footer-bottom'>"+
			"<p>	FALL 2015 - CSP595 Project by TEAM - 5</p></div></footer>"+
			"</div></body></html>");
		
		}else{
			response.sendRedirect("login.jsp");
		}
			
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }


}


