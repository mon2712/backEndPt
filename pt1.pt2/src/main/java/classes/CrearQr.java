package classes;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.FileSystems;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

public class CrearQr {

	 public void generateQRCodeImage(String id, String nombre) throws WriterException, IOException {
		 	int width, height;
		 	width=350; 
		 	height=350;
		 	String filePath=System.getProperty("user.home")+"/Documents/qrImages/"+id+".png";
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        
	        BitMatrix bitMatrix = qrCodeWriter.encode("4", BarcodeFormat.QR_CODE, 350, 350);
	        
	        
	        java.nio.file.Path path = FileSystems.getDefault().getPath(filePath);
	        
	        
	        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	 }

}
