package eg.edu.alexu.csd.oop.db.cs35.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtilities {

    public boolean checkOnlyOneAppearance(String regex,String query){
        Pattern testedPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher testMatcher = testedPattern.matcher(query);
        int count = 0;
        while (testMatcher.find()) {
            if (count > 0) {
                return false;
            }
            count++;
        }
        if (count == 0) return false;
        return true;
    }

    public boolean checkOneOrNoAppearance(String regex,String query){
        Pattern testedPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher testMatcher = testedPattern.matcher(query);
        int count = 0;
        while (testMatcher.find()) {
            if (count > 0) {
                return false;
            }
            count++;
        }
        return true;
    }


    public boolean checkOnlyOneAppearanceAtStart(String regex,String query){
        Pattern testedPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher testMatcher = testedPattern.matcher(query);
        int count = 0;
        while (testMatcher.find()) {
            if (count > 0) {
                return false;
            } if(testMatcher.start() != 0) {
                return false;
            }
            count++;
        }
        if (count == 0) return false;
        return true;
    }

    public boolean checkOnlyOneAppearanceAtEnd(String regex,String query){
        Pattern testedPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher testMatcher = testedPattern.matcher(query);
        int count = 0;
        while (testMatcher.find()) {
            if (count > 0) {
                return false;
            } if(testMatcher.start() != query.length() - 1 ) {
                return false;
            }
            count++;
        }
        if (count == 0) return false;
        return true;
    }

    String[] keywords = new String[]{"CREATE","INTO","VALUES","DROP","TABLE","DELETE"
                ,"UPDATE","SET","INSERT","WHERE","DATABASE"};

    public boolean containsOtherKeyWords(String query,String[] keyword){
        int numberOfKeyWords = keyword.length;
        int numberOfValidKeyWords = keywords.length;
        query = query.toUpperCase().substring(0 , query.length() - 2);
        for (int j = 0 ; j < numberOfValidKeyWords ; j++){
            boolean breakTheLoop = false;
            for(int i = 0 ;i < numberOfKeyWords;i++) {
                if(keyword[i].equals(keywords[j])){
                    breakTheLoop = true;
                    break;
                }
            }
            if(!breakTheLoop && query.contains(" "+keywords[j]+" ")){
                return true;
            }
        }
        return false;
    }
}
