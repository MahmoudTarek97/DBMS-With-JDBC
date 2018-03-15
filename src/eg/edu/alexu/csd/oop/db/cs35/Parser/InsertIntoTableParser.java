package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;
import java.util.ArrayList;

public class InsertIntoTableParser implements Parser {
    @Override
    public QueryProperties parse(String query) throws SQLException {
    	
    	// INSERT INTO table_name (column1, column2, column3) VALUES
    	// (value1, value2, value3);
    	query = query.trim().toLowerCase(); 
    	String[] sections = query.substring(0, query.length()-1).split("(?i) into ");
    	sections[1] = sections[1].trim();
    	String[] columnsAndValues = sections[1].split("(?i)values");
    	columnsAndValues[0] = columnsAndValues[0].substring(0, columnsAndValues[0].length()-1).trim();
    	String[] columnsAndTableNameSection = columnsAndValues[0].split("\\(");
    	if(columnsAndTableNameSection.length > 2){
            throw new SQLException();
        }
    	QueryProperties properties = new QueryProperties();
        
    	if(columnsAndTableNameSection.length == 1) {
    		properties.columns = new ArrayList<>();
            properties.values = new ArrayList<>();
            properties.tableName = columnsAndTableNameSection[0].toLowerCase();
            return properties;
    	}
    	columnsAndTableNameSection[0] = columnsAndTableNameSection[0].trim();
        if (columnsAndTableNameSection[0].length() == 0 ||
        columnsAndTableNameSection[0].contains(" ")) {
            throw new SQLException();
        }
        properties.columns = new ArrayList<>();
        properties.values = new ArrayList<>();
        properties.tableName = columnsAndTableNameSection[0].toLowerCase();

        String[] columns = columnsAndTableNameSection[1].substring(0, columnsAndTableNameSection[1].length()-1).trim().split(",");
        for(String i : columns){
        	properties.columns.add(i.trim().toLowerCase());
        }
        
        
        columnsAndValues[1] = columnsAndValues[1].trim().substring(1, columnsAndValues[1].length()-1);
 
        String[] valuesOnly = columnsAndValues[1].trim().substring(0, columnsAndValues[1].length()-1).split(",");
        for(String i : valuesOnly){
        	properties.values.add(i.trim());
        }
        
        return properties;
    }
}
