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
			  <form method="post" action="LoginServlet">
				 <h4>Enter your User ID and Password and click Login</h4>
				 <table cellpadding='2' cellspacing='1'>
					<tr>
						<td>UserName*</td>
						<td><input type="TEXT" size="15" name="username"></input></td>
					</tr>
					<tr>
						<td>Password *</td>
						<td><input type="PASSWORD" size="15" name="password"/></td>
					</tr>
					<tr>
						<td colspan='2'>
							<center><input type="submit" value="Login" /></center>
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
                        <li><a href="register.jsp">Register</a></li>
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