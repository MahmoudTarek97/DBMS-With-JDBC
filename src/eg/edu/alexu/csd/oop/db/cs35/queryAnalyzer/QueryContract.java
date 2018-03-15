package eg.edu.alexu.csd.oop.db.cs35.queryAnalyzer;

public class QueryContract {

    public class QueryTypes {
        public static final int DELETE = 1;
        public static final int UPDATE = 2;
        public static final int INSERT = 3;
        public static final int SELECT = 4;
        public static final int CREATE_TABLE = 5;
        public static final int DROP_TABLE = 6;
        public static final int CREATE_DATABASE = 7;
        public static final int DROP_DATABASE = 8;
    }
}
