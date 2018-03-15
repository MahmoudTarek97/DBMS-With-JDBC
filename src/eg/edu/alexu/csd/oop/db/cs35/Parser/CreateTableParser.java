package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;
import java.util.ArrayList;

public class CreateTableParser implements Parser {


    @Override
    public QueryProperties parse(String query) throws SQLException {
    	
    	// CREATE TABLE table_name ( column1 datatype, column2 datatype, column3 datatype );
    	
    	query = query.trim().toLowerCase();
        String[] sections = query.split("(?i) table ");
        sections[1] = sections[1].trim();
        String[] columnsAndTableNameSection = sections[1].split("\\(");
       
        if(columnsAndTableNameSection.length > 2){
            throw new SQLException();
        }
        if (columnsAndTableNameSection.length == 1) {
        		
        		columnsAndTableNameSection[0] = 
        			columnsAndTableNameSection[0].substring(0, columnsAndTableNameSection[0].length() - 2);
        			
        }
        
        columnsAndTableNameSection[0] = columnsAndTableNameSection[0].trim();
        if (columnsAndTableNameSection[0].length() == 0 ||
        columnsAndTableNameSection[0].contains(" ")) {
            throw new SQLException(query);
        }
        QueryProperties properties = new QueryProperties();
        properties.tableName = columnsAndTableNameSection[0].toLowerCase();

        if (columnsAndTableNameSection.length == 1) {
        	properties.columns = new ArrayList<>();
        	return properties;	
        }
        
        String[] columnsAndSemiColon = columnsAndTableNameSection[1].split("\\)");

        if(!columnsAndSemiColon[1].trim().equals(";")){
            throw new SQLException();
        }
        String[] columnsAndTypesOnly = columnsAndSemiColon[0].split(",");

        properties.columns = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        String[] tempColAndType;

        for(int i = 0 ; i < columnsAndTypesOnly.length ;i++){
            columnsAndTypesOnly[i] = columnsAndTypesOnly[i].trim();
            if(columnsAndTypesOnly[i].contains(" ")){
                tempColAndType = columnsAndTypesOnly[i].split("\\s+");
                if(tempColAndType.length>2){
                
                    throw new SQLException(query +"\n" + tempColAndType[0] +"\n" + tempColAndType[1] +"\n" + tempColAndType[2]+"\n" + tempColAndType[3]);
                }

                if(!(tempColAndType[1].equalsIgnoreCase("INTEGER")
                        ||tempColAndType[1].equalsIgnoreCase("VARCHAR") || tempColAndType[1].equalsIgnoreCase("int"))){
                		throw new SQLException();
                }
                if(tempColAndType[1].equalsIgnoreCase("int")) {
                	tempColAndType[1] = "INTEGER";
                }
                properties.columns.add(tempColAndType[0]);
                types.add(tempColAndType[1]);
            } else {
                throw new SQLException();
            }
        }
        properties.columns.addAll(types);
        return properties;
    }
}
