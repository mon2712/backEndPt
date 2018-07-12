package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import org.json.JSONObject;

public class Recepcion {
	static PreparedStatement prepareStat = null;
    static Connection conn = BaseDatos.conectarBD();
    
    public static String getAlumnosLlamadas() {
		StringWriter swriter = new StringWriter();
		String resultado="";
		try {
			String getQueryStatement = "CALL alumnosConFalta();";
			CallableStatement cStmt = conn.prepareCall("{call listaNuevasLlamadas(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}");
			String getQueryStatement2 = "call getLlamadasEnNotificaciones();";

		    //Otiene alumnos con una falta
			prepareStat = conn.prepareStatement(getQueryStatement);
            ResultSet rs = prepareStat.executeQuery(); //devuelbe nombre, apellido, idAlumno, lunes, miercoles, jueves, sabado
            
            //Obteniendo las notificaciones de llamadas viejas
            prepareStat = conn.prepareStatement(getQueryStatement2);
            ResultSet rs2 = prepareStat.executeQuery();
            
            //Alumno alumn= new Alumno();
            String nota= "", ultimaAsistencia="", active="false", done="",fechaLlamada="";
            int estatus=0, activacion=0; 
            int[] arrayDays = new int[4];
            String old="";

            //Fecha actual desglosada://///////////////////////////////////////
            Calendar fecha = Calendar.getInstance();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH) + 1;
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            int diaSemana = fecha.get(Calendar.DAY_OF_WEEK);
            int hora = fecha.get(Calendar.HOUR_OF_DAY);
            String mesString="", diaString="";

            if(mes<10) {
            	mesString="0"+ Integer.toString(mes);
            }
            else {
            	mesString=Integer.toString(mes);
            }
            if(dia<10) {
            	diaString="0"+ Integer.toString(dia);
            }
            else {
            	diaString=Integer.toString(dia);
            }
            String hoy= año + "-" + mesString + "-" + diaString;
            
            //////////////////////////////////////////////////////////////////
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            gen.writeStartObject();
            gen.writeStartArray("listOfCalls");
            	//notificaciones ya cargadas (llamadas viejas)
            	while(rs2.next()) {
            		if(hoy.equals(rs2.getString(4))) {
            			old="false";
            		}
            		else {
            			old="true";
            		}
            		if( rs2.getString(5).equals(null) || rs2.getString(5).equals("") ) {
            			done="false";
            		}
            		else{
            			done="true";
            		}
	            	if((old.equals("true") && done.equals("false")) || (old.equals("false"))){	
	            		gen.writeStartObject();
		    	            gen.write("idStudent", rs2.getInt(1));
		    	            gen.write("name", ""+rs2.getString(2)+  " " + rs2.getString(3));
		    	            gen.writeStartObject("call");
			    	            gen.write("done", done);
			    	            gen.write("active", active);
			    	            gen.write("note", ""+rs2.getString(5));
			    	            gen.write("date", ""+rs2.getString(4));
			    	            gen.write("old", old);
			    	        gen.writeEnd();
			    	    gen.writeEnd();
	            	}
            	}
            	
            	//notificaciones para cargar (llamadas nuevas)
            	if(hora>=19) { //PARA QUE SE EJECUTE DESPUES DE LAS 7 PM
            		if(diaSemana!=1 && diaSemana!=3 && diaSemana!=6) {
	            		if(rs.getRow()==1 && rs.getInt(9)==1){ //no hay faltas
		            			System.out.println("recibo elerror" + rs.getString(9) );
		                }else { //si hay faltas
		                    while(rs.next()){
			            		old= "false";
			            		List<Integer> diasQueViene = new ArrayList<Integer> ();
			
			            		int diaAnterior=0;
			            		int today=0;
			            		
			            		if(rs.getInt(8)==2) today=0;
			            		if(rs.getInt(8)==4) today=1;
			            		if(rs.getInt(8)==5) today=2;
			            		if(rs.getInt(8)==7) today=3;
			            		
			            		// 2 lunes, 3 miercoles, 4 jueves, 6 sabado
			            		arrayDays[0] = rs.getInt(4); //lunes
			            		arrayDays[1] = rs.getInt(5); //miercoles
			            		arrayDays[2] = rs.getInt(6); //jueves
			            		arrayDays[3] = rs.getInt(7); //sabado
			            		
			            		int count=0;
			            		
			            		for(int i=0; i<4; i++) {
			            			
			            			if(today != i && arrayDays[i] == 1) {
			            				if(i == 0) diasQueViene.add(2);
			            				if(i == 1) diasQueViene.add(4);
			            				if(i == 2) diasQueViene.add(5);
			            				if(i == 3) diasQueViene.add(7);
			            			}
			            		}
			            		int size=diasQueViene.size();
			            		
			            		if(size == 0) {
			            			diaAnterior=rs.getInt(8);
			
			            		}else {
			            			diaAnterior=diasQueViene.get(size-1);
			            		}
			            		
			            		//Ejecutando uno a uno los alumnos para ver si pertenecen a la lista dellamdas por realizar (es su segunda falta)
			            		cStmt.setInt(1, diaAnterior); //dìa anterior
			        		    cStmt.setInt(2, rs.getInt(3)); //id del alumno
			        		    cStmt.registerOutParameter(3, Types.INTEGER); // id regreso
			        		    cStmt.registerOutParameter(4, Types.VARCHAR); // nombre
			        		    cStmt.registerOutParameter(5, Types.VARCHAR); // apellido
			        		    cStmt.registerOutParameter(6, Types.VARCHAR); // nota
			        		    cStmt.registerOutParameter(7, Types.DATE);	  // fecha
			        		    cStmt.registerOutParameter(8, Types.INTEGER); // estatus
			        		    cStmt.registerOutParameter(9, Types.INTEGER); // activacion
			        		    cStmt.registerOutParameter(10, Types.DATE);	  // fecha de ultima asistencia
			        		    cStmt.registerOutParameter(11, Types.INTEGER); // ERROR
			        		    cStmt.registerOutParameter(12, Types.VARCHAR); // MENSAJE DE ERROR
		
			        		    
			        		    cStmt.execute();    
		
			        		   if (cStmt.getInt(11) == 0){
				        		    
				        		    nota=""+cStmt.getString(6);
				        		    fechaLlamada=""+cStmt.getString(7);
				        		    estatus=cStmt.getInt(8);
				        		    activacion=cStmt.getInt(9);
				        		    ultimaAsistencia=cStmt.getString(10);
				        		    if(estatus == 1) done="true";	
				        		    else done="false";
				        		    if(activacion == 1) active="true";	
				        		    else active="false";
				        		    if(cStmt.getInt(11)==0 && cStmt.getInt(3)!=0){
				        		    	gen.writeStartObject();
						    	            gen.write("idStudent", cStmt.getInt(3));
						    	            gen.write("name", cStmt.getString(4) +  " " + cStmt.getString(5));
						    	            gen.writeStartObject("call");
							    	            gen.write("done", done);
							    	            gen.write("active", active);
							    	            gen.write("note", nota);
							    	            gen.write("date", fechaLlamada);
							    	            gen.write("old", old);
							    	        gen.writeEnd();
							    	     gen.writeEnd();
				        		    }
					    	     }
			            	} 
		                }
            		}
            	}
	            gen.writeEnd();
		        gen.writeEnd();  
            	}
            resultado=swriter.toString();
            System.out.println(resultado);
		 } catch (SQLException e) {

			e.printStackTrace();
		 }
		return resultado;  
    }
    
    public static void NotaLlamada(int idAlumno, String nota, String fec) {
        CallableStatement cStmt;
		try {
			cStmt = conn.prepareCall("{call setNotaLlamada(?, ?, ?, ?)}");
			cStmt.setInt(1, idAlumno);
		    cStmt.setString(2, nota);
		    cStmt.setString(3, fec);
		    cStmt.setInt(4, 0);
		    cStmt.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    public static String setAsistencia(String array) throws SQLException {
		StringWriter swriter = new StringWriter();
		String finalResult = "";
		
		JSONObject obj = new JSONObject(array);
		
		JSONObject scanned = obj.getJSONObject("scanned");
		
		int id = scanned.getInt("id");
		String types = scanned.getString("type");
		
		
		if(types.equals("S")) {
			Alumno alumno = new Alumno();
			
			String alumnoInfo = alumno.obtenerFichaAlumno(id);
			
			
						
			Auxiliar aux = new Auxiliar();
			String asistente = aux.asignarAsistente(alumnoInfo);
			

			JSONObject obj2 = new JSONObject(asistente);
			
			JSONObject infoAssistant = obj2.getJSONObject("infoAssistant");
			
			if(infoAssistant.getInt("error") == 0 ) {
				CallableStatement cStmt = conn.prepareCall("{call setAsistencia(?,?,?,?)}");
				
	    		    cStmt.setInt(1, id);
	    		    cStmt.setInt(2, infoAssistant.getInt("id"));
	    		    cStmt.setString(3, "student");
	    		    cStmt.registerOutParameter(4, Types.INTEGER);
	    		    
	    		    cStmt.execute();  
	    		    
	    		    if(cStmt.getInt(4) == 0) {
	    		    		//getRecomendaciones
	    		    		
	    		    		if(infoAssistant.getBoolean("missingPayment") == true) {
	    		    			CallableStatement cStmt2 = conn.prepareCall("{call setNotificacionPago(?)}");

	    		    			cStmt2.setInt(1, id);
	    		    		    
	    		    			cStmt2.execute();

	    		    		}
	    		    		
	    		    }else {
	    		    		finalResult = asistente;
	    		    }
	    		    
			}else {
				finalResult = asistente;
			}
			
			finalResult = alumnoInfo;
		}else if(types.equals("A")) {
			CallableStatement cStmt = conn.prepareCall("{call setAsistencia(?,?,?,?)}");
			
		    cStmt.setInt(1, 0);
		    cStmt.setInt(2, id);
		    cStmt.setString(3, "assistant");
		    cStmt.registerOutParameter(4, Types.INTEGER);
		    
		    cStmt.execute();  
		    
		    if(cStmt.getInt(4) == 0) {
		    		//getRecomendaciones
		    		System.out.println("se agrego");
		    		String queryAsistentes = "SELECT asis.Asistente_Usuario_idUsuario, users.nombre, users.apellido, users.telefono, asis.horaEntrada, users.horaLlegada, asist.nivel FROM Asistencia as asis \r\n" + 
		    				"JOIN Usuario as users JOIN Asistente as asist ON asis.Asistente_Usuario_idUsuario=users.idUsuario AND asist.Usuario_idUsuario=users.idUsuario \r\n" + 
		    				"WHERE  asis.Asistente_Usuario_idUsuario="+Integer.toString(id)+" AND fecha=CURDATE() AND Alumno_idAlumno is NULL;";
		    		
	
	        		prepareStat = conn.prepareStatement(queryAsistentes);
	
	            ResultSet rsAsistente = prepareStat.executeQuery();
	            
	            if(rsAsistente.first()) {
		            	try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        				gen.writeStartObject();
	        				gen.writeStartObject("student");
	        					gen.write("code", 0);
	        					gen.write("type","assistant");
	    		                	gen.write("id", rsAsistente.getInt(1));
	    		                	gen.write("name", rsAsistente.getString(2));
	    		                	gen.write("lastName", ""+rsAsistente.getString(3));
	    	    	            		gen.write("level", rsAsistente.getString(7));
	    	    	            		gen.write("phone", rsAsistente.getString(4));
	    	    	            		gen.write("entranceTime", rsAsistente.getString(5));
	    	    	            		gen.write("realEntrance", rsAsistente.getString(6));
	    	    	            		gen.write("assistances", "");
	    	    	            		gen.write("nameMom", "");
	    	    	            		gen.write("lastNameMom", "");
	    	    	            		gen.write("phoneHouse","");
	    	    	            		gen.write("cellMom", "");
	    		            gen.writeEnd();
	            	        gen.writeEnd();
	        			} catch (SQLException e) {
	        		        e.printStackTrace();
	        		        return null;
	        		    }
	            }
	            finalResult = swriter.toString();
		    }
		    
		}else {
			System.out.println("error");
		}
		
		return finalResult;

    }

    public static String getNotificacion() throws SQLException {
    		StringWriter swriter = new StringWriter();
    
	    	String qryLlamadas = "SELECT COUNT(*) FROM Notificacion WHERE tipo='llamada' AND fecha IS NULL AND nota IS NULL GROUP BY tipo;";
	    	String qryPagos = "SELECT * FROM Notificacion as noti JOIN Alumno as alu ON noti.Alumno_idAlumno=alu.idAlumno WHERE fecha=CURDATE() AND tipo='pago';";
	    	
	
		prepareStat = conn.prepareStatement(qryLlamadas);
	
	    ResultSet rsLlamadas = prepareStat.executeQuery();
	    
		prepareStat = conn.prepareStatement(qryPagos);
		
	    ResultSet rsPagos = prepareStat.executeQuery();
	    
	    
	    try (JsonGenerator gen = Json.createGenerator(swriter)) {
			gen.writeStartObject();
			gen.writeStartArray("notifications");

		    if(rsLlamadas.first()) {
		    		//No esta vació
		    		gen.writeStartObject();
		    			gen.write("type", "call");
		    			gen.write("idStudent",0);
		    			gen.write("title","Hay llamadas de realizar");
		    			gen.write("button", "Revisar lista");
		    		gen.writeEnd();
		    		
		    }
		    
		    if(!rsPagos.isBeforeFirst()) {
		    		System.out.println("vacio");
		    }else {
		    		while(rsPagos.next()) {
		    			gen.writeStartObject();
			    			gen.write("type", "student");
			    			gen.write("idStudent",rsPagos.getInt(2));
			    			gen.write("name",rsPagos.getString(8));
			    			gen.write("lastName",rsPagos.getString(7));
			    			gen.write("nivel",rsPagos.getString(14));
			    			gen.write("startDate",rsPagos.getString(12));
			    			gen.write("title", rsPagos.getString(8)+" "+rsPagos.getString(7));
			    			gen.write("button", "Falta de pago");
			    		gen.writeEnd();
		    		}
		    }
		    
		    gen.writeEnd();
		    gen.writeEnd();
	    
	    }
	    
	    
    	
    		return swriter.toString();
    }
    
    public static String deleteNotificacion(int idAlumno) throws SQLException {
		
		String query = "DELETE FROM Notificacion WHERE fecha=CURDATE() AND tipo='pago' AND Alumno_idAlumno= ?";
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    preparedStmt.setInt(1, idAlumno);

	    preparedStmt.execute();
		
		Recepcion recep = new Recepcion();
		
		String notificaciones = recep.getNotificacion();
		
		return notificaciones;
		
    }
    
}