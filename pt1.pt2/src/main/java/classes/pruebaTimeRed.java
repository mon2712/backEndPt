package classes;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.WorkingMemory;
import org.json.JSONObject;

public class pruebaTimeRed {

	public static void main(String[] args) throws DroolsParserException, IOException {
		
		BaseDatos dataDB = new BaseDatos();
		Centro centro= new Centro();
		centro.updateTimeRed("00:20:00", 2);
		System.out.print("Hecho");
		
		
	}

}
