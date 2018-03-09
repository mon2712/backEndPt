package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import classes.BaseDatos;

public class Personal {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
    public static String iniciarSesion(String username, String password) {
    		String type="", name="";
    		int id;
    		StringWriter swriter = new StringWriter();
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
    		    
    		    
    	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
    	            gen.writeStartObject();
    	            gen.writeStartObject("infoLogin");
    	            gen.write("name", name);
    	            gen.write("type", type);
    	            gen.write("id", id);
    	            gen.writeEnd();
    	            gen.writeEnd();
    	        }

    	        
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    		return swriter.toString();
    }

}
