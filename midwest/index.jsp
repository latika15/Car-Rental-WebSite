<!-- INDEX -->
<style>
.button-primary{
 text-decoration:none; 
 text-align:center; 
 padding:11px 32px; 
 border:solid 1px #004F72; 
 -webkit-border-radius:4px;
 -moz-border-radius:4px; 
 border-radius: 4px; 
 font:22px Arial, Helvetica, sans-serif; 
 font-weight:bold; 
 color:#E5FFFF; 
 background-color:#1e7e9e; 
 background-image: -moz-linear-gradient(top, #1e7e9e 0%, #1982A5 100%); 
 background-image: -webkit-linear-gradient(top, #1e7e9e 0%, #1982A5 100%); 
 background-image: -o-linear-gradient(top, #1e7e9e 0%, #1982A5 100%); 
 background-image: -ms-linear-gradient(top, #1e7e9e 0% ,#1982A5 100%); 
 filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#1982A5', endColorstr='#1982A5',GradientType=0 ); 
 background-image: linear-gradient(top, #1e7e9e 0% ,#1982A5 100%);   
 -webkit-box-shadow:0px 0px 2px #bababa, inset 0px 0px 1px #ffffff; 
 -moz-box-shadow: 0px 0px 2px #bababa,  inset 0px 0px 1px #ffffff;  
 box-shadow:0px 0px 2px #bababa, inset 0px 0px 1px #ffffff; 
} 
</style>  
  
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Mid-West Car Rentals</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
	<script type="text/javascript" src="javascript/javascript.js"></script>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

	<script type="text/javascript" src="javascript/jquery.timepicker.js"></script>
  	<link rel="stylesheet" type="text/css" href="css/jquery.timepicker.css" />
</head>

<body onload="init()">
<% String username=((String)session.getAttribute("username")); 
	boolean isAdmin = false;%>
<div id="container">
    <header>
		<%@include file="global/header.jsp"%>
		<% if(username != null)
		{ 
			if(username.equals("admin"))
				isAdmin = true;
		%>
		<p>Welcome <%= username %>
		<% } %>
    </header>
    <nav>
		<%@include file="global/nav.jsp"%>
    </nav>
<%
	if(isAdmin){
%>
	<jsp:forward page="/AdminServlet" />
<%
	}else
	{
%>
<div id="body">		
	<form name="autofillform" action="autocomplete">
	      <table border="0" cellpadding="5"> 
	        <tbody>
	          <tr>
	            <td><div style="width :130 px;"><strong>Search your favourite cars:</strong></td></div>
	            <td><input type="text" size="100" id="complete-field" onkeyup="doCompletion()"></td>
	          </tr>
	          <tr>
	            <td id="auto-row" colspan="2"><table id="complete-table" class="popupBox"/></td>
	          </tr>
	        </tbody>
	      </table>
	    </form>
	<section id="content">
	    <article>
	    
		<br>
		<% String message = (String) session.getAttribute("message");
						if(message != null){
							out.println("<p style='color:red'><b>"+message+"</b></p>");
							message = "";
							session.setAttribute("message",message);
						}
		%>
			<img class="header-image" src="images/background1.jpg" width = "100%" height = "100%" alt="Index Page Image" />
			<br>
			<p>To let us serve you better. Please search a car that best matches your Criteria.</p>
			<% if(username==null)
			{
			%>
            <h2>Special Offer</h2>
			<h4><font color="red"> HURRAY...Get 15% off on your first SUGNUP !!! ...</font></h4>
			<a class="button-primary" href="register.jsp">SIGN UP</a>
			<% } %>
			
			<br>
			
		</article>
	
		
    </section>
        
<aside class="sidebar">
<ul>	                                

<li>
<%@include file="global/search.jsp"%>	
</li>
</ul>

</aside>
    
	<div class="clear"></div>
	</div>
		
<%
	}
%>
	<footer>
        
        <div class="footer-bottom">
           <p>	FALL 2015 - CSP595 Project by TEAM - 5</p>
        </div>
    </footer>
</div>

</body>

</html>