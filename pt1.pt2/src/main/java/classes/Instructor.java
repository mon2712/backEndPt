package classes;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
    
    
    public static void obtenerGafetesAlumnos() throws WriterException, IOException{
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
    
    public static void obtenerCredenciales(String array) throws WriterException, IOException{
    				
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
				if(person.has("idStudent")) {
					String id=person.getString("idAssistant");
					String nombre = person.getString("name");
					
					crearqr.generateQRCodeImage(id+"A", nombre);
							
					String filePath="/"+id+"A.png";
					Image image = new Image(ImageIO.read(new File(System.getProperty("user.home")+"/Documents/qrImages"+filePath)));
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
		String pdfPath = System.getProperty("user.home")+"/Documents/Credenciales Asistentes.pdf";
		File file = new File(pdfPath);
		document.save(file);
		document.close();

    }
    
    public static void obtenerEtiquetas(String array) throws WriterException, IOException{
    		
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
					
					crearqr.generateQRCodeImage(id+"S", nombre);
							
					String filePath="/"+id+"S.png";
					Image image = new Image(ImageIO.read(new File(System.getProperty("user.home")+"/Documents/qrImages"+filePath)));
					image = image.scaleByWidth(imageWidth);
								
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
}