package classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;


public class pruebaDrools {

		public static void main(String[] args) throws DroolsParserException,
				IOException {
			DroolsTest droolsTest = new DroolsTest();
			droolsTest.executeDrools();
		}

		public void executeDrools() throws DroolsParserException, IOException {

			PackageBuilder packageBuilder = new PackageBuilder();

			String ruleFile = "../rules/proyeccionNivel.drl";
			InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);

			Reader reader = new InputStreamReader(resourceAsStream);
			packageBuilder.addPackageFromDrl(reader);
			org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
			RuleBase ruleBase = RuleBaseFactory.newRuleBase();
			ruleBase.addPackage(rulesPackage);

			WorkingMemory workingMemory = ruleBase.newStatefulSession();
			/*
			double[ ] frec3A = {1.01,1.5, 2.0, 2.3, 3.0, 3.3, 3.4};
			double[ ] frec2A = {1.0,1.2,1.7,2.0, 2.5, 2.6, 3.2, 3.3, 3.7, 4.3, 4.4};
			double[ ] frecA = {1.0, 1.2, 1.3, 1.5, 1.7, 2.1, 2.4, 2.8, 3.1, 3.3, 3.5, 3.8, 3.9, 4.3, 4.5};
			double[ ] frecB = {1.0, 1.2, 1.3, 1.5, 1.7, 1.9, 2.0, 2.5, 2.8, 2.9, 3.1, 3.2, 3.5, 3.6, 3.9, 4.1, 4.2};
			double[ ] frecC = {1.0, 1.2, 1.3, 1.5,2.0, 2.3, 2.7, 3.0, 3.3, 3.4, 3.4, 3.6, 3.7, 3.8, 3.9, 4.0};
			double[ ] frecD = {1.0, 1.1, 1.7, 1.8, 2.0, 2.1, 2.5, 2.6, 2.7, 3.0, 3.1, 3.2, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0};
		
			 
			List<Double> Freq3A = Arrays.asList(1.0,1.5, 2.0, 2.3, 3.0, 3.3, 3.4);
			List<Double> Freq2A = Arrays.asList(1.0,1.2,1.7,2.0, 2.5, 2.6, 3.2, 3.3, 3.7, 4.3, 4.4);
			List<Double> FreqA = Arrays.asList(1.0, 1.2, 1.3, 1.5, 1.7, 2.1, 2.4, 2.8, 3.1, 3.3, 3.5, 3.8, 3.9, 4.3, 4.5);
			List<Double> FreqB = Arrays.asList(1.0, 1.2, 1.3, 1.5, 1.7, 1.9, 2.0, 2.5, 2.8, 2.9, 3.1, 3.2, 3.5, 3.6, 3.9, 4.1, 4.2);
			List<Double> FreqC = Arrays.asList(1.0, 1.2, 1.3, 1.5,2.0, 2.3, 2.7, 3.0, 3.3, 3.4, 3.4, 3.6, 3.7, 3.8, 3.9, 4.0);
			List<Double> FreqD = Arrays.asList(1.0, 1.1, 1.7, 1.8, 2.0, 2.1, 2.5, 2.6, 2.7, 3.0, 3.1, 3.2, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0);
			
			//List <Nivel> niveles = new ArrayList<Nivel>();
			
			
			Nivel nivel1 = new Nivel(1,"3A", Freq3A);
			Nivel nivel2 = new Nivel(2,"2A", Freq2A);
			Nivel nivel3 = new Nivel(3,"A", FreqA);
			Nivel nivel4 = new Nivel(4,"B", FreqB);
			Nivel nivel5 = new Nivel(5,"C", FreqC);
			Nivel nivel6 = new Nivel(6,"D", FreqD);
			
			nivel1.setPuntajeDesempeño(90);
			nivel2.setPuntajeDesempeño(80);
			nivel3.setPuntajeDesempeño(71);
			nivel4.setPuntajeDesempeño(31);
			nivel5.setPuntajeDesempeño(30);
			nivel6.setPuntajeDesempeño(8);
			*/
			
			
			int [] puntaje = {80,71,31,30,8};  //obtenidos del front
			int nivelUbicacion=2; //Obtenido del front
			double frecInicial = 2.5;
			int dimension = 6 - nivelUbicacion+1;
			System.out.println("Dimension: " + dimension);
			Nivel[] niveles = new Nivel[dimension];
			List<Double> Frec=new ArrayList<Double>();
			
