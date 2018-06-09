package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
//import org.json.simple.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import TrabajoTerminalBack.pt1.pt2.conexionDB;

public class Centro {
	
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
	
	public static String getStudents(String filter) {
		StringWriter swriter = new StringWriter();
        try {
        	int totalR=0, totalC=0, n=0;
        	String getQueryStatement = "CALL jsonStudentsAtCenter('"+filter+"')";
        	//System.out.println("fileter: " + filter);
            prepareStat = conn.prepareStatement(getQueryStatement);
            ResultSet rs = prepareStat.executeQuery();
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
                gen.writeStartObject();
                gen.writeStartArray("studentsInCenter");
               
                 while(rs.next()){
                     
                         gen.writeStartObject();
                         gen.write("name", rs.getString(1)+" "+rs.getString(2));
                         gen.write("idStudent", rs.getString(3));
                         gen.write("hourIn", ""+rs.getString(4));
                         gen.write("timeRed", ""+rs.getString(5));                    
                         gen.writeEnd();
     
                     }
                 gen.writeEnd();
                 gen.writeEnd();
            }
            
        }catch (SQLException e) {
        	
            e.printStackTrace();
        }  
        
        return swriter.toString();    
    }
	
	public static void updateTimeRed(String timeRed, int idStudent){
		try {
		    CallableStatement cS = conn.prepareCall("{call updateTimeRed(?, ?)}");
		    cS.setString(1, timeRed);
		    cS.setInt(2, idStudent);
		    cS.execute();  
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    public static String getEstatusCentro() {
			//System.out.println("filter en funcion"+filter);
			StringWriter swriter = new StringWriter();
	    try {
	        String getQueryStatement = "SELECT a.idAlumno, a.nombre, a.apellido, a.nivel, asis.horaEntrada, TIME_TO_SEC(subtime(curtime(), horaEntrada))/60 ,asis.Asistente_Usuario_idUsuario, us.nombre, us.apellido, asis.tiempoReducido, ass.nivel\r\n" + 
	        		"FROM asistencia as asis JOIN alumno as a JOIN usuario as us JOIN Asistente as ass\r\n" + 
	        		"ON asis.Asistente_Usuario_idUsuario=us.idUsuario AND ass.Usuario_idUsuario=us.idUsuario AND a.idAlumno=asis.Alumno_idAlumno\r\n" + 
	        		"WHERE  asis.fecha=CURDATE() AND asis.horaSalida='00:00:00' ORDER BY asis.Asistente_Usuario_idUsuario;";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	        
	        String qryAsistVacias = "SELECT asist.Asistente_Usuario_idUsuario, users.nombre, users.apellido, ass.nivel, (COUNT(asist.Asistente_Usuario_idUsuario) -1) \r\n" + 
	        		"FROM Asistencia as asist  JOIN Usuario as users JOIN Asistente as ass\r\n" + 
	        		"ON asist.Asistente_Usuario_idUsuario=users.idUsuario AND ass.Usuario_idUsuario=users.idUsuario\r\n" + 
	        		"WHERE asist.fecha=CURDATE()  AND asist.horaSalida='00:00:00' GROUP BY asist.Asistente_Usuario_idUsuario;";
	
	        prepareStat = conn.prepareStatement(qryAsistVacias);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rsEmptyAssistants = prepareStat.executeQuery();
	     
            
            List<String> asistentes=new ArrayList<>();
            int i;
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	   gen.writeStartObject();
	           gen.writeStartArray("asistentes");
	            int bandera=0;
	            if (!rs.isBeforeFirst()){
	            		//ResultSet is empty
	            		System.out.println("esta vacio de alumnos");
	            		
	            		if(!rsEmptyAssistants.isBeforeFirst()) {
	    					System.out.println("esta vacio en el segundo rs");
	    				}else {
	    					System.out.println("no esta vacio en el segundo rs");
	    					while(rsEmptyAssistants.next()){
	    						System.out.println("asistente " + rsEmptyAssistants.getString(2));
	    						
	    						if(rsEmptyAssistants.getInt(5) == 0) {
	    							gen.writeStartObject();
	    				        			gen.write("idAsistente", rsEmptyAssistants.getString(1));
	    				        			gen.write("name", rsEmptyAssistants.getString(2));
	    				        			gen.write("lastName", ""+rsEmptyAssistants.getString(3));
	    				        			gen.write("level", ""+rsEmptyAssistants.getString(4));
	    				        			gen.writeStartArray("students");
	    					        			
	    					        		gen.writeEnd();
	    					        	gen.writeEnd();
	    						}
	    						
	    					}
	    				}
	            		
	            		
	            		
	            		gen.writeEnd();
	    				gen.writeEnd();
	            	}else {
			        while(rs.next()) {
			        		System.out.println("hola"+rs.getString(1) + " "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4) + " " +rs.getString(5) + " " + rs.getString(6) +" " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9));
			        		
			        		if(asistentes.isEmpty()) {
			        			asistentes.add(rs.getString(7));
			        			gen.writeStartObject();
				        			gen.write("idAsistente", rs.getString(7));
				        			gen.write("name", rs.getString(8));
				        			gen.write("lastName", ""+rs.getString(9));
				        			gen.write("level", ""+rs.getString(11));
				        			gen.writeStartArray("students");
					        			gen.writeStartObject();
						        			gen.write("idStudent", rs.getString(1));
						        			gen.write("name", rs.getString(2));
						        			gen.write("lastName", ""+rs.getString(3));
						        			gen.write("level", rs.getString(4));
					        				gen.write("entranceTime", rs.getString(5));
					        				gen.write("timeAtCenter", rs.getString(6));
					        				gen.write("timeReduced", ""+ rs.getString(10));
					        			gen.writeEnd(); //Cierra el objeto de 1 alumno
					        		//gen.writeEnd(); //Cierra el array de alumnos
			        			//gen.writeEnd();//cierra el objeto de asistente
			        		}else {
			        			for(i=0; i<asistentes.size(); i++) {
				        			if(asistentes.get(i).equals(rs.getString(7))) {
				        				System.out.println("agrego alumno " + rs.getString(1) + " mando a crear alumno nuevo");
				        				
				        				gen.writeStartObject();
					        				gen.write("idStudent", rs.getString(1));
					        				gen.write("name", rs.getString(2));
					        				gen.write("lastName", ""+rs.getString(3));
					        				gen.write("level", rs.getString(4));
					        				gen.write("entranceTime", rs.getString(5));
					        				gen.write("timeAtCenter", rs.getString(6));
					        				gen.write("timeReduced", ""+ rs.getString(10));
					        			gen.writeEnd();
					        		
				        				bandera=0;
				        			}else {
				        				bandera=1;
				        			}
				        		}
			        			if(bandera==1) {
			        				System.out.println("si agrego");
			        				gen.writeEnd();
			        				gen.writeEnd();
			        				
			        				gen.writeStartObject();
				        				gen.write("idAsistente", rs.getString(7));
				        				gen.write("name", rs.getString(8));
				        				gen.write("lastName", ""+rs.getString(9));
				        				gen.write("level", ""+rs.getString(11));
				        				gen.writeStartArray("students");
				        				gen.writeStartObject();
						        			gen.write("idStudent", rs.getString(1));
						        			gen.write("name", rs.getString(2));
						        			gen.write("lastName", ""+rs.getString(3));
						        			gen.write("level", rs.getString(4));
					        				gen.write("entranceTime", rs.getString(5));
					        				gen.write("timeAtCenter", rs.getString(6));
					        				gen.write("timeReduced", ""+ rs.getString(10));
					        			gen.writeEnd(); //Cierra el objeto de 1 alumno
	
				        				
			        				asistentes.add(rs.getString(7));
			        			}
			        		}
			       }
		        
		        for(i=0; i<asistentes.size(); i++) {
		        		System.out.println(asistentes.get(i));
		        }

				gen.writeEnd();
				gen.writeEnd();
				
				if(!rsEmptyAssistants.isBeforeFirst()) {
					System.out.println("esta vacio en el segundo rs");
				}else {
					System.out.println("no esta vacio en el segundo rs");
					while(rsEmptyAssistants.next()){
						System.out.println("asistente " + rsEmptyAssistants.getString(2));
						
						if(rsEmptyAssistants.getInt(5) == 0) {
							gen.writeStartObject();
				        			gen.write("idAsistente", rsEmptyAssistants.getString(1));
				        			gen.write("name", rsEmptyAssistants.getString(2));
				        			gen.write("lastName", ""+rsEmptyAssistants.getString(3));
				        			gen.write("level", ""+rsEmptyAssistants.getString(4));
				        			gen.writeStartArray("students");
					        			
					        		gen.writeEnd();
					        	gen.writeEnd();
						}
						
					}
				}
				
		        gen.writeEnd();
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



