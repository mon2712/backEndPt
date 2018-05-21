package classes;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import com.google.zxing.WriterException;

//import classes.Centro;
//import classes.BaseDatos;

public class pruebafecha {
	public static void main(String[] args) throws WriterException, IOException, SQLException {
		
		//BaseDatos dataDB = new BaseDatos();
		//Centro centro= new Centro();
		
		//String students=centro.getEstatusCentro();
		//System.out.println(students);

		//String Students=centro.getStudents(" ");
		//System.out.print(Students);

		//String Students=centro.getStudents("JOSE");
		//System.out.print(Students);
		
		/*Alumno alumno = new Alumno();
		String Alumnos=alumno.obtenerFichaAlumno(3);
		System.out.println(Alumnos);*/

		//Alumno alumno = new Alumno();
		/*String alumnitos=alumno.getAlumnos("je");
		System.out.println(alumnitos);*/
		
		//alumno.getAlumnosNuevos();
		

		//Instructor instructor = new Instructor();
		//String pagos = instructor.getPagosAlumno();
		//System.out.println(pagos);

		//Instructor instructor = new Instructor();
		//instructor.obtenerGafetesAlumnos();	
		//Recepcion recepcion = new Recepcion();
		//String alumnosLlamada= recepcion.getAlumnosLlamadas();
		//System.out.print(alumnosLlamada);
		//recepcion.NotaLlamada(3, "hola", "23/04/2018");

		
		//Asistente asistente = new Asistente();
		//String asistentes = asistente.getAsistentes();
		//System.out.println(asistentes);
		
		
		
		/*StringWriter swriter = new StringWriter();
		try(JsonGenerator gen = Json.createGenerator(swriter)){
			gen.writeStartObject();
			gen.writeStartObject("infoForm");
			
			gen.writeStartArray("allAssistants");
            		gen.writeStartObject();
            			gen.write("name", "hola");
            			gen.write("idAssistant", "hola");
            		gen.writeEnd();
            		gen.writeStartObject();
	        			gen.write("name", "hola");
	        			gen.write("idAssistant", "hola");
	        		gen.writeEnd();
            	gen.writeEnd();
            	
            	gen.writeStartArray("allAssistants2");
	        		gen.writeStartObject();
	        			gen.write("name", "hola");
	        			gen.write("idAssistant", "hola");
	        		gen.writeEnd();
	        		gen.writeStartObject();
	        			gen.write("name", "hola");
	        			gen.write("idAssistant", "hola");
	        		gen.writeEnd();
	        	gen.writeEnd();
            	
            gen.writeEnd();
            gen.writeEnd();
		}*/
        
		
	}

}
