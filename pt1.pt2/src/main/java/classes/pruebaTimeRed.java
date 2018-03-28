package classes;

public class pruebaTimeRed {

	public static void main(String[] args) {
		
		BaseDatos dataDB = new BaseDatos();
		Centro centro= new Centro();
		centro.updateTimeRed("00:20:00", 2);
		System.out.print("Hecho");
	}

}
