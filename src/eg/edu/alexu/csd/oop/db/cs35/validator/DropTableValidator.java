package eg.edu.alexu.csd.oop.db.cs35.validator;

public class DropTableValidator implements Validator {
	@Override
	public boolean validate(String query) {

		// DROP TABLE table_name;

		ValidatorUtilities utility = new ValidatorUtilities();
		query = query.trim();

		if (!utility.checkOnlyOneAppearanceAtStart("DROP\\s+TABLE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("DROP\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("\\sTABLE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearanceAtEnd(";", query)) {
			return false;
		}

		return !utility.containsOtherKeyWords(query, new String[] { "DROP", "TABLE" });
	}
}
