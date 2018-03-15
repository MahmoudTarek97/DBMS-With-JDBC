package eg.edu.alexu.csd.oop.jdbc.cs35;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultsMetaData implements ResultSetMetaData {


    private int columnsCount ;
    private String[] columnsNames ;
    private String[] columnsTypes ;
    private String tableName ;
    private final int STRING_TYPE = 12;
    private final int INTEGER_TYPE = 4;


    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public void setColumnsCount(int columnsCount) {
        this.columnsCount = columnsCount;
    }

    public void setColumnsNames(String[] columnsNames) {
        this.columnsNames = columnsNames;
    }

    public void setColumnsTypes(String[] columnsTypes) {
        this.columnsTypes = columnsTypes;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return columnsCount;
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        return false;
    }

    @Override
    public int isNullable(int column) throws SQLException {
        return 0;
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        return false;
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        return 0;
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return getColumnName(column);
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        try {
            return columnsNames[column-1];
        } catch (Exception e) {
            throw new SQLException();
        }

    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        return null;
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        return 0;
    }

    @Override
    public int getScale(int column) throws SQLException {
        return 0;
    }

    @Override
    public String getTableName(int column) throws SQLException {
        return tableName;
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        return null;
    }

    @Override
    public int getColumnType(int column) throws SQLException {

        try {
            if(columnsTypes[column - 1 ].equalsIgnoreCase("VARCHAR")){
                return STRING_TYPE;
            }
            return INTEGER_TYPE;
        } catch (Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        return null;
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        return false;
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }


}
