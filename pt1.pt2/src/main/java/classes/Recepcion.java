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

public class Recepcion {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
    public static void obtenerAlumnosConFalta() {
    		
		//StringWriter swriter = new StringWriter();
		try {
			String getQueryStatement = "CALL alumnosConFalta();";

		    prepareStat = conn.prepareStatement(getQueryStatement);
            ResultSet rs = prepareStat.executeQuery();
            
            int[] arrayDays = new int[4];
            
            
    
            while(rs.next()){
            		List<Integer> diasQueViene = new ArrayList<Integer> ();

            		System.out.println(rs.getString(1)+ " "+rs.getString(2)+ " "+rs.getString(3)+ " "+rs.getString(4)+ " "+rs.getString(5)+ " "+rs.getString(6)+ " "+rs.getString(7)+ " "+rs.getString(8));
            		
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
            		
            		/*for(int i=0; i<diasQueViene.size(); i++) {
            			System.out.println("dia que viene "+diasQueViene.get(i));
            		}*/
            		
            		int size=diasQueViene.size();
            		
            		if(size == 0) {
            			System.out.println("size" + diasQueViene.size());
                		System.out.println("El dia inmediato anterior es: " + rs.getInt(8));
            		}else {
            			System.out.println("size" + diasQueViene.size());
                		System.out.println("El dia inmediato anterior es: " + diasQueViene.get(size-1));
            		}

            }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    }
}
