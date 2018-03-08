package TrabajoTerminalBack.pt1.pt2;

public class prueba {
	
	public static void main(String[] args) {
		
		conexionDB dataDB = new conexionDB();
		
		dataDB.makeJDBCConnection();
		
		dataDB.getUsernames();
	}
}
