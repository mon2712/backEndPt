package classes;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kie.internal.KnowledgeBaseFactory;

public class Auxiliar {
	
	static PreparedStatement prepareStat = null;
    static Connection conn = BaseDatos.conectarBD();
    
	public float frecuenciaInicial;
	public String nivelInicial;
	public String[] respuestasUbicacion;
	public int desempeñoGral;
	public int contadorMala=0;
	//public String[] respuestasRegistro;
	//public int[] calificaciones;
	//public String[] nivelUbicacion;
	//public int[]calificacionUbicacion;
	//public String[] nivelesProyeccion;
	//public float[] frecProyeccion;
	/*public static void conexionDrools()throws DroolsParserException,
		IOException {
		Auxiliar droolsTest = new Auxiliar();
		droolsTest.executeDrools();
	}*/
		
	public WorkingMemory conexionDrools(String ruleFile) throws DroolsParserException, IOException {

		PackageBuilder pB = new PackageBuilder();
		//String ruleFile = "../rules/proyeccionNivel.drl";
		//String ruleFile = "../rules/registro_progDiaria.drl";
		InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);
		Reader reader = new InputStreamReader(resourceAsStream);
		pB.addPackageFromDrl(reader);
		org.drools.core.rule.Package rulesPackage = pB.getPackage();
		RuleBase rB = RuleBaseFactory.newRuleBase();
		rB.addPackage(rulesPackage);

		WorkingMemory workingMemory = rB.newStatefulSession();
		
