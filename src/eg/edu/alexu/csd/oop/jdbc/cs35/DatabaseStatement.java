package eg.edu.alexu.csd.oop.jdbc.cs35;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs35.queryAnalyzer.QueryContract.QueryTypes;
import eg.edu.alexu.csd.oop.db.cs35.queryAnalyzer.QueryTypeRecognizer;
import eg.edu.alexu.csd.oop.jdbc.cs35.utilities.MetaData;

public class DatabaseStatement implements Statement{

	private ResultDataSet resultSet;
	private Connection connection;
	private int queryTimeout;
	private ArrayList<String> batch;
	private Database database;
	private Counter counter;
	private final static Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());


	public DatabaseStatement(Connection connection, Database database) {
		this.connection = connection;
		this.batch = new ArrayList<>();
		this.resultSet = new ResultDataSet();
		this.database = database;
		this.queryTimeout = -1;
	}
	
	@Override
	public void addBatch(String sql) throws SQLException {
		if(this.database == null) {
			throw new SQLException();
		}
		this.batch.add(sql);
	}
	
	@Override
	public void clearBatch() throws SQLException {
		if(this.database == null) {
			throw new SQLException();
		}
		this.batch.clear();
	}
	
	@Override
	public void close() throws SQLException {
		if(this.database == null) {
			throw new SQLException();
		}
		this.database = null;
	}
	
	@Override
	public boolean execute(String sql) throws SQLException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("execute");
		if(this.database == null) {
			throw new SQLException();
		}
		
		QueryTypeRecognizer recognizer  = new QueryTypeRecognizer();
        int type = recognizer.recognizeQueryType(sql);
        switch (type) {
            case QueryTypes.DELETE:
            case QueryTypes.INSERT:
            case QueryTypes.UPDATE:
                
                if (database.executeUpdateQuery(sql) != 0) {
					return true;
				}
                return false;
            case QueryTypes.SELECT:
                resultSet = (ResultDataSet) executeQuery(sql);
                if(resultSet == null || resultSet.getSelectedTable() == null) {
					return false;
				}
                return true;
            case QueryTypes.CREATE_TABLE:
            case QueryTypes.DROP_TABLE:
            case QueryTypes.CREATE_DATABASE:
            case QueryTypes.DROP_DATABASE:
                
                return database.executeStructureQuery(sql);
        }
        return false;
	}
	
	@Override
	public int[] executeBatch() throws SQLException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("execute batch");
		if(this.database == null) {
			throw new SQLException();
		}
		
		this.counter = new Counter(this.queryTimeout, Thread.currentThread());
		counter.start();
		
		int[] count = new int[this.batch.size()];
		
		for(int i = 0 ; i < count.length ; i++)
		{
			String query = this.batch.get(i).toLowerCase();
			Object testValue = null;
			
			if(Thread.currentThread().isInterrupted()) {
				throw new SQLTimeoutException();
			}
			
			try{
				if(query.contains("select")) {
					testValue = this.database.executeQuery(query);
				} else if(query.contains("create") || query.contains("drop")) {
					testValue = this.database.executeStructureQuery(query);
				} else {
					testValue = this.database.executeUpdateQuery(query);
				}
			} catch (SQLException e) {
				testValue = null;
				
				count[i] = Statement.EXECUTE_FAILED;
			}
			
			
			if(testValue instanceof Integer) {
				count[i] = (int) testValue;
			} else {
				count[i] = Statement.SUCCESS_NO_INFO;
			}
			 
		}
		return count;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("execute query");
		if(this.database == null) {
			throw new SQLException();
		}
		
		Object[][] ElementsResult = database.executeQuery(sql);

		if(ElementsResult.length == 0) {
			return null;
		}
		
        ResultDataSet finalResultSet = new ResultDataSet();
        MetaData data = null;

        try {
            data = readMetaData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ElementsResult == null) {
			return null;
		}

        finalResultSet.setTableData(data);
        finalResultSet.setTable(ElementsResult);
        finalResultSet.setCreatorStatement(this);
        resultSet = finalResultSet;
        return finalResultSet;
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("execute update");
		if(this.database == null) {
			throw new SQLException();
		}
        return database.executeUpdateQuery(sql);
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		if(this.database == null) {
			throw new SQLException();
		}
		return this.connection;
	}
	
	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		if(this.database == null || seconds < 0) {
			throw new SQLException();
		}
		this.queryTimeout = seconds;
	}
	
	@Override
	public int getQueryTimeout() throws SQLException {
		if(this.database == null) {
			throw new SQLException();
		}
		return this.queryTimeout;
	}
	
	private MetaData readMetaData() throws IOException {
        FileReader fr = new FileReader("MetaData.txt");
        BufferedReader br = new BufferedReader(fr);
        MetaData data = new MetaData();
        data.tableName = br.readLine();
        data.columnsCount = Integer.parseInt(br.readLine());
        data.selectedColumns = new ArrayList<>(Arrays.asList(br.readLine().split(" ")));
        data.selectedColumnsTypes = br.readLine().split(" ");
        br.close();
        return data;
    }
	
	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cancel() throws SQLException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();		
	}

	

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return this.resultSet;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		throw new UnsupportedOperationException();		
	}
}
