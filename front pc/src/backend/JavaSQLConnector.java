package backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

 
public class JavaSQLConnector {
    public static void main(String[] args) {

        Connection conn1 = null;
        
        try {
            String url = "jdbc:mysql://193.136.11.180:3306/suddenalert?useSSL=false";
            String user = "suddenalertuser";
            String pass = "Suddenalert.0";
            
         conn1 = DriverManager.getConnection(url, user, pass);
            if (conn1 != null) {
                System.out.println("Connected to the database");
            } 
            
            } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
}    
}
}
