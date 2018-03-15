package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.ExecutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;

public class DatabaseCommand {

	// TODO: raga3hom private (hidden from 3bdo) 
    public ExecutionBehavior executionBehavior;
    public QueryProperties properties;

    public Object execute() throws SQLException{
        return executionBehavior.execute(properties);
    }
    public void setExecutionBehavior(ExecutionBehavior behavior){
        executionBehavior = behavior;
    }
    public void setProperties(QueryProperties properties) {
        this.properties = properties;
    }
}
