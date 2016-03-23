import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
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
import java.util.*;
import javax.activation.*;
import java.io.*;
import javax.servlet.*;
import java.util.Random;
import javax.servlet.http.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
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
import java.lang.Object;


public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			HttpSession session = request.getSession();
			DB db = mongo.getDB("CarRentals");
			Boolean registrationFailed = false;
			String firstName = request.getParameter("first_name");
			String lastName = request.getParameter("last_name");
			String email = request.getParameter("email");
			int age = Integer.parseInt(request.getParameter("age"));
			String address = request.getParameter("address");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			PrintWriter out = response.getWriter();
			String contentTitle = "Register";
			out.println("<head><title>Mid-West Car Rentals</title><link rel='stylesheet' href='styles.css' type='text/css' /></head><body><div id='container'><header>"+
			"<h1><a href='index.jsp'><img src='images/12.png'></a><h1>"+ 
			"</header><nav><ul><li class='start'><a href='index.jsp'>Home</a></li><li class='r'>"+
			"<a href='login.jsp'>Login</a></li><li class='r'><a href='register.jsp'>Register</a></li><li class= 'r'></li>"+
			"<li><a href='about-us.jsp'>About US</a></li></ul></nav><hr>");
			
			System.out.println(firstName+""+lastName+""+age+""+address+""+username+""+password);
			if (firstName != "" && firstName.length() != 0){
				firstName = firstName.trim();
			}

			if (lastName != "" && lastName.length() != 0){
				lastName = lastName.trim();
			}

			
			if (username != "" && username.length() != 0){
				username = username.trim();
			}
			
			if (password != "" && password.length() != 0){
				password = password.trim();
			}
			
			
			if(username != "" && password != ""){
			
				
				DBCollection user_auth = db.getCollection("user_auth");
				BasicDBObject user = new BasicDBObject("firstName",firstName)
						.append("lastName",lastName)
						.append("address",address)
						.append("email",email)
						.append("age",age)
						.append("username",username)
						.append("password",password);
				user_auth.insert(user);
				
			
				
				out.println("<h3> Registered successfully. </h3>");
				out.println("<br>");
				out.println("<h3>Confirmation email has been sent to your email ID </h3>");
				out.println("<a class='blue-button' href=\"login.jsp\">Click here to login</a>");
				
				
				
			
			Random r = new Random( System.currentTimeMillis() );
			int rand= 20000000 + r.nextInt(20000000);
			String randString=Integer.toString(rand);
			
			DBCollection promos = db.getCollection("promo");
			BasicDBObject promodetails = new BasicDBObject("promo",randString)
						.append("email",email);
				promos.insert(promodetails);
				
				
				

				
			String to = email;

          // Sender's email ID needs to be mentioned
         final String from = "midwestcarrentals@gmail.com";
			String subject="Registration";
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
			String body="First Name:"+firstName+"\n Last Name:"+lastName+"\n";
			body+=  "<br>"+
					"<h1>MidWest Car Rentals</h1>"+
					"<p>Hi, Thank you for signing up at Mid West CarRentals.</p>"+
					"<p>Go to website and search for amazing cars for rent.</p>"+
					"<p>Also for more offers visit Midwest Car Rental Offers</p>"+
					"<br>"+
					"<p>You have got coupon for 15 % off on your first booking</p>"+
					"<p><b>"+randString+"<b></p>";
					
					
					
		
			
			
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

			
			 
             // Now set the actual message
			 message.setContent(body, "text/html; charset=utf-8");
             //message.setText(body);

			 
			
			 
             // Send message
             Transport.send(message);		 	
	
	


	
	

	
			}
			else{
				System.out.println("Enter valid username");
				registrationFailed = true;
			}
			
			if(registrationFailed){
				
				out.println("<h1> Unsuccessful Registeration </h1>");
				out.println("<p> Click <a href=\"register.jsp\">here</a> to register</p>");
			}
			
			String myPageBottom ="<footer>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>FALL 2015 - CSP595 Project by TEAM - 5</p>"
					+ "</div>"
					+ "</footer>"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		
		out.println(myPageBottom);			
		} 
		catch (MongoException e) {
			e.printStackTrace();
		}
		catch (Exception e)
		{
		response.getWriter().println(e);
		}
	}	
}