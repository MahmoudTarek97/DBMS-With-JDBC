package eg.edu.alexu.csd.oop.db.cs35;

import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.DatabaseCommand;
import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;
import eg.edu.alexu.csd.oop.db.cs35.queryAnalyzer.QueryContract.QueryTypes;
public class CommandsEngineer {




    private DatabaseCommandBuilder commandBuilder;
    private QueryProperties properties;
    private static CommandsEngineer engineer = null;

    private CommandsEngineer() {
        commandBuilder = DatabaseCommandBuilder.CreateBuilder();
        properties = null;
    }

    public static CommandsEngineer CreateCommandsEngineer(){
        if(engineer == null){
            engineer = new CommandsEngineer();
        }
        return engineer;
    }

    public void setCommandProperties(QueryProperties properties){
        this.properties = properties;
    }



    public void setBuilder(int type){
        switch (type) {
            case QueryTypes.DELETE:
                commandBuilder.setToDeleteCommand(properties);
                break;
            case QueryTypes.INSERT:
                commandBuilder.setToInsertCommand(properties);
                break;
            case QueryTypes.UPDATE:
                commandBuilder.setToUpdateCommand(properties);
                break;
            case QueryTypes.SELECT:
                commandBuilder.setToSelectCommand(properties);
                break;
            case QueryTypes.CREATE_TABLE:
                commandBuilder.setToCreateTableCommand(properties);
                break;
            case QueryTypes.DROP_TABLE:
                commandBuilder.setToDropTableCommand(properties);
                break;
            case QueryTypes.CREATE_DATABASE:
                commandBuilder.setToCreateDatabaseCommand(properties);
                break;
            case QueryTypes.DROP_DATABASE:
                commandBuilder.setToDropDatabaseCommand(properties);
                break;
        }
    }

    public DatabaseCommand synthesisCommand() {
        return commandBuilder.buildCommand();
    }

}