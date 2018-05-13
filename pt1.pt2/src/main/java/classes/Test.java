package classes;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
	
	public static String obtenerTestProyeccion(String array) throws SQLException {
		StringWriter swriter = new StringWriter();
		JSONObject obj = new JSONObject(array);
		JSONArray selectedExams = obj.getJSONArray("selectedExams");
		
		String cadenaExamenes = "";
		System.out.println("longitud " + selectedExams.length());
		for(int i=0; i<selectedExams.length(); i++) {
			System.out.println("exam " + selectedExams.getString(i));
			if(selectedExams.getString(i).length() != 0) {
				if(cadenaExamenes == "") {
					System.out.println("Primer termino ");
					cadenaExamenes=cadenaExamenes + "'" + selectedExams.getString(i) + "'";
				}else if(selectedExams.length()>1){
					System.out.println("Otros terminos ");
					cadenaExamenes=cadenaExamenes + ",'" + selectedExams.getString(i) + "'";
				} 
				else{
					/*if(i==(selectedExams.length()-1)) {
						cadenaExamenes=cadenaExamenes+","+"'" + selectedExams.getString(i) + "'";
					}else {
						cadenaExamenes=cadenaExamenes + "'" + selectedExams.getString(i) + "'";
					}	*/
				}
			}
		}
		
		System.out.println("cadenaFinal"+cadenaExamenes);
		String getQueryStatement = "Select exu_pr.P_has_R_idpreguntaTest, preg.pregunta,resp.IdRespuesta, resp.respuesta, resp.puntaje from ExamenUbicacion as exu JOIN ExamenUbicacion_has_PreguntaTest_has_Respuesta as exu_pr  JOIN PreguntaTest as preg JOIN Respuesta as resp\n" + 
				"ON exu.idExamenU=exu_pr.ExamenUbicacion_idExamenU AND preg.idpreguntaTest=exu_pr.P_has_R_idpreguntaTest AND resp.IdRespuesta=exu_pr.P_has_R_idRespuesta\n" + 
				"WHERE  exu.nombre IN ("+cadenaExamenes+") group by exu_pr.P_has_R_idpreguntaTest, preg.pregunta, resp.IdRespuesta, resp.respuesta, resp.puntaje order by exu_pr.P_has_R_idpreguntaTest;";

        prepareStat = conn.prepareStatement(getQueryStatement);

        ResultSet rs = prepareStat.executeQuery();
        
        String getQueryStatementExamsScore = "Select exu_n.ExamenUbicacion_idExamenU, exu.nombre ,n.idNivel, n.nombre, exu_n.puntaje, exu.puntajeTotal, exu.TEF  from ExamenUbicacion_has_Nivel as exu_n JOIN ExamenUbicacion as exu JOIN Nivel as n\n" + 
        		"ON exu_n.ExamenUbicacion_idExamenU=exu.idExamenU AND n.idNivel=exu_n.Nivel_idNivel WHERE exu.nombre IN ("+cadenaExamenes+") ORDER BY  exu_n.ExamenUbicacion_idExamenU ASC, n.idNivel ASC;";

        prepareStat = conn.prepareStatement(getQueryStatementExamsScore);

        ResultSet rsExamsScore = prepareStat.executeQuery();
        
        String getQueryStatementTotalScore = "Select n.idNivel, n.nombre, SUM(exu_n.puntaje)  from ExamenUbicacion_has_Nivel as exu_n JOIN ExamenUbicacion as exu JOIN Nivel as n\n" + 
        		"ON exu_n.ExamenUbicacion_idExamenU=exu.idExamenU AND n.idNivel=exu_n.Nivel_idNivel WHERE exu.nombre IN ("+cadenaExamenes+") GROUP BY n.nombre, n.idNivel ORDER BY n.idNivel;";

        prepareStat = conn.prepareStatement(getQueryStatementTotalScore);

        ResultSet rsTotalScore = prepareStat.executeQuery();
        
        List<String> questions=new ArrayList<>();
        List<String> exams=new ArrayList<>();
        int i=0;

        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        	gen.writeStartObject();
	    		gen.writeStartArray("infoForm");
	    		
	    		gen.writeStartObject();
	    		gen.writeStartArray("generalForm");
	        int bandera=0;
	            if (!rs.isBeforeFirst()){
	            		//ResultSet is empty
	            		System.out.println("is empty");
	            		gen.writeStartObject();
		        		gen.write("err", 1);
		        		gen.write("messageError", "No existen registros de preguntas");
		        		gen.writeEnd();
		        		
		        		gen.writeEnd();
					gen.writeEnd();
	    				//gen.writeEnd();
	            	}else {
			        while(rs.next()) {
				        	if(questions.isEmpty()) {
				        		questions.add(rs.getString(1));
				        		
				        		gen.writeStartObject();
				        		gen.write("id", rs.getInt(1));
				        		gen.write("question", rs.getString(2));
			        			gen.writeStartArray("answers");
				        			gen.writeStartObject();
					        			gen.write("id", rs.getInt(3));
					        			gen.write("label", rs.getString(4));
					        			gen.write("score", ""+rs.getInt(5));
				        			gen.writeEnd(); //Cierra el objeto de 1 alumno
				        		//gen.writeEnd(); //Cierra el array de alumnos
				        	}else {
			        			for(i=0; i<questions.size(); i++) {
				        			if(questions.get(i).equals(rs.getString(1))) {
				        				//System.out.println("agrego nuevo año " + rs.getString(6) + " mando a crear nuevo año");
				        				
				        				gen.writeStartObject();
				        					gen.write("id", rs.getInt(3));
					        				gen.write("label", rs.getString(4));
					        				gen.write("score", rs.getInt(5));
					        			gen.writeEnd();
					        		
				        				bandera=0;
				        			}else {
				        				bandera=1;
				        			}
			        			}
			        			
			        			if(bandera==1) {
			        				//System.out.println("si agrego");
			        				
			        				gen.writeEnd();
			        				gen.writeEnd();
			        				
			        				gen.writeStartObject();
			        					gen.write("id", rs.getInt(1));
			        					gen.write("question", rs.getString(2));
				        				gen.writeStartArray("answers");
				        				gen.writeStartObject();
					        				gen.write("id", rs.getInt(3));
					        				gen.write("label", rs.getString(4));
					        				gen.write("score", rs.getInt(5));
					        			gen.writeEnd(); //Cierra el objeto de 1 alumno
	
				        			
					        			questions.add(rs.getString(1));
			        			}
				        	}
			        	}
			        gen.writeEnd();
					gen.writeEnd();
					gen.writeEnd();
					gen.writeEnd();
	            	}
	                 
	    		gen.writeStartObject();
	    		gen.writeStartArray("examsInfo");
	        bandera=0;
	            if (!rsExamsScore.isBeforeFirst()){
	            		//ResultSet is empty
	            		System.out.println("is empty");
	            		gen.writeStartObject();
		        		gen.write("err", 1);
		        		gen.write("messageError", "No existen registros de preguntas");
		        		gen.writeEnd();
		        		
		        		gen.writeEnd();
					gen.writeEnd();
	    				//gen.writeEnd();
	            	}else {
			        while(rsExamsScore.next()) {
			        		System.out.println("hola" + rsExamsScore.getString(4));
				        	if(exams.isEmpty()) {
				        		exams.add(rsExamsScore.getString(1));
				        		gen.writeStartObject();
				        		gen.write("id", rsExamsScore.getInt(1));
				        		gen.write("exam", rsExamsScore.getString(2));
			        			gen.writeStartArray("levels");
				        			gen.writeStartObject();
					        			gen.write("id", rsExamsScore.getInt(3));
					        			gen.write("level", rsExamsScore.getString(4));
					        			gen.write("score", rsExamsScore.getInt(5));
				        			gen.writeEnd(); //Cierra el objeto de 1 alumno
				        		//gen.writeEnd(); //Cierra el array de alumnos
				        	}else {
			        			for(i=0; i<exams.size(); i++) {
				        			if(exams.get(i).equals(rsExamsScore.getString(1))) {
				        				//System.out.println("agrego nuevo año " + rs.getString(6) + " mando a crear nuevo año");
				        				
				        				gen.writeStartObject();
				        					gen.write("id", rsExamsScore.getInt(3));
					        				gen.write("level", rsExamsScore.getString(4));
					        				gen.write("score", rsExamsScore.getInt(5));
					        			gen.writeEnd();
					        		
				        				bandera=0;
				        			}else {
				        				bandera=1;
				        			}
			        			}
			        			
			        			if(bandera==1) {
			        				//System.out.println("si agrego");
			        				
			        				gen.writeEnd();
			        				gen.writeEnd();
			        				
			        				gen.writeStartObject();
			        					gen.write("id", rsExamsScore.getInt(1));
			        					gen.write("exam", rsExamsScore.getString(2));
				        				gen.writeStartArray("levels");
				        				gen.writeStartObject();
					        				gen.write("id", rsExamsScore.getInt(3));
					        				gen.write("level", rsExamsScore.getString(4));
					        				gen.write("score", rsExamsScore.getInt(5));
					        			gen.writeEnd(); //Cierra el objeto de 1 alumno
	
				        			
					        			exams.add(rsExamsScore.getString(1));
			        			}
				        	}
			        	}
			        gen.writeEnd();
					gen.writeEnd();
					gen.writeEnd();
					gen.writeEnd();
	            	}
	            
	        
	       	gen.writeStartObject();
	    		gen.writeStartArray("finalScorePerLevel");
	            if (!rsTotalScore.isBeforeFirst()){
	            		//ResultSet is empty
	            		System.out.println("is empty");
	            		gen.writeStartObject();
		        			gen.write("err", 1);
		        			gen.write("messageError", "No existen registros de preguntas");
		        		gen.writeEnd();
		        		
	            	}else {
			        while(rsTotalScore.next()) {
				        gen.writeStartObject();
				        		gen.write("id", rsTotalScore.getInt(1));
				        		gen.write("level", rsTotalScore.getString(2));
				        		gen.write("total", rsTotalScore.getInt(3));
				        	gen.writeEnd();
			        	}
			    
	            	}
	       	gen.writeEnd();
			gen.writeEnd();
				
	            
	            gen.writeEnd();
	            gen.writeEnd();
	    }
        
        return swriter.toString();
	}
	
}
