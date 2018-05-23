package classes;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.FileSystems;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.stream.JsonGenerator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.JSONArray;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.line.LineStyle;

import org.json.JSONArray;
import org.json.JSONObject;

public class Instructor {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
    
    public static void obtenerGafetesAlumnos() throws WriterException, IOException, SQLException{
    	try {
            String getQueryStatement = "SELECT * FROM alumno WHERE estatus='N';";

            prepareStat = conn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = prepareStat.executeQuery();
            CrearQr crearqr = new CrearQr();
            
            //Creación de documento
            PDDocument document = new PDDocument();
	    		PDPage page = new PDPage();
	    		document.addPage( page );
	    		
	    		PDPageContentStream contentStream = new PDPageContentStream(document, page);
	    		PDFont font = PDType1Font.HELVETICA;
	    		
	    		//Insertar título
	    		contentStream.beginText();
	    		contentStream.setFont( font, 12 );
	    		contentStream.moveTextPositionByAmount(100, 700);
	    		contentStream.drawString( "Gafetes de alumnos" );
	    		contentStream.endText();
    		
	    		//Dummy Table
	    	    float margin = 50;
	    	    // starting y position is whole page height subtracted by top and bottom margin
	    	    float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
	    	    // we want table across whole page width (subtracted by left and right margin ofcourse)
	    	    float tableWidth = page.getMediaBox().getWidth() - (2 * margin);

	    	    boolean drawContent = true;
	    	    float yStart = yStartNewPage;
	    	    float bottomMargin = 70;
	    	    // y position is your coordinate of top left corner of the table
	    	    float yPosition = 650;

	    	    BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);
	    	    float imageWidth = 80; //Para ajustar la imagen
	    	    
	    	    Row<PDPage> headerRow = table.createRow(15f);
	    	    
	    	    Cell<PDPage> cell = headerRow.createCell(50, "Gafetes Alumnos");
	    	    table.addHeaderRow(headerRow);
	    	    LineStyle thinline = new LineStyle(Color.WHITE, 0.75f);
            
            while (rs.next()) {
            		System.out.println(rs.getInt(1)+ " "+rs.getString(2));
            		
            		crearqr.generateQRCodeImage(rs.getString(1),rs.getString(2)); //mandamos a crear el qr con el id
            		
            		String filePath="/"+rs.getString(1)+".png";
            		Image image = new Image(ImageIO.read(new File(System.getProperty("user.home").toString()+"/Documents/qrImages"+filePath)));
            		image = image.scaleByWidth(imageWidth);
            		
            		Row<PDPage> row = table.createRow(12);
            	    
            	    
            	    cell = row.createImageCell(10 , image);
            	    cell.setRightBorderStyle(thinline);
            	    cell = row.createCell(15, rs.getString(3) + " " + rs.getString(2), HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
            	    cell = row.createCell(25, "Tareas", HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
            		
            }

            table.draw();
            contentStream.close();

	    		// Save the results and ensure that the document is properly closed:
	    		document.save( "./GafetesAlumnos.pdf");
	    		document.close();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void obtenerCredenciales(String array) throws WriterException, IOException, SQLException{
    				
		JSONObject obj = new JSONObject(array);
		JSONArray selectedPeople = obj.getJSONArray("selectedPeople");
		
		CrearQr crearqr = new CrearQr();
	    //Creación de documento
	    PDDocument document = new PDDocument();
	    PDPage page = new PDPage();
	    document.addPage( page );
	    PDPageContentStream contentStream = new PDPageContentStream(document, page);
	    PDFont font = PDType1Font.HELVETICA;
			
	    //Insertar título
		contentStream.beginText();
		contentStream.setFont( font, 12 );
		contentStream.moveTextPositionByAmount(100, 700);
		contentStream.drawString( "Credenciales Asistente" );
		contentStream.endText();
	
		
		float margin = 50;
		// starting y position is whole page height subtracted by top and bottom margin
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		// we want table across whole page width (subtracted by left and right margin ofcourse)
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
	
		boolean drawContent = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
			// y position is your coordinate of top left corner of the table
		float yPosition = 650;
	
		BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);
		float imageWidth = 80; //Para ajustar la imagen
			
		Row<PDPage> headerRow = table.createRow(15f);
			
		Cell<PDPage> cell = headerRow.createCell(50, "Credenciales Asistentes");
		table.addHeaderRow(headerRow);
		LineStyle thinline = new LineStyle(Color.WHITE, 0.75f);
		
		if(selectedPeople.length() == 0) {
			System.out.println("No llego nada");
		}else {
		
			for (int i = 0; i < selectedPeople.length(); i++) {
						
				JSONObject person = selectedPeople.getJSONObject(i);
				if(person.has("idAssistant")) {
					String id=person.getString("idAssistant");
					String nombre = person.getString("name");
					System.out.println("id: "+ id);
					System.out.println("nombre: " + nombre);
					
					crearqr.generateQRCodeImage(id+"-A-F", nombre);
							
					String filePath="/"+id+"-A-F.png";
					System.out.println(filePath);
					Image image = new Image(ImageIO.read(new File(System.getProperty("user.home")+"/Documents/qrImages"+filePath)));
					//System.out.println(System.getProperty("user.home"));
					image = image.scaleByWidth(imageWidth);
								
					Row<PDPage> row = table.createRow(12);
							    
							    
					cell = row.createImageCell(10 , image);
					cell.setRightBorderStyle(thinline);
					cell = row.createCell(15, nombre, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
					cell = row.createCell(25, "Asistente", HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
				}
			}
		}
	
		table.draw();
		contentStream.close();
	
		// Save the results and ensure that the document is properly closed:
		String pdfPath = System.getProperty("user.home")+"/Documents/CredencialesAsistentes.pdf";
		File file = new File(pdfPath);
		document.save(file);
		document.close();

    }
    
    public static void obtenerEtiquetas(String array) throws WriterException, IOException, SQLException{
    		
		JSONObject obj = new JSONObject(array);
		JSONArray selectedPeople = obj.getJSONArray("selectedPeople");
		
		CrearQr crearqr = new CrearQr();
	    //Creación de documento
	    PDDocument document = new PDDocument();
	    PDPage page = new PDPage();
	    document.addPage( page );
	    PDPageContentStream contentStream = new PDPageContentStream(document, page);
	    PDFont font = PDType1Font.HELVETICA;
			
	    //Insertar título
		contentStream.beginText();
		contentStream.setFont( font, 12 );
		contentStream.moveTextPositionByAmount(100, 700);
		contentStream.drawString( "Etiquetas para alumnos" );
		contentStream.endText();
	
		
		float margin = 50;
		// starting y position is whole page height subtracted by top and bottom margin
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		// we want table across whole page width (subtracted by left and right margin ofcourse)
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
	
		boolean drawContent = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
			// y position is your coordinate of top left corner of the table
		float yPosition = 650;
	
		BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);
		float imageWidth = 80; //Para ajustar la imagen
			
		Row<PDPage> headerRow = table.createRow(15f);
			
		Cell<PDPage> cell = headerRow.createCell(50, "Etiquetas para micas");
		table.addHeaderRow(headerRow);
		LineStyle thinline = new LineStyle(Color.WHITE, 0.75f);
		
		if(selectedPeople.length() == 0) {
			System.out.println("No llego nada");
		}else {
			for (int i = 0; i < selectedPeople.length(); i++) {
						
				JSONObject person = selectedPeople.getJSONObject(i);
				if(person.has("idStudent")) {
					String id=person.getString("idStudent");
					String nombre = person.getString("name");
					
					crearqr.generateQRCodeImage(id+"-S-F", nombre);
							
					String filePath="/"+id+"-S-F.png";
					Image image = new Image(ImageIO.read(new File(System.getProperty("user.home")+"/Documents/qrImages"+filePath)));
					image = image.scaleByWidth(imageWidth);
					System.out.println(System.getProperty("user.home"));		
					Row<PDPage> row = table.createRow(12);
							    
							    
					cell = row.createImageCell(10 , image);
		    	    		cell.setRightBorderStyle(thinline);
		    	    		cell = row.createCell(15, nombre, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
		    	    		cell = row.createCell(25, "Tareas <br />" + nombre, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
				}
	
			}
		}
	
		table.draw();
		contentStream.close();
	
		String pdfPath = System.getProperty("user.home")+"/Documents/EtiquetasAlumnos.pdf";
		File file = new File(pdfPath);
		document.save(file);
		document.close();

    }
    
    public static String getListaAdeudos() {
    		
		StringWriter swriter = new StringWriter();
	    try {
	        String getQueryStatement = "SELECT * FROM alumno as al WHERE al.adeudo=1;";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	
	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        	gen.writeStartObject();
	            gen.writeStartArray("studentMissingPayment");
	            while(rs.next()) {
	            		System.out.println(rs.getString(1) + " " + rs.getString(3)+" "+rs.getString(2));
	                gen.writeStartObject();
		                gen.write("name", ""+rs.getString(3)+ " " + rs.getString(2));
		                gen.write("idStudent", ""+rs.getString(1));
		                gen.write("startDate", ""+rs.getString(7));
		                gen.write("nivel", ""+rs.getString(9));
	                gen.writeEnd();
	            }
	            gen.writeEnd();
	            gen.writeEnd();
	        }
	        return swriter.toString();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    
    public static String getPagosAlumno(String idStudent) {
		StringWriter swriter = new StringWriter();
		System.out.println("idStudent"+ idStudent);
	    try {
	        String getQueryStatement = "Select * from colegiatura where Alumno_idAlumno='"+idStudent+"' order by año ASC , mes ASC;";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	        int i=0;
	        List<String> payments=new ArrayList<>();
	        
	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        		gen.writeStartObject();
	        		gen.writeStartArray("paymentsStudent");
		        int bandera=0;
		            if (!rs.isBeforeFirst()){
		            		//ResultSet is empty
		            		System.out.println("is empty");
		            		gen.writeStartObject();
			        		gen.write("err", 1);
			        		gen.write("messageError", "No tiene pagos registrados");
			        		gen.writeEnd();
			        		
			        		gen.writeEnd();
						gen.writeEnd();
		    				//gen.writeEnd();
		            	}else {
				        while(rs.next()) {
					        	if(payments.isEmpty()) {
					        		payments.add(rs.getString(6));
					        		gen.writeStartObject();
					        		gen.write("year", rs.getString(6));
				        			gen.writeStartArray("months");
					        			gen.writeStartObject();
						        			gen.write("idPayment", rs.getString(1));
						        			gen.write("month", rs.getString(5));
						        			gen.write("quantity", ""+rs.getString(2));
						        			gen.write("typePayment", rs.getString(4));
					        				gen.write("date", rs.getString(9));
					        				gen.write("card", rs.getString(10));
					        			gen.writeEnd(); //Cierra el objeto de 1 alumno
					        		//gen.writeEnd(); //Cierra el array de alumnos
					        	}else {
				        			for(i=0; i<payments.size(); i++) {
					        			if(payments.get(i).equals(rs.getString(6))) {
					        				//System.out.println("agrego nuevo año " + rs.getString(6) + " mando a crear nuevo año");
					        				
					        				gen.writeStartObject();
					        					gen.write("idPayment", rs.getString(1));
						        				gen.write("month", rs.getString(5));
						        				gen.write("quantity", rs.getString(2));
						        				gen.write("typePayment", ""+rs.getString(4));
						        				gen.write("date", rs.getString(9));
						        				gen.write("card", rs.getString(10));
						        			gen.writeEnd();
						        		
					        				bandera=0;
					        			}else {
					        				bandera=1;
					        			}
				        			}
				        			
				        			if(bandera==1) {
				        				//System.out.println("si agrego");
				        				
				        				gen.writeEnd();
				        				gen.writeEnd();
				        				
				        				gen.writeStartObject();
				        					gen.write("year", rs.getString(6));
					        				gen.writeStartArray("months");
					        				gen.writeStartObject();
						        				gen.write("idPayment", rs.getString(1));
						        				gen.write("month", rs.getString(5));
						        				gen.write("quantity", rs.getString(2));
						        				gen.write("typePayment", ""+rs.getString(4));
						        				gen.write("date", rs.getString(9));
						        				gen.write("card", rs.getString(10));
						        			gen.writeEnd(); //Cierra el objeto de 1 alumno
		
					        			
				        				payments.add(rs.getString(6));
				        			}
					        	}
				        	}
				        gen.writeEnd();
						gen.writeEnd();
						gen.writeEnd();
						gen.writeEnd();
		            	}
	        }
	        return swriter.toString();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    public static String setColegiatura(String array) {
		StringWriter swriter = new StringWriter();
		System.out.println("llega a pagar colegiatura"+array);
		
	    try {
	    		JSONObject obj = new JSONObject(array);
	    		
			//JSONArray selectedPeople = obj.getJSONArray("infoAssistant");
			
			JSONObject colegiatura = obj.getJSONObject("infoPayment");
			
			CallableStatement cStmt = conn.prepareCall("{call setColegiatura(?,?,?,?,?,?,?,?,?,?)}");
			
			int card=0;
			if(colegiatura.getString("card").length() == 0) card=0; else card=Integer.parseInt(colegiatura.getString("card"));
			
			cStmt.setInt(1, colegiatura.getInt("idStudent"));
		    cStmt.setFloat(2, colegiatura.getFloat("quantity"));
		    cStmt.setInt(3, colegiatura.getInt("month")+1);
		    cStmt.setInt(4, colegiatura.getInt("year"));
		    cStmt.setInt(5, card);
		    cStmt.setInt(6, colegiatura.getInt("idRecepcionista"));
		    cStmt.setString(7, colegiatura.getString("type"));
		    cStmt.registerOutParameter(8, Types.INTEGER);
		    cStmt.registerOutParameter(9, Types.INTEGER);
		    cStmt.registerOutParameter(10, Types.VARCHAR);
		    cStmt.execute(); 
		    
		    String messageError, nombre, typeUsuario;
			int err, debe;
			
			debe=cStmt.getInt(8);
			err=cStmt.getInt(9);
			messageError=cStmt.getString(10);
	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        		gen.writeStartObject();
	        		gen.writeStartObject("response");
		        		gen.write("err", ""+err);
		        		gen.write("messageError", ""+messageError);
		        		gen.write("debe", ""+debe);
	        		gen.writeEnd();
	        		gen.writeEnd();
	        }
	    	
	        return swriter.toString();
	    	} catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
