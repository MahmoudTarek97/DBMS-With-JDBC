package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;

public interface ExecutionBehavior {
    public Object execute(QueryProperties properties) throws SQLException;
}
