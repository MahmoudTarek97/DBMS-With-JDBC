package eg.edu.alexu.csd.oop.db.cs35;

import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.DatabaseCommand;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.CreateDatabaseExecutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.CreateTableExecutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.DeleteFromTableExecutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.DropDatabaseExecutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.DropTableExecutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.InsertIntoTableExecutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.SelectFromTableExcutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.UpdateTableExecutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;

public class DatabaseCommandBuilder {

	private QueryProperties collectedProperties;
	private DatabaseCommand Command;
	private static DatabaseCommandBuilder Builder = null;

	private DatabaseCommandBuilder() {
	}

	public static DatabaseCommandBuilder CreateBuilder() {
		if (Builder == null) {
			Builder = new DatabaseCommandBuilder();
		}
		return Builder;
	}

	public DatabaseCommand buildCommand() {
		if (collectedProperties != null) {
			Command.setProperties(collectedProperties);
			collectedProperties = null;
		}
		return Command;
	}

	public void setToSelectCommand(QueryProperties properties) {

		Command = new DatabaseCommand();
		collectedProperties = properties;
		Command.setExecutionBehavior(new SelectFromTableExcutionBehavior());

	}

	public void setToDeleteCommand(QueryProperties properties) {

		Command = new DatabaseCommand();
		collectedProperties = properties;
		Command.setExecutionBehavior(new DeleteFromTableExecutionBehavior());

	}

	public void setToCreateTableCommand(QueryProperties properties) {

		Command = new DatabaseCommand();
		collectedProperties = properties;
		Command.setExecutionBehavior(new CreateTableExecutionBehavior());

	}

	public void setToDropTableCommand(QueryProperties properties) {

		Command = new DatabaseCommand();
		collectedProperties = properties;
		Command.setExecutionBehavior(new DropTableExecutionBehavior());

	}

	public void setToCreateDatabaseCommand(QueryProperties properties) {

		Command = new DatabaseCommand();
		collectedProperties = properties;
		Command.setExecutionBehavior(new CreateDatabaseExecutionBehavior());

	}

	public void setToDropDatabaseCommand(QueryProperties properties) {

		Command = new DatabaseCommand();
		collectedProperties = properties;
		Command.setExecutionBehavior(new DropDatabaseExecutionBehavior());
	}

	public void setToInsertCommand(QueryProperties properties) {

		Command = new DatabaseCommand();
		collectedProperties = properties;
		Command.setExecutionBehavior(new InsertIntoTableExecutionBehavior());

	}

	public void setToUpdateCommand(QueryProperties properties) {

		Command = new DatabaseCommand();
		collectedProperties = properties;
		Command.setExecutionBehavior(new UpdateTableExecutionBehavior());

	}

}
