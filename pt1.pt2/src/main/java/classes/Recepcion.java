package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

public class Recepcion {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
    public static String obtenerAlumnosConFalta() {
    		
		StringWriter swriter = new StringWriter();
		try {
			String getQueryStatement = "CALL alumnosConFalta();";
			CallableStatement cStmt = conn.prepareCall("{call listaLlamadas(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

		    prepareStat = conn.prepareStatement(getQueryStatement);
            ResultSet rs = prepareStat.executeQuery();
            
            Alumno alumn= new Alumno();
            String nota= "", ultimaAsistencia="", active="", done="" ;
			String fechaLlamada="";
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
	            				System.out.println("dia: " + arrayDays[i]+ i);
	            				if(i == 0) diasQueViene.add(2);
	            				if(i == 1) diasQueViene.add(4);
	            				if(i == 2) diasQueViene.add(5);
	            				if(i == 3) diasQueViene.add(7);
	            			}
	            			
	            		}
	            		
	            		int size=diasQueViene.size();
	            		
	            		if(size == 0) {
	            			System.out.println("size" + diasQueViene.size());
	                		System.out.println("El dia inmediato anterior es: " + rs.getInt(8));
	                		diaAnterior=rs.getInt(8);
	
	            		}else {
	            			System.out.println("size" + diasQueViene.size());
	                		System.out.println("El dia inmediato anterior es: " + diasQueViene.get(size-1));
	                		diaAnterior=diasQueViene.get(size-1);
	            		}
	            		cStmt.setInt(1, 7);
	        		    cStmt.setInt(2, 5);
	        		    System.out.println("holi:"+rs.getInt(3) + diaAnterior);
	        		    cStmt.registerOutParameter(3, Types.INTEGER);
	        		    cStmt.registerOutParameter(4, Types.VARCHAR);
	        		    cStmt.registerOutParameter(5, Types.VARCHAR);
	        		    cStmt.registerOutParameter(6, Types.VARCHAR);
	        		    cStmt.registerOutParameter(7, Types.DATE);
	        		    cStmt.registerOutParameter(8, Types.INTEGER);
	        		    cStmt.registerOutParameter(9, Types.INTEGER);
	        		    cStmt.registerOutParameter(10, Types.DATE);
	        		    
	        		    
	        		    cStmt.execute();    
	        		    System.out.println("idAlumno: " + cStmt.getString(3));
	        		    
	        		    alumn.setIdAlumno(Integer.toString(cStmt.getInt(3)));
	        		    System.out.println("idAlumno: " + cStmt.getInt(3));
	        		    alumn.setNombre(cStmt.getString(4));
	        		    alumn.setApellidoPaterno(cStmt.getString(5));
	        		    nota=""+cStmt.getString(6);
	        		    fechaLlamada=""+cStmt.getString(7);
	        		    estatus=cStmt.getInt(8);
	        		    activacion=cStmt.getInt(9);
	        		    ultimaAsistencia=cStmt.getString(10);
	        		    if(estatus == 1) done="true";	
	        		    else done="false";
	        		    
	        		    if(activacion == 1) active="true";	
	        		    else active="false";
	        		    
	        		    	gen.writeStartObject();
			    	            gen.write("idStudent", cStmt.getInt(3));
			    	            gen.write("name", cStmt.getString(4) +  " " +cStmt.getString(5));
			    	            gen.writeStartObject("call");
				    	            gen.write("done", done);
				    	            gen.write("active", active);
				    	            gen.write("note", nota);
				    	            gen.write("date", fechaLlamada);
				    	        gen.writeEnd();
				    	     gen.writeEnd();
		    	        }
	            gen.writeEnd();
		        gen.writeEnd();
		        
            }   
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return swriter.toString();  
    }
    
    public static void prueba() {
    	
    	try {
			CallableStatement cStmt = conn.prepareCall("{call listaLlamadas(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cStmt.setInt(1, 7);
		    cStmt.setInt(2, 5);
		    //System.out.println("holi:"+rs.getInt(3) + diaAnterior);
		    cStmt.registerOutParameter(3, Types.INTEGER);
		    cStmt.registerOutParameter(4, Types.VARCHAR);
		    cStmt.registerOutParameter(5, Types.VARCHAR);
		    cStmt.registerOutParameter(6, Types.VARCHAR);
		    cStmt.registerOutParameter(7, Types.DATE);
		    cStmt.registerOutParameter(8, Types.INTEGER);
		    cStmt.registerOutParameter(9, Types.INTEGER);
		    cStmt.registerOutParameter(10, Types.DATE);
		    
		    
		    cStmt.execute();    
		    System.out.println("idAlumno: " + cStmt.getInt(9));
		    
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    } 
}