
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;

public class AutoCompleteServlet extends HttpServlet {

    private ServletContext context;
    //private ComposerData compData = new ComposerData();
    //private HashMap composers = compData.getComposers();

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    protected void processPage(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        ComposerData compData = new ComposerData();
        HashMap composers = compData.getComposers();
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        String targetId = request.getParameter("id");
        StringBuffer sb = new StringBuffer();
        boolean namesAdded = false;

        if (targetId != null) {
            targetId = targetId.trim().toLowerCase();
        } else {
            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        if (action.equals("complete")) {

            // check if user sent empty string
            if (!targetId.equals("")) {

                Iterator it = composers.keySet().iterator();

                while (it.hasNext()) {
                    String id = (String) it.next();
                    Composer composer = (Composer) composers.get(id);

                     // targetId matches product name
                    if (composer.getCMake().toLowerCase().contains(targetId) || composer.getCModel().toLowerCase().contains(targetId)) {

                        sb.append("<composer>");
                        sb.append("<cid>" + composer.getCId() + "</cid>");
                        sb.append("<cModel>" + composer.getCMake()+" "+composer.getCModel() + "</cModel>");
                        sb.append("</composer>");
                        namesAdded = true;
                    }
                }
            }

            if (namesAdded) {
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<composers>" + sb.toString() + "</composers>");
            } else {
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }

        if (action.equals("lookup")) {

            // put the target composer in the request scope to display 
            if ((targetId != null) && composers.containsKey(targetId.trim())) {
                //request.setAttribute("composer", composers.get(targetId));
                //context.getRequestDispatcher("/csj/SingleProductPage").forward(request, response);
                response.sendRedirect("/midwest/SingleCarPage?carId="+targetId.trim());
            }
        }
    } 
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processPage(request, response);
    } 

 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processPage(request, response);
    }
}
