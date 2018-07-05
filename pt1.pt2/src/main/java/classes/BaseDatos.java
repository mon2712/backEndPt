package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDatos {
	static Connection conn = null;
    static PreparedStatement prepareStat = null;


    public static Connection conectarBD() {
    		Connection conn = null;
    		//String pass ="dBoy6Ap281015";
    		String pass = "vfcnmm2201";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.print("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.print("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
        }

        try {
        		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_pt2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", "root", pass);
            
            if (conn != null) {
                System.out.println("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.print("MySQL Connection Failed!");
            e.printStackTrace();
        }
        return conn;

    }

}
