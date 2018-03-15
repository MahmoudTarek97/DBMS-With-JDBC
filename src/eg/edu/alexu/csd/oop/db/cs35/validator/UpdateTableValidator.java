package eg.edu.alexu.csd.oop.db.cs35.validator;

public class UpdateTableValidator implements Validator {
	@Override
	public boolean validate(String query) {
		
		// UPDATE table_name SET column1 = value1, column2 = value2 WHERE condition;
		 
		ValidatorUtilities utility = new ValidatorUtilities();
		query = query.trim();

		if (!utility.checkOnlyOneAppearanceAtStart("UPDATE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("\\sSET\\s", query)) {
			return false;
		}

		if (!utility.checkOneOrNoAppearance("\\sWHERE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearanceAtEnd(";", query)) {
			return false;
		}

		return !utility.containsOtherKeyWords(query, new String[] { "UPDATE", "SET", "WHERE" });
	}
}
