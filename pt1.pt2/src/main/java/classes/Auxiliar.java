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
    Connection conn = BaseDatos.conectarBD();
    
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
	        niveles[x-nivelUbicacion].setearValores(n[x-nivelUbicacion], conn);
			niveles[x-nivelUbicacion].setDesempeñoGeneral(pn.getAlumno().getDesempeño());
			niveles[x-nivelUbicacion].setDesempeñoNivel(desempeñosNivel[x-nivelUbicacion]);
			
			workingMemory.insert(niveles[x-nivelUbicacion]);
			workingMemory.fireAllRules();
	        
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
		//String [] desempeñoNivel = {"malo","malo","malo","malo","malo","malo"};  //obtenidos del front
		//System.out.println("Examenes desempeños:  "+ Arrays.asList(desempeñosNivel));
		//System.out.println("Dimension: " + dimension);
		Nivel[] niveles = new Nivel[dimension];
		
		//alu.setDesempeño("bueno");
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
			//System.out.println("Niveles en la segunda: " + Arrays.asList(n));
			niveles[x-nivelUbicacion].setNombreNivel(n[x-nivelUbicacion]);
			niveles[x-nivelUbicacion].setFrecuencias(Frec);
			niveles[x-nivelUbicacion].setTipos(Incisos);
			niveles[x-nivelUbicacion].setDesempeñoGeneral(pn.getAlumno().getDesempeño());
			niveles[x-nivelUbicacion].setDesempeñoNivel(desempeñosNivel[x-nivelUbicacion]);
			//System.out.println(" x: "+ niveles[x-nivelUbicacion].getIdNivel() + " nivelUbicacion: " + nivelUbicacion+ " indice: " + (x-nivelUbicacion));
			
			//System.out.println("Nombre nivel: "+ niveles[x-nivelUbicacion].getNombreNivel());
			//System.out.println(" Freceuncias: " + niveles[x-nivelUbicacion].getFrecuencias());
			//System.out.println("Desempeño del nivel: " + niveles[x-nivelUbicacion].getDesempeñoNivel() + " Desempeño general: " + pn.getAlumno().getDesempeño());
			
			workingMemory.insert(niveles[x-nivelUbicacion]);
			workingMemory.fireAllRules();
			
			
			//System.out.println("Desempeño del nivel: "+ niveles[x-nivelUbicacion].getDesempeñoNivel()+", Desempeño general: "+ niveles[x-nivelUbicacion].getDesempeñoGeneral() + ", Desempeño total: "+ niveles[x-nivelUbicacion].getDesempeño());
			//System.out.println("Mensaje: "+niveles[x-nivelUbicacion].getNivelMessage());
			//niveles[x-nivelUbicacion].setPuntajeDesempeño(puntaje[x-nivelUbicacion]);
	        //int size= Frec.size();
	        //System.out.println("size: "+ size);
	        
		}*/
		double []frecProy = new double [6-nivelUbicacion+1];
		String []frecProyTipo = new String [6-nivelUbicacion+1];
		int size, frecOriginal, s=0, indiceActual=0, mov=0, totalF=0, err;
		String msn;
		
		for(int x=0;x<=(6-nivelUbicacion);x++) { //nivel
			size=niveles[x].getFrecuencias().size();
			//System.out.println("Numero de frecuenicas del nivel actual: " + size);
			//s=0;
			/*while(s<size && niveles[x].getFrecuencias().get(s)!=frecInicial) {
				//System.out.println("Freceuncia " +  (x+1) + ": " + niveles[x].getFrecuencias().get(s));
				s++;
			}*/
			if(x==0) {
				indiceActual=niveles[x].getFrecuencias().indexOf(frecInicial);
				if(indiceActual == -1) {
					//System.out.println("La frecuencia no existe");
					break;
				}
				else {
					//System.out.println("Está en el indice: " + indiceActual);
				}
				//System.out.println("Avanzaré " + (niveles[x].getDesempeño()) + " Indices");
	
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
			frecProy[x]= niveles[x].getFrecuencias().get(indiceActual);
			frecProyTipo[x]= niveles[x].getTipos().get(indiceActual);
		  }
		int idAl=Integer.parseInt(pn.getAlumno().getIdAlumno());
		for(int x=0; x<6-nivelUbicacion+1; x++) {
			System.out.println("Frecuencia " + (x+1) + " : " + frecProy[x] + " " + frecProyTipo[x] + " " + niveles[x].getNombreNivel());
			CallableStatement cS = conn.prepareCall("{call setProyeccionAnual(?, ?, ?, ?, ?, ?)}");
		 	
			cS.setInt(1, idAl);
		 	cS.setString(2, String.valueOf(frecProy[x]));
		 	cS.setString(3, niveles[x].getNombreNivel());
		 	cS.setString(4, frecProyTipo[x] );
		 	cS.registerOutParameter(5, Types.INTEGER);
		 	cS.registerOutParameter(6, Types.VARCHAR);
		 	cS.execute();
		 	
		 	if(cS.getInt(5)==1 && x==0) {
		 		System.out.println("Error: " + cS.getInt(5) + " " + cS.getString(6));
		 		break;
		 	}
		 	else {
		 		//System.out.println("Frecuencia " + (x+1) + " : " + frecProy[x] + " " + frecProyTipo[x] + " " + niveles[x].getNombreNivel());
				CallableStatement cS2 = conn.prepareCall("{call setProyeccionNivelInProyeccionAnual(?, ?, ?, ?, ?, ?)}");
				cS2.setInt(1, idAl);
			 	cS2.setString(2, String.valueOf(frecProy[x]));
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
		//Nivel niv = new Nivel();
		//String array=this.crearJson();
		JSONObject obj = new JSONObject(array);
		
		JSONObject results = obj.getJSONObject("resultsTest");
		
			JSONObject infoStudent = results.getJSONObject("infoStudent");
		
			int desempeñoGeneral = results.getInt("finalScore");
		
			JSONArray exams = results.getJSONArray("exams");
			JSONArray frecuenciaIncial = results.getJSONArray("startPoint");
			String n;
			System.out.println("puntajeDesempeño " + desempeñoGeneral);
			System.out.println("examenes " + exams);
			System.out.println("infoStudent" +infoStudent.toString());
			System.out.println("para frecuencia inicial" + frecuenciaIncial.toString());
			System.out.println("nivel" + infoStudent.getString("level"));
			
			n=infoStudent.getString("level");
			String g=infoStudent.getString("grade");
			String idStudent=infoStudent.getString("idStudent");
			Alumno alumn = new Alumno();
			alumn.setPuntajeDesempeño(desempeñoGeneral);
			workingMemory.insert(alumn);
			workingMemory.fireAllRules();
			//System.out.println("Desempeño general: " +  alumn.getDesempeño());
			alumn.setNivelActual(n);
			alumn.setGrado(g);
			alumn.setIdAlumno(idStudent);
			String id;
			//String desempeñosNivel[] = new String [exams.length()];
			String desNivel[] = new String [exams.length()];
			String level[] = new String [exams.length()];
			//List<Double> Frec=new ArrayList<Double>();
			//System.out.println("Longitud de examenes: "+exams.length());
			for(int i=0; i<exams.length();i++) {
				JSONObject res = (JSONObject) exams.get(i);
				desNivel[i]=res.getString("desempeño");
				level[i] = res.getString("level");
				System.out.println("Indice de Nivel: " + i + " nivel: " + level[i] );
			}
			
			int newIndice=Arrays.binarySearch(level, n);
			//System.out.println("New indice: " + newIndice);
			//String desempeñosNivel[] = new String [exams.length()- newIndice];
			//String nivel[] = new String [exams.length()- newIndice];
			String desempeñosNivel[]=Arrays.copyOf(desNivel, newIndice);
			String nivel[]=Arrays.copyOf(level, newIndice);
			//System.out.println("Examenes desempeños:  "+ Arrays.asList(desempeñosNivel) + " Niveles: " + Arrays.asList(nivel));
			
			
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
						" NumFlecha: " + reg.getNumFlecha() + " Evaluacion:" );
				
				System.out.println(reg.getEvaluacion());
				//System.out.println(reg.getAccion());
			//workingMemory.insert(pn);
			//workingMemory.fireAllRules();
				//System.out.println("Evaluaciones malas: " + contadorMala);
			}
			//System.out.println("Evalucacion:  " + reg.getEvaluacion());
			
	}
	//PARA UN SOLO REGISTRO
	public void executeProg(WorkingMemory workingMemory, WorkingMemory workingMemory2, String array) throws SQLException {
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
				System.out.println("Tiempo maximo del nivel: " + reg.getNivel().getMaxTime());
			
			//alumn.setPuntajeDesempeño(desempeñoGeneral);
		//workingMemory.insert(alumn);
		//workingMemory.fireAllRules();
			//System.out.println("Desempeño general: " +  alumn.getDesempeño());
			
			//seteando valores del objeto alumno
			alumn.setNivelActual(n);
			alumn.setGrado(g);
			alumn.setIdAlumno(idStudent);
			
			//seteando vaores del objeto registro
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
				//System.out.println("Resutado consulta: " + reg.getNivel().getIdNivel() + " minTime: " + reg.getNivel().getMintime() + " maxTime " + reg.getNivel().getMaxTime());
				//sSystem.out.println(reg.getTiempo() +"<= ("+ reg.getNivel().getMaxTime()*10);
				//System.out.println(reg.getEvaluacion());
				//if(reg.getEvaluacion().equals("Exce")){
				
				//workingMemory.insert(reg);
				//workingMemory.fireAllRules();
				//if(reg.getNivel().getFrecuencias().indexOf(reg.getFrec())>0) {
					
					System.out.println(" Frecuencia y tipo actual: "+ reg.getFrec() + " "+ reg.getTipoFrec());
					//inidicetemporal de la frecuencia actual
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
					
				//lanzando las reglas para cambio de frecuencia
				workingMemory2.insert(reg);
				workingMemory2.fireAllRules();
				
				System.out.println("Index:"+ reg.getIndice());
				System.out.println(" Accion: " + reg.getAccion());
				
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
				
				
				/*
				CallableStatement cStmt = conn.prepareCall("{call getDiasAlumno(?, ?, ?, ?, ?, ?)}");
				cStmt.setInt(1, Integer.parseInt(alumn.getIdAlumno()));
				cStmt.registerOutParameter(2, Types.INTEGER);
			    cStmt.registerOutParameter(3, Types.INTEGER);
			    cStmt.registerOutParameter(4, Types.VARCHAR);
			    cStmt.registerOutParameter(5, Types.VARCHAR);
			    cStmt.registerOutParameter(6, Types.VARCHAR);
			    cStmt.execute();   
			    int today=cStmt.getInt(6);
				
				int lunes = cStmt.getInt(2); //lunes
				int miercoles = cStmt.getInt(3); //miercoles
				int jueves = cStmt.getInt(4); //jueves
				int sabado = cStmt.getInt(5); //sabado*/

					//int diaAnterior  =alumn.getDiaInmediato(/*alumn.getIdAlumno()*/"anterior",1,1,1,1,7);
					int diaSiguiente=alumn.getDiaInmediato("siguiente",lunes,miercoles,jueves,sabado,today);
					//System.out.println("Dia anterior: " + diaAnterior);
					System.out.println("Dia proximo: " + diaSiguiente);
				//}
			//workingMemory.insert(pn);
			//workingMemory.fireAllRules();
				
			//}
				
				
				//System.out.println(" Evaluaciones malas: " + contadorMala);
	}
	
