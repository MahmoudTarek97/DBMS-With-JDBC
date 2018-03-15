package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs35.queryAnalyzer.QueryContract.QueryTypes;

public class ParsersFactory {

    public Parser getParserByType(int type) throws SQLException {

        switch (type) {
            case QueryTypes.DELETE:

                return new DeleteFromTableParser();

            case QueryTypes.INSERT:

                return new InsertIntoTableParser();

            case QueryTypes.UPDATE:

                return new UpdateTableParser();

            case QueryTypes.SELECT:

                return new SelectFromTableParser();

            case QueryTypes.CREATE_TABLE:

                return new CreateTableParser();

            case QueryTypes.DROP_TABLE:

                return new DropTableParser();

            case QueryTypes.CREATE_DATABASE:

                return new CreateDatabaseParser();

            case QueryTypes.DROP_DATABASE:

                return new DropDatabaseParser();

            default:
                throw new SQLException();
        }
    }


}
