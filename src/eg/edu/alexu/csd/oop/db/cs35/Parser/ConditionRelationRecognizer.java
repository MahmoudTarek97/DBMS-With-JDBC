package eg.edu.alexu.csd.oop.db.cs35.Parser;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionRelationRecognizer {


    private static ConditionRelationRecognizer recognizer;

    Pattern[] relationsPatterns = new Pattern[6];
    String[] relations = new String[]{
            ">=",
            "<=",
            "!=",
            "=",
            ">",
            "<"
    };

    private ConditionRelationRecognizer() {
        for (int i = 0 ; i < 6 ; i++){
            relationsPatterns[i] = Pattern.compile(relations[i]);
        }
    }

    public static ConditionRelationRecognizer CreateRecognizer(){
        if(recognizer == null){
            recognizer = new ConditionRelationRecognizer();
        }
        return recognizer;
    }

    public String recognizeRelation(String expression) throws SQLException {
        String currentRegex;
        Matcher matcher;
        for (int i = 0 ; i < 6 ; i++){
            matcher = relationsPatterns[i].matcher(expression);
            if(matcher.find()){
                currentRegex = relations[i];
                String[] expressionParts = expression.split(currentRegex);
                if (expressionParts.length > 2) {
                    throw new SQLException();
                }
                if(containsAnyOtherRelation(currentRegex,expressionParts[0])
                        ||containsAnyOtherRelation(currentRegex,expressionParts[1])){
                    throw new SQLException();
                }
                return currentRegex;
            }
        }
        throw new SQLException();
    }
    
    private boolean containsAnyOtherRelation(String currentRegex,String expression){

        for (int i = 0 ;i < 6 ; i++){
            if(!relations[i].equals(currentRegex)){
                if(expression.contains(relations[i])) {
                    return true;
                }
            }
        }
        return false;
    }



}
