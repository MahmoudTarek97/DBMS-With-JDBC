package eg.edu.alexu.csd.oop.db.cs35;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.DatabaseCommand;
import eg.edu.alexu.csd.oop.db.cs35.Parser.ParsersFactory;
import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;
import eg.edu.alexu.csd.oop.db.cs35.queryAnalyzer.QueryTypeRecognizer;
import eg.edu.alexu.csd.oop.db.cs35.validator.ValidatorsFactory;

public class DataBaseManager implements Database {

	private String currentDatabaseName = null;
	private CommandsEngineer engineer = CommandsEngineer.CreateCommandsEngineer();
	private ValidatorsFactory validatorsFactory = new ValidatorsFactory();
	private ParsersFactory parsersFactory = new ParsersFactory();
	private QueryTypeRecognizer queryTypeRecognizer = new QueryTypeRecognizer();

	
	public DataBaseManager() {
		File rootDirectory = new File(System.getProperty("user.dir") + File.separator +"Databases");
    	
    	if(!rootDirectory.exists()) {
			rootDirectory.mkdir();
		}	
	}
	
	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {		
		databaseName = databaseName.toLowerCase().trim();
		currentDatabaseName = databaseName;
		File databaseFolder = new File(System.getProperty("user.dir") + File.separator + "Databases" +File.separator + databaseName);

		if (databaseFolder.exists() && dropIfExists) {
			try {
				executeStructureQuery("DROP DATABASE " + currentDatabaseName + ";");
				executeStructureQuery("CREATE DATABASE " + currentDatabaseName + ";");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (!databaseFolder.exists()) {
			try {
				executeStructureQuery("CREATE DATABASE " + currentDatabaseName + ";");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//throw new RuntimeException(databaseFolder.getAbsolutePath());
		return databaseFolder.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		if(!query.contains(";")) {
			query = query+";";
		}
		
	
		int type = queryTypeRecognizer.recognizeQueryType(query);
		
		
		String temp = query.substring(0);
		if (!validatorsFactory.getValidatorByType(type).validate(temp)) {
			throw new SQLException(query);
		}
		QueryProperties properties = parsersFactory.getParserByType(type).parse(query);
		if (properties.databaseName != null) {
			 currentDatabaseName = properties.databaseName.toLowerCase();
		}else{
			if(currentDatabaseName == null) {
				throw new SQLException();
			}
			properties.databaseName = currentDatabaseName;
		}
		properties.databaseName = properties.databaseName.toLowerCase();
		engineer.setCommandProperties(properties);
		engineer.setBuilder(type);
		DatabaseCommand command = engineer.synthesisCommand();
		
		
		Object lol = command.execute();
		
		if(lol == null)
			return true;
			
		if(lol instanceof Integer)
			throw new SQLException(Integer.toString((int)lol));
		else
			return (boolean) lol;
		
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		if(!query.contains(";")) {
			query = query+";";
		}
		/*if(true){
			throw new RuntimeException(query);	
		}*/
		int type = queryTypeRecognizer.recognizeQueryType(query);
		if (!validatorsFactory.getValidatorByType(type).validate(query)) {
			throw new SQLException();
		}
		QueryProperties properties = parsersFactory.getParserByType(type).parse(query);

		properties.databaseName = currentDatabaseName;
		engineer.setCommandProperties(properties);
		engineer.setBuilder(type);
		DatabaseCommand command = engineer.synthesisCommand();
		
			Object[][] lol = (Object[][]) command.execute();
			if(lol == null) {
				return new Object[0][0];
			}
		
			return lol;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		if(!query.contains(";")) {
			query = query+";";
		}
		
		int type = queryTypeRecognizer.recognizeQueryType(query);
		
		if (!validatorsFactory.getValidatorByType(type).validate(query)) {
			throw new SQLException();
		}
		QueryProperties properties = parsersFactory.getParserByType(type).parse(query);

		properties.databaseName = currentDatabaseName;
		engineer.setCommandProperties(properties);
		engineer.setBuilder(type);
		DatabaseCommand command = engineer.synthesisCommand();
		
		int returnType = (int) command.execute();
		
		if(returnType == -1)
			throw new SQLException();
		
		return returnType;
	}

}
