package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class FileReport {
	
	static PreparedStatement prepareStat = null;
	private static Connection conn = BaseDatos.conectarBD();
	private static int numCols =0, numCol=0, numRow=0, numRows=0;
	
	public static void getBaseInfo(String routeFile1) throws IOException, ParseException {
		String getQueryStatement = "CALL resetBaseAlumnos()";
    	
        try {
			prepareStat = conn.prepareStatement(getQueryStatement);
			ResultSet rs = prepareStat.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileInputStream fis = new FileInputStream(new File(routeFile1));
		
		//create workbook instance that readers to .xls file
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		
		//crate a sheet object to retrive the sheet
		HSSFSheet sheet = wb.getSheetAt(0);
		
		//get total number of rows
		numRows=sheet.getLastRowNum()+2;
		System.out.println("Numero de rows: " +  numRows);
		Alumno alumn= new Alumno();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
		
					
		//that is for evaluate the cell type
		FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
		int argIndex[];
		String value[];
		
		for(Row row: sheet) {
			//System.out.println("Num row: " + numRow);
			numRow++;
			numCol=0;
			for(Cell cell: row) {
				numCol++;
				numCols = row.getLastCellNum()+1;
				
				if (row.getRowNum() == 0) {
					String[] colNames= new String[numCols];
					colNames[numCol]=cell.getStringCellValue();
					//System.out.print(colNames[numCol] + "\t\t"); 
				}
				else {
					
					switch(numCol) {
					case 1:
						alumn.setIdAlumno(cell.getStringCellValue());
						
						//System.out.print(alumn.getIdAlumno() + "\t\t");
						break;
					case 2:
						String cp = convertirUpLow(cell.getStringCellValue());
						alumn.setApellidoPaterno(cp);
						
						//System.out.print(alumn.getApellidoPaterno() + "\t\t");
						break;
					case 3:
						String cp2 = convertirUpLow(cell.getStringCellValue());
						alumn.setNombre(cp2);
						
						//System.out.print(alumn.getNombre() + "\t\t");
						break;
					case 4:
						alumn.setFechaNac(cell.getDateCellValue());
						
						//System.out.print(alumn.getFechaNac() + "\t\t");
						break;
					case 5:
						alumn.setGrado(cell.getStringCellValue());
						
						//System.out.print(alumn.getGrado() + "\t\t");
						break;
					case 6:
						alumn.setTel(cell.getStringCellValue());
						
						//System.out.print(alumn.getTel() + "\t\t");
						break;						
					case 7:
						//alumn= new Alumno();
						String cp3 = convertirUpLow(cell.getStringCellValue());
						alumn.setNombreMadre(cp3);
						
						//System.out.print(alumn.getNombreMadre() + "\t\t");
						break;
					case 8:
						String cp4 = convertirUpLow(cell.getStringCellValue());
						alumn.setApellidoMadre(cp4);
						
						//System.out.print(alumn.getFecIngreso() + "\t\t");
						break;
					case 9:
						alumn.setEmailMadre(cell.getStringCellValue());
						
						//System.out.print(alumn.getEmailMadre() + "\t\t");
						break;
					case 10:
						alumn.setCelMadre(cell.getStringCellValue());
						
						//System.out.print(alumn.getCelMadre() + "\t\t");
						break;
					case 11:
						String cp5 = convertirUpLow(cell.getStringCellValue());
						alumn.setTutorNombre(cp5);
						
						//System.out.print(alumn.getTutorNombre() + "\t\t");
						break;
					case 18:
						alumn.setTelTutor(cell.getStringCellValue());
						
						//System.out.print(alumn.getTelTutor() + "\t\t");
						break;
					case 20:
						alumn.setCelTutor(cell.getStringCellValue());
						fileToDB(alumn.getIdAlumno(),alumn.getApellidoPaterno(),alumn.getNombre(),alumn.getFechaNac(),alumn.getGrado(),alumn.getTel(),alumn.getNombreMadre(),alumn.getApellidoMadre(),alumn.getEmailMadre(),alumn.getCelMadre(),alumn.getTutorNombre(),alumn.getCelTutor(),alumn.getTelTutor());
						
						//System.out.print(alumn.getCelTutor() + "\t\t");
						break;
					}
					
				}	
				
			}
		}
		
	}
	public static void getInfo(String ruteFile) throws IOException, ParseException {
		int numCols =0, numCol=0, numRow=0;
		
		FileInputStream fis = new FileInputStream(new File(ruteFile));
		
		//create workbook instance that readers to .xls file
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		
		//crate a sheet object to retrive the sheet
		HSSFSheet sheet = wb.getSheetAt(0);
		
		//get total number of rows
		int numRows=sheet.getLastRowNum()+2;
		
		Alumno alumn= new Alumno();
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
			
		//that is for evaluate the cell type
		FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
		
		for(Row row: sheet) {
			System.out.println();
			numRow++;
			numCol=0;
			for(Cell cell: row) {
				numCol++;
				numCols = row.getLastCellNum()+1;
				if (row.getRowNum() == 0) {
					String[] colNames= new String[numCols];
					colNames[numCol]=cell.getStringCellValue();
					System.out.print(colNames[numCol] + "\t\t"); 
				}
				else {
					switch(numCol) {
					case 1:
						//alumn= new Alumno();
						alumn.setIdAlumno(cell.getStringCellValue());
						//System.out.print(alumn.getIdAlumno() + "\t\t");
						break;
					case 4:
						//alumn= new Alumno();
						alumn.setGrado(cell.getStringCellValue());
						//System.out.print(alumn.getGrado() + "\t\t");
						break;
					case 6:
						//alumn= new Alumno();
						alumn.setMateria(cell.getStringCellValue());
						//System.out.print(alumn.getMateria() + "\t\t");
						break;
					case 7:
						//alumn= new Alumno();
						alumn.setFecIngreso(cell.getDateCellValue());
						//alumn.setFecIngreso(cell.getDateCellValue());
						//System.out.print(alumn.getFecIngreso() + "\t\t");
						break;
					case 8:
						//alumn= new Alumno();
						alumn.setNivelAnterior(cell.getStringCellValue());
						//System.out.print(alumn.getNivelAnterior() + "\t\t");
						break;
					case 9:
						//alumn= new Alumno();
						alumn.setNivelActual(cell.getStringCellValue());
						//System.out.print(alumn.getNivelActual() + "\t\t");
						break;
					case 10:
						//alumn= new Alumno();
						alumn.setSet((int)cell.getNumericCellValue());
						//System.out.print(alumn.getSet() + "\t\t");
						break;
					case 11:
						//alumn= new Alumno();
						alumn.setNumHojas((int)cell.getNumericCellValue());
						//System.out.print(alumn.getNumHojas() + "\t\t");
						break;
					case 12:
						//alumn= new Alumno();
						//String a=formatter.format(cell.getStringCellValue());
						alumn.setUltimaAusencia(cell.getStringCellValue());
						//System.out.print(alumn.getUltimaAusencia() + "\t\t");
						
						break;
					case 13:
						//alumn= new Alumno();
						alumn.setStatusAnterior(cell.getStringCellValue());
						//System.out.print(alumn.getStatusAnterior() + "\t\t");
						
						break;
					case 14:
						//alumn= new Alumno();
						alumn.setStatusActual(cell.getStringCellValue());
						//System.out.print(alumn.getStatusActual() + "\t\t");
						//System.out.print("Estoy a punto de mnadar la fec al procedimiento"+alumn.getIdAlumno()+alumn.getGrado()+
							//	alumn.getMateria()+alumn.getFecIngreso()+
								//alumn.getNivelAnterior());
						updateDB(alumn.getIdAlumno(),alumn.getGrado(),
								alumn.getMateria(),alumn.getFecIngreso(),
								alumn.getNivelAnterior(),alumn.getNivelActual(),
								alumn.getSet(),alumn.getNumHojas(),
								alumn.getUltimaAusencia(),alumn.getStatusAnterior(),
								alumn.getStatusActual());
						break;
					}
					
					
				}
					/*switch(formulaEvaluator.evaluateInCell(cell).getCellType()) {
					
					//if cell is a numeric format
					case Cell.CELL_TYPE_NUMERIC:
						alumnoValues[numRow].set
								cell.getNumericCellValue();
						System.out.print(cell.getNumericCellValue() + "\t\t");
						break;
					//if cell is a string format
					case Cell.CELL_TYPE_STRING:
						String cp = convertirUpLow(cell.getStringCellValue());
						System.out.print(cp + "\t\t");
						break;
					}
				}*/				
			}
		}
		
	}
	
	public static String convertirUpLow(String campo) {
		if (campo == null) {
	        return null;
	    }
	    StringBuilder builder = new StringBuilder();
	    StringTokenizer st = new StringTokenizer(campo," ");
	    while (st.hasMoreElements()) {
	        String ne = (String)st.nextElement();
	        if (ne.length()>0) {
	            builder.append(ne.substring(0, 1).toUpperCase());
	            builder.append(ne.substring(1).toLowerCase()); //agregado
	            builder.append(' ');
	        }
	    }
	    return builder.toString();
		
	}
	
	public static void fileToDB(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8,String s9,String s10,String s11,String s12,String s13 ){
		 try {
			 CallableStatement cS = conn.prepareCall("{CALL setBaseAlumnos(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			 	//System.out.println("Estoy ne el procedimiento");
			 	cS.setString(1, s1);
			 	cS.setString(2, s2);
			 	cS.setString(3, s3);
			 	cS.setString(4, s4);
			 	cS.setString(5, s5);
			 	cS.setString(6, s6);
			 	cS.setString(7, s7);
			 	cS.setString(8, s8);
			 	cS.setString(9, s9);
			 	cS.setString(10, s10);
			 	cS.setString(11, s11);
			 	cS.setString(12, s12);
			 	cS.setString(13, s13);
			 	cS.execute();		
				
	    } catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	public static void updateDB(String s1, String s2, String s3, String s4, String s5, String s6, int s7, int s8,String s9,String s10,String s11 ){
		 try {
			 CallableStatement cS = conn.prepareCall("{CALL setDatosAlumno(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			 	//System.out.println("Estoy ne el procedimiento y la fecha de ingreso es: " + s1+s2+s3+s4+s5);
			 	String getQueryStatement = "SET sql_mode = ''";
		    	
		        try {
					prepareStat = conn.prepareStatement(getQueryStatement);
					ResultSet rs = prepareStat.executeQuery();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 	cS.setString(1, s1);
			 	cS.setString(2, s2);
			 	cS.setString(3, s3);
			 	cS.setString(4, s4);
			 	cS.setString(5, s5);
			 	cS.setString(6, s6);
			 	cS.setInt(7, s7);
			 	cS.setInt(8, s8);
			 	cS.setString(9, s9);
			 	cS.setString(10, s10);
			 	cS.setString(11, s11);
			 	cS.execute();	
				
	    } catch (SQLException e) {
			e.printStackTrace();
		}			
	}
}
