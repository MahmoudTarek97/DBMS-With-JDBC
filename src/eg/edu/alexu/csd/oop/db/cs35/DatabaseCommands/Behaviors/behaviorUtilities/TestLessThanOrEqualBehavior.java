package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.behaviorUtilities;

public class TestLessThanOrEqualBehavior implements ComparisonBehavior {

    @Override
    public boolean compare(String first, String second) {
        return Integer.parseInt(first) <= Integer.parseInt(second);
    }


}