			for(int x =nivelUbicacion; x<=6; x++) {
				String n="";
				switch (x){
					case 1: 
						n= "3A";
						Frec=Arrays.asList(1.0, 1.1, 1.2, 1.4, 1.5, 1.6, 1.7, 1.8, 2.0, 2.2, 2.3, 3.0, 3.3, 3.4);
					break;
					case 2: 
						n= "2A";
						Frec=Arrays.asList(1.0,1.2,1.7,2.0, 2.5, 2.6, 3.2, 3.3, 3.7, 4.3, 4.4);
					break;
					case 3: 
						n= "A";
						Frec=Arrays.asList(1.0, 1.2, 1.3, 1.5, 2.0, 2.1, 2.4, 2.8, 3.1, 3.3, 3.5, 3.8, 3.9, 4.3, 4.5);
					break;
					case 4: 
						n= "B";
						Frec=Arrays.asList(1.0, 1.2, 1.3, 1.4, 1.5, 1.7, 1.9, 2.0, 2.5, 2.8, 2.9, 3.1, 3.2, 3.5, 3.6, 3.9, 4.1, 4.2);
					break;
					case 5: 
						n= "C";
						Frec=Arrays.asList(1.0, 1.2, 1.3, 1.5,1.6, 1.7,2.0, 2.3, 2.8, 3.0, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0);
					break;
					case 6: 
						n= "D";
						Frec=Arrays.asList(1.0, 1.1, 1.3, 1.4, 1.7, 1.8, 2.0, 2.1, 2.5, 2.6, 2.7, 3.0, 3.1, 3.2, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0);
					break;
					}
				System.out.println(" nivel: "+ n + " Freceuncias: " + Frec);
				niveles[x-nivelUbicacion] = new Nivel(x,n,Frec);
				niveles[x-nivelUbicacion].setPuntajeDesempeño(puntaje[x-nivelUbicacion]);
			}
			//List <Nivel> niveles = Arrays.asList(nivel1, nivel2, nivel3, nivel4, nivel5, nivel6);
			int resultado=0, residuo=0, rango=0, frecCompartidas=0;
			double []frecProy = new double [6-nivelUbicacion];
			double [][]frecs = null;
			int size, frecOriginal, s=0, indiceActual=0, mov=0;
		
			for(int x=0;x<=(6-nivelUbicacion);x++) { //nivel
				size=niveles[x].getFrecuencias().size();
				s=0;
				while(s<size && niveles[x].getFrecuencias().get(s)!=frecInicial) {
					System.out.println("Freceuncia " +  (x+1) + ": " + niveles[x].getFrecuencias().get(s));
					s++;
				}
				if(x==0) {
					
					indiceActual=niveles[x].getFrecuencias().indexOf(frecInicial);
					System.out.println("Está en el indice: " + indiceActual + " frecuencia: "+niveles[x].getFrecuencias().get(indiceActual));

				}
				else {
					indiceActual = indiceActual+ niveles[x].getDesempeño();
					//frecProy[x] = niveles[x].getFrecuencias().get(indiceActual);
				}
				frecProy[x]= niveles[x].getFrecuencias().get(indiceActual);
				//frecs[x][0]=
				/*resultado=size/3;
				residuo=size%3;
				rango=resultado+residuo;
				frecCompartidas=residuo;
				frecOriginal=0;
				System.out.println("Frecuencias por rangos del nivel " + niveles[x].getNombreNivel() + ": " + rango + " Compartiendo" + frecCompartidas );
				for(int y=0; y<=2;y++) {//rango (3 rangos de frecuencias por nivel)
					
					for(int f=0;f<=rango-1;f++ ) { //frecuencias
						if(frecOriginal<=rango) {
							frecOriginal=frecOriginal;
						}
						else {
							frecOriginal=frecOriginal-frecCompartidas;
						}
						System.out.println("Nivel: " + x + " Rango: " +  y + " frecuencia numero " + f + " del rango, " + niveles[x].getFrecuencias().get(frecOriginal));
						//frecs[x][y][f]=niveles[x].getFrecuencias().get(frecOriginal);
						frecOriginal++;
					}
				}*/
				
			}
			
			
			
			
			Alumno alumno = new Alumno();
			alumno.setNivelActual("3A");
			alumno.setIdAlumno("1");
			List <Nivel> niv = new ArrayList <Nivel> ();
			niv = Arrays.asList (niveles);
			ProyeccionNivel proyeccionNivel = new ProyeccionNivel(alumno, niv);
			
			//product.setType("gold");
			for(int x=nivelUbicacion; x<=6; x++) {
				workingMemory.insert(niveles[x-nivelUbicacion]);
				workingMemory.fireAllRules();
				System.out.println(niveles[x-nivelUbicacion].getNivelMessage());
			}
			
			//System.out.println("The discount for the product " + product.getType()
				//	+ " is " + product.getDiscount());
			//System.out.println("El nivel inicial es:" + proyeccionNivel.getNivel());

	}
	
}

