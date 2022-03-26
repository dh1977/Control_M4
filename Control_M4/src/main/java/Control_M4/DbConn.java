package Control_M4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConn {
	protected static Connection iniciarConexion()
			throws SQLException, ClassNotFoundException
	{
		// Initialize all the information regarding
		Connection con=null;
		
		// Database Connection
		String dbDriver = "com.mysql.cj.jdbc.Driver";
		String dbURL = "jdbc:mysql:// localhost:3306/";
	
		// Database name to access
		String dbName = "Employees";
		String dbUsername = "root";
		String dbPassword = "4321";
	
		Class.forName(dbDriver);
		con = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword);

		return con;
	}
	
}
