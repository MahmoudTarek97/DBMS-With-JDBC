package eg.edu.alexu.csd.oop.db.cs35.queryAnalyzer;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs35.queryAnalyzer.QueryContract.QueryTypes;

public class QueryTypeRecognizer {
    public int recognizeQueryType(String query) throws SQLException {
        String toLowerCaseQuery = query.toLowerCase();
        toLowerCaseQuery = toLowerCaseQuery.trim();

        if(toLowerCaseQuery.startsWith("delete")){
            return QueryTypes.DELETE;
        }

        else if (toLowerCaseQuery.startsWith("insert")) {
            return QueryTypes.INSERT;
        }

        else if (toLowerCaseQuery.startsWith("update")) {
            return QueryTypes.UPDATE;
        }

        else if (toLowerCaseQuery.startsWith("select")) {
            return QueryTypes.SELECT;
        }

        else if (toLowerCaseQuery.startsWith("create")) {

            if(toLowerCaseQuery.contains("table")){
                return QueryTypes.CREATE_TABLE;
            }
            return QueryTypes.CREATE_DATABASE;

        }
        else if (toLowerCaseQuery.startsWith("drop")) {

            if(toLowerCaseQuery.contains("table")){
                return QueryTypes.DROP_TABLE;
            }
            return QueryTypes.DROP_DATABASE;

        }

        else {
            throw new SQLException();
        }
    }
}
