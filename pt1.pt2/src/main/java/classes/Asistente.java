package classes;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		            		System.out.println(rs.getString(1) + " " + rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" " +rs.getString(5) + " " + ""+rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8)+" "+rs.getString(9)+" "+rs.getString(10));
		                gen.writeStartObject("assistantInfo");
		                gen.write("name", ""+rs.getString(4));
		                gen.write("idAssistant", ""+rs.getString(2));
		                gen.write("username", ""+rs.getString(5));
		                gen.write("password", ""+rs.getString(6));
		                gen.write("phone", ""+rs.getString(7));
		                gen.write("lastName", ""+rs.getString(9));
		                gen.write("arriveTime", ""+rs.getString(10));
		                gen.write("level", ""+rs.getString(1));
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
}
