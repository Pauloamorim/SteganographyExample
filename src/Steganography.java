import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Steganography {
	
	private static String message = "Test enconding to binary";

	public static void main(String[] args) throws IOException {
		Path image = Paths.get("download.png");
		byte[] bytes = Files.readAllBytes(image);
		
		List<String> listImage = new ArrayList<>();
		List<Character> listMessage = new ArrayList<>();
		
		//adding \q to know where I should stop on decoding
		message = message.concat("\\q");
				
		convertingBytesOfImageToBits(bytes, listImage);
		convertingMessageToListOfCharacter(listMessage);
		changeLeastSignificantBits(listImage, listMessage);
		
		//creating another image with the updated list of bits
		byte[] newImage = new byte[listImage.size()];
		for(int i = 0; i < listImage.size();i++) {
			newImage[i] = Byte.parseByte(listImage.get(i));
		}
		
		
	}
	/**
	 * Iterate over List<Image>, for each sequence of 8 bits
	 * Get the least significant bits, in this case the last 2
	 * change the value with the bit of message we want to hide
	 * @param listImage
	 * @param listMessage
	 */
	private static void changeLeastSignificantBits(List<String> listImage, List<Character> listMessage) {
		int countMessage=0;
		for (int i = 0; i < listImage.size();i++) {
			if(countMessage + 1 < listMessage.size()) {
				String bits = listImage.get(i);
				bits = bits.substring(0, bits.length()-2)
						+listMessage.get(countMessage)
						+listMessage.get(++countMessage);
				countMessage++;
				listImage.set(i,bits);
			}else {
				break;
			}
		}
	}
	/**
	 * Get the bytes of message, convert into a String representation of bits. Add these bits
	 * in a List<Character>
	 * @param listMessage
	 */
	private static void convertingMessageToListOfCharacter(List<Character> listMessage) {
		for(byte b : message.getBytes()) {
			for(char c : toBitString(b).toCharArray()) {
				listMessage.add(c);
			}
		}
	}
	/**
	 * Converting the bytes into a String representation of bits value.
	 * @param bytes
	 * @param listImage
	 */
	private static void convertingBytesOfImageToBits(byte[] bytes, List<String> listImage) {
		for (byte b : bytes) {
			 listImage.add(toBitString(b));
		}
	}
	/**
	 * Converting byte in String representation of bits
	 * @param val
	 * @return
	 */
	private static String toBitString(final byte val) {
		return String.format("%8s", Integer.toBinaryString(val & 0xFF))
	               .replace(' ', '0');
	}		
	
}
