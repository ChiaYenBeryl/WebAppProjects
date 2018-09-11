// Saved as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/eshoporder") 
public class EshopOrderServlet extends HttpServlet {  // JDK 6 and above only
	public int SIZE = 8;
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
            "jdbc:mysql://localhost:3306/ebookshop?useSSL=false", "myuser", "asdfasdf");  // <<== Check
 
         // Step 2: Create a "Statement" object inside the "Connection"
         stmt = conn.createStatement();
 
         // Step 3 & 4: Execute a SQL SELECT query and Process the query result
		 out.println("<html><head><title>Order Results</title></head><body>");
 
		 // Retrieve the books' id. Can order more than one books.
		 String[] ids = request.getParameterValues("id");
		 String[] qtys = request.getParameterValues("qty_ordered");
		 String[] cust_names = request.getParameterValues("cust_name");
		 String[] cust_emails = request.getParameterValues("cust_email");
		 String[] cust_phones = request.getParameterValues("cust_phone");
		 int[] qtyInt = new int[SIZE];
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
			+"</div></ul></nav><article>");
		
			for(int i =0; i<qtys.length;i++){
				qtyInt[i]= Integer.parseInt(qtys[i]);// Parsing from string to int
			}
		 if (ids != null) {
		 String sqlStr = "";
		 int count;
		 
		 
		
		 // Process each of the books
		 for (int i = 0; i < ids.length; ++i) {
			 // Update the qty of the table books
			 sqlStr = "Select * from princess where id =" + ids[i];
			 ResultSet rset = stmt.executeQuery(sqlStr);
			 rset.next(); //to move the pointer to the first item((it's pointing at null at first
			 if (qtyInt[i]>Integer.parseInt(rset.getString("qty"))){
			 out.println("<h3>The quantity for princess "
				+rset.getString("name")
				+" has exceeded stock.<h3>");}
			 else{
			 sqlStr = "UPDATE princess SET qty = qty - " + qtys[i] + " WHERE id = " + ids[i];
			 out.println("<p>" + sqlStr + "</p>");  // for debugging
			 count = stmt.executeUpdate(sqlStr);
			 out.println("<p>" + count + " record updated.</p>");
 
			 // Create a transaction record
			 sqlStr = "INSERT INTO order_records (id, qty_ordered, cust_name, cust_email, cust_phone) VALUES ("
					 + ids[i] + ", " + qtys[i] + ", '" + cust_names[0] + "', '" + cust_emails[0] + "', " + cust_phones[0] + ")";
			 out.println("<p>" + sqlStr + "</p>");  // for debugging
			 count = stmt.executeUpdate(sqlStr);
			 out.println("<p>" + count + " record inserted.</p>");
			 out.println("<h3>Your order for princess id=" + ids[i] + " with quantity of " + qtys[i]
					 + " has been confirmed.</h3>");}
		 }
		 out.println("<h3>Thank you.<h3>");
		 } else { // No book selected
		 out.println("<h3>Please go back and select a princess...</h3>");
		 }
		 out.println("</article>");
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