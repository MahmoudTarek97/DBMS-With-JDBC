package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;

public class DropDatabaseParser implements Parser{
    @Override
    public QueryProperties parse(String query) throws SQLException {
    	
    	// DROP DATABASE databasename;
    	query = query.trim();
    	String[] sections = query.substring(0,query.length() - 1).split("(?i) database ");
    	if(sections.length > 2){
    		throw new SQLException();
    	}
    	sections[1] = sections[1].trim();
    	if(sections[1].length() == 0 || sections[1].contains(" ")){
    		throw new SQLException();
    	}
    	QueryProperties properties = new QueryProperties();
    	properties.databaseName = sections[1].toLowerCase();
    	
    	
    	return properties;
    }
}
