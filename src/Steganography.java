import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Steganography {
	
	private static String message = "Test enconding to binary";

	public static void main(String[] args) throws IOException, InterruptedException {
		Path image = Paths.get("ASTRO.bmp");
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
		
			String bitRepresentation = listImage.get(i);
			boolean isNegative = bitRepresentation.charAt(0) == '-';
														 
			if(isNegative && !bitRepresentation.equals("-10000000")) {
				bitRepresentation = twosComplement(bitRepresentation.replace("-", ""));
			}
			//TODO UNDERSTAND WHY WORKS FOR -10000000
			// -10001001 = -137 -> WHY NOT -119?
			// -10000000 =  -128
			byte bt = Byte.parseByte(bitRepresentation,2);
			newImage[i] = isNegative ? (byte)(bt*-1) : bt;
		}
		
		File newFile = new File("newFile.bmp");
		FileUtils.writeByteArrayToFile(newFile, bytes);
		
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
		for (int i = 20; i < listImage.size() && countMessage + 1 < listMessage.size();i++) {
				String bits = listImage.get(i);
				bits = bits.substring(0, bits.length()-2)
						+listMessage.get(countMessage)
						+listMessage.get(++countMessage);
				countMessage++;
				listImage.set(i,bits);
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
	 * If the byte is negative add a sign - . So we know we should use 
	 * decimal from signed 2's complement when recreate the image
	 * @param bytes
	 * @param listImage
	 */
	private static void convertingBytesOfImageToBits(byte[] bytes, List<String> listImage) {
		for (byte b : bytes) {
			 listImage.add((b < 0 ? "-" : "")+toBitString(b));
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
	private static String twosComplement(String bin) {
        String twos = "", ones = "";

        for (int i = 0; i < bin.length(); i++) {
            ones += flip(bin.charAt(i));
        }
        StringBuilder builder = new StringBuilder(ones);
        boolean b = false;
        for (int i = ones.length() - 1; i > 0; i--) {
            if (ones.charAt(i) == '1') {
                builder.setCharAt(i, '0');
            } else {
                builder.setCharAt(i, '1');
                b = true;
                break;
            }
        }
        if (!b) {
        	builder.deleteCharAt(0);
        	builder.insert(0, "11111111");
        }
        twos = builder.toString();
        return twos;
    }

    private static char flip(char c) {
        return (c == '0') ? '1' : '0';
    }
	
}
