package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

public class Recepcion {
	static PreparedStatement prepareStat = null;
    static Connection conn = BaseDatos.conectarBD();
    
    public static String getAlumnosLlamadas() {
    	/*PreparedStatement prepareStat = null;
        Connection conn = BaseDatos.conectarBD();
        */
		StringWriter swriter = new StringWriter();
		try {
			String getQueryStatement = "CALL alumnosConFalta();";
			CallableStatement cStmt = conn.prepareCall("{call listaLlamadas(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}");

		    prepareStat = conn.prepareStatement(getQueryStatement);
            ResultSet rs = prepareStat.executeQuery(); //devuelbe nombre, apellido, idAlumno, lunes, miercoles, jueves, sabado
            
            
            Alumno alumn= new Alumno();
            String nota= "", ultimaAsistencia="", active="", done="",fechaLlamada="";
            int estatus=0, activacion=0; 
            int[] arrayDays = new int[4];
                 
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            gen.writeStartObject();
            gen.writeStartArray("listOfCalls");
	            while(rs.next()){
	            	 
	            		List<Integer> diasQueViene = new ArrayList<Integer> ();
	
	            		System.out.println(rs.getString(1)+ " "+rs.getString(2)+ " "+rs.getInt(3)+ " "+rs.getString(4)+ " "+rs.getString(5)+ " "+rs.getString(6)+ " "+rs.getString(7)+ " "+rs.getString(8));
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
	            				//System.out.println("dia: " + arrayDays[i]+ i);
	            				if(i == 0) diasQueViene.add(2);
	            				if(i == 1) diasQueViene.add(4);
	            				if(i == 2) diasQueViene.add(5);
	            				if(i == 3) diasQueViene.add(7);
	            			}
	            			
	            		}
	            		
	            		int size=diasQueViene.size();
	            		
	            		if(size == 0) {
	            			//System.out.println("size" + diasQueViene.size());
	                		//System.out.println("El dia inmediato anterior es: " + rs.getInt(8));
	                		diaAnterior=rs.getInt(8);
	
	            		}else {
	            			//System.out.println("size" + diasQueViene.size());
	                		//System.out.println("El dia inmediato anterior es: " + diasQueViene.get(size-1));
	                		diaAnterior=diasQueViene.get(size-1);
	            		}
	            		
	            		//Ejecutando uno a uno los alumnos para ver si pertenecen a la lista dellamdas por realizar (es su segunda falta)
	            		cStmt.setInt(1, diaAnterior); //dìa anterior
	        		    cStmt.setInt(2, rs.getInt(3)); //id del alumno
	        		    //System.out.println("holi:"+rs.getInt(3) + diaAnterior);
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
	        		    System.out.println("idAlumno enviado: " +rs.getInt(3) + " idAlumno regresado: "+ cStmt.getInt(3));
	        		    if (cStmt.getInt(11) == 0){
		        		    alumn.setIdAlumno(Integer.toString(cStmt.getInt(3)));
		        		    //System.out.println("idAlumno: " + cStmt.getInt(3));
		        		    alumn.setNombre(cStmt.getString(4));
		        		    alumn.setApellidoPaterno(cStmt.getString(5));
		        		    nota=""+cStmt.getString(6);
		        		    System.out.println("nota: "+cStmt.getString(6));
		        		    fechaLlamada=""+cStmt.getString(7);
		        		    estatus=cStmt.getInt(8);
		        		    activacion=cStmt.getInt(9);
		        		    ultimaAsistencia=cStmt.getString(10);
		        		    if(estatus == 1) done="true";	
		        		    else done="false";
		        		    if(activacion == 1) active="true";	
		        		    else active="false";
		        		    
		        		    	gen.writeStartObject();
				    	            gen.write("idStudent", alumn.getIdAlumno());
				    	            gen.write("name", alumn.getNombre()+  " " + alumn.getApellidoPaterno());
				    	            gen.writeStartObject("call");
					    	            gen.write("done", done);
					    	            gen.write("active", active);
					    	            gen.write("note", nota);
					    	            gen.write("date", fechaLlamada);
					    	        gen.writeEnd();
					    	     gen.writeEnd();
			    	     }
	            	}
	            gen.writeEnd();
		        gen.writeEnd();  
            	}
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
		return swriter.toString();  
    }
    
    public static void NotaLlamada(int idAlumno, String nota, String fec) {
    	/*PreparedStatement prepareStat = null;
        Connection conn = BaseDatos.conectarBD();*/
        CallableStatement cStmt;
        /*SimpleDateFormat formatF = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatF1 = new SimpleDateFormat("dd-MM-yyyy");
		String fech = formatF.format(fec);
		java.util.Date fechaDate = null;
        try {
        	 fechaDate = formatF1.parse(fec);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        String fech = formatF.format(fechaDate);
		*/
		try {
			cStmt = conn.prepareCall("{call setNotaLlamada(?, ?, ?)}");
			cStmt.setInt(1, idAlumno);
			System.out.println("id: " +idAlumno);
		    cStmt.setString(2, nota);
		    System.out.println("nOTA: " + nota);
		    cStmt.setString(3, fec);
		    System.out.println("Fecha: " + fec);
		    cStmt.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        /*try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
    
}