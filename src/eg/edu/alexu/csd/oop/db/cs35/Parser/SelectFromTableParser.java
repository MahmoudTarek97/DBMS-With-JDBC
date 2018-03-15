package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;
import java.util.ArrayList;

public class SelectFromTableParser implements Parser {
    @Override
    public QueryProperties parse(String query) throws SQLException {
    	
    	// SELECT column1, column2 FROM table_name;
    	// SELECT * FROM table_name;
    	// SELECT column1 FROM table_name WHERE column2 > 5
    	query = query.trim().toLowerCase();
    	QueryProperties properties = new QueryProperties();
    	String[] sections = query.substring(0, query.length()-1).split("(?i) from ");
    	
    	sections[1] = sections[1].trim();
    	if(sections[1].length()==0 || sections[1].equals(" ")){
    		throw new SQLException();
    	}
    	properties.conditions = new ConditionOnSelection();
    	//properties.tableName = sections[1].trim();
    	sections[0] = sections[0].trim();
    	if(sections[0].contains("*")){
    		String[] selectAndStar = sections[0].split("\\s+");
    		if(selectAndStar.length!= 2 || !selectAndStar[1].equals("*")){
    			throw new SQLException();
    		}
    		properties.columns = new ArrayList<>();
    		properties.columns.add("*");
    	}else{
    		sections[0] = sections[0].substring(6).trim();
    		String[] columns = sections[0].split(",");
    		properties.columns = new ArrayList<>();
    		for(int i =0 ; i < columns.length;i++){
    			properties.columns.add(columns[i].trim());
    		}
    		
    	}

		properties.conditions = new ConditionOnSelection();
    	if (sections[1].toLowerCase().contains("where")) {
    		String[] tableNameAndCondition = sections[1].split("where");
    		properties.tableName = tableNameAndCondition[0].toLowerCase().trim();
    		
    		ConditionRelationRecognizer recognizer ;
        	recognizer = ConditionRelationRecognizer.CreateRecognizer();
        	String operation = recognizer.recognizeRelation(tableNameAndCondition[1].trim());
    		String[] condition = tableNameAndCondition[1].split(operation);
    		properties.conditions.columnsName = condition[0].trim();
        	properties.conditions.operation  = operation;
        	//condition[1] = condition[1].replaceAll("\\'"," ") ;
        	condition[1] = condition[1].trim();
        	properties.conditions.value = condition[1];
    	}
    	else{
    		properties.tableName = sections[1].toLowerCase().trim();	
    	}
    	
    	
        return properties;
    }
}
