package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;

public interface Parser {
    public QueryProperties parse(String query) throws SQLException;
}
