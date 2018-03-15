package eg.edu.alexu.csd.oop.db.cs35.validator;

public class CreateTableValidator implements Validator {

	@Override
	public boolean validate(String query) {

		
		// CREATE TABLE table_name ( column1 datatype, column2 datatype, column3 datatype );
		 
		ValidatorUtilities utility = new ValidatorUtilities();
		query = query.trim();

		if (!utility.checkOnlyOneAppearanceAtStart("CREATE\\s+TABLE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("CREATE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearance("\\sTABLE\\s", query)) {
			return false;
		}

		if (!utility.checkOnlyOneAppearanceAtEnd(";", query)) {
			return false;
		}

		return !utility.containsOtherKeyWords(query, new String[] { "CREATE", "TABLE" });
	}
}
