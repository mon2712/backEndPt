package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import org.json.JSONArray;
import org.json.JSONObject;

public class Asistente {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
    public static String getAsistentes() {
		StringWriter swriter = new StringWriter();
	    try {
	        String getQueryStatement = "SELECT * FROM asistente as asis JOIN usuario as us WHERE us.idUsuario=asis.Usuario_idUsuario;";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	
	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        	gen.writeStartObject();
	            gen.writeStartArray("allAssistants");
	            while(rs.next()) {
	            		System.out.println(rs.getString(1) + " " + rs.getString(3)+" "+rs.getString(2));
	                gen.writeStartObject();
	                gen.write("name", ""+rs.getString(4)+ " " + rs.getString(9));
	                gen.write("idAssistant", ""+rs.getString(2));
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
    
    public static String getAsistenteInfo(String array) {
		StringWriter swriter = new StringWriter();
	    try {
	    		JSONObject obj = new JSONObject(array);
			JSONArray selectedPeople = obj.getJSONArray("selectedPeople");
			
			JSONObject asis = selectedPeople.getJSONObject(0);
			
	    		if(asis.has("idAssistant")) {
		        String getQueryStatement = "SELECT * FROM asistente as asis JOIN usuario as us WHERE us.idUsuario=asis.Usuario_idUsuario AND us.idUsuario="+asis.getString("idAssistant")+";";
		
		        prepareStat = conn.prepareStatement(getQueryStatement);
		
		        // Execute the Query, and get a java ResultSet
		        ResultSet rs = prepareStat.executeQuery();
		        
		        try (JsonGenerator gen = Json.createGenerator(swriter)) {
		        	gen.writeStartObject();
		            while(rs.next()) {
		            		System.out.println(rs.getString(1) + " " + rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" " +rs.getString(5) + " " + ""+rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8)+" "+rs.getString(9)+" "+rs.getString(10)+" "+rs.getString(11)+" "+rs.getString(12)+" "+rs.getString(13)+" "+rs.getString(14));
		                
		            		
		            	gen.writeStartObject("assistantInfo");
		                gen.write("name", ""+rs.getString(4));
		                gen.write("idAssistant", ""+rs.getString(2));
		                gen.write("username", ""+rs.getString(5));
		                gen.write("password", ""+rs.getString(6));
		                gen.write("phone", ""+rs.getString(7));
		                gen.write("lastName", ""+rs.getString(9));
		                gen.write("arriveTime", ""+rs.getString(10));
		                gen.write("status", ""+rs.getString(8));
		                gen.write("level", ""+rs.getString(1));
		                gen.write("lunes", ""+rs.getString(11));
		                gen.write("miercoles", ""+rs.getString(12));
		                gen.write("jueves", ""+rs.getString(13));
		                gen.write("sabado", ""+rs.getString(14));

		                gen.writeEnd();
		            }
		            gen.writeEnd();
		        }
	    		}
	        return swriter.toString();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    /**
     * @param array
     */
    public static String setAssistant(String array) {
		StringWriter swriter = new StringWriter();
		System.out.println("llega a la funcion"+array);
	    try {
	    		JSONObject obj = new JSONObject(array);
	    		
			//JSONArray selectedPeople = obj.getJSONArray("infoAssistant");
			
			JSONObject asis = obj.getJSONObject("infoAssistant");
			
	    		if(asis.has("idAssistant") ) {
	    			System.out.println("hola");
	    			if(asis.getString("idAssistant").length() == 0 ) {
	    				System.out.println("no existe");
	    				
	    				int lunes=0, miercoles=0, jueves=0, sabado=0;
	    				
	    				if(asis.getString("lunes").length() == 0) lunes=0;
	    				if(asis.getString("miercoles").length() == 0) miercoles=0;
	    				if(asis.getString("jueves").length() == 0) jueves=0;
	    				if(asis.getString("sabado").length() == 0) sabado=0;
	    				
	    				CallableStatement cStmt = conn.prepareCall("{call setUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	    				
	        		    cStmt.setString(1, asis.getString("name"));
	        		    cStmt.setString(2, asis.getString("lastName"));
	        		    cStmt.setString(3, asis.getString("phone"));
	        		    cStmt.setString(4, asis.getString("password"));
	        		    cStmt.setString(5, asis.getString("username"));
	        		    cStmt.setString(6, asis.getString("arriveTime"));
	        		    cStmt.setString(7, asis.getString("level"));
	        		    cStmt.setString(8, asis.getString("type"));
	        		    cStmt.setInt(9, lunes);
	        		    cStmt.setInt(10, miercoles);
	        		    cStmt.setInt(11, jueves);
	        		    cStmt.setInt(12, sabado);
	        		    cStmt.registerOutParameter(13, Types.VARCHAR);
	        		    cStmt.registerOutParameter(14, Types.VARCHAR);
	        		    cStmt.registerOutParameter(15, Types.INTEGER);
	        		    cStmt.registerOutParameter(16, Types.INTEGER);
	        		    cStmt.execute();    
	    				
	    				String messageError, nombre, typeUsuario;
	    				int err;
	    				
	    				messageError=cStmt.getString(16);
	    				nombre=cStmt.getString(13);
	    				typeUsuario=cStmt.getString(14);
	    				err=cStmt.getInt(15);
	    		
	    		        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	    		        		gen.writeStartObject();
	    		        		
	    		        		gen.writeStartObject("assistantInfo");
	    		        		gen.write("name", ""+nombre);
	    		        		gen.write("typeUser", ""+typeUsuario);
	    		        		gen.write("err", ""+err);
	    		        		gen.write("messageError", ""+messageError);
	    		        		gen.writeEnd();
	    		            
	    		        		gen.writeEnd();
	    		        }
	    			}else {
	    				System.out.println("si existe");
	    				
	    				CallableStatement cStmt = conn.prepareCall("{call updateUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	    				
	    				cStmt.setInt(1, asis.getInt("idAssistant"));
	        		    cStmt.setString(2, asis.getString("name"));
	        		    cStmt.setString(3, asis.getString("lastName"));
	        		    cStmt.setString(4, asis.getString("phone"));
	        		    cStmt.setString(5, asis.getString("password"));
	        		    cStmt.setString(6, asis.getString("username"));
	        		    cStmt.setString(7, asis.getString("arriveTime"));
	        		    cStmt.setString(8, asis.getString("level"));
	        		    cStmt.setString(9, asis.getString("status"));
	        		    cStmt.setInt(10, asis.getInt("lunes"));
	        		    cStmt.setInt(11, asis.getInt("miercoles"));
	        		    cStmt.setInt(12, asis.getInt("jueves"));
	        		    cStmt.setInt(13, asis.getInt("sabado"));
	        		    cStmt.registerOutParameter(14, Types.VARCHAR);
	        		    cStmt.registerOutParameter(15, Types.INTEGER);
	        		    cStmt.registerOutParameter(16, Types.VARCHAR);
	        		    cStmt.execute();    
	    				
	    				String messageError, nombre;
	    				int err;
	    				
	    				messageError=cStmt.getString(16);
	    				nombre=cStmt.getString(14);
	    				err=cStmt.getInt(15);
	    		
	    		        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	    		        		gen.writeStartObject();
	    		        		
	    		        		gen.writeStartObject("assistantInfo");
	    		        		gen.write("name", ""+nombre);
	    		        		gen.write("err", ""+err);
	    		        		gen.write("messageError", ""+messageError);
	    		        		gen.writeEnd();
	    		            
	    		        		gen.writeEnd();
	    		        }
	    				
	    			}
	    			
		       
	    		}
	        return swriter.toString();
	    	} catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
