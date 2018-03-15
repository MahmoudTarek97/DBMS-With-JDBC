package eg.edu.alexu.csd.oop.db.cs35.validator;

public class SelectFromTableValidator implements Validator {
	@Override
	public boolean validate(String query) {

		// SELECT column1, column2 FROM table_name;
		// SELECT * FROM table_name;
		
		ValidatorUtilities utility = new ValidatorUtilities();
		query = query.trim();

		if (!utility.checkOnlyOneAppearanceAtStart("SELECT\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("\\sFROM\\s", query)) {
			return false;
		}
		if (!utility.checkOneOrNoAppearance("\\sWHERE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearanceAtEnd(";", query)) {
			return false;
		}

		return !utility.containsOtherKeyWords(query, new String[] { "SELECT", "FROM" , "WHERE" });
	}
}
