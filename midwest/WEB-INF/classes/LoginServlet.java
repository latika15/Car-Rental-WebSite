import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
import java.util.NoSuchElementException;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		try{
			
			DB db = mongo.getDB("CarRentals");
			PrintWriter out = response.getWriter();
			Boolean registrationFailed = false;
			Boolean isAdmin = false;
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			out.println(username);
			out.println(password);
			String dbusername = "";
			String email="";
			String firstName="";
				
			if (username != "" && username.length() != 0){
				username = username.trim();
			}
			if (password != "" && password.length() != 0){
				password = password.trim();
			}
			if(username.equals("admin")){
				isAdmin = true;
			}
			if(username != "" && password != ""){
				
				DBCollection user_auth = db.getCollection("user_auth");
				BasicDBObject query = new BasicDBObject();
				query.put("username",username);
				query.put("password",password);
				
				DBCursor cursor = user_auth.find(query);
				
				if(cursor.count() == 0){
					System.out.print("unsuccessful");
					response.sendRedirect("login.jsp");
				}
				else{
					while(cursor.hasNext()){
						
						BasicDBObject obj = (BasicDBObject) cursor.next();
						dbusername = obj.getString("username");
						email= obj.getString("email");
						System.out.println("successful login");		
					}
					HttpSession session = request.getSession();
					session.setAttribute("username",username);
					session.setAttribute("email",email);
					session.setAttribute("firstName",firstName);
				
					if(isAdmin){
						response.sendRedirect("AdminServlet");
					}
					else{
						response.sendRedirect("index.jsp");		
					}	
				}					
			}
			else{
				response.sendRedirect("login.jsp");

			}
			} 
		catch (MongoException e) {
		e.printStackTrace();
	}
	}	
}