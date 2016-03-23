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
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.AggregationOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.*;
import javax.servlet.http.*;

public class SingleCarPage extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		PrintWriter output = response.getWriter();
					
		DB db = mongo.getDB("CarRentals");
		boolean countOnly = false;
		
		// If the collection does not exists, MongoDB will create it for you
		DBCollection myReviews = db.getCollection("car_details");
		
		BasicDBObject query = new BasicDBObject();
				
		try {
			
			// Get the form data
			HttpSession session = request.getSession(true);
			
			String carId = request.getParameter("carId");
			String user=((String)session.getAttribute("username"));
			String searchField = "carid";
			String searchParameter = "";

			if (carId != ""){
			searchParameter = carId;
			}
			
			// Find and display 
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put(searchField, searchParameter);

			DBCursor dbCursor = myReviews.find(searchQuery);


			
			//Construct the top of the page
			constructPageTop(output,user);

			int count = 0;
		String tableData = " ";
		String pageContent = " ";
			
		
		while (dbCursor.hasNext()) {		
			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
			tableData =  "<p>Location: <b>" + bobj.getString("location") + " </b><br>"
				     		+"Car: <b>" + bobj.getString("carmake")+" "+ bobj.getString("carmodel") + " </b><br>"
							+ "Car Type:       " + bobj.getString("cartype") + "<br>"
							+ "Rent amount: $" + bobj.getString("rentalamount") + "</p>"
							 ;
			count++;
				
				output.println("<h3>"+count+"</h3>");
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				String backURL =
	          response.encodeURL("/midwest/index.jsp");
	       
	        // "Proceed to Checkout" button below table
			 output.println
	          ("</TABLE>\n" +
	           "<FORM ACTION=\"" + backURL + "\">\n" +
	           "<BIG><CENTER>\n" +
			   "<p>Please go back and input all details to book car</p>"+
	           "<INPUT class='blue-button' TYPE=\"SUBMIT\"\n" +
	           "       VALUE=\"Back\">\n" +
			   
	           "</CENTER></BIG></FORM>");
		}

		//No data found
		if(count == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
			
			
			
			
			//Construct the bottom of the page
			constructPageBottom(output);

		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
	
	public void constructPageTop(PrintWriter output, String user){
		String pageHeading = "Cars Matching the search criteria";
		output.println("<!DOCTYPE html>" + "<html lang=\"en\">"
					+ "<head>	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
					+ "<title>Mid-West Car Rentals</title>"
					+ "<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />"
					+ "</head>"
					+ "<body>"
					+ "<div id=\"container\">"
					+ "<header>"
					+ "<h1><a href='index.jsp'><img src='images/12.png'></a><h1>"
					+ "</h1><h2><b>Welcome " +user+ "</b></h2>"
					+ "</header>"
					+ "<nav>"
					+ "<ul>"
					+ "<li class='start'><a href='index.jsp'>Home</a></li>");
					
					if(user == null){					
					output.println("<li class='r'><a href='login.jsp'>Login</a></li>");
					}
					else{
					output.println("<li class='r'><a href='Logout'>Logout</a></li>");
					}
					output.println("<li class= 'r'><a href='#'>Manage Your Reservations</a></li>"
					+ "<li><a href='about-us.jsp'>About US</a></li>"
					+ "</ul>"
					+ "</nav>"
					+ "<div id=\"body\">"
					+ "<section id=\"review-content\">"
					+ "<article>"
					+ "<h2 style=\"color:#DE2D3A;font-weight:700;\">" +pageHeading + "</h2>");
		
			
	}
	
	public void constructPageBottom(PrintWriter output){
		String myPageBottom = "</article>"
					+ "</section>"
                    + "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<footer>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>Mid-West Car Rentals</p>"
					+ "</div>"
					+ "</footer>"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		
		output.println(myPageBottom);
	}
	
	public void constructDefaultContent(DBCursor dbCursor, PrintWriter output, boolean countOnly){
		
		
	}

}