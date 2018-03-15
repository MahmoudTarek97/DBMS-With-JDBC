package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.behaviorUtilities;

public class ComparatorsFactory {

    public ComparisonBehavior createComparator(String operation) {

        if (operation.equals("=")){
            return new TestEqualityBehavior();
        } else if (operation.equals(">=")){
            return new TestGreaterThanOrEqualBehavior();
        } else if (operation.equals("!=")){
            return new TestNotEqualBehavior();
        } else if (operation.equals("<=")){
            return new TestLessThanOrEqualBehavior();
        } else if (operation.equals(">")){
            return new TestGreaterThanBehavior();
        } else {
            return new TestLessThanBehavior();
        }
    }
}
