package TrabajoTerminalBack.pt1.pt2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Personal {
	static Connection conn = null;
    static PreparedStatement prepareStat = null;
	
    public static void iniciarSesion(String username, String password) {
    		try {
	    		System.out.println(username);
	        System.out.println(password);
	        
	        CallableStatement cStmt = conn.prepareCall("{call verify_user(?, ?, ?)}");
	
	        cStmt.setString(1, username);
	        cStmt.setString(2, password);
	        
	        cStmt.executeQuery();
	        System.out.println(username + " added successfully");
    		} catch (SQLException e) {
    			e.printStackTrace();
        }


    }

}
