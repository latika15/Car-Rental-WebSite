import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Logout extends HttpServlet {

public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {	
	
	HttpSession session = request.getSession();
	session.invalidate();
	response.sendRedirect("index.jsp");			
}
}