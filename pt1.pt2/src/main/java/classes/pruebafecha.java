package classes;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.RuleBase;
import org.drools.core.WorkingMemory;
import org.json.JSONObject;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

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
		//String pagos = instructor.getPagosAlumno();
		//System.out.println(pagos);

		//Instructor instructor = new Instructor();
		//instructor.obtenerGafetesAlumnos();	
		//Recepcion recepcion = new Recepcion();
		//String alumnosLlamada= recepcion.getAlumnosLlamadas();
		//System.out.print(alumnosLlamada);
		//recepcion.NotaLlamada(3, "hola", "23/04/2018");

		Auxiliar aux= new Auxiliar();
		
		String fileRules1="../rules/proyeccionNivel.drl";
		//String fileRules2="../rules/desempeño.drl";
		String arrayJson=aux.crearJson();
		JSONObject obj = new JSONObject(arrayJson);
		WorkingMemory wk=aux.conexionDrools(fileRules1);
		JSONObject results = obj.getJSONObject("resultsTest");
		//aux.executeFrecuencias(wk);
		aux.executeFrecInicial(wk, arrayJson);/*
		//aux.exFrecuencias(wk,2);
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
		}
         /* 
		Auxiliar aux= new Auxiliar();
		
		String fileRules1="../rules/proyeccionNivel.drl";
		//String fileRules2="../rules/desempeño.drl";
		String arrayJson=aux.crearJsonRegistro();
		JSONObject obj = new JSONObject(arrayJson);
		WorkingMemory wk=aux.conexionDrools(fileRules1);
		JSONObject results = obj.getJSONObject("resultsRegistro");
		aux.executeProgramacion(wk, arrayJson);
		//aux.executeFrecuencias(wk);
		//aux.executeFrecInicial(wk, arrayJson);
		//aux.exFrecuencias(wk,2);
		System.out.println("Hola");
		/*pruebaDSL prueba = new pruebaDSL();
		
		//Asistente asistente = new Asistente();
		//String asistentes = asistente.getAsistentes();
		//System.out.println(asistentes);
		
		//ProyeccionAnual.crearProyeccionAnual(null);
		
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
        
		
		/*Registro rs= new Registro();
		rs.setUno(100);
		rs.setDos(100);
		rs.setTres(100);
		rs.setCuatro(110);
		rs.setCinco(110);
		rs.setSeis(110);
		rs.setSiete(100);
		rs.setOcho(100);
		rs.setNueve(110);
		rs.setDiez(110);
		//System.out.println(rs.getUno() + " " + rs.getDos()  + " " + rs.getTres()  + " " + rs.getCuatro() + " " + rs.getCinco() + " " +  rs.getSeis()  + " " +  rs.getSiete()  + " " +  rs.getOcho() + " " + rs.getNueve() + " " + rs.getDiez());
		Auxiliar aux=new Auxiliar();
		WorkingMemory wm=aux.conexionDrools();
		rs.setCantidadCalificaciones(rs, wm);
		System.out.println("Num 70: " +  rs.getNumSetenta() + " Num 80: " + rs.getNumOchenta() + " Num 90: " + rs.getNumNoventa() +" Num 100: " + rs.getNumCien()+ " Num flechas:" + rs.getNumFlecha() + "  Num triangulo: " + rs.getNumTriangulo());

		System.out.println("La evaluacion es: "+ rs.getEvaluacion());*/
	}

}
