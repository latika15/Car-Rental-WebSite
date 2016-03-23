<%-- Begin Search --%>

	<h4>Search Car</h4>
	<ul>
		<li class="text">
			<form action="SearchCars" method='POST'>
			<table cellpadding = "2px" cellspacing = "2px">
			<tr><td><label>Pick Up Location:</label>
			<input required id="pickuplocation" name="pickuplocation" type="text" style="width: 120px; border: 1px solid #aac;border-radius: 7px;font-size: 13px;padding: 3px; margin-left: 1px;" /></td></tr>

			<tr><td><label>Pick Up date:</label>
			<input required id="pickupdate" name="pickupdate" type="date" style="width: 123px; border: 1px solid #aac;border-radius: 7px;font-size: 13px;padding: 3px; margin-left: 26px;" /></td></tr>

			<tr><td><label>Pick Up Time:</label>
			<input id="pickuptime" name="pickuptime" type="text" style="width: 123px; border: 1px solid #aac;border-radius: 7px;font-size: 13px;padding: 3px; margin-left: 23px;" /></td></tr>
			<script>
                $(function() {
                    $('#pickuptime').timepicker({ 'scrollDefault': 'now' });
                });
            </script>

			<tr><td><label>Drop Off date:</label>
			<input  required id="dropoffdate" name="dropoffdate" type="date" style="width: 123px; border: 1px solid #aac;border-radius: 7px;font-size: 13px;padding: 3px; margin-left: 20px;" /></td></tr>


			<tr><td><label>Drop Off Time:</label>
			<input id="dropofftime" name="dropofftime" type="text" style="width: 125px; border: 1px solid #aac;border-radius: 7px;font-size: 13px;padding: 3px; margin-left: 15px;" /></td></tr>
			<script>
                $(function() {
                    $('#dropofftime').timepicker({ 'scrollDefault': 'now' });
                });
            </script>

			<tr>
				<td><label>Age: </label>
				<select name="age" style=" width: 124px; border: 1px solid #aac;border-radius: 7px;font-size: 13px;padding: 3px; margin-left: 82px;">
				<option value = "21">21</option>
				<option value = "22">22</option>
				<option value = "23">23</option>
				<option value = "24">24</option>
				<option value = "25">24+</option>
				
				</select>
			  </td></tr>
			  <tr><td><label>Car Type: </label><Select name="cartype" value="CarSize" style="width: 123px; border: 1px solid #aac;border-radius: 7px;font-size: 13px;padding: 3px; margin-left: 48px;">
				<option value = "ALL">ALL</option>
				<option value = "economy">Economy</option>
				<option value = "standard">Standard</option>
				<option value = "suv">SUV</option>
				<option value = "luxury">Luxury</option>
			  </td></tr>
			</table>
				<% 
			if(username == null){
			%>
			  <input disabled type="submit" class ="formbutton" value="Search Cars" />
			  <p>Please login/register to Search for Cars</p>
			<% } else {%>
			 <input type="submit" class ="formbutton" value="Search Cars" />
			<% } %> 
			</form>
		</li>
		</ul>
<%-- End Search --%>