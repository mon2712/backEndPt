package classes;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.line.LineStyle;

public class CrearPDF {
	
	public static void createStudentsIDs() throws IOException {
		
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage( page );
		
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		PDFont font = PDType1Font.HELVETICA;
		
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
	    float yPosition = 550;

	    BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);
	    

	    Image image = new Image(ImageIO.read(new File("./MyQRCode.png")));
		 float imageWidth = 70;
		 image = image.scaleByWidth(imageWidth);
	    
	    Row<PDPage> headerRow = table.createRow(15f);
	    
	    Cell<PDPage> cell = headerRow.createCell(100, "Gafetes Alumnos");
	    //table.addHeaderRow(headerRow);
	    
	    LineStyle thinline = new LineStyle(Color.WHITE, 0.75f);


	    Row<PDPage> row = table.createRow(12);
	    
	    
	    cell = row.createImageCell(25 , image);
	    //cell.setBorderStyle(thinline);
	    cell = row.createCell(25, "Montserrat Mendoza Romero", HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
	    cell = row.createCell(50, "Tareas", HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
	    
	    

	    table.draw();

		
		contentStream.close();

		// Save the results and ensure that the document is properly closed:
		document.save( "./HelloWorld.pdf");
		document.close();
	}
	
	public static void main (String args[]) throws IOException {
		
	     //Loading an existing document
	      //File file = new File("./sample.pdf");
	      PDDocument doc = new PDDocument();
	        
	      //Retrieving the page
	      PDPage page = new PDPage();
	      doc.addPage( page );
	       
	      //Creating PDImageXObject object
	      PDImageXObject pdImage = PDImageXObject.createFromFile("./MyQRCode.png",doc);
	       
	      //creating the PDPageContentStream object
	      PDPageContentStream contents = new PDPageContentStream(doc, page);

	      //Drawing the image in the PDF document
	      contents.drawImage(pdImage, 50, 350);

	      System.out.println("Image inserted");
	      
	      //Closing the PDPageContentStream object
	      contents.close();		
			
	      //Saving the document
	      doc.save("./sample.pdf");
	            
	      //Closing the document
	      doc.close();
	      
	      createStudentsIDs();

	} 
}
