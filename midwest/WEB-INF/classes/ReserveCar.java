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
import java.text.DateFormat;
import java.util.*;
import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.*;
import javax.servlet.*;
import java.util.Random;
import javax.servlet.http.*;
import java.util.*;
import javax.mail.BodyPart ;
import javax.mail.Message ; 
import javax.mail.* ;
import javax.mail.MessagingException ;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session ;
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage ; 
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMultipart; 

public class ReserveCar extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			HttpSession session = request.getSession(true);
			
			//String user=((String)session.getAttribute("userid")); 
			//Get the values from the form
			String carid=request.getParameter("carid");
			String carmake=request.getParameter("carmake");
			String cartype=request.getParameter("cartype");
			String carmodel=request.getParameter("carmodel");
			double rentalamount=Double.parseDouble(request.getParameter("rentalamount"));
			//double price=Integer.parseInt(rentalamount);
			String location= request.getParameter("location");
			String pick= request.getParameter("pick");
			String drop= request.getParameter("drop");
			
			String firstName=request.getParameter("firstName");
			String lastName=request.getParameter("lastName");
			String address=request.getParameter("address");
			String phoneNumber=request.getParameter("phoneNumber");
			String creditcard=request.getParameter("creditcard");	
			
			String insurance=request.getParameter("insurance");	
			String status="Active";
			String user=((String)session.getAttribute("username"));
				
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CarRentals");
			
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myOrders = db.getCollection("reservation_details");
			System.out.println("Collection Reservation Details selected successfully");
			
			Random r = new Random( System.currentTimeMillis() );
			int rand= 10000000 + r.nextInt(20000000);
			String randString=Integer.toString(rand);			
	
			BasicDBObject doc = new BasicDBObject("title", "reservation_details").
				append("first_name", firstName).
				append("last_name" , lastName).
				append("address", address).
				append("phone_number", phoneNumber).
				append("creditcard", creditcard).
				append("location",location).
				append("carid",carid).
				append("carmake", carmake).
				append("cartype", cartype).
				append("carmodel", carmodel).
				append("total_price",rentalamount).
				append("insurance",insurance).
				append("pick",pick).
				append("drop",drop).
				append("user",user).
				append("confirmation_number",randString).
				append("status", status);
					
			myOrders.insert(doc);
				
			System.out.println("Document inserted successfully");
			
			String insurance_message = "";
			if(insurance.equals("yes")){
					insurance_message = "$9 coverage added.";
			}else{
					insurance_message = "No insurance coverage";
			}
			//Send the response back to the JSP
			PrintWriter out = response.getWriter();
			
	
			String email=((String)session.getAttribute("email"));
		
			String to = email;

          // Sender's email ID needs to be mentioned
         final String from = "midwestcarrentals@gmail.com";
			String subject="Reservation Confirmation";
		 final String password1="midwest123";		 
		
          // Get system properties
          Properties properties = System.getProperties();

          properties.put("mail.smtp.starttls.enable", "true"); 
          properties.put("mail.smtp.host", "smtp.gmail.com");

          properties.put("mail.smtp.port", "587");
          properties.put("mail.smtp.auth", "true");
          Authenticator authenticator = new Authenticator () {
                public PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(from,password1);
                }
            };
			
			String body="<h1>MidWest Car Rentals</h1>"+
						"Dear"+firstName+"\n<br>"+
						"<p><b>Your Reservation is Confirmed</b></p>"+
						"<p>Your Confirmation number is "+randString+"."+
						"<p>Thank you for your recent MidWest Car Rental reservation at"+location+" We hope that the entire experience"+
						"met or exceeded your expectations.</p>"+
						"<p>Below are the details for your reservation</p>"+
						"<p>Name: " +firstName+lastName+ "</p>"+
						"<p>Vehicle Type: " +cartype+ "</p>"+
						"<p>Vehicle Model: "+carmodel+"</p>"+
						"<p>Pick Up Date and Time: "+pick+"</p>"+
						"<p>Drop off Date and Time: "+drop+"</p>"+
						"<p>Insurance: "+insurance_message+"</p>";
						

            Session session1 = Session.getInstance( properties , authenticator);  
          
             // Create a default MimeMessage object.
             
			 MimeMessage message = new MimeMessage(session1);

             // Set From: header field of the header.
             message.setFrom(new InternetAddress(from));

             // Set To: header field of the header.
             message.addRecipient(Message.RecipientType.TO,
                                      new InternetAddress(to));
					  
             // Set Subject: header field
            message.setSubject(subject);

			body+="First Name:"+firstName+"\n Last Name:"+lastName+"\n";
			 
             // Now set the actual message
			 message.setContent(body, "text/html; charset=utf-8");
             //message.setText(body);

            // Send message
            Transport.send(message);
			
			String docType =
					"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
					"Transitional//EN\">\n";
					
					String title = "Your Car has been Reserved Successfully";
					
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
				out.println("<!DOCTYPE html>" + "<html lang=\"en\">"
					+ "<nav>"
					+ "<ul>"
					+ "<li class='start'><a href='index.jsp'>Home</a></li>");
					
					if(user == null){					
					out.println("<li class='r'><a href='login.jsp'>Login</a></li>");
					}
					else{
					out.println("<li class='r'><a href='Logout'>Logout</a></li>");
					}
					out.println("<li class= 'r'><a href='ViewReservations'>Manage Your Reservation</a></li>"
					+ "<li><a href='about-us.jsp'>About US</a></li>"
					+ "</ul>"
					+ "</nav>"
					+ "<div id=\"body\">"
					+ "<section id=\"review-content\">"
					+ "</section>");
	
				
                out.println("<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
				"<H3 ALIGN=CENTER>Your Confirmation Number is :" + randString + "</H3>\n" +
                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "<TH colspan='2'>Reservation Details</th>");
				
				out.print("<TR><TD> First Name  \n</TD>");
				out.print("<TD>" + request.getParameter("firstName") + "\n</TD></TR>");
				
				
				out.print("<TR><TD> Last Name  \n</TD>");
				out.print("<TD>" + request.getParameter("lastName") + "\n</TD></TR>");
				
				
				out.print("<TR><TD> Phone Number \n</TD>");
				out.print("<TD>" + request.getParameter("phoneNumber") + "\n</TD></TR>");
				
				out.print("<TR><TD> Address  \n</TD>");
				out.print("<TD>" + request.getParameter("address") + "\n</TD></TR>");
				
				out.print("<TR><TD> Car Make  \n</TD>");
				out.print("<TD>" + request.getParameter("carmake") + "\n</TD></TR>");
				
				out.print("<TR><TD> Car Type  \n</TD>");
				out.print("<TD>" + request.getParameter("cartype") + "\n</TD></TR>");
				
				out.print("<TR><TD> Car Model  \n</TD>");
				out.print("<TD>" + request.getParameter("carmodel") + "\n</TD></TR>");
				
				out.print("<TR><TD> Total Price  \n</TD>");
				out.print("<TD>" + request.getParameter("rentalamount") + "\n</TD></TR>");
				
				out.print("<TR><TD> Pick Up Date and Time  \n</TD>");
				out.print("<TD>" + request.getParameter("pick") + "\n</TD></TR>");
				
				out.print("<TR><TD> Drop off Date and Time  \n</TD>");
				out.print("<TD>" + request.getParameter("drop") + "\n</TD></TR>");
				
				out.println("</TABLE>\n");
			
		
			String backURL =
          response.encodeURL("/midwest/index.jsp");
       
        // "Proceed to Checkout" button below table
		 out.println
          ("</TABLE>\n" +
           "<FORM ACTION=\"" + backURL + "\"\n" +
		   " METHOD=\"post\">\n"+
           "<BIG><CENTER>\n" +
			 "<p>Email with Reservation details has been sent on your registered email id</p>"+
		   "<p>Thank you for Reserving a car from MidWest CarRental...</p>"+
		   "<p>Have a safe journey</p>"+
           "<INPUT class='blue-button' TYPE=\"SUBMIT\"\n" +
           "  VALUE=\"Go to Home Page\">\n" +
		   
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
		catch (Exception e)
		{
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	
}