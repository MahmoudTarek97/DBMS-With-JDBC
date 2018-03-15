package eg.edu.alexu.csd.oop.db.cs35.validator;

public class DropDatabaseValidator implements Validator {
	@Override
	public boolean validate(String query) {

		// DROP DATABASE databasename;

		ValidatorUtilities utility = new ValidatorUtilities();
		query = query.trim();

		if (!utility.checkOnlyOneAppearanceAtStart("DROP\\s+DATABASE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("DROP\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("\\sDATABASE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearanceAtEnd(";", query)) {
			return false;
		}

		return !utility.containsOtherKeyWords(query, new String[] { "DROP", "DATABASE" });
	}
}
