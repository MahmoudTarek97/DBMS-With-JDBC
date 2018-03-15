package eg.edu.alexu.csd.oop.db.cs35.validator;

public class DeleteFromTableValidator implements Validator {
	@Override
	public boolean validate(String query) {

		// DELETE FROM table_name WHERE condition;
		// DELETE FROM table_name;
		// DELETE * FROM table_name;
		
		ValidatorUtilities utility = new ValidatorUtilities();
		query = query.trim();

		if (!utility.checkOnlyOneAppearanceAtStart("DELETE\\s+FROM\\s", query) && 
		!utility.checkOnlyOneAppearanceAtStart("DELETE\\s+\\*\\s+FROM\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("DELETE\\s", query)) {
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

		return !utility.containsOtherKeyWords(query, new String[] { "DELETE", "FROM", "WHERE" });
	}
}