		return workingMemory;
	}
	
	public void exFrecuencias(WorkingMemory workingMemory,ProyeccionNivel pn, String desempeñosNivel[], String n[]) throws SQLException {
		//Nueva estructuracion
		Nivel nivelActual = new Nivel();
		String nombreNivel=pn.getAlumno().getNivelActual(); 
		nivelActual.setearValores(nombreNivel, conn);
		int nivelUbicacion=nivelActual.getIdNivel();
		System.out.println();
        System.out.println("______________________________________________________________");
		int dimension = 6 - nivelUbicacion+1;
		
		Nivel[] niveles = new Nivel[dimension];
		double frecInicial = pn.getFrecuenciaEstudio();
		String inciso = pn.getFrecuenciaInciso();
		
		//para obtener las frecuencias de estudio de cada nivel
		for(int x =nivelUbicacion; x<=6; x++) {
	        niveles[x-nivelUbicacion] = new Nivel();
	        niveles[x-nivelUbicacion].setNombreNivel(n[x-nivelUbicacion]);
	        niveles[x-nivelUbicacion].setearValores(n[x-nivelUbicacion], conn);
			niveles[x-nivelUbicacion].setDesempeñoGeneral(pn.getAlumno().getDesempeño());
			niveles[x-nivelUbicacion].setDesempeñoNivel(desempeñosNivel[x-nivelUbicacion]);
			
			workingMemory.insert(niveles[x-nivelUbicacion]);
			workingMemory.fireAllRules();
	        System.out.println("Nombre nivel "+ niveles[x-nivelUbicacion].getNombreNivel());
		}
		
		
		
		
		
		//Vieja estructuracion
		/*String getQueryStatementN ="Select idNivel from Nivel where nombre='"+ pn.getAlumno().getNivelActual()+"';";
        prepareStat = conn.prepareStatement(getQueryStatementN);
        ResultSet rsN = prepareStat.executeQuery();
       // System.out.println(rsN.getString(0));
        //System.out.println("La frecuencia del nivel: " + pn.getAlumno().getNivelActual() + " es: " + pn.getFrecuenciaEstudio() + " " + pn.getFrecuenciaInciso() );
        int nivelUbicacion = 0;
        while(rsN.next()) {
        	nivelUbicacion = rsN.getInt(1);
        }
        System.out.println();
        System.out.println("______________________________________________________________");
		int dimension = 6 - nivelUbicacion+1;
		Nivel[] niveles = new Nivel[dimension];
		double frecInicial = pn.getFrecuenciaEstudio();
		String inciso = pn.getFrecuenciaInciso();

		//System.out.println("Nivel inicial: "+ nivelUbicacion);
		//System.out.println("Frecuencia inicial: " + frecInicial+ inciso );
		
		//List<Double> Frec=new ArrayList<Double>();
		//para obtenr las frecuencis de estudio de cada nivel
		for(int x =nivelUbicacion; x<=6; x++) {
			List<Double> Frec=new ArrayList<Double>();
			List<String> Incisos=new ArrayList<String>();
			String getQueryStatement = " ";
			ResultSet rs;
			getQueryStatement = "SELECT frecuenciaEstudio,tipo FROM ProyeccionNivel where Nivel_idNivel="+Integer.toString(x)+" order by frecuenciaEstudio;";
	        prepareStat = conn.prepareStatement(getQueryStatement);
	        rs = prepareStat.executeQuery();
			//int i=0;
			
	        while(rs.next()) {
	        	Frec.add(rs.getDouble(1));
	        	Incisos.add(rs.getString(2));
	        	//i++;
	        }
	        

	        niveles[x-nivelUbicacion] = new Nivel();

			niveles[x-nivelUbicacion].setIdNivel(x);
			niveles[x-nivelUbicacion].setNombreNivel(n[x-nivelUbicacion]);
			niveles[x-nivelUbicacion].setFrecuencias(Frec);
			niveles[x-nivelUbicacion].setTipos(Incisos);
			niveles[x-nivelUbicacion].setDesempeñoGeneral(pn.getAlumno().getDesempeño());
			niveles[x-nivelUbicacion].setDesempeñoNivel(desempeñosNivel[x-nivelUbicacion]);

			workingMemory.insert(niveles[x-nivelUbicacion]);
			workingMemory.fireAllRules();

	        

		}*/
		//double []frecProy = new double [6-nivelUbicacion+1];
		String []frecProy = new String [6-nivelUbicacion+1];

		String []frecProyTipo = new String [6-nivelUbicacion+1];
		int size, frecOriginal, s=0, indiceActual=0, mov=0, totalF=0, err;
		String msn;
		
		for(int x=0;x<=(6-nivelUbicacion);x++) { //nivel
			size=niveles[x].getFrecuencias().size();
			if(x==0) {
				indiceActual=niveles[x].getFrecuencias().indexOf(frecInicial);
				if(indiceActual == -1) {
					//System.out.println("La frecuencia no existe");
					break;
				}
	
			}
			else {
				
				indiceActual = indiceActual+ niveles[x].getDesempeño();
				//System.out.println("Está en el indice: " + indiceActual);
				//System.out.println("Avanzaré " + (niveles[x].getDesempeño()) + " Indices");
				totalF = niveles[x].getFrecuencias().size()-1;
				//System.out.println("El numero de frecuencias de este nivel es: " + totalF);
				if(indiceActual > totalF){
					indiceActual = totalF;
					//System.out.println("No se puede avanzar mas, nos vamos ala ultima freceuncia: " + indiceActual);
				}
				//frecProy[x] = niveles[x].getFrecuencias().get(indiceActual);
			}
			//System.out.println("Frecuencia: "+niveles[x].getFrecuencias().get(indiceActual) + " x: " + x);
			frecProy[x]= niveles[x].getFrecuencias().get(indiceActual).toString();
			frecProyTipo[x]= niveles[x].getTipos().get(indiceActual);
		  }
		int idAl=Integer.parseInt(pn.getAlumno().getIdAlumno());
		for(int x=0; x<6-nivelUbicacion+1; x++) {
			System.out.println("Frecuencia " + (x+1) + " : " + frecProy[x] + " " + frecProyTipo[x] + " " + niveles[x].getNombreNivel());
				CallableStatement cS = conn.prepareCall("{call setProyeccionAnual(?, ?, ?, ?, ?, ?)}");
				
			 	
				cS.setInt(1, idAl);
			 	cS.setString(2, frecProy[x]);
			 	cS.setString(3, niveles[x].getNombreNivel());
			 	cS.setString(4, frecProyTipo[x] );
			 	cS.registerOutParameter(5, Types.INTEGER);
			 	cS.registerOutParameter(6, Types.VARCHAR);
			 	cS.execute();
			 	
		 	if(cS.getInt(5)==1 && x==0) {
		 		System.out.println("Error: " + cS.getInt(5) + " " + cS.getString(6) );
		 		break;
		 	}
		 	else { System.out.println("Entro al else de proyeccion anual");
		 		
		 		System.out.println("Frecuencia " + (x+1) + " : " + frecProy[x] + " " + frecProyTipo[x] + " " + niveles[x].getNombreNivel());
				CallableStatement cS2 = conn.prepareCall("{call setProyeccionNivelInProyeccionAnual(?, ?, ?, ?, ?, ?)}");
				cS2.setInt(1, idAl);
			 	cS2.setString(2,frecProy[x]);
			 	//(2, String.valueOf(frecProy[x]));
			 	cS2.setString(3, niveles[x].getNombreNivel());
			 	cS2.setString(4, frecProyTipo[x] );
			 	cS2.registerOutParameter(5, Types.INTEGER);
			 	cS2.registerOutParameter(6, Types.VARCHAR);
			 	cS2.execute();
			 	System.out.println("Error: " + cS2.getInt(5) + " " + cS2.getString(6));
		 	}
		 	
			
			//String insertProyeccionPS="call setProyeccionAnual(4,"+ frecProy[x] +"," + niveles[x].getNombreNivel() + "," + frecProyTipo[x] + ", @mensaje, @err);";
		}
	}

	
	public void executeFrecInicial(WorkingMemory workingMemory, String array) {
		
		JSONObject obj = new JSONObject(array);
		
		JSONObject results = obj.getJSONObject("resultsTest");
		
			JSONObject infoStudent = results.getJSONObject("infoStudent");
			JSONArray exams = results.getJSONArray("exams");
			JSONArray frecuenciaIncial = results.getJSONArray("startPoint");
			String n;
			
			//---------------Impresion de datos obtenidos del Json-------------
			//System.out.println("puntajeDesempeño " + desempeñoGeneral);
			System.out.println("examenes " + exams);
			System.out.println("infoStudent" +infoStudent.toString());
			System.out.println("para frecuencia inicial" + frecuenciaIncial.toString());
			System.out.println("nivel" + infoStudent.getString("level"));
			//-----------------------------------------------------------------
			
			n=infoStudent.getString("level"); //obtención del nivel
			String g=infoStudent.getString("grade"); //obtención del grado
			String idStudent=infoStudent.getString("idStudent");  //obtención del id
			int desempeñoGeneral = results.getInt("finalScore");  //obtención del desempeño general
			Alumno alumn = new Alumno();
			//dando atributos al alumno
			alumn.setPuntajeDesempeño(desempeñoGeneral);
			//workingMemory.insert(alumn);
			//workingMemory.fireAllRules();
			//System.out.println("Desempeño general: " +  alumn.getDesempeño());
			alumn.setNivelActual(n);
			alumn.setGrado(g);
			alumn.setIdAlumno(idStudent);
			String id;
			//String desempeñosNivel[] = new String [exams.length()];
			
			//checar
			String desNivel[] = new String [exams.length()];
			String [] level = new String [exams.length()];
			//List<Double> Frec=new ArrayList<Double>();
			//System.out.println("Longitud de examenes: "+exams.length());
			
			//obtencion de desempeño por nivel
			System.out.println("_______________________________________________");
			System.out.println("Exámenes que realizó: ");
			for(int i=0; i<exams.length();i++) {
				JSONObject res = (JSONObject) exams.get(i);

				desNivel[i]=res.getString("desempeño");
				level[i] = res.getString("level");
				System.out.println("Indice de Nivel: " + i + " nivel: " + level[i] );
			//cambio de dev checar
			/*
				if(res.getString("level").compareTo("E")!=0 && res.getString("level").compareTo("F")!=0 ) {
					desNivel[i]=res.getString("desempeño");
					level[i] = res.getString("level");
					System.out.println("Nivel: "+ level[i]);
				}*/
			}
			System.out.println("_______________________________________________");
			//buscando el indice del nivel de ubicación
			int newIndice=indexOfIntArray(level,n);
			

			//int newIndice=Arrays.binarySearch(level, n);
			//System.out.println("New indice: " + newIndice);
			//String desempeñosNivel[] = new String [exams.length()- newIndice];
			//String nivel[] = new String [exams.length()- newIndice];
			//String desempeñosNivel[]=Arrays.copyOf(desNivel, newIndice);
			//String nivel[]=Arrays.copyOf(level, newIndice);
			//System.out.println("Examenes desempeños:  "+ Arrays.asList(desempeñosNivel) + " Niveles: " + Arrays.asList(nivel));

		//cambio de dev checar si va	
			//buscando indie de nivel final (D)
			String fin="D";
			int newIndice2=indexOfIntArray(level,fin);
		/////
			
			
			//System.out .println("Tamaño del array de desempeños: "+ desNivel.length + " New indice: " + newIndice + " tamaño de array examenes" + exams.length());
			
			//acotando los desempeños de nivel a partir del nivel deubicación
			System.out.println("Nivel ubicacion: " + n + " Indice de incicio: "+ newIndice);
			System.out.println("Nivel final: " + fin + " Indice de fin: "+ newIndice2);
			String desempeñosNivel[]=Arrays.copyOfRange(desNivel, newIndice, newIndice2+1);
			String nivel[]=Arrays.copyOfRange(level, newIndice, newIndice2+1);
			System.out.println("Examenes desempeños:  "+ Arrays.asList(desempeñosNivel) + " Niveles: " + Arrays.asList(nivel));
			
			//Obtención de variables a tomar en cuenta ne el árbol de decisión de la freceuncia incial
			for(int i=0; i<frecuenciaIncial.length();i++) {
				JSONObject res = (JSONObject) frecuenciaIncial.get(i);
				id=res.getString("identificador");
			
				switch (id) {
					case "fluidez":
						alumn.setFluidez(res.getInt("answer"));
					break;
					case "concentracion":
						alumn.setConcentracion(res.getInt("answer"));
					break;
					case "antysuc":
						alumn.setAnteSuc(res.getInt("answer"));
					break;
					case "cuenta":
						alumn.setCuenta(res.getInt("answer"));
					break;
					case "trabajoB":
						alumn.setTrabajoB(res.getInt("answer"));
					break;
					case "procedimiento":
						alumn.setProcedimientosCortos(res.getInt("answer"));
					break;
					case "calculo":
						alumn.setCalculoMental(res.getInt("answer"));
					break;
					case "division":
						alumn.setDivisionConResta(res.getInt("answer"));
					break;
					case "simplifica":
						alumn.setSimplificacion(res.getInt("answer"));
					break;
				}
				System.out.println("Id: " + id + " Answer: " + res.getInt("answer"));
			}
		
		ProyeccionNivel pn = new ProyeccionNivel();
		pn.setAlumno(alumn);
		workingMemory.insert(pn);
		workingMemory.insert(alumn);
		workingMemory.fireAllRules();
		try {
			this.exFrecuencias(workingMemory,pn, desempeñosNivel, nivel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	//PARA COMBINACIONES
	public void executeProgramacion(WorkingMemory workingMemory, String array) {
		
		
		
		JSONObject obj = new JSONObject(array);
		
		JSONObject results = obj.getJSONObject("resultsRegistro");
		
			JSONObject infoStudent = results.getJSONObject("infoStudent");

			JSONArray cal = results.getJSONArray("calificaciones");
			int time = results.getInt("time");
			String n;
			/*System.out.println("tiempo " + time);
			System.out.println("calificaciones " + cal );
			System.out.println("infoStudent" +infoStudent.toString());
			//System.out.println("para frecuencia inicial" + frecuenciaIncial.toString());
			System.out.println("nivel " + infoStudent.getString("level"));
			*/
			System.out.println("nivel " + infoStudent.getString("level"));
			n=infoStudent.getString("level");
			String g=infoStudent.getString("grade");
			String idStudent=infoStudent.getString("idStudent");
			Alumno alumn = new Alumno();
			Registro reg =  new Registro();
			Nivel niv= new Nivel();
			//alumn.setPuntajeDesempeño(desempeñoGeneral);
		//workingMemory.insert(alumn);
		//workingMemory.fireAllRules();
			//System.out.println("Desempeño general: " +  alumn.getDesempeño());
			alumn.setNivelActual(n);
			alumn.setGrado(g);
			alumn.setIdAlumno(idStudent);
			String id;
			reg.setIdAlumno(Integer.parseInt(alumn.getIdAlumno()));
			int calif[] = new  int [10];
			for(int i=0; i<10;i++) {
				JSONObject res = (JSONObject) cal.get(i);
				calif[i]=res.getInt("calif");
				//System.out.println("Hoja: " + (i+1) + " calficacion: " + calif[i]);
			}
			reg.setCalificaciones(calif);
			reg.setCantidadCalificaciones();
			String ev="Mala";
			workingMemory.insert(reg);
			workingMemory.fireAllRules();
			if (reg.getEvaluacion()=="Excelente") {	
			//	contadorMala++;
				System.out.print(
						" Num100: " + reg.getNumCien() +
						" Num90: " + reg.getNumNoventa() +
						" Num80: " + reg.getNumOchenta() +
						" Num70: " + reg.getNumSetenta() +
						" NumTriangulo: " + reg.getNumTriangulo() +
						" NumFlecha: " + reg.getNumFlecha() );
				
				System.out.println(" Evaluacion: " +reg.getEvaluacion());
				//System.out.println(reg.getAccion());
			//workingMemory.insert(pn);
			//workingMemory.fireAllRules();
				//System.out.println("Evaluaciones malas: " + contadorMala);
			}
			//System.out.println("Evalucacion:  " + reg.getEvaluacion());
			
	}
	//PARA UN SOLO REGISTRO
	public void executeProg(WorkingMemory workingMemory, WorkingMemory workingMemory2, String array) throws SQLException {
		int entendimiento=0;
		JSONObject obj = new JSONObject(array);
		//leer el json
		JSONObject results = obj.getJSONObject("resultsRegistro");
		
			JSONObject infoStudent = results.getJSONObject("infoStudent");

			JSONArray cal = results.getJSONArray("calificaciones");
			int time = results.getInt("time"), today=0, lunes=0, miercoles=0, jueves=0, sabado=0;
			
			System.out.println("tiempo " + time);
			System.out.println("calificaciones " + cal );
			System.out.println("infoStudent" +infoStudent.toString());
			//System.out.println("para frecuencia inicial" + frecuenciaIncial.toString());
			System.out.println("nivel " + infoStudent.getString("level"));
			//obtener informacion del alumno
			String n=infoStudent.getString("level");
			String g=infoStudent.getString("grade");
			String idStudent=infoStudent.getString("idStudent");
			
			//objeto de la clase alumno, registro & nivel
			Alumno alumn = new Alumno();
			Registro reg =  new Registro();
			Nivel niv =  new Nivel();

			reg.setTiempo(time);
			
				niv.setearValores(n, conn); //inicializando valores del objeto nivel
				reg.setNivel(niv); //asociandolo al objeto registro
				System.out.println("Tiempo maximo del nivel: " + reg.getNivel().getMaxTime()*10);
			
			//alumn.setPuntajeDesempeño(desempeñoGeneral);
		//workingMemory.insert(alumn);
		//workingMemory.fireAllRules();
			//System.out.println("Desempeño general: " +  alumn.getDesempeño());
			
			//seteando valores del objeto alumno
			alumn.setNivelActual(n);
			alumn.setGrado(g);
			alumn.setIdAlumno(idStudent);
			
			//seteando valores del objeto registro, nivel, frecuencia y tipo
			try {
				reg.setearValores(Integer.parseInt(alumn.getIdAlumno()), n, conn); //revisar si se debe mandar el nivel
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			reg.setIdAlumno(Integer.parseInt(alumn.getIdAlumno())); //esto deberia estar asociado al objeto alumno
			int calif[] = new  int [10];
			//guardando las 10 calificaciones en un arreglo
			for(int i=0; i<10;i++) {
				JSONObject res = (JSONObject) cal.get(i);
				calif[i]=res.getInt("calif");
				//System.out.println("Hoja: " + (i+1) + " calficacion: " + calif[i]);
			}
			//seteando calificaciones del registro
			reg.setCalificaciones(calif);
			reg.setCantidadCalificaciones();
			//if (reg.getEvaluacion() == "Buena") {	
			//reg.setEvaluacion("Excelente");
			
			//lanzando las reglas para clasificar la evaluacion por la cantidad de calificaciones
			workingMemory.insert(reg);
			workingMemory.fireAllRules();
			
				contadorMala++;
				
				System.out.print(
				" Num100:" + reg.getNumCien() +
				" Num90:" + reg.getNumNoventa() +
				" Num80:" + reg.getNumOchenta() +
				" Num70:" + reg.getNumSetenta() +
				" NumTriangulo:" + reg.getNumTriangulo() +
				" NumFlecha:" + reg.getNumFlecha() + " Evaluacion:" + reg.getEvaluacion());
					
					System.out.println(" Frecuencia y tipo actual: "+ reg.getFrec() + " "+ reg.getTipoFrec());
					//inidice temporal de la frecuencia actual
					int indexF=reg.getNivel().getFrecuencias().indexOf(reg.getFrec());
					
					//verificando que para ese indice coincida la frecuencia y el tipo, si no va aumentando
					if(!reg.getNivel().getTipos().get(indexF).equals(reg.getTipoFrec()))  {
						while(!reg.getNivel().getTipos().get(indexF).equals(reg.getTipoFrec())) {
							//System.out.println("No " + reg.getNivel().getTipos().get(indexF));
							indexF=indexF+1;
						}
					}
					//seteando el inidice
					reg.setIndice(indexF);
				//System.out.println("Frecuencias: " + reg.getNivel().getFrecuencias() + "Tipos: " + reg.getNivel().getTipos());
				System.out.println("");
				System.out.println("Indice actual:"+ reg.getIndice());
				//lanzando las reglas para cambio de frecuencia y obtención del índice
				workingMemory2.insert(reg);
				workingMemory2.fireAllRules();
				
				
				System.out.println("Accion: " + reg.getAccion());
				System.out.println("Proximo indice:"+ reg.getIndice());
				
				////////Obtener día siguiente que le corresponde venir al alumno
				String getQueryStatementN ="SELECT lunes, miercoles, jueves, sabado,  dayofweek(CURDATE()) from Alumno WHERE idAlumno="+alumn.getIdAlumno()+";";
		        prepareStat = conn.prepareStatement(getQueryStatementN);
		        ResultSet rsN = prepareStat.executeQuery();
		        while(rsN.next()) {
		        	
			        today=rsN.getInt(5);
					
					lunes = rsN.getInt(1); //lunes
					miercoles = rsN.getInt(2); //miercoles
					jueves = rsN.getInt(3); //jueves
					sabado = rsN.getInt(4); //sabado
		        }

					//int diaAnterior  =alumn.getDiaInmediato(/*alumn.getIdAlumno()*/"anterior",1,1,1,1,7);
					int diaSiguiente=alumn.getDiaInmediato("siguiente",lunes,miercoles,jueves,sabado,today);
					//System.out.println("Dia anterior: " + diaAnterior);
					System.out.println("Dia proximo: " + diaSiguiente);
					
					//obtener todas las programaciones que ha tenido de ese nivel
				    /*getQueryStatementN =" SELECT Nivel_idNivel, ProgramacionDiaria_frecuencia, ProgramacionDiaria_idProgramacionDiaria \r\n" + 
				    		" FROM Nivel_has_ProgramacionDiaria WHERE Alumno_idAlumno="+alumn.getIdAlumno()+ " AND Nivel_idNivel="+reg.getNivel().getIdNivel()+
				    		" GROUP BY Nivel_idNivel, ProgramacionDiaria_frecuencia, ProgramacionDiaria_idProgramacionDiaria;";
				    */
					getQueryStatementN =" SELECT Nivel_idNivel, ProgramacionDiaria_frecuencia, ProgramacionDiaria_idProgramacionDiaria, numSecuencia \r\n" + 
				    		" FROM Nivel_has_ProgramacionDiaria WHERE Alumno_idAlumno="+alumn.getIdAlumno()+ " AND Nivel_idNivel="+reg.getNivel().getIdNivel()+
				    		" ORDER BY numSecuencia DESC;";

				    
				    prepareStat = conn.prepareStatement(getQueryStatementN);
				    rsN = prepareStat.executeQuery();
			        int contadorCambiosFrec=0, frecAnterior=0;
			        
			        while(rsN.next()) {
			        	
			        	//System.out.println(rsN.getInt(1));
			        	if(frecAnterior!=0) {//Si no es la primera o unica frecuencia de ese nivel 
			        		if(frecAnterior!=rsN.getInt(3)) {
			        			System.out.println("idFecuencia " + rsN.getInt(3));
				        		if(rsN.getInt(3)<frecAnterior) { //si cambio a una frecuencia mayor
					        		System.out.println("Cambio a una frecuencia mayor");
				        			contadorCambiosFrec++;
				        			if(contadorCambiosFrec==2) {
				        				break;
				        			}
				        		}
				        		else {//si no cambio a una frecuencia mayor
	
					        		System.out.println("El contador se reestableció");
				        			contadorCambiosFrec=0; //se reestablece el contador
				        			break;
				        		}
			        		}
			        	}
			        	else {
		        			System.out.println("idFecuencia " + rsN.getInt(3));
		        		}
			        	frecAnterior=rsN.getInt(3);// guardamos la frecuenncia en la variable auxiliar
			        }
			        System.out.println("Se cambió " + contadorCambiosFrec + " veces de frecuencia");
			        //if(reg.getAccion()=="AUMENTA") {  
			        String mensajeError="";
			        int err=0;
			        int diaSiguiente2=0;
			        String tipo="";
//________________________________//REPITE BLOQUE
				        if(reg.getAccion()=="AUMENTA" && contadorCambiosFrec>=2) { //repeticion de bloque
				        	//Obtener nuemero de bloque al que pertenece el set actual
				        	CallableStatement cS = conn.prepareCall("{call getBloque(?,?,?,?,?,?)}");
					 		
					 		cS.setInt(1, Integer.parseInt(alumn.getIdAlumno()));
						 	cS.setInt(2, reg.getNivel().getIdNivel());
						 	cS.registerOutParameter(3, Types.INTEGER);
						 	cS.registerOutParameter(4, Types.VARCHAR);//obtener el tipo
						 	cS.registerOutParameter(5, Types.INTEGER);
						 	cS.registerOutParameter(6, Types.INTEGER);
						 	
						 	cS.execute();
				        	
				        	
				        	System.out.println("Repaso lineal (sin repeticiones) del bloque anterior al que se encuentra actualmente.");
				        	//Numero de veces que ha repetido el bloque
				        	getQueryStatementN="SELECT count(*) \r\n" + 
				        			"		FROM Registro  join  `Set` on Set_idSet=idSet WHERE  Registro.Alumno_idAlumno="+alumn.getIdAlumno()+" AND `Set`.Nivel_idNivel="+reg.getNivel().getIdNivel()+" AND Registro.tipo='"+ cS.getString(4)+"';";
				        	
				        	prepareStat = conn.prepareStatement(getQueryStatementN);
						     rsN = prepareStat.executeQuery();
						     
				        	 while(rsN.next()) {
				   //________________________________//REPETICION DEPENDIENDO DEL NIVEL
								 	if(rsN.getInt(1)>=2) { //se ha repetido dos veces el bloque
								 		System.out.println("Se debe hacer repetición dependiendo el nivel");
								 		tipo="NIVEL";
								 		CallableStatement cS3 = conn.prepareCall("{call getRepeticionNivel(?,?,?,?,?,?,?)}");
								 		//call getRepeticionNivel(7,4, @nivel, @setInic , @tipoRe, @nSets, @dia);
								 		cS3.setInt(1, Integer.parseInt(alumn.getIdAlumno()));
									 	cS3.setInt(2, reg.getNivel().getIdNivel());
									 	cS3.registerOutParameter(3, Types.INTEGER);
									 	cS3.registerOutParameter(4, Types.INTEGER);
									 	cS3.registerOutParameter(5, Types.VARCHAR);
									 	cS3.registerOutParameter(6, Types.INTEGER);
									 	cS3.registerOutParameter(7, Types.VARCHAR);
									 	
									 	cS3.execute();
								 		
									 	int diaSiguienteAux=cS3.getInt(7);
									    diaSiguiente2=Alumno.getDiaInmediato("siguiente",lunes,miercoles,jueves,sabado,diaSiguienteAux);

									 	System.out.println("DiaSiguiente2: "+ diaSiguiente2);
									 	if(entendimiento==0) {
									 		CallableStatement cS2 = conn.prepareCall("{call setRepeticionBloque(?,?,?,?,?,?,?,?,?)}");
									 		
									 		//call setRepeticionBloque(7,4, 1.4, "a", 5, 4, "BLOQUE", @e, @msgErr);
									 		
									 		cS2.setInt(1, Integer.parseInt(alumn.getIdAlumno()));
										 	cS2.setInt(2, reg.getNivel().getIdNivel());
										 	cS2.setString(3, String.valueOf(reg.getNivel().getFrecuencias().get(reg.getIndice()-1))); //FRECUENCIA NO AUMENTA POR QUE REPITE EL BLOQUE
										 	cS2.setString(4, String.valueOf(reg.getNivel().getTipos().get(reg.getIndice()-1)));//TIPO
										 	cS2.setInt(5, diaSiguiente); //DIA SIGUIENTE
										 	cS2.setInt(6, diaSiguiente2); //PROXIMO DIA SI EL NUMERO DE SETS DE BLOQUE EXCEDE A SU DIA SIGUINETE
										 	cS2.setString(7, tipo);
										 	cS2.registerOutParameter(8, Types.INTEGER);
										 	cS2.registerOutParameter(9, Types.VARCHAR);
										 	System.out.println("Parametros: "+alumn.getIdAlumno() +", "+ reg.getNivel().getIdNivel()+", "+ reg.getNivel().getFrecuencias().get(reg.getIndice()-1)+", "+reg.getNivel().getTipos().get(reg.getIndice()-1)+", "+diaSiguiente+", "+diaSiguiente2+", "+tipo);
										 	
										 	cS2.execute();
										 	
										 	err=cS2.getInt(8);
										 	mensajeError=cS2.getString(9);
									 		/*7,4, 1.5, "a", dayofweek(curdate()),4, 4, "BLOQUE", @e, @msgErr*/
									 	}else {
									 		CallableStatement cS2 = conn.prepareCall("{call setProgramacionDiaria(?,?,?,?,?,?,?,?,?,?,?,?)}");
										 	
											cS2.setInt(1, Integer.parseInt(alumn.getIdAlumno()));
										 	cS2.setInt(2, reg.getNivel().getIdNivel());
										 	cS2.setString(3, String.valueOf(reg.getNivel().getFrecuencias().get(reg.getIndice()))); //FRECUENCIA
										 	cS2.setString(4, String.valueOf(reg.getNivel().getTipos().get(reg.getIndice())));//TIPO
										 	cS2.setString(5, "CU");
										 	cS2.setInt(6, 0);
										 	cS2.setInt(7, diaSiguiente); //proximo día
										 	cS2.setString(8, "NORMAL");
										 	cS2.setString(9, "C");
										 	cS2.setInt(10, 0);
										 	cS2.registerOutParameter(11, Types.INTEGER);
										 	cS2.registerOutParameter(12, Types.VARCHAR);
										 	
										 	cS2.execute();
										 	
										 	err=cS2.getInt(11);
										 	mensajeError=cS2.getString(12);
										 	System.out.println("Resultado error: " + err +" mensaje error: " + mensajeError);
								        	//call setProgramacionDiaria(7,4,1.5,"a",curdate(),dayofweek(curdate()),7,"NORMAL","C", 0,@e, @msgErr);

									 		
									 	}
									 	
									 	
									 	
								 	}
                   //________________________________//REPETICION DEPENDIENDO DEL BLOQUE								 	
								 	else {
								 		System.out.println("Repite el bloque");
								 		tipo="BLOQUE";
								 		
								 		//verificar que el repaso no exceda el dia siguiente
								 		int dias=0;
								 		if(diaSiguiente>today) {
								 			dias=diaSiguiente-today;
								 		}else {
								 			dias=diaSiguiente+7-today;
								 		}
								 		
								 		System.out.println("DiaSiguiente: "+ diaSiguiente);
								 		System.out.println("Diferencia de días: "+ dias);
								 		System.out.println("Numero de sets: "+ cS.getInt(6));
									 	int diaSiguienteAux=diaSiguiente, dif=0, dias2=0;
									 	diaSiguiente2=0;
									 	if(cS.getInt(6)>=dias) {
									 		while(dias2<=cS.getInt(6)) {
									 			diaSiguiente2=Alumno.getDiaInmediato("siguiente",lunes,miercoles,jueves,sabado,diaSiguienteAux);
									 			dias2=0;
									 			if(diaSiguiente2>today) {
										 			dias2=diaSiguiente2-today;
										 		}else {
										 			dias2=diaSiguiente2+7-today;
										 		}
									 			diaSiguienteAux=diaSiguiente2;
									 			
									 			System.out.println("Nueva diferencia de días: "+ dias2);
										 		System.out.println("Nuevo dia Siguiente: "+ diaSiguiente2);
									 		}
									 	}
									 	System.out.println("DiaSiguiente2: "+ diaSiguiente2);
								 		//7,4,@numS, @tipoReg , @setBloqueInicial , @numSets
									 	CallableStatement cS2 = conn.prepareCall("{call setRepeticionBloque(?,?,?,?,?,?,?,?,?)}");
								 		
								 		//call setRepeticionBloque(7,4, 1.4, "a", 5, 4, "BLOQUE", @e, @msgErr);
								 		
								 		cS2.setInt(1, Integer.parseInt(alumn.getIdAlumno()));
									 	cS2.setInt(2, reg.getNivel().getIdNivel());
									 	cS2.setString(3, String.valueOf(reg.getNivel().getFrecuencias().get(reg.getIndice()-1))); //FRECUENCIA NO AUMENTA POR QUE REPITE EL BLOQUE
									 	cS2.setString(4, String.valueOf(reg.getNivel().getTipos().get(reg.getIndice()-1)));//TIPO
									 	cS2.setInt(5, diaSiguiente); //DIA SIGUIENTE
									 	cS2.setInt(6, diaSiguiente2); //PROXIMO DIA SI EL NUMERO DE SETS DE BLOQUE EXCEDE A SU DIA SIGUINETE
									 	cS2.setString(7, tipo);
									 	cS2.registerOutParameter(8, Types.INTEGER);
									 	cS2.registerOutParameter(9, Types.VARCHAR);
									 	System.out.println("Parametros: "+alumn.getIdAlumno() +", "+ reg.getNivel().getIdNivel()+", "+ reg.getNivel().getFrecuencias().get(reg.getIndice()-1)+", "+reg.getNivel().getTipos().get(reg.getIndice()-1)+", "+diaSiguiente+", "+diaSiguiente2+", "+tipo);
									 	
									 	cS2.execute();
									 	
									 	err=cS2.getInt(8);
									 	mensajeError=cS2.getString(9);
								 		/*7,4, 1.5, "a", dayofweek(curdate()),4, 4, "BLOQUE", @e, @msgErr*/
								 	} 	
								 	
						        }
				        }
//________________________________//CAMBIO DE FRECUENCIA (AUMENTA, DISMINUYE O PERMANECE)
				        else { //cambio de frecuencia, aumenta en una, disminuye o permanece
				        	CallableStatement cS = conn.prepareCall("{call setProgramacionDiaria(?,?,?,?,?,?,?,?,?,?,?,?)}");
						 	
							cS.setInt(1, Integer.parseInt(alumn.getIdAlumno()));
						 	cS.setInt(2, reg.getNivel().getIdNivel());
						 	cS.setString(3, String.valueOf(reg.getNivel().getFrecuencias().get(reg.getIndice()))); //FRECUENCIA
						 	cS.setString(4, String.valueOf(reg.getNivel().getTipos().get(reg.getIndice())));//TIPO
						 	cS.setString(5, "CU");
						 	cS.setInt(6, 0);
						 	cS.setInt(7, diaSiguiente); //proximo día
						 	cS.setString(8, "NORMAL");
						 	cS.setString(9, "C");
						 	cS.setInt(10, 0);
						 	cS.registerOutParameter(11, Types.INTEGER);
						 	cS.registerOutParameter(12, Types.VARCHAR);
						 	
						 	cS.execute();
						 	
						 	err=cS.getInt(11);
						 	mensajeError=cS.getString(12);
						 	System.out.println("Resultado error: " + err +" mensaje error: " + mensajeError);
				        	//call setProgramacionDiaria(7,4,1.5,"a",curdate(),dayofweek(curdate()),7,"NORMAL","C", 0,@e, @msgErr);

				        }
	}
	
//Creación deJsons
	public String crearJson() {
		StringWriter swriter = new StringWriter();
		//String alumno[], testInicial[], desempeñoGral[], puntajeNivel[];
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	gen.writeStartObject();
                gen.writeStartObject("resultsTest");
	                gen.write("finalScore", "72");
	                gen.writeStartObject("infoStudent");
	                    //gen.writeStartObject();
		                gen.write("level", "2A");
		                gen.write("grade", "1");
		                gen.write("name", "Itzel  Aguilar ");
		                gen.write("idStudent", "3");
		                gen.write("startDate", "2017-04-03");
		            gen.writeEnd();
		            gen.writeStartArray("exams");
			            gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "2A");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "A");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "B");
			             gen.writeEnd();
				             gen.writeStartObject();
			                 gen.write("desempeño", "bueno");
			                 gen.write("level", "C");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "D");
			             gen.writeEnd();
			             /*gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "D");
			             gen.writeEnd();*/
			             gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "E");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "E");
		                 gen.writeEnd();
		            gen.writeEnd();
		            gen.writeStartArray("startPoint");
		             gen.writeStartObject();
		                 gen.write("identificador", "fluidez");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Si");
		             gen.writeEnd();
		             gen.writeStartObject();
			             gen.write("identificador", "calculo");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Si");
		             gen.writeEnd();
		             gen.writeStartObject();
			             gen.write("identificador", "procedimeinto");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Cortos");
		                 gen.writeEnd();
	            gen.writeEnd();
                gen.writeEnd();
                gen.writeEnd();
            }
        return swriter.toString(); 
	}
	
	public String crearJsonRegistro() {
		StringWriter swriter = new StringWriter();
		//String alumno[], testInicial[], desempeñoGral[], puntajeNivel[];
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	gen.writeStartObject();
                gen.writeStartObject("resultsRegistro");
	                gen.write("set", "21");
	                gen.write("time", "30");
	                gen.writeStartObject("infoStudent");
		                gen.write("level", "B");
		                gen.write("grade", "1");
		                gen.write("name", "Ximena Aguilar");
		                gen.write("idStudent", "7");
		                gen.write("startDate", "2017-03-02");
		            gen.writeEnd();
		            /*gen.writeStartObject("Test");
		                gen.write("level", "B");
		                gen.write("grade", "1");
		                gen.write("name", "Ximena Aguilar");
		                gen.write("idStudent", "7");
		                gen.write("startDate", "2017-03-02");
	                gen.writeEnd();*/
		            gen.writeStartArray("calificaciones");
			            gen.writeStartObject();
			            	 gen.write("hoja","1");
			                 gen.write("calif", "110");
			             gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","2");
			                 gen.write("calif", "110");
			             gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","3");
			                 gen.write("calif", "110");
		                 gen.writeEnd();
				         gen.writeStartObject();
			            	 gen.write("hoja","4");
			                 gen.write("calif", "110");
			             gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","5");
			                 gen.write("calif", "110");
			             gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","6");
			                 gen.write("calif", "100");
		                 gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","7");
			                 gen.write("calif", "100");
		                 gen.writeEnd();
				         gen.writeStartObject();
			            	 gen.write("hoja","8");
			                 gen.write("calif", "100");
			             gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","9");
			                 gen.write("calif", "90");
			             gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","10");
			                 gen.write("calif", "90");
		                 gen.writeEnd();
		            gen.writeEnd();
                gen.writeEnd();
                gen.writeEnd();
            }
        return swriter.toString(); 
	}
	//JSON PARA COMBINACIONES
	public String crearJsonRegistro2(int f, int t, int s, int o, int n, int c) {
		StringWriter swriter = new StringWriter();
		//String alumno[], testInicial[], desempeñoGral[], puntajeNivel[];
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	gen.writeStartObject();
                gen.writeStartObject("resultsRegistro");
	                gen.write("set", "21");
	                gen.write("time", "20");
	                gen.writeStartObject("infoStudent");
	                    //gen.writeStartObject();
		                gen.write("level", "B");
		                gen.write("grade", "1");
		                gen.write("name", "Ximena Aguilar");
		                gen.write("idStudent", "7");
		                gen.write("startDate", "2017-03-02");
		            gen.writeEnd();
		            gen.writeStartArray("calificaciones");
		            for(int i=1; i<=t;i++) {
		            	gen.writeStartObject();
			            	 gen.write("hoja", i);
			                 gen.write("calif", "110");
		                gen.writeEnd();
		            }
		            for(int i=(t+1); i<=(t+f);i++) {
		            	gen.writeStartObject();
			            	 gen.write("hoja", i);
			                 gen.write("calif", "120");
		                gen.writeEnd();
		            }
		            for(int i=(t+f+1); i<=(t+f+c);i++) {
		            	gen.writeStartObject();
			            	 gen.write("hoja", i);
			                 gen.write("calif", "100");
		                gen.writeEnd();
		            }
		            for(int i=(t+f+c+1); i<=(t+f+c+n);i++) {
		            	gen.writeStartObject();
			            	 gen.write("hoja", i);
			                 gen.write("calif", "90");
		                gen.writeEnd();
		            }
		            for(int i=(t+f+c+n+1); i<=(t+f+c+n+o);i++) {
		            	gen.writeStartObject();
			            	 gen.write("hoja", i);
			                 gen.write("calif", "80");
		                gen.writeEnd();
		            }
		            for(int i=(t+f+c+n+o+1); i<=(t+f+c+n+o+s);i++) {
		            	gen.writeStartObject();
			            	 gen.write("hoja", i);
			                 gen.write("calif", "70");
		                gen.writeEnd();
		            }
		            gen.writeEnd();
                gen.writeEnd();
                gen.writeEnd();
            }
        return swriter.toString(); 
	}

	public static int indexOfIntArray(String[] array, String key) {
	    int returnvalue = -1;
	    for (int i = 0; i < array.length; ++i) {
	        if (key.equals(array[i])) {
	            returnvalue = i;
	            break;
	        }
	    }
	    return returnvalue;
	}

    public static String asignarAsistente(String alumno) throws SQLException {
    		String[] niveles = {"7A","6A","5A","4A","3A","2A","A","B","C","D","E","F","G","H","I","J"}; 
    		String asistente = "";
    		JSONObject obj2 = new JSONObject(alumno);
		JSONObject alumnoIn = obj2.getJSONObject("student");
		StringWriter swriter = new StringWriter();
		
		String nivel="";
		nivel=alumnoIn.getString("level");
		
		int index=-1;
		for(int i=0; i<niveles.length; i++) {
			if(niveles[i].equals(nivel)) {
				index=i;
			}else {
				//System.out.println("no esta en el array");
			}
		}
		
		String nivelesToCheck="";
		if(index==-1) {
			nivelesToCheck="'J'";
		}else {
			for(int i=0; i<niveles.length; i++) {
				if(i >= index) {
					if(nivelesToCheck == "") {
						nivelesToCheck=nivelesToCheck + "'" + niveles[i] +"'";
					}else if(niveles.length > 1) {
						nivelesToCheck=nivelesToCheck +",'"+niveles[i] + "'";
					}
				}
			}
		}
			
			String getQueryStatement = "SELECT pas.Asistente_Usuario_idUsuario, users.nombre, users.apellido, users.telefono, COUNT(pas.Alumno_idAlumno) FROM Asistencia as pas JOIN Usuario as users JOIN Asistente as asiste\r\n" + 
					"ON pas.Asistente_Usuario_idUsuario=users.idUsuario AND asiste.Usuario_idUsuario=users.idUsuario \r\n" + 
					"WHERE fecha=CURDATE() AND Asistente_Usuario_idUsuario IN (\r\n" + 
					"	SELECT asist.Usuario_idUsuario FROM Asistencia as lista JOIN Asistente as asist ON lista.Asistente_Usuario_idUsuario=asist.Usuario_idUsuario \r\n" + 
					"	WHERE fecha=CURDATE() AND horaSalida='00:00:00' AND asist.nivel IN ("+nivelesToCheck+") )\r\n" + 
					"GROUP BY pas.Asistente_Usuario_idUsuario ORDER BY COUNT(pas.Alumno_idAlumno);";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        ResultSet rs = prepareStat.executeQuery();
	
	        if (!rs.isBeforeFirst()){
	        		
	        		String queryAsistentes = "SELECT asist.Usuario_idUsuario, users.nombre, users.apellido, users.telefono, asist.nivel FROM Asistencia as lista JOIN Asistente as asist JOIN Usuario as users\r\n" + 
	        				"ON lista.Asistente_Usuario_idUsuario=asist.Usuario_idUsuario  AND asist.usuario_idUsuario=users.idUsuario\r\n" + 
	        				"WHERE fecha=CURDATE() AND horaSalida='00:00:00' AND asist.nivel IN ("+nivelesToCheck+") GROUP BY asist.Usuario_idUsuario;";
	
	        		prepareStat = conn.prepareStatement(queryAsistentes);
	
	            ResultSet rsAsistentes = prepareStat.executeQuery();
	            
	            if (!rsAsistentes.isBeforeFirst()){
	            		
	            		String queryNoCalificadas = "SELECT pas.Asistente_Usuario_idUsuario, users.nombre, users.apellido, users.telefono, asist.nivel, COUNT(pas.Alumno_idAlumno) FROM Asistencia as pas JOIN Usuario as users JOIN Asistente as asist\r\n" + 
	            				"ON pas.Asistente_Usuario_idUsuario=users.idUsuario AND asist.Usuario_idUsuario=users.idUsuario\r\n" + 
	            				"WHERE  fecha=CURDATE() AND horaSalida='00:00:00' AND Asistente_Usuario_idUsuario IN (\r\n" + 
	            				"	SELECT asist.Usuario_idUsuario FROM Asistencia as lista JOIN Asistente as asist ON lista.Asistente_Usuario_idUsuario=asist.Usuario_idUsuario \r\n" + 
	            				"	WHERE fecha=CURDATE()  )\r\n" + 
	            				"GROUP BY pas.Asistente_Usuario_idUsuario ORDER BY asist.nivel DESC;";
	
	            		prepareStat = conn.prepareStatement(queryNoCalificadas);
	
	                ResultSet rsNoCualificados = prepareStat.executeQuery();
	                
	                if (!rsNoCualificados.isBeforeFirst()){
	                		
	              		try (JsonGenerator gen = Json.createGenerator(swriter)) {
                				gen.writeStartObject();
                				gen.writeStartObject("infoAssistant");
                					gen.write("error", 1);
            		                gen.write("message", "No hay asistentes en el centro.");
            		            gen.writeEnd();
                    	        gen.writeEnd();
                			}
	              		
	                }else {
	                		//esta vacio
	                		if(rsNoCualificados.first()) {
	                			asistente=rsNoCualificados.getString(2) + " " + rsNoCualificados.getString(3);
	                			
	                			try (JsonGenerator gen = Json.createGenerator(swriter)) {
	                				gen.writeStartObject();
	                				gen.writeStartObject("infoAssistant");
	                					gen.write("error", 0);
	            		                	gen.write("id", rsNoCualificados.getInt(1));
	            		                	gen.write("name", rsNoCualificados.getString(2));
	            		                	gen.write("lastName", ""+rsNoCualificados.getString(3));
		        	    	            		gen.write("level", rsNoCualificados.getString(4));
		        	    	            		gen.write("tutor", "");
		        	    	            		gen.write("cellTutor", "");
		        	    	            		gen.write("missingPayment", alumnoIn.getString("missingPayment"));
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

	                }
	                
	                
	            		
	            }else {
	            		if(rsAsistentes.first()) {
	            			asistente=rsAsistentes.getString(2) + " " + rsAsistentes.getString(3);
	            			try (JsonGenerator gen = Json.createGenerator(swriter)) {
	            				gen.writeStartObject();
	            				gen.writeStartObject("infoAssistant");
	            					gen.write("error", 0);
            		                gen.write("id", rsAsistentes.getInt(1));
            		                gen.write("name", rsAsistentes.getString(2));
            		                gen.write("lastName", ""+rsAsistentes.getString(3));
		    	    	            		gen.write("level", rsAsistentes.getString(4));
		    	    	            		gen.write("tutor", "");
		    	    	            		gen.write("cellTutor", "");
		    	    	            		gen.write("missingPayment", alumnoIn.getString("missingPayment"));
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
	            }
	        		
	        }else {
	        		if(rs.first()) {
	        			asistente=rs.getString(2) + " " + rs.getString(3);
	        			try (JsonGenerator gen = Json.createGenerator(swriter)) {
	            			
		        			gen.writeStartObject();
	            			gen.writeStartObject("infoAssistant");
	            				gen.write("error", 0);
	        		            	gen.write("id", rs.getInt(1));
	        		            	gen.write("name", rs.getString(2));
	        		            	gen.write("lastName", ""+rs.getString(3));
	    	    	            		gen.write("level", rs.getString(4));
	    	    	            		gen.write("tutor", "");
	    	    	            		gen.write("cellTutor", "");
	    	    	            		gen.write("missingPayment", alumnoIn.getString("missingPayment"));
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
	        		
	        }
	        
	        return swriter.toString();

    }
    
    public static String analisisRecomendaciones(String recomendaciones, String nivel) {
    		StringWriter swriter = new StringWriter();
    		System.out.println("nivel " + nivel);
    		
    		JSONObject obj2 = new JSONObject(recomendaciones);
    		JSONArray recom = obj2.getJSONArray("recomendaciones");
    		
    		int bandera=0;
    		
	    	switch (nivel) {
	    		case "3A":
				try (JsonGenerator gen = Json.createGenerator(swriter)) {
					gen.writeStartObject();
					gen.writeStartArray("recomendations");
				
						for(int i=0; i<recom.length(); i++) {
							JSONObject pregunta = recom.getJSONObject(i);
							
								switch (pregunta.getInt("idPregunta")) {
									case 21:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Medir tus tiempos");
									    		gen.writeEnd();
										}
									break;
									case 22:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Concentrarte en tu trabajo");
									    		gen.writeEnd();
										}
									break;
									case 24:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "No utilizar dedos al contar");
									    		gen.writeEnd();
										}
									break;
								}
						}
						
						if(bandera==0) {
							gen.writeStartObject();
					    			gen.write("recomendacion", "Buen trabajo sigue así");
					    		gen.writeEnd();
						}
					gen.writeEnd();
					gen.writeEnd();
				}
			break;
			case "2A":
				try (JsonGenerator gen = Json.createGenerator(swriter)) {
					gen.writeStartObject();
					gen.writeStartArray("recomendations");
				
						for(int i=0; i<recom.length(); i++) {
							JSONObject pregunta = recom.getJSONObject(i);
							
								switch (pregunta.getInt("idPregunta")) {
									case 21:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Medir tus tiempos");
									    		gen.writeEnd();
										}
									break;
									case 22:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Concentrarte en tu trabajo");
									    		gen.writeEnd();
										}
									break;
									case 24:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "No utilizar dedos al contar");
									    		gen.writeEnd();
										}
									break;
								}
						}
						if(bandera==0) {
							gen.writeStartObject();
					    			gen.write("recomendacion", "Buen trabajo sigue así");
					    		gen.writeEnd();
						}
					gen.writeEnd();
					gen.writeEnd();
				}
			break;
	    		case "A":
				try (JsonGenerator gen = Json.createGenerator(swriter)) {
					gen.writeStartObject();
					gen.writeStartArray("recomendations");
				
						for(int i=0; i<recom.length(); i++) {
							JSONObject pregunta = recom.getJSONObject(i);
							
								switch (pregunta.getInt("idPregunta")) {
									case 25:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Medir tus tiempos");
									    		gen.writeEnd();
										}
									break;
									case 26:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "No anotar la que se lleva y no utilizar dedos al contar");
									    		gen.writeEnd();
										}
									break;
									case 22:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Concentrarte en tu trabajo");
									    		gen.writeEnd();
										}
									break;
								}
						}
						if(bandera==0) {
							gen.writeStartObject();
					    			gen.write("recomendacion", "Buen trabajo sigue así");
					    		gen.writeEnd();
						}
					gen.writeEnd();
					gen.writeEnd();
				}
			break;
	    		case "B":
				try (JsonGenerator gen = Json.createGenerator(swriter)) {
					gen.writeStartObject();
					gen.writeStartArray("recomendations");
				
						for(int i=0; i<recom.length(); i++) {
							JSONObject pregunta = recom.getJSONObject(i);
							
								switch (pregunta.getInt("idPregunta")) {
									case 21:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Medir tus tiempos");
									    		gen.writeEnd();
										}
									break;
									case 26:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "No anotar la que se lleva, ni utilizar dedos para contar");
									    		gen.writeEnd();
										}
									break;
									case 22:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Concentrarte en tu trabajo");
									    		gen.writeEnd();
										}
									break;
								}
						}
						if(bandera==0) {
							gen.writeStartObject();
					    			gen.write("recomendacion", "Buen trabajo sigue así");
					    		gen.writeEnd();
						}
					gen.writeEnd();
					gen.writeEnd();
				}
			break;
			case "C":
				System.out.println("entra al case");
				try (JsonGenerator gen = Json.createGenerator(swriter)) {
					gen.writeStartObject();
					gen.writeStartArray("recomendations");
						System.out.println("long "+recom.length());
				
						for(int i=0; i<recom.length(); i++) {
							JSONObject pregunta = recom.getJSONObject(i);
							
								switch (pregunta.getInt("idPregunta")) {
									case 27:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Repasar las tablas en casa");
									    		gen.writeEnd();
										}
									break;
									case 28:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "No utilizar dedos al contar");
									    		gen.writeEnd();
										}
									break;
									case 29:
										if(pregunta.getString("respuesta").equals("Largos")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Utilizar procedimiento enseñado en centro");
									    		gen.writeEnd();
										}
									break;
								}
						}
						if(bandera==0) {
							gen.writeStartObject();
					    			gen.write("recomendacion", "Buen trabajo sigue así");
					    		gen.writeEnd();
						}
					gen.writeEnd();
					gen.writeEnd();
				}
				System.out.println("swirter " + swriter.toString());
			break;
			case "D":
				try (JsonGenerator gen = Json.createGenerator(swriter)) {
					gen.writeStartObject();
					gen.writeStartArray("recomendations");
				
						for(int i=0; i<recom.length(); i++) {
							JSONObject pregunta = recom.getJSONObject(i);
							
								switch (pregunta.getInt("idPregunta")) {
									case 30:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Realizar el procedimiento de división con resta");
									    		gen.writeEnd();
										}
									break;
									case 31:
										if(pregunta.getString("respuesta").equals("No")) {
											bandera=1;
											gen.writeStartObject();
									    			gen.write("recomendacion", "Simplificar");
									    		gen.writeEnd();
										}
									break;
								}
						}
						if(bandera==0) {
							gen.writeStartObject();
					    			gen.write("recomendacion", "Buen trabajo sigue así");
					    		gen.writeEnd();
						}
					gen.writeEnd();
					gen.writeEnd();
				}
			break;
			
		}
    		return swriter.toString();
    }
}
