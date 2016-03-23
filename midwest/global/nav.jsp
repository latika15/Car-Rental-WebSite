

<%-- Begin Navigation --%>
	<ul>
        	<li class="start"><a href="index.jsp">Home</a></li>
			<% String user=((String)session.getAttribute("username"));
			if(user == null){
			
			%>
			<li class="r"><a href="login.jsp">Login</a></li>
			<li class="r"><a href="register.jsp">Register</a></li>
			<%}
			else{
			%>
			<li class="r"><a href="Logout">Logout</a></li>	
			<li class= "r"><a href="ViewReservations">Manage Your Reservation</a></li>
			
			<%} %>
			
			
			
			<li><a href="about-us.jsp">About US</a></li>
</ul>
<%-- End Navigation --%>