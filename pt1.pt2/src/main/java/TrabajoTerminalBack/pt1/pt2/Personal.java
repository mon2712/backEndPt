package TrabajoTerminalBack.pt1.pt2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Personal {
    static PreparedStatement prepareStat = null;
    private static Connection conn = conexionDB.conectarBD();
    
    public static String iniciarSesion(String username, String password) {
    		String type="", name="";
    		int id;
    		try {
    		    CallableStatement cStmt = conn.prepareCall("{call verify_user(?, ?, ?, ?, ?)}");
	
    		    cStmt.setString(1, username);
    		    cStmt.setString(2, password);
    		    cStmt.registerOutParameter(3, Types.VARCHAR);
    		    cStmt.registerOutParameter(4, Types.VARCHAR);
    		    cStmt.registerOutParameter(5, Types.INTEGER);
    		    
    		    cStmt.execute();    
    		    
    		    type = cStmt.getString(3);
    		    name = cStmt.getString(4);
    		    id = cStmt.getInt(5);

    		    System.out.println("usuario: "+type + " " + name + " " + id);

    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    		return type;
    }

}
