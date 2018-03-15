package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors;

import java.io.File;

import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.DTDHandlers.DTDFileWriter;
import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;


public class CreateTableExecutionBehavior implements ExecutionBehavior {
    @Override
    public Object execute(QueryProperties properties) {
       DTDFileWriter writer = new DTDFileWriter();
       
       String path = System.getProperty("user.dir") + File.separator +"Databases" + File.separator +  properties.databaseName;
   
       //System.out.println(path);
     /*if(true) {
		throw new RuntimeException(path);
	}*/
       
       File testForExistanceDatabase = new File(path);
       File testForExistance = new File(System.getProperty("user.dir") + File.separator +"Databases" + File.separator+properties.databaseName+ File.separator
    		   +properties.tableName+".xml");
       if (!testForExistanceDatabase.exists() ) {
    	   return 1;
       }
       
       if(testForExistance.exists())
    	   return false;
       if(properties.columns.size() == 0) {
    	   return -1;
       }
       
       //if(writer.write(properties)) 
    	 
       return writer.makeTable(properties);
       
       
       //return false;
       
    }
}

