import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class LogoutServlet extends HttpServlet {
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		

			HttpSession session = request.getSession();
			session.invalidate();
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> You are now logged out </h1>");
			out.println("<p> Click <a href=\"login.html\">here</a> to login again</p>");
			out.println("</body>");
			out.println("</html>");


		
	}	
}