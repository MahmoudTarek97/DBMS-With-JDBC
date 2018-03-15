package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateTableParser implements Parser {
	@Override
	public QueryProperties parse(String query) throws SQLException {

		// UPDATE table_name SET column1 = value1, column2 = value2 WHERE condition;
		// UPDATE table_name SET column1 = value1, column2 = value2;
		query = query.trim().toLowerCase();
		QueryProperties properties = new QueryProperties();
		String[] sections = query.substring(0, query.length() - 1).split("(?i) set ");

		sections[0] = sections[0].trim();
		String[] updateAndTableName = sections[0].split("\\s+");
		if(updateAndTableName.length>2){
			throw new SQLException();
		}
		updateAndTableName[1] = updateAndTableName[1].trim();
		if (updateAndTableName[1].length() == 0 || updateAndTableName[1].contains(" ")) {
			throw new SQLException();
		}
		properties.tableName = updateAndTableName[1].toLowerCase();
		properties.columns = new ArrayList<>();
		properties.values = new ArrayList<>();
		properties.conditions = new ConditionOnSelection();
		if (sections[1].contains("where")) {
			String[] columnsValuesAndCondition = sections[1].split("where");
			columnsValuesAndCondition[0] = columnsValuesAndCondition[0].trim();
			String[] allColumnsAndValues = columnsValuesAndCondition[0].split(",");
			for (String i : allColumnsAndValues) {
				String[] columnAndValue = i.trim().split("=");
				properties.columns.add(columnAndValue[0].trim());
				properties.values.add(columnAndValue[1].trim());
			}

			columnsValuesAndCondition[1] = columnsValuesAndCondition[1].trim();
			ConditionRelationRecognizer recognizer;
			recognizer = ConditionRelationRecognizer.CreateRecognizer();
			String operation = recognizer.recognizeRelation(columnsValuesAndCondition[1]);
			String[] columnsAndOperation = columnsValuesAndCondition[1].split(operation);
			properties.conditions = new ConditionOnSelection();
			properties.conditions.columnsName = columnsAndOperation[0].trim();
			properties.conditions.operation = operation;
			//columnsAndOperation[1] = columnsAndOperation[1].replaceAll("\\'", " ");
			columnsAndOperation[1] = columnsAndOperation[1].trim();
			properties.conditions.value = columnsAndOperation[1];

		} else {

			sections[1] = sections[1].trim();
			String[] allColumnsAndValues = sections[1].split(",");

			for (String i : allColumnsAndValues) {
				String[] columnAndValue = i.trim().split("=");
				properties.columns.add(columnAndValue[0].trim());
				properties.values.add(columnAndValue[1].trim());
			}
		}
		
		
		return properties;
	}
}
