package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;

public class DropTableParser implements Parser {
    @Override
    public QueryProperties parse(String query) throws SQLException {
    	
    	// DROP TABLE table_name;
    	query = query.trim();
        String[] sections = query.split("(?i) table ");
        sections[1] = sections[1].trim();
        sections[1] = sections[1].substring(0,sections[1].length() - 1).trim();
        if (sections[1].length() == 0 || sections[1].contains(" ")){
            throw new SQLException();
        }
        QueryProperties properties = new QueryProperties();
        properties.tableName = sections[1].toLowerCase();
        return properties;
    }
}
