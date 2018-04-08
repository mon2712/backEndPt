package classes;

//import classes.Centro;
//import classes.BaseDatos;

public class pruebafecha {
	public static void main(String[] args) {
		
		BaseDatos dataDB = new BaseDatos();
		//Centro centro= new Centro();
		
		//String Students=centro.getStudents("JOSE");
		//System.out.print(Students);
		
		/*Alumno alumno = new Alumno();
		String Alumnos=alumno.obtenerFichaAlumno(3);
		System.out.println(Alumnos);*/
		
		Alumno alumno = new Alumno();
		String alumnitos=alumno.getAlumnos("je");
		System.out.println(alumnitos);
		
	}

}
