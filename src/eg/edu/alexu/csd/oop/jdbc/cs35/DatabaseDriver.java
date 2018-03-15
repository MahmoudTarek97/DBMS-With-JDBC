package eg.edu.alexu.csd.oop.jdbc.cs35;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs35.DataBaseManager;

import java.io.File;
import java.lang.UnsupportedOperationException;

public class DatabaseDriver implements Driver{

	private Database db;
	
	public DatabaseDriver() {
		this.db = new DataBaseManager();
		db.createDatabase("testdb", false);
	}
	
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		
		boolean jdbcFlag = false;
		boolean subProtocolFlag = false;
		boolean dataSourceFlag = false;
		
		String[] sections = url.split("\\:");
		
		if(sections.length != 3)
			throw new SQLException();
		
		if(sections[0].trim().equals("jdbc"))
			jdbcFlag = true;
		if(sections[1].trim().equals("xmldb"))
			subProtocolFlag = true;
		if(sections[2].trim().equals("//localhost"))
			dataSourceFlag = true;
		
		
		return jdbcFlag & subProtocolFlag & dataSourceFlag;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		
		if(!acceptsURL(url))
			return null;
		
		File dbDir = new File(((File) info.get(info.keySet().iterator().next())).getAbsolutePath());
		
		if(!dbDir.exists())
			this.db.createDatabase(dbDir.getPath(), false);
		
		return new DatabaseConnection(db);
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		DriverPropertyInfo[] driverProperties = new DriverPropertyInfo[3];

		if(!acceptsURL(url))
			throw new SQLException();
		
		String[] sections = url.split("\\:");	
		
		driverProperties[0] = new DriverPropertyInfo("Connection Type",sections[0].trim());
		driverProperties[1] = new DriverPropertyInfo("Sub Protocol",sections[1].trim());
		driverProperties[2] = new DriverPropertyInfo("Data Source",sections[2].trim());

			
		return driverProperties;
	}

	@Override
	public int getMajorVersion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMinorVersion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean jdbcCompliant() {
		throw new UnsupportedOperationException();
	}	
}
