package eg.edu.alexu.csd.oop.db.cs35;

import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.SelectFromTableExcutionBehavior;
import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;
import eg.edu.alexu.csd.oop.jdbc.cs35.DatabaseStatement;
import eg.edu.alexu.csd.oop.jdbc.cs35.utilities.MetaData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class mainForTest {
    public static void main(String[] args) throws SQLException, IOException {

        /*
        DTDValidator lol = new DTDValidator();
        String path = "Databases/7amo/jim.xml";
        System.out.println(lol.validateWithDTDUsingDOM(path));
       
        CreateTableParser k = new CreateTableParser();
        
        try {
			QueryProperties l = k.parse("CREATE TABLE jim (age Integer, name Varchar, email varchar);");
			DTDFileWriter j = new DTDFileWriter();
			l.databaseName = "7amo";
			j.write(l);
			j.makeTable(l);
		} catch (SQLException e1) {
			
		}
        
        */
/*
        QueryProperties x = new QueryProperties();
        x.tableName = "test2";
        x.databaseName = "Databases/7amo";
        x.columns = new ArrayList<>();
        x.columns.add("id");
        x.columns.add("name");
        x.columns.add("age");
        x.columns.add("INTEGER");
        x.columns.add("VARCHAR");
        x.columns.add("INTEGER");
        CommandsEngineer y = CommandsEngineer.CreateCommandsEngineer();
        y.setCommandProperties(x);
        y.setBuilder(CREATE_TABLE);

        DatabaseCommand m =y.synthesisCommand();
        m.execute();*/
        /*QueryProperties x = new QueryProperties();
        x.tableName = "test";
        x.databaseName = "Databases/7amo";
        x.conditions = new ConditionOnSelection();*/
        /*x.columns.add("id");
        x.columns.add("name");
        x.columns.add("age");
        x.values = new ArrayList<>();
        x.values.add("20");
        x.values.add("sadasd");
        x.values.add("20");
        CommandsEngineer y = CommandsEngineer.CreateCommandsEngineer();
        y.setCommandProperties(x);
        y.setBuilder(INSERT);

        DatabaseCommand m =y.synthesisCommand();
        m.execute();*/



       /* x.conditions = new ConditionOnSelection();
        x.conditions.columnsName = "id";
        x.conditions.operation = "<";
        x.conditions.value= "4";
        */
      /*  CommandsEngineer  y = CommandsEngineer.CreateCommandsEngineer();
        y.setCommandProperties(x);
        y.setBuilder(DELETE);
        DatabaseCommand m = y.synthesisCommand();
        m.execute();*/
/*

        x.conditions = new ConditionOnSelection();
        x.conditions.value="1";
        x.conditions.operation ="<=";
        x.conditions.columnsName="id";
        y.setCommandProperties(x);
        y.setBuilder(DELETE);

        m = y.synthesisCommand();
        m.execute();
*/

       /* QueryProperties prop = new QueryProperties();
        InsertIntoTableValidator validator = new InsertIntoTableValidator();
        System.out.println(validator.validate("INSERT INTO table_name (column1, column2, column3) VALUES (value1, value2, value3);"));
        InsertIntoTableParser parser = new InsertIntoTableParser();
			try {
				prop = parser.parse("INSERT  INTO  table_name  ( column1 ,  column2, column3) VALUES (value1, value2, value3);");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(prop.databaseName);
			System.out.println(prop.tableName);
			System.out.println(prop.columns);
			System.out.println(prop.conditions);
			//System.out.println(prop.conditions.columnsName);
			//System.out.println(prop.conditions.operation);
			//System.out.println(prop.conditions.value);
			System.out.println(prop.values);
			*/
        
//        DataBaseManager db = new DataBaseManager();
//        try {
//
//        	db.createDatabase("TestDB", true);
//			int count ;
//			db.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
//        	count = db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
//        	count = db.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
//        	count = db.executeUpdateQuery("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)");
//        	count = db.executeUpdateQuery("DELETE From table_name1  WHERE coLUmn_NAME2=4");
//        	System.out.println(db.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 < 6").length +"hna");
//        	count = db.executeUpdateQuery("UPDATE table_name1 SET column_name1='11111111', COLUMN_NAME2=10, column_name3='333333333' WHERE coLUmn_NAME2=5");
//        	Object[][] x = db.executeQuery("SELECT * FROM table_name1 WHERE coluMN_NAME2 > 4");
//        	for(int i = 0 ; i < x.length ;i++) {
//        		for(int j =0;j<x[i].length;j++) {
//        			System.out.println(x[i][j]);
//        		}
//        	}
//
// 			System.out.println(x[0][1] instanceof Integer);
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

    	DataBaseManager db  = new DataBaseManager();
    	String path = db.createDatabase("sample" + System.getProperty("file.separator") + "test", true);
System.out.println(path);
//		DatabaseStatement x= new DatabaseStatement();
//		MetaData y = x.readMetaData();
//		System.out.println();

		// UpdateTableParser d = new UpdateTableParser();
      //  QueryProperties l = d.parse("update  data set id = 2;");
        //System.out.println(l.toString());	

       		
    }
}
