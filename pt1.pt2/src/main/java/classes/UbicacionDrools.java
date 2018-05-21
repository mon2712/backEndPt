package classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;

public class UbicacionDrools {

	public static void main(String[] args) throws DroolsParserException, IOException {
		UbicacionDrools droolsNivelUbicacion = new UbicacionDrools();
		droolsNivelUbicacion.executeDrools();
	}

	public void executeDrools() throws DroolsParserException, IOException {
	
		PackageBuilder packageBuilder = new PackageBuilder();
		
		String ruleFile = "../rules/frecuenciaInicial.drl";
		InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);
		
		Reader reader = new InputStreamReader(resourceAsStream);
		packageBuilder.addPackageFromDrl(reader);
		org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage(rulesPackage);
		
		WorkingMemory workingMemory = ruleBase.newStatefulSession();
		
		//int [] puntaje = {8,8,8,8,8,8};  //obtenidos del front
		int nivelUbicacion=1; //Obtenido del front
		double frecInicial;
		//int dimension = 6 - nivelUbicacion+1;
		//System.out.println("Dimension: " + dimension);
		//Nivel[] niveles = new Nivel[dimension];
		//List<Double> Frec=new ArrayList<Double>();
		
	}
}