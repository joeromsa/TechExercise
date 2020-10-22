

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/RegistryFormLookup")
public class RegistryFormLookup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public RegistryFormLookup() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String plateNum = request.getParameter("license");
		searchLicense(plateNum, response);
	}
	
	void searchLicense(String license, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    String title = "License Lookup Result";
	    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	            "transitional//en\">\n"; //
	    out.println(docType + //
	            "<html>\n" + //
	            "<head><title>" + title + "</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            "<h1 align=\"center\">" + title + "</h1>\n");
	    
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	    	DBConnection.getDBConnection();
	    	connection = DBConnection.connection;
	    	
	    	String sqlSelect = "Select * FROM techExRomsaTable WHERE LICENSE = ?";
	    	preparedStatement = connection.prepareStatement(sqlSelect);
	    	preparedStatement.setString(1, license);
	    	
	    	ResultSet rs = preparedStatement.executeQuery();
	    	
	    	while (rs.next()) {
	    		String fName = rs.getString("fname").trim();
	    		String lName = rs.getString("lname").trim();
	    		String address = rs.getString("address").trim();
	    		String make = rs.getString("carmake").trim();
	    		String model = rs.getString("carmodel").trim();
	    		String year = rs.getString("caryear").trim();
	    		
	    		out.println("First Name: " + fName + ", ");
	    		out.println("Last Name: " + lName + ", ");
	    		out.println("Address: " + address + ", ");
	    		out.println("Car Make: " + make + ", ");
	    		out.println("Car Model: " + model + ", ");
	    		out.println("Car Year: " + year + "<br>");
	    	}
	    	
	    	out.println("<a href=/techExerciseRomsa/registryLookup.html>Search License Plate</a> <br>");
	        out.println("</body></html>");
	        rs.close();
	        preparedStatement.close();
	        connection.close();
	    }
	    catch (SQLException se) {
	         se.printStackTrace();
	    } catch (Exception e) {
	         e.printStackTrace();
	    } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } 
	         catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } 
	         catch (SQLException se) {
	            se.printStackTrace();
	         }
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
