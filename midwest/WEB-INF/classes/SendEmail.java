import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


public class SendEmail extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	
	
    final String firstname=(String)request.getAttribute("firstname");
	final String lastname=(String)request.getAttribute("lastname");
	
	String  email=request.getParameter("email");
	
	

	
	
			String to = email;

          // Sender's email ID needs to be mentioned
          String from = "kaustubh0512@gmail.com";
			String subject="Registration";
		String body="";
          // Get system properties
          Properties properties = System.getProperties();

          properties.put("mail.smtp.starttls.enable", "true"); 
          properties.put("mail.smtp.host", "smtp.gmail.com");

          properties.put("mail.smtp.port", "587");
          properties.put("mail.smtp.auth", "true");
          Authenticator authenticator = new Authenticator () {
                public PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication("kaustubh0512@gmail.com","bojewar0512");
                }
            };

			
			
			
            Session session1 = Session.getInstance( properties , authenticator);  
          try{
             // Create a default MimeMessage object.
             
			 
			 
			 MimeMessage message = new MimeMessage(session1);

			 
			 
             // Set From: header field of the header.
             message.setFrom(new InternetAddress(from));

			 
			 
			 
             // Set To: header field of the header.
             message.addRecipient(Message.RecipientType.TO,
                                      new InternetAddress(to));

									  
			 
									  
             // Set Subject: header field
             message.setSubject(subject);

			body="First Name:"+firstname+"\n Last Name:"+lastname+"\n";
			 
             // Now set the actual message
             message.setText(body);

			 
			 response.getWriter().println("Here");
			 
             // Send message
             Transport.send(message);
			 
			 response.getWriter().println("Success");
	
	}
	catch(Exception e)
	{}
	
	
	response.sendRedirect("/midwest/index.jsp");
	
	
  }

  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}