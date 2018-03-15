package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors;

import java.io.File;

import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;

public class DropDatabaseExecutionBehavior implements ExecutionBehavior{
    @Override
    public Object execute(QueryProperties properties) {
    	
    	File databaseDirectory = new File(System.getProperty("user.dir") + File.separator + "Databases" +File.separator + properties.databaseName);
    	
    	if(!databaseDirectory.exists())
    		return false;
    	
    	return deleteDir(databaseDirectory); 
    }
    private boolean deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        return file.delete();
    }
}
