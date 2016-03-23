import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SearchCars extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		PrintWriter output = response.getWriter();
					
		DB db = mongo.getDB("CarRentals");
		boolean countOnly = false;
		
		// If the collection does not exists, MongoDB will create it for you
		DBCollection myReviews = db.getCollection("car_details");
		DBCollection reservation_details = db.getCollection("reservation_details");
		BasicDBObject query = new BasicDBObject();
		BasicDBObject r_query = new BasicDBObject();
			
			// Get the form data
			HttpSession session = request.getSession(true);
			
			String pickuplocation = request.getParameter("pickuplocation").trim().toLowerCase();
			String cartype = request.getParameter("cartype");
		
			String pickupdate= request.getParameter("pickupdate");
			String pickuptime= request.getParameter("pickuptime");
			String dropoffdate= request.getParameter("dropoffdate");
			String dropofftime= request.getParameter("dropofftime");
			String age= request.getParameter("age");
			
			String user=((String)session.getAttribute("username"));
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mmaa");
			String message="";
			Date s_pickup = new Date();
			Date s_drop = new Date();
			Date currentDate=new Date();
			try{    
			s_pickup = sdf.parse(pickupdate+" "+pickuptime);
			s_drop = sdf.parse(dropoffdate+" "+dropofftime);
			System.out.println(s_pickup+" "+s_drop);
			}catch(Exception e){e.printStackTrace();}
			if(s_drop.before(s_pickup) || s_pickup.before(currentDate) || s_drop.before(currentDate)){
				message="Please select the valid Date/Time";
				session.setAttribute("message",message);
				response.sendRedirect("index.jsp");
			}else{
			if(!pickuplocation.equals("")){
				query.put("location", pickuplocation);
				r_query.put("location", pickuplocation);
			}
			if(!cartype.equals("ALL")){
				query.put("cartype", cartype);
			}	
			
			//Construct the top of the page
			constructPageTop(output,user);

			DBCursor dbCursor = myReviews.find(query);
			//Construct the page content

			int count = 0;
		String tableData = " ";
		String pageContent = " ";
			
		try {
		while (dbCursor.hasNext()) {		
			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
			String carid = bobj.getString("carid");
			r_query.put("carid",carid);
			DBCursor cursor = reservation_details.find(r_query);

			if (cursor.count() == 0) {				
				
				tableData =  "<p margin-top: 50px;>Availability: <b>Available</b><br>"
				     		+"Car: <b>" + bobj.getString("carmake")+" "+ bobj.getString("carmodel") + " </b><br>"
							+ "Car Type:   <b>    " + bobj.getString("cartype") + "</b><br>"
							+ "Rent amount: <b> $" + bobj.getString("rentalamount") + "</b></p>"
							 ;
				count++;
					pageContent = "<table ><tr><td>"+tableData+"</td>";		
				    output.println(pageContent);
				    output.println("<td><p><form action='/midwest/BookCar.jsp' method='get' style='margin-bottom:20px;'>"
							+"<input type='submit' value='Book Car' class='submit-button' style='width:200px;margin-top:0px;margin-left: 142px;'>"
							+"<input type='hidden' name='carid' value='"+bobj.getString("carid")+"' />"
							+"<input type='hidden' name='carmake' value='"+bobj.getString("carmake")+"' />" 
							+"<input type='hidden' name='cartype' value='"+bobj.getString("cartype")+"' />" 
							+"<input type='hidden' name='carmodel' value='"+bobj.getString("carmodel")+"' />" 
							+"<input type='hidden' name='rentalamount' value='"+bobj.getDouble("rentalamount")+"' />" 
							+"<input type='hidden' name='pickuplocation' value='"+pickuplocation+"' />" 
							+"<input type='hidden' name='age' value='"+age+"' />" 
							+"<input type='hidden' name='pickupdate' value='"+pickupdate+"' />" 
							+"<input type='hidden' name='pickuptime' value='"+pickuptime+"' />" 
							+"<input type='hidden' name='dropoffdate' value='"+dropoffdate+"' />" 
							+"<input type='hidden' name='dropofftime' value='"+dropofftime+"' />" +
													
							"</form></p>");
												
								
				output.println("<p><form action='/midwest/WriteReview' method='get' style='margin-bottom:20px;margin-top:0px;'>"+		
				"<input type='submit' value='Write Review' class='submit-button' style='width:200px;margin-top:0px;margin-left: 142px;'/>"+		
				"<input type='hidden' name='carmake' value='"+bobj.getString("carmake")+"' />" +		
				"<input type='hidden' name='cartype' value='"+bobj.getString("cartype")+"' />" +		
				"<input type='hidden' name='carmodel' value='"+bobj.getString("carmodel")+"' />" +		
				"<input type='hidden' name='carmodel' value='"+bobj.getString("carmodel")+"' />" +		
				"<input type='hidden' name='price' value='"+bobj.getString("rentalamount")+"' />" +	
				"<input type='hidden' name='carid' value='"+bobj.getString("carid")+"' />" +					
						
				"</form></p>"		
				);		

				output.println("<p><form action='/midwest/ViewReviews' method='POST' style='margin-bottom:20px;'>"+
						"<input type='submit' value='View Review' class='submit-button' style='width:200px;margin-top:0px;margin-left: 142px;'/>"+
						"<input type='hidden' name='carid' value='"+bobj.getString("carid")+"' />" +
						"</form></p></td></tr></table>");	
				
			}else{
				
				String availability = "";
				int iter = 0;
				boolean not_available=false;
				while(cursor.hasNext()){
					BasicDBObject robj = (BasicDBObject) cursor.next();
					String r_pick = robj.getString("pick");
					String r_drop = robj.getString("drop");

					sdf=new SimpleDateFormat("yyyy-MM-DD hh:mmaa");
				    
				        Date r_pickup = sdf.parse(r_pick);
				        Date search_pickup = sdf.parse(pickupdate+" "+pickuptime);
				        Date r_dropoff = sdf.parse(r_drop);
				        Date search_drop = sdf.parse(dropoffdate+" "+dropofftime);
				        
				        if(search_pickup.before(r_pickup) && (search_drop.equals(r_pickup) || search_drop.before(r_pickup)) ){
				                      	
				            		availability = "Available";
				            		//System.out.println("before pick before pick");
				        }
				        else if(search_pickup.after(r_dropoff) && (search_drop.equals(r_dropoff) || search_drop.after(r_dropoff))){
				                      	
				            		availability = "Available";
				            		//System.out.println("after drop after drop");
				        }
				        else if(search_pickup.after(r_pickup) && search_pickup.before(r_dropoff)){
				                      	
				            		availability = "Unavailable";
									not_available = true;
				            		//System.out.println("pick is in between");
				        }
				        else if(search_drop.after(r_pickup) && search_pickup.before(r_dropoff)){
				                      	
				            		availability = "Unavailable";
									not_available = true;
				            		//System.out.println("drop in between");
				        }else{
				        	availability = "Unavailable";
				        }
				        
				        if(iter>0 && not_available){
				        	availability = "Unavailable";
				        }
				     	iter++;
				}
				tableData =  "<p>Availability: <b>" + availability + " </b><br>"
				     		+"Car: <b>" + bobj.getString("carmake")+" "+ bobj.getString("carmodel") + " </b><br>"
							+ "Car Type:<b>       " + bobj.getString("cartype") + "</b><br>"
							+ "Rent amount:<b> $" + bobj.getString("rentalamount") + "</b></p>"
							 ;
				count++;
				pageContent = "<table ><tr><td>"+tableData+"</td>";		
			    output.println(pageContent);
			    output.println("<td><p><form action='/midwest/BookCar.jsp' method='get' style='margin-bottom:20px;'>");
				if(availability.equals("Available")){
					output.println("<input type='submit' value='Book Car' class='submit-button' style='width:200px;margin-top: 42px;margin-left: 142px;'>");
				}else{
					output.println("<input type='submit' value='Book Car' class='submit-button' style='width:200px;margin-top: 42px;margin-left: 142px;' disabled>");
				}
						
				output.println("<input type='hidden' name='carid' value='"+bobj.getString("carid")+"' />"
						+"<input type='hidden' name='carmake' value='"+bobj.getString("carmake")+"' />" 
						+"<input type='hidden' name='cartype' value='"+bobj.getString("cartype")+"' />" 
						+"<input type='hidden' name='carmodel' value='"+bobj.getString("carmodel")+"' />" 
						+"<input type='hidden' name='rentalamount' value='"+bobj.getDouble("rentalamount")+"' />" 
						+"<input type='hidden' name='pickuplocation' value='"+pickuplocation+"' />" 
						+"<input type='hidden' name='age' value='"+age+"' />" 
						+"<input type='hidden' name='pickupdate' value='"+pickupdate+"' />" 
						+"<input type='hidden' name='pickuptime' value='"+pickuptime+"' />" 
						+"<input type='hidden' name='dropoffdate' value='"+dropoffdate+"' />" 
						+"<input type='hidden' name='dropofftime' value='"+dropofftime+"' />" +
												
						"</form></p>");
												
								
				output.println("<p><form action='/midwest/WriteReview' method='get' style='margin-bottom:20px;'>"+		
				"<input type='submit' value='Write Review' class='submit-button' style='width:200px;margin-top:0px;margin-left: 142px;'/>"+		
				"<input type='hidden' name='carmake' value='"+bobj.getString("carmake")+"' />" +		
				"<input type='hidden' name='cartype' value='"+bobj.getString("cartype")+"' />" +		
				"<input type='hidden' name='carmodel' value='"+bobj.getString("carmodel")+"' />" +		
				"<input type='hidden' name='carmodel' value='"+bobj.getString("carmodel")+"' />" +		
				"<input type='hidden' name='price' value='"+bobj.getString("rentalamount")+"' />" +	
				"<input type='hidden' name='carid' value='"+bobj.getString("carid")+"' />" +					
						
				"</form></p>"		
				);		

				output.println("<p><form action='/midwest/ViewReviews' method='POST' style='margin-bottom:20px;'>"+
						"<input type='submit' value='View Review' class='submit-button' style='width:200px;margin-top:0px;margin-left: 142px;'/>"+
						"<input type='hidden' name='carid' value='"+bobj.getString("carid")+"' />" +
						"</form></p></td></tr></table>");		
				    
			}
		}
		}catch (MongoException e) {
			e.printStackTrace();
		}catch(Exception e){e.printStackTrace();
			  		} 

		//No data found
			if(count == 0){
				pageContent = "<h1>No Data Found</h1>";
				output.println(pageContent);
			}
			
			//Construct the bottom of the page
			constructPageBottom(output);
		}
	}
	
	public void constructPageTop(PrintWriter output, String user){
		String pageHeading = "Choose from the available cars below";
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
					output.println("<li class= 'r'><a href='ViewReservations'>Manage Your Reservations</a></li>"
					+ "<li><a href='about-us.jsp'>About US</a></li>"
					+ "</ul>"
					+ "</nav>"
					+ "<div id=\"body\">"
					
					+ "<h2 style=\"color:#799AC0;font-weight:700;\">" +pageHeading + "</h2>");
		
			
	}
	
	public void constructPageBottom(PrintWriter output){
		String myPageBottom = "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<footer>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>FALL 2015 - CSP595 Project by TEAM - 5</p>"
					+ "</div>"
					+ "</footer>"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		
		output.println(myPageBottom);
	}
	
}