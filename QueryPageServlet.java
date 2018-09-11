
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
 
@WebServlet("/querypage") 
public class QueryPageServlet extends HttpServlet {  // JDK 6 and above only
 
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
 
		String sqlStr = "select trait from princess;";
 
         // Print an HTML page as output of query
         out.println("<html><head><title>Query Page</title></head><body>");
         ResultSet rset = stmt.executeQuery(sqlStr); // Send the query to the server
		
		 out.println("<!DOCTYPE html>"
			+"<html><head>"
			+"<link rel='stylesheet'"
			+"href='https://www.w3schools.com/w3css/4/w3.css'>"
			+"<style>"
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

		out.println("<form method='get' action='query'>");
		out.println("<article>Choose any thing you prefer:<br /><br />");
		while(rset.next()) {
		 out.println("<p><input type='checkbox' name='trait' value="
				 + "'" + rset.getString("trait") + "' />"+rset.getString("trait")+"</p>");		   
		 }
		out.println("<input type='submit' value='Search' />");
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