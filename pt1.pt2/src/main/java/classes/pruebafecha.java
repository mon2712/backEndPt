package classes;

import java.io.IOException;
import java.sql.SQLException;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.RuleBase;
import org.drools.core.WorkingMemory;

import com.google.zxing.WriterException;

//import classes.Centro;
//import classes.BaseDatos;

public class pruebafecha {
	public static void main(String[] args) throws WriterException, IOException, SQLException, DroolsParserException {
		
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
		//Recepcion recepcion = new Recepcion();
		//String alumnosLlamada= recepcion.getAlumnosLlamadas();
		//System.out.print(alumnosLlamada);
		//recepcion.NotaLlamada(3, "hola", "23/04/2018");
		Auxiliar aux= new Auxiliar();
		WorkingMemory wk=aux.conexionDrools();
		aux.executeFrecInicial(wk);
		
		/*pruebaDSL prueba = new pruebaDSL();
		
        //Cargamos la base de reglas
          RuleBase ruleBase;
		try {
			ruleBase = prueba.leerReglas();
			WorkingMemory workingMemory = ruleBase.newStatefulSession();
	        prueba.executeFrecInicial(workingMemory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
          
       
		
		/*Asistente asistente = new Asistente();
		String asistentes = asistente.getAsistentes();
		System.out.println(asistentes);*/
		
	}

}
