package eg.edu.alexu.csd.oop.jdbc.cs35;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {

	public static void main(String[] args) {

		Driver driver = new DatabaseDriver();
		Properties info = new Properties();
		File dbDir = new File("Databases");
		info.put("path", dbDir.getAbsoluteFile());
		try {
			Connection connection = driver.connect("jdbc:xmldb://localhost", info);
			Statement stmt = connection.createStatement();
			connection.close();
		} catch (SQLException e) {
			
		}
		
	}

}
