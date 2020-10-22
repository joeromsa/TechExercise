

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegistryFormInsert")
public class RegistryFormInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public RegistryFormInsert() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fName = request.getParameter("fname");
		String lName = request.getParameter("lname");
		String address = request.getParameter("address");
		
		String make = request.getParameter("make");
		String model = request.getParameter("model");
		String year = request.getParameter("year");
		
		String license = LisenceFinder.assignLisence();
		
		Connection connection = null;
		String insertSql = " INSERT INTO techExRomsaTable (id, fname, lname, address, carmake, carmodel, caryear, license) values (default, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, fName);
			preparedStmt.setString(2, lName);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, make);
			preparedStmt.setString(5, model);
			preparedStmt.setString(6, year);
			preparedStmt.setString(7, license);
			
			preparedStmt.execute();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// response
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      String title = "Inserted into registry";
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	      out.println(docType + //
	            "<html>\n" + //
	            "<head><title>" + title + "</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            "<h2 align=\"center\">" + title + "</h2>\n" + //
	            "<ul>\n" + //

	            "  <li><b>First Name</b>: " + fName + "\n" + //
	            "  <li><b>Last Name</b>: " + lName + "\n" + //
	            "  <li><b>Address</b>: " + address + "\n" + //
	            "  <li><b>Make</b>: " + make + "\n" + //
	            "  <li><b>Model</b>: " + model + "\n" + //
	            "  <li><b>Year</b>: " + year + "\n" + //
	            "  <li><b>License</b>: " + license + "\n" + //

	            "</ul>\n");

	      out.println("<a href=/techExerciseRomsa/simpleFormSearch.html>Search License</a> <br>");
	      out.println("</body></html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
