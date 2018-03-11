package classes;

import classes.Centro;
//import classes.BaseDatos;

public class pruebafecha {
	public static void main(String[] args) {
		
		BaseDatos dataDB = new BaseDatos();
		Centro centro= new Centro();
		
		String Students=centro.getStudents();
		System.out.print(Students);
	}

}
