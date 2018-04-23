package classes;

import java.io.IOException;

import com.google.zxing.WriterException;

//import classes.Centro;
//import classes.BaseDatos;

public class pruebafecha {
	public static void main(String[] args) throws WriterException, IOException {
		
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
		//instructor.obtenerGafetesAlumnos();	
		
		Recepcion recepcion = new Recepcion();
		recepcion.obtenerAlumnosConFalta();	
		
		/*Asistente asistente = new Asistente();
		String asistentes = asistente.getAsistentes();
		System.out.println(asistentes);*/
		
	}

}
