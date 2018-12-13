package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import classes.BaseDatos;

public class Personal {
	static PreparedStatement prepareStat = null;
    
    
    public static String iniciarSesion(String username, String password) {
    		Connection conn = BaseDatos.conectarBD();
    		
    		String type="", name="", finalResult="";
    		int id;
    		StringWriter swriter = new StringWriter();
    		try {
    			Statement stmt=conn.createStatement();  

    		    CallableStatement cStmt = conn.prepareCall("{call verifyUser(?, ?, ?, ?, ?)}");
	
    		    cStmt.setString(1, username);
    		    cStmt.setString(2, password);
    		    cStmt.registerOutParameter(3, Types.VARCHAR);
    		    cStmt.registerOutParameter(4, Types.VARCHAR);
    		    cStmt.registerOutParameter(5, Types.INTEGER);
    		    
    		    cStmt.execute();    
    		    
    		    type = cStmt.getString(3);
    		    name = cStmt.getString(4);
    		    id = cStmt.getInt(5);
    		    
    		    System.out.println("type " + type + " name " + name + " id " + id);

    		    if(id == 0) {
    		    		try (JsonGenerator gen = Json.createGenerator(swriter)) {
    	    	            gen.writeStartObject();
    	    	            gen.writeStartObject("infoLogin");
    	    	            gen.write("code", 0);
    	    	            gen.write("type", "Usuario o contraseña incorrecta");
    	    	            gen.writeEnd();
    	    	            gen.writeEnd();
    	    	        }
    		    }else {
    		    		if(type.equals("asistente")) {
    		    			Asistente asis = new Asistente();
    		    			finalResult = asis.getAssignedStudents(id);   			
    		    			System.out.println(finalResult);
    		    		}else {
    		    			try (JsonGenerator gen = Json.createGenerator(swriter)) {
    		    	            gen.writeStartObject();
    		    	            gen.writeStartObject("infoLogin");
    		    	            gen.write("code", 1);
    		    	            gen.write("name", name);
    		    	            gen.write("type", type);
    		    	            gen.write("id", id);
    		    	            gen.writeEnd();
    		    	            gen.writeEnd();
    		    	        }
    		    			
    		    			finalResult = swriter.toString();
    		    			
    		    		}
    		    }
    		    
    		    if(!conn.isClosed()) {
    		    		conn.close();
    		    }
    	        
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    		return finalResult;
    }
    
    public static String getPersonal() {
		StringWriter swriter = new StringWriter();
	    try {
	    		Connection conn = BaseDatos.conectarBD();
	        String getQueryStatement = "SELECT us.idUsuario, us.nombre, us.apellido FROM Usuario as us  JOIN Asistente as asis JOIN Recepcionista as rec  \n" + 
	        		"ON (us.idUsuario=rec.Usuario_idUsuario) OR (us.idUsuario=asis.Usuario_idUsuario) GROUP BY us.idUsuario;";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	
	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        	gen.writeStartObject();
	            gen.writeStartArray("allAssistants");
	            while(rs.next()) {
		            	gen.writeStartObject();
		               	gen.write("name", ""+rs.getString(2)+ " " + rs.getString(3));
		                gen.write("idAssistant", ""+rs.getString(1));
		            gen.writeEnd();
	            }
	            gen.writeEnd();
	            gen.writeEnd();
	        }
	        return swriter.toString();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
