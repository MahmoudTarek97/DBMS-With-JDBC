package eg.edu.alexu.csd.oop.db.cs35.validator;

public class CreateDatabaseValidator implements Validator{
    @Override
    public boolean validate(String query) {
    	
    	// CREATE DATABASE databasename;
    	
        ValidatorUtilities utility = new ValidatorUtilities();
        query = query.trim();

        if(!utility.checkOnlyOneAppearanceAtStart("CREATE\\s+DATABASE\\s",query)) {
			return false;
		}

        if(!utility.checkOnlyOneAppearance("CREATE\\s",query)) {
			return false;
		}

        if(!utility.checkOnlyOneAppearance("\\sDATABASE\\s",query)) {
			return false;
		}

        if(!utility.checkOnlyOneAppearanceAtEnd(";",query)) {
			return false;
		}


        return !utility.containsOtherKeyWords(query,new String[]{"CREATE","DATABASE"});
    }
}
