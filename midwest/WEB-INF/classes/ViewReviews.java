import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.mongodb.*;
import java.util.*;


public class ViewReviews extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//Get the values from the form
			String searchField = "carid";
			
			//Get the product selected
			String searchParameter = "";
			String id = request.getParameter("carid");
			DB db = mongo.getDB("CarRentals");
			DBCollection reviews = db.getCollection("Reviews");
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put(searchField, id);

			DBCursor cursor = reviews.find(searchQuery);
			
			PrintWriter out = response.getWriter();
			HttpSession session = request.getSession(false);
			String user=((String)session.getAttribute("username"));
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
					out.println("<li class= 'r'><a href='ViewReservations'>Manage Your Reservations</a></li>"
					+ "<li><a href='about-us.jsp'>About US</a></li>"
					+ "</ul>"
					+ "</nav>");


			
			if(cursor.count() == 0){
				out.println("There are no reviews for this product.");
			}else{
				out.println("<h2>Reviews</h2>");
				out.println("<table class = 'query-table'>");
				
				String carmake = "";
				String cartype = "";
				String carmodel = "";
				String price = "";
				String company = "";
				String companyzip = "";
				String companycity = "";
				String companystate = "";
				String discount = "";
				String username = "";
				String userAge = "";
				String gender = "";
				String userOccupation = "";
				String reviewRating = "";
				String reviewDate =  "";
				String reviewText = "";
				int serialNo = 0;
				while (cursor.hasNext()) {
					//out.println(cursor.next());
					BasicDBObject obj = (BasicDBObject) cursor.next();				
					serialNo++;
					out.println("<tr>");
					out.println("<td colspan='8'><font color='red'><strong>Review#"+serialNo+"</strong></font></td>");
					out.println("</tr>");

					out.println("<tr>");
					out.println("<th> Car Make: </th>");
					carmake = obj.getString("carmake");
					out.println("<td>" +carmake+ "</td>");
					
					out.println("<th> Car Type: </th>");
					cartype = obj.getString("cartype");
					out.println("<td >" +cartype+ "</td>");

					out.println("<th > Car Model: </th>");
					carmodel = obj.getString("carmodel");
					out.println("<td>" +carmodel+ "</td>");

					out.println("<th> Rent Amount: </th>");
					price = obj.getString("price");
					out.println("<td>" +price+ "</td>");
					out.println("</tr>");
					///////////////////////////////////////////////////////
					out.println("<tr>");
					
					
					out.println("<th> Company: </th>");
					company = obj.getString("company");
					out.println("<td>" +company+ "</td>");

					out.println("<th> Zip: </th>");
					companyzip = obj.getString("companyzip");
					out.println("<td>" +companyzip+ "</td>");

					out.println("<th> Company City: </th>");
					companycity = obj.getString("companycity");
					out.println("<td>" +companycity+ "</td>");

					out.println("<th> State: </th>");
					companystate = obj.getString("companystate");
					out.println("<td>" +companystate+ "</td>");

					out.println("</tr>");
					//////////////////////////////////////////////////////
					out.println("<tr>");
					out.println("<th> Discount: </th>");
					discount = obj.getString("sale");
					out.println("<td>" +discount+ "</td>");
					
					out.println("<th> User Name: </th>");
					username = obj.getString("userid");
					out.println("<td>" +username+ "</td>");

					out.println("<th> User Age: </th>");
					userAge = obj.getString("userage");
					out.println("<td>" +userAge+ "</td>");

					out.println("<th> Gender: </th>");
					gender = obj.getString("usergender");
					out.println("<td>" +gender+ "</td>");
					out.println("</tr>");
					//////////////////////////////////////////////////////////
					out.println("<tr>");
					
					out.println("<th> Occupation: </th>");
					userOccupation = obj.getString("userOccupation");
					out.println("<td>" +userOccupation+ "</td>");
					
					out.println("<th> Review Rating: </th>");
					reviewRating = obj.getString("reviewRating").toString();
					out.println("<td>" +reviewRating+ "</td>");
					
					out.println("<th> Review Date: </th>");
					reviewDate = obj.getString("reviewDate");
					out.println("<td>" +reviewDate+ "</td>");
					out.println("</tr>");
					/////////////////////////////////////////////////////
					out.println("<tr>");
					out.println("<th> Review Text: </th>");
					reviewText = obj.getString("reviewText");
					out.println("<td colspan='7'>" +reviewText+ "</td>");
					out.println("</tr>");
					
				}
			}	

				out.println("</table>");
				String myPageBottom = "<div class=\"clear\"></div>"
					+ "<footer>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>Mid-West Car Rentals</p>"
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