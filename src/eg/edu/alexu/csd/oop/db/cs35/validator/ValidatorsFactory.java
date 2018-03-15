package eg.edu.alexu.csd.oop.db.cs35.validator;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs35.queryAnalyzer.QueryContract;

public class ValidatorsFactory {

    public Validator getValidatorByType(int type) throws SQLException {

        switch (type) {
            case QueryContract.QueryTypes.DELETE:

                return new DeleteFromTableValidator();

            case QueryContract.QueryTypes.INSERT:

                return new InsertIntoTableValidator();

            case QueryContract.QueryTypes.UPDATE:

                return new UpdateTableValidator();

            case QueryContract.QueryTypes.SELECT:

                return new SelectFromTableValidator();

            case QueryContract.QueryTypes.CREATE_TABLE:

                return new CreateTableValidator();

            case QueryContract.QueryTypes.DROP_TABLE:

                return new DropTableValidator();

            case QueryContract.QueryTypes.CREATE_DATABASE:

                return new CreateDatabaseValidator();

            case QueryContract.QueryTypes.DROP_DATABASE:

                return new DropDatabaseValidator();

            default:
                throw new SQLException();
        }
    }

}
