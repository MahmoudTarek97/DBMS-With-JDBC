package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors;

import java.io.File;

import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;

public class DropTableExecutionBehavior implements ExecutionBehavior {
    @Override
    public Object execute(QueryProperties properties) {

        try {
            File xmlFile = new File(System.getProperty("user.dir") +File.separator+"Databases" +File.separator + properties.databaseName +File.separator+properties.tableName +".xml");
            if(xmlFile.exists()){
                File dtdFile = new File(System.getProperty("user.dir") + File.separator +"Databases" +File.separator + properties.databaseName +File.separator+properties.tableName +".dtd");
            	xmlFile.delete();

                return true;
            }
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }
}
