// Saved as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
 
@WebServlet("/query") 
public class EshopQueryServlet extends HttpServlet {  // JDK 6 and above only
 
   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
 
      Connection conn = null;
      Statement stmt = null;
      try {
         // Step 1: Create a database "Connection" object
         // For MySQL
         Class.forName("com.mysql.jdbc.Driver");  // Needed for JDK9/Tomcat9
         conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/ebookshop", "myuser", "asdfasdf");  // <<== Check
 
         // Step 2: Create a "Statement" object inside the "Connection"
         stmt = conn.createStatement();
 
         // Step 3: Execute a SQL SELECT query
		 String[] traits = request.getParameterValues("trait");  // Returns an array
		 if (traits == null) {
		 out.println("<h2>Please go back and select an trait</h2>");
		 return; // Exit doGet()
		 } 
		 // Okay to perform the database query using the above codes

		 String sqlStr = "SELECT * FROM princess WHERE trait IN (";
		 sqlStr += "'" + traits[0] + "'";  // First trait
		 for (int i = 1; i < traits.length; ++i) {
			sqlStr += ", '" + traits[i] + "'";  // Subsequent traits need a leading commas
			}
		 sqlStr += ") AND qty > 0 ORDER BY trait ASC, name ASC";
		 ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server
         // Print an HTML page as output of query
         //out.println("<html><head><name>Query Results</name></head><body>");
         
		 out.println("<!DOCTYPE html>"
			+"<html><head><title>Query Results</title>"
			+"<link rel='stylesheet'"
			+"href='https://www.w3schools.com/w3css/4/w3.css'>"
			+"<style>"
			+ " table, th, td {"
			+ " border: 1px solid black;"
			+ " border-collapse: collapse;"
			+ " } "
			+ " th, td {"
			+ " padding: 15px;"
		    + "}"
			+".w3-button {width:150px;}"
			+"div.container {"
			+"width: 100%;"
			+"border: 1px solid gray;}"
			+"header, footer {padding: 1em;    color: white;    background-color: lightBlue;    clear: left;    text-align: center;}"
			+"nav {    float: left;    max-width: 300px;    margin: 1;    padding: 1em;}"
			+"nav ul {    list-style-type: none;    padding: 0;}"
			+"article {    margin-left: 170px;    border-left: 1px solid gray;    padding: 1em;    overflow: hidden;}"
			+"</style></head><body><div class='container'><header>   <h1>Disney Princess</h1></header>"
			+"<nav><ul>    <div class='w3-container'>	<form method='get' action='index.html'>"
			+"<p><button type='submit' class='w3-button w3-pale-red' >About</button></p></form>"
			+"<form method='get' action='querypage'><p><button class='w3-button w3-pale-red'>Shop</button></p></form>"
			+"<form method='get' action='product'><p><button type='submit' class='w3-button w3-pale-red'>Our Princesses</button></p></form>"
			+"</div></ul></nav>");
         // Step 4: Process the query result
		 // Print the <form> start tag
		 out.println("<article><h2>Thank you for your query.</h2>");
         out.println("<p>You query is: " + sqlStr + "</p>"); // Echo for debugging
         
		 out.println("<form method='get' action='eshoporder'>");
		 
		 out.println("<table>"+
		 "<tr>"+
			 "<th>Check if wish to order</th>"+
			 "<th>Name</th>"+
			 "<th>Photo</th>"+
			 "<th>Trait</th>"+
			 "<th>PRICE</th>"+
			  "<th>Quantity</th></tr>");
 
		 // For each row in ResultSet, print one checkbox inside the <form>
		 while(rset.next()) {
		 out.println("<tr><td><p><input type='checkbox' name='id' value="
				 + "'" + rset.getString("id") + "' /></p></td>"
				 + "<td><p>" + rset.getString("name") + "</p></td>"
				 + "<td><p><img src='" + rset.getString("image_src") + "' alt='"
				 + rset.getString("name")+"' width='50%' height='30%'></p></td>"
				 + "<td><p>" + rset.getString("trait") + "</p></td>"
				 + "<td><p>" + rset.getString("price") + "</p></td>"
				 + "<td><p><input type='number' name='qty_ordered' min='1'></p></td></tr>");		   
		 }
		 out.println("</table>");
 
		 // Print the submit button and </form> end-tag
		 out.println("<p>Enter your Name: <input type='text' name='cust_name' required /></p>");
		 out.println("<p>Enter your Email: <input type='email' name='cust_email' required /></p>");
		 out.println("<p>Enter your Phone Number: <input type='number' name='cust_phone' required /></p>");


		 out.println("<p><input type='submit' value='ORDER'/>");
		 out.println("</article></form>");
		 out.println("<footer>Copyright &copy; Hsieh Chia Yen 2018 </footer></div></body></html>");
      } catch (SQLException ex) {
         ex.printStackTrace();
     } catch (ClassNotFoundException ex) {
        ex.printStackTrace();
     } finally {
         out.close();
         try {
            // Step 5: Close the Statement and Connection
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
         } catch (SQLException ex) {
            ex.printStackTrace();
         }
      }
   }
}