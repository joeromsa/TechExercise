import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LisenceFinder {
	
	public static String assignLisence() {
		
		Connection connection = null;
		String selectSql = "SELECT license FROM techExRomsaTable ORDER BY id DESC LIMIT 1";
		PreparedStatement preparedStatement = null;
		String last = "";
		
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			preparedStatement = connection.prepareStatement(selectSql);
			ResultSet rs = preparedStatement.executeQuery();
			
			rs.next();
			last = rs.getString("license").trim();
			
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
	         } catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
		
		
		System.out.println(last);
		int i = Integer.parseInt(last.substring(4));
		i++;
		String newLicense = last.substring(0, 4);
		newLicense = newLicense + Integer.toString(i);
		
		
		
		
		return newLicense;
	}

}
