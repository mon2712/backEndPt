package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

public class Alumno {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
    public static String obtenerFichaAlumno(int idAlumno) {
    		
    		StringWriter swriter = new StringWriter();
    		try {
    		    CallableStatement cStmt = conn.prepareCall("{call getStudentFile(?,?,?,?,?,?,?,?,?)}");
	
    		    cStmt.setInt(1, idAlumno);
    		    cStmt.registerOutParameter(2, Types.INTEGER);
    		    cStmt.registerOutParameter(3, Types.INTEGER);
    		    cStmt.registerOutParameter(4, Types.VARCHAR);
    		    cStmt.registerOutParameter(5, Types.VARCHAR);
    		    cStmt.registerOutParameter(6, Types.VARCHAR);
    		    cStmt.registerOutParameter(7, Types.VARCHAR);
    		    cStmt.registerOutParameter(8, Types.VARCHAR);
    		    cStmt.registerOutParameter(9, Types.INTEGER);
    		    cStmt.execute();    
    		    
    		    int err, debeColegiatura, asistencias;
    		    String nombre, tutor, telefono, apellido, nivel,debe;
    		    err=cStmt.getInt(9);
    		    debeColegiatura=cStmt.getInt(2);
    		    asistencias=cStmt.getInt(3);
    		    nombre = cStmt.getString(5);
    		    apellido = cStmt.getString(6);
    		    nivel = cStmt.getString(7);
    		    telefono = cStmt.getString(8);
    		    tutor=cStmt.getString(4);
    		    
    		    System.out.println("usuario: "+nombre + " " + apellido + " " + nivel+"telefono"+telefono);
    		    if (debeColegiatura == 1) debe="true"; else debe="false";
    		    if (telefono == null) telefono="";
    		    
    		    if(err == 1) {
    		    		System.out.println("no existe alumno");
    		    		try (JsonGenerator gen = Json.createGenerator(swriter)) {
    	    	            gen.writeStartObject();
    	    	            gen.writeStartObject("student");
    	    	            gen.write("code", 1);
    	    	            gen.write("type", "No existen alumnos con ese id");
    	    	            gen.writeEnd();
    	    	            gen.writeEnd();
    	    	        }
    		    }else if(err == 0) {
    		    		System.out.println("existe un alumno"+nombre);
	    	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	    	            gen.writeStartObject();
	    	            gen.writeStartObject("student");
	    	            gen.write("code", 0);
	    	            gen.write("name", nombre);
	    	            gen.write("lastName", apellido);
	    	            gen.write("level", nivel);
	    	            gen.write("tutor", tutor);
	    	            gen.write("phone", telefono);
	    	            gen.write("cellphone", "5564555656");
	    	            gen.write("missingPayment", debe);
	    	            gen.write("assistances", asistencias);
	    	            gen.writeEnd();
	    	            gen.writeEnd();
	    	        }
    		    }
    	        
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    		return swriter.toString();
    }
    
    public static String getAlumnos(String filter) {
    		System.out.println("filter en funcion"+filter);
    		StringWriter swriter = new StringWriter();
        try {
            String getQueryStatement = "SELECT * FROM alumno WHERE CONCAT(nombre, ' ',apellido) LIKE '%"+filter+"%';";

            prepareStat = conn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = prepareStat.executeQuery();

            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	gen.writeStartObject();
	            gen.writeStartArray("allStudents");
	            while(rs.next()) {
	            		System.out.println(rs.getString(1) + " " + rs.getString(3)+" "+rs.getString(2));
	                gen.writeStartObject();
	                gen.write("name", rs.getString(3)+ " " + rs.getString(2));
	                gen.write("idStudent", rs.getString(1));
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
