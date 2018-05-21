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
import org.json.JSONException;
import org.json.JSONObject;

public class Test {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
	
	public static String obtenerTestProyeccion(String array) throws SQLException {
		StringWriter swriter = new StringWriter();
		JSONObject obj = new JSONObject(array);
		
		JSONObject infoStudent = obj.getJSONObject("selectedStudent");
		
		JSONArray selectedExams = obj.getJSONArray("selectedExams");
		
		String level=infoStudent.getString("level");
		
		String cadenaExamenes = "";
		for(int i=0; i<selectedExams.length(); i++) {
			if(selectedExams.getString(i).length() != 0) {
				if(cadenaExamenes == "") {
					cadenaExamenes=cadenaExamenes + "'" + selectedExams.getString(i) + "'";
				}else if(selectedExams.length()>1){
					cadenaExamenes=cadenaExamenes + ",'" + selectedExams.getString(i) + "'";
				}
			}
		}
		
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

        String getQueryStatementStartPoint = "Select * from Nivel_has_PreguntaTest_has_Respuesta as nivelPR JOIN PreguntaTest as pt JOIN Respuesta as resp JOIN nivel as niv\n" + 
        		"ON nivelPR.P_has_R_idpreguntaTest=pt.idpreguntaTest AND nivelPR.P_has_R_IdRespuesta=resp.IdRespuesta AND nivelPR.Nivel_idNivel=niv.idNivel\n" + 
        		"WHERE nivelPR.puntoInicio=1 AND niv.nombre='"+level+"';";

        prepareStat = conn.prepareStatement(getQueryStatementStartPoint);

        ResultSet rsStartPointTest = prepareStat.executeQuery();

        List<String> questions=new ArrayList<>();
        List<String> exams=new ArrayList<>();
        List<String> questionsPI=new ArrayList<>();
        int i=0;

        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        	gen.writeStartObject();
	        	gen.writeStartObject("infoForm");
	        	
	    		gen.writeStartArray("generalForm");
	        int bandera=0;
	            if (!rs.isBeforeFirst()){
	            		gen.writeStartObject();
		        			gen.write("err", 1);
		        			gen.write("messageError", "No existen registros de preguntas");
		        		gen.writeEnd(); //cierro el de error 
		        		
		        		gen.writeEnd(); //cierro el de array generalForm cuando esta vacio
					
		        		//gen.writeEnd(); era el del objeto que se borro
	            	}else {
			        while(rs.next()) {
				        	if(questions.isEmpty()) {
				        		questions.add(rs.getString(1));
				        		
				        		gen.writeStartObject(); //obj con id 2
				        		gen.write("id", rs.getInt(1));
				        		gen.write("question", rs.getString(2));
			        			gen.writeStartArray("answers");
				        			gen.writeStartObject();
					        			gen.write("id", rs.getInt(3));
					        			gen.write("label", rs.getString(4));
					        			gen.write("score", ""+rs.getInt(5));
				        			gen.writeEnd(); //Cierra el objeto de 1 elemento
				        	}else {
			        			for(i=0; i<questions.size(); i++) {
				        			if(questions.get(i).equals(rs.getString(1))) {
				        				//System.out.println("agrego nuevo año " + rs.getString(6) + " mando a crear nuevo año");
				        				
				        				gen.writeStartObject();
				        					gen.write("id", rs.getInt(3));
					        				gen.write("label", rs.getString(4));
					        				gen.write("score", rs.getInt(5));
					        			gen.writeEnd(); //cierra los objetos de los demas 
					        		
				        				bandera=0;
				        			}else {
				        				bandera=1;
				        			}
			        			}
			        			
			        			if(bandera==1) {
			        				gen.writeEnd(); //cierra el array de answers
			        				gen.writeEnd(); //cierro el objeto que inicia con id 2
			        				
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
	            	}
	                 
	    		
	    		gen.writeStartArray("examsInfo");
	        bandera=0;
	            if (!rsExamsScore.isBeforeFirst()){
	            		gen.writeStartObject();
		        		gen.write("err", 1);
		        		gen.write("messageError", "No existen registros de preguntas");
		        		gen.writeEnd();
		        		
		        		gen.writeEnd();
	            	}else {
			        while(rsExamsScore.next()) {
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
					        			gen.write("real", 0);
				        			gen.writeEnd(); //Cierra el objeto de 1 
				        	}else {
			        			for(i=0; i<exams.size(); i++) {
				        			if(exams.get(i).equals(rsExamsScore.getString(1))) {
				        				
				        				gen.writeStartObject();
				        					gen.write("id", rsExamsScore.getInt(3));
					        				gen.write("level", rsExamsScore.getString(4));
					        				gen.write("score", rsExamsScore.getInt(5));
					        				gen.write("real", 0);
					        			gen.writeEnd();
					        		
				        				bandera=0;
				        			}else {
				        				bandera=1;
				        			}
			        			}
			        			
			        			if(bandera==1) {			        				
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
					        				gen.write("real", 0);
					        			gen.writeEnd(); //Cierra el objeto de 1 alumno
	
				        			
					        			exams.add(rsExamsScore.getString(1));
			        			}
				        	}
			        	}
					gen.writeEnd();
					gen.writeEnd();
					gen.writeEnd();
	            	}
	            
	        
	       	
	    		gen.writeStartArray("finalScorePerLevel");
	            if (!rsTotalScore.isBeforeFirst()){
	            		gen.writeStartObject();
		        			gen.write("err", 1);
		        			gen.write("messageError", "No existen niveles");
		        		gen.writeEnd();
		        		
	            	}else {
			        while(rsTotalScore.next()) {
				        gen.writeStartObject();
				        		gen.write("id", rsTotalScore.getInt(1));
				        		gen.write("level", rsTotalScore.getString(2));
				        		gen.write("total", rsTotalScore.getInt(3));
				        		gen.write("real", 0);
				        	gen.writeEnd();
			        	}
			    
	            	}
	       	gen.writeEnd();
	       	
	       	
	       	gen.writeStartArray("questionsPI");
	        bandera=0;
	            if (!rsStartPointTest.isBeforeFirst()){
	            		gen.writeStartObject();
		        			gen.write("err", 4);
		        			gen.write("messageError", "No se tienen datos para una proyección de ese nivel");
		        		gen.writeEnd();
		        		
					gen.writeEnd();
	            	}else {
			        while(rsStartPointTest.next()) {
				        	if(questionsPI.isEmpty()) {
				        		questionsPI.add(rsStartPointTest.getString(2));
				        		gen.writeStartObject();
				        		gen.write("id", rsStartPointTest.getInt(2));
				        		gen.write("question", rsStartPointTest.getString(7));
				        		gen.write("identification", rsStartPointTest.getString(5));
				        		gen.write("selected", rsStartPointTest.getString(3));
			        			gen.writeStartArray("answers");
				        			gen.writeStartObject();
					        			gen.write("id", rsStartPointTest.getInt(8));
					        			gen.write("answer", rsStartPointTest.getString(9));
					        			gen.write("score", rsStartPointTest.getInt(10));
				        			gen.writeEnd(); //Cierra el objeto de 1 
				        	}else {
				        		for(i=0; i<questionsPI.size(); i++) {
				        			if(questionsPI.get(i).equals(rsStartPointTest.getString(2))) {
				        			
				        				gen.writeStartObject();
				        					gen.write("id", rsStartPointTest.getInt(8));
				        					gen.write("answer", rsStartPointTest.getString(9));
				        					gen.write("score", rsStartPointTest.getInt(10));
					        			gen.writeEnd();
					        		
				        				bandera=0;
				        			}else {
				        				bandera=1;
				        			}
			        			}
			        			
			        			if(bandera==1) {	     
			        				gen.writeEnd();
			        				gen.writeEnd();
			        				
			        				gen.writeStartObject();
			        				gen.write("id", rsStartPointTest.getInt(2));
					        		gen.write("question", rsStartPointTest.getString(7));
					        		gen.write("identification", rsStartPointTest.getString(5));
					        		gen.write("selected", rsStartPointTest.getString(3));
				        			gen.writeStartArray("answers");
					        			gen.writeStartObject();
						        			gen.write("id", rsStartPointTest.getInt(8));
						        			gen.write("answer", rsStartPointTest.getString(9));
						        			gen.write("score", rsStartPointTest.getInt(10));
						        			gen.write("real", 0);
					        			gen.writeEnd(); //Cierra el objeto de 1 alumno
	
					        			questionsPI.add(rsStartPointTest.getString(2));
			        			}
				        	}
			        	}
					gen.writeEnd();
					gen.writeEnd();
					gen.writeEnd();
	            	}
	            
	            gen.writeEnd(); //inicio de la variable infoForm
	            gen.writeEnd(); //inicio
	    }
        
        return swriter.toString();
	}
	
}
