<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>CSP 595 - Assignment 3</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>

<body>
<% String user=((String)session.getAttribute("userid")); %>
<div id="container">
    <header>
    	<h1><a href="/">CSP595 <span>GAMECENTER</span></a></h1>
		<p>Welcome <%= user%></p>
        <h2>Assignment 3 for CSP 595</h2>
		<a href="Logoutform.jsp" style='font-weight: bold;float:right;margin-right:20px;'>Logout</a>
    </header>

	
		
	<%@ page import="java.util.*,java.text.*, java.io.*,com.mongodb.*" %>
	
	<% 
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("CSP595Tutorial");
			
		DBCollection myReviews = db.getCollection("myReviews");
		String productID= request.getParameter("productID");
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("productID", productID);

		DBCursor cursor1 = myReviews.find(searchQuery1);
		String productName=request.getParameter("productName");
		
	%>
		<p>Reviews For:" <%= productName %></p>
		<br><br><hr>
	<% 
		if(cursor1.count() == 0){
		%>
				<p>There are no reviews for this product.</p>
		
		<%		
			}else{
			
				
				
				
				String productName1 = "";
				String userName = "";
				String category="";
				String price= "";
				String retailer= "";
				String retailerzip="";
				String retailercity="";
				String retailerstate= "";
				String sale= "";
				String mfname = "";
				String userid = "";
				String userage = "";
				String usergender = "";
				String reviewRating = "";
				String reviewDate =  "";
				String reviewText = "";
				
				int count=0;
				while (cursor1.count()>count) {
				
				
					BasicDBObject obj = (BasicDBObject) cursor1.next();	
					out.println("<div style='margin-bottom:30px;border-bottom:1px solid black;'>");
						
						productName1 = obj.getString("productName");
						%>
						<p>Product Name: <%= obj.getString("productName") %></p>
						
						
						<% productName1 = obj.getString("productName"); %>
						<p>Product ID: <%= obj.getString("productID") %></p>
						
						
						<% productName1 = obj.getString("category"); %>
						<p>Category:<%= obj.getString("category") %></p>
						
						
						<% productName1 = obj.getString("price"); %>
						<p>Price:<%= obj.getString("price") %></p>
						
						
						<% productName1 = obj.getString("retailer"); %>
						<p>Retailer:<%= obj.getString("retailer") %></p>
						
						<% productName1 = obj.getString("retailerzip"); %>
						<p>Retailer Zip:<%= obj.getString("retailerzip") %></p>
						
						<% productName1 = obj.getString("retailercity"); %>
						<p>Retailer City:<%= obj.getString("retailercity") %></p>
						
						<% productName1 = obj.getString("retailerstate"); %>
						<p>Retailer State:<%= obj.getString("retailerstate") %></p>
				
						<% productName1 = obj.getString("sale"); %>
						<p>Sale:<%= obj.getString("sale") %></p>

						<% productName1 = obj.getString("mfname"); %>
						<p>Manufacturer Name:<%= obj.getString("mfname") %></p>
						
					
						<% productName1 = obj.getString("userage"); %>
						<p>User Age:<%= obj.getString("userage") %></p>
						
						
						<% productName1 = obj.getString("usergender"); %>
						<p>User Gender:<%=obj.getString("usergender") %></p>
						
					
						<% userName = obj.getString("userid"); %>
						<p>User Name:<%= obj.getString("userid") %></p>
									
						
					
						<% reviewRating = obj.getString("reviewRating").toString(); %>
						<p>Review Rating:<%= obj.getString("reviewRating").toString() %></p>
						
						
						<% reviewDate = obj.getString("reviewDate"); %>
						<p>Review Date:<%= obj.getString("reviewDate") %></p>
						
						
						<% reviewText = obj.getString("reviewText"); %>
						<p>Review Text:<%= obj.getString("reviewText") %></p>
						
						<% 
					count++; %>
						</div>
					
		<%			
					
				}
			}	

		%>
		</TABLE>
        <FORM ACTION="index.jsp" METHOD="post">
        <BIG><CENTER>
		  
        <INPUT TYPE="SUBMIT"
           VALUE="Go to Home Page">
		   
        </CENTER></BIG></FORM>

		<footer>
			<div class='footer-bottom'>
            <p>CSP 595 - Enterprise Web Application - Assignment 3</p>
			</div>
			</footer>			
			</body>
			</html>

