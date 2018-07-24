import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Steganography {

	public static void main(String[] args) throws IOException {
		//File image = new File("download.png");
		Path image = Paths.get("download.png");
		byte[] bytes = Files.readAllBytes(image);
		
		
		for (byte b : bytes) {
			String bitRepresentation  = toBitString(b);
			System.out.println(b+"->  "+bitRepresentation 
					+ " -> length: "+bitRepresentation.length());
			
		}
	}
	public static String toBitString(final byte val) {
		return String.format("%8s", Integer.toBinaryString(val & 0xFF))
	               .replace(' ', '0');
	}		
	
}
