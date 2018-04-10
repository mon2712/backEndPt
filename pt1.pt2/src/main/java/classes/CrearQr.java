package classes;

import java.io.IOException;
import java.nio.file.FileSystems;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

public class CrearQr {
	private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";
	
	 public void generateQRCodeImage(String id, String nombre) throws WriterException, IOException {
		 	int width, height;
		 	width=350; 
		 	height=350;
		 	String filePath="./"+id+".png";
		 	System.out.println(filePath);
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix bitMatrix = qrCodeWriter.encode(id, BarcodeFormat.QR_CODE, width, height);

	        java.nio.file.Path path = FileSystems.getDefault().getPath(filePath);
	        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	 }
	 
	 /*public static void main(String[] args) {
	        try {
	            generateQRCodeImage("This is my first QR Code", 350, 350, QR_CODE_IMAGE_PATH);
	        } catch (WriterException e) {
	            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
	        } catch (IOException e) {
	            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
	        }
	 }*/
}
