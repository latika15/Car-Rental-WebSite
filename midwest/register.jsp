<!doctype html>

<!-- INDEX -->
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Mid-West Car Rentals</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>

<body>
<div id="container">
    <header>
    	<%@include file="global/header.jsp"%>
    </header>
    <nav>
		<%@include file="global/nav.jsp"%>
    </nav>
<div id="body">		

	<section id="content">
	    <article>	
		<center>
			<form method="POST" action="RegisterServlet">
				  <p><strong>Create a new account</strong></p>
				  <table cellpadding='2' cellspacing='1'>
					<tr>
						<td>First Name:</td>
						<td><input required type="TEXT" size="15" name="first_name" placeholder="First name"></input></td>
					</tr>
					<tr>
						<td>Last Name:</td>
						<td><input required type="TEXT" size="15" name="last_name" placeholder="Last name"></input></td>
					</tr>
					<tr>
						<td>Email:</td>
						<td><input required type="TEXT" size="15" name="email" placeholder="Email"></input></td>
					</tr>
					<tr>
						<td>Username:</td>
						<td><input required type="TEXT" size="15" name="username" placeholder="Username"></input></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input required type="PASSWORD" size="15" name="password" placeholder="Password"/></td>
					</tr>
					<tr>
						<td>Age:</td>
						<td><input required type="TEXT" size="15" name="age" placeholder="age"></input></td>
					</tr>
					<tr>
						<td>Address:</td>
						<td><input required 
						type="TEXT" size="15" name="address" placeholder="Address"></input></td>
					</tr>
									
					<tr>
						<td colspan='2'>
							<center><input type="submit" value="Register" /></center>
						</td>
					</tr>
				</table>
			</form>
		</center>
		<p>Note: Please make sure your details are correct before submitting form and that all fields marked with * are completed!.</p>
			
		</article>
    </section>
        
    <aside class="sidebar">
	
            <ul>	
               <li> <h4></h4>
					<ul>
                        <li><a href="login.jsp">Login</a></li>
                </li>                
            </ul>
		
    </aside>
    
	<div class="clear"></div>
	</div>
    
	<footer>
        <div class="footer-content">           
        <div class="clear"></div>
        </div>
        <div class="footer-bottom">
           <p>	FALL 2015 - CSP595 Project by TEAM - 5</p>
        </div>
    </footer>
</div>

</body>

</html>