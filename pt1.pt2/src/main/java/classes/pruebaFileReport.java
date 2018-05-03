package classes;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class pruebaFileReport {
public static void main(String[] args) throws IOException, ParseException, SQLException {
		
		BaseDatos dataDB = new BaseDatos();
		FileReport fr= new FileReport();
		System.out.println("Se jecutó ");
		fr.getBaseInfo("C:\\Users\\Vanessa Miranda\\Desktop\\Base.xls");
		fr.getInfo("C:\\Users\\Vanessa Miranda\\Desktop\\Reporte agosto.xls");
		
		//fr.getInfo();
		//"C:\\Users\\Vanessa Miranda\\Desktop\\Reporte agosto.xls"
		//System.out.print(Students);
	}

}
