package eg.edu.alexu.csd.oop.db.cs35.validator;

public class InsertIntoTableValidator implements Validator {
	@Override
	public boolean validate(String query) {

		// INSERT INTO table_name (column1, column2, column3) VALUES
		// (value1, value2, value3);

		ValidatorUtilities utility = new ValidatorUtilities();
		query = query.trim();

		if (!utility.checkOnlyOneAppearanceAtStart("INSERT\\s+INTO\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("INSERT\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("\\sINTO\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("\\sVALUES\\s", query)
				&& !utility.checkOnlyOneAppearance("\\sVALUES\\(", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearanceAtEnd(";", query)) {
			return false;
		}

		return !utility.containsOtherKeyWords(query, new String[] { "INSERT", "INTO", "VALUES" });

	}
}
