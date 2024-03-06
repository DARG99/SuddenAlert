package android.example.dai2;

public class BD {
    private static final String BD_URL = "jdbc:mysql://193.136.11.180:3306/suddenalert";
    private static final String USER = "suddenalertuser";
    private static final String PASS = "Suddenalert.0";
    //private static final String BD_URL = "jdbc:mysql://193.136.11.180:3306/SuddenAlert2020";
    //private static final String USER = "suddenalertuser";
    //private static final String PASS = "Suddenalert.0";

    public static String getBdUrl() {
        return BD_URL;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASS() {
        return PASS;
    }
}