//Creación deJsons
	public String crearJson() {
		StringWriter swriter = new StringWriter();
		//String alumno[], testInicial[], desempeñoGral[], puntajeNivel[];
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	gen.writeStartObject();
                gen.writeStartObject("resultsTest");
	                gen.write("finalScore", "76");
	                gen.writeStartObject("infoStudent");
	                    //gen.writeStartObject();
		                gen.write("level", "B");
		                gen.write("grade", "1");
		                gen.write("name", "Ximena Aguilar");
		                gen.write("idStudent", "7");
		                gen.write("startDate", "2017-03-02");
		            gen.writeEnd();
		            gen.writeStartArray("exams");
			            gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "3A");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "2A");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "A");
			             gen.writeEnd();
				             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "B");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "C");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "D");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "E");
		                 gen.writeEnd();
		            gen.writeEnd();
		            gen.writeStartArray("startPoint");
		             gen.writeStartObject();
		                 gen.write("identificador", "fluidez");
		                 gen.write("answer", "0");
		                 gen.write("answerLbl", "Si");
		             gen.writeEnd();
		             gen.writeStartObject();
			             gen.write("identificador", "concentracion");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Si");
		             gen.writeEnd();
		             gen.writeStartObject();
			             gen.write("identificador", "trabajoB");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Si");
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
			            gen.writeStartObject();
			            	 gen.write("hoja","1");
			                 gen.write("calif", "100");
			             gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","2");
			                 gen.write("calif", "100");
			             gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","3");
			                 gen.write("calif", "100");
		                 gen.writeEnd();
				         gen.writeStartObject();
			            	 gen.write("hoja","4");
			                 gen.write("calif", "100");
			             gen.writeEnd();
			             gen.writeStartObject();
			            	 gen.write("hoja","5");
			                 gen.write("calif", "100");
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
			                 gen.write("calif", "90");
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
	
	
	
}