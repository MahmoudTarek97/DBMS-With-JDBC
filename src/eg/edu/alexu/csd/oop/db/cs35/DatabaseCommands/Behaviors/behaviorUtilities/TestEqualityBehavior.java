package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.behaviorUtilities;

public class TestEqualityBehavior implements ComparisonBehavior {
    @Override
    public boolean compare(String first, String second) {
        return first.equals(second);
    }
}
