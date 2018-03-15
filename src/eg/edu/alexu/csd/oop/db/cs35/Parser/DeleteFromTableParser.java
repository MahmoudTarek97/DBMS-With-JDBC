package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;

public class DeleteFromTableParser implements Parser {
    @Override
    public QueryProperties parse(String query) throws SQLException {
    	
    	// DELETE FROM table_name WHERE condition;
    	// DELETE FROM table_name;
    	// DELETE * FROM table_name;
    	query = query.trim().toLowerCase();
    	QueryProperties properties = new QueryProperties();
    	String[] sections = query.split("(?i) from ");
    	sections[1] = sections[1].substring(0, sections[1].length()-1).trim();
    	if (sections[1].contains("where")) {
    		String[] columnsAndTableNameSection = sections[1].split("(?i) where ");
        	if (columnsAndTableNameSection.length > 2 ){
        		throw new SQLException();
        	}
        	columnsAndTableNameSection[0] = columnsAndTableNameSection[0].trim();
        	if (columnsAndTableNameSection[0].length() == 0 ||
        	        columnsAndTableNameSection[0].contains(" ")) {
        	            throw new SQLException();
        	 }
        	properties.tableName = columnsAndTableNameSection[0].toLowerCase();
        	ConditionRelationRecognizer recognizer ;
        	recognizer = ConditionRelationRecognizer.CreateRecognizer();
        	String operation = recognizer.recognizeRelation(columnsAndTableNameSection[1]);
        	String[] columnsAndOperation = columnsAndTableNameSection[1].split(operation);
        	properties.conditions = new ConditionOnSelection();
        	properties.conditions.columnsName = columnsAndOperation[0].trim();
        	properties.conditions.operation  = operation;
        	//columnsAndOperation[1] = columnsAndOperation[1].replaceAll("\\'"," ") ;
        	columnsAndOperation[1] = columnsAndOperation[1].trim();
        	properties.conditions.value = columnsAndOperation[1];
    	}
    	else{
    		properties.tableName = sections[1];
    		String[] deleteAndStar = sections[0].trim().split("\\s+");
    		if(deleteAndStar.length>2 || (deleteAndStar.length==2 && !deleteAndStar[1].equals("*"))){
    			throw new SQLException();
    		}
    		
    	}
    	
    	return properties;
    }
}
