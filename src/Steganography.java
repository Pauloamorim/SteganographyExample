import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Steganography {
	
	private static final int POSITION_START_CHANGE_BYTES = 150;
	private static String message = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit.\n" + 
			"\n" + 
			"Aenean commodo ligula eget dolor. Aenean massa.\n" + 
			"\n" + 
			"Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.\n" + 
			"\n" + 
			"Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem.\n" + 
			"\n" + 
			"Nulla consequat massa quis enim.\n" + 
			"\n" + 
			"Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.\n" + 
			"\n" + 
			"In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo.\n" + 
			"\n" + 
			"Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus.\n" + 
			"\n" + 
			"Vivamus elementum semper nisi. Aenean vulputate eleifend tellus.\n" + 
			"\n" + 
			"Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim.\n" + 
			"\n" + 
			"Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus.\n" + 
			"\n" + 
			"Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet.\n" + 
			"\n" + 
			"Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui.\n" + 
			"\n" + 
			"Etiam rhoncus.\n" + 
			"\n" + 
			"Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum.\n" + 
			"\n" + 
			"Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem.\n" + 
			"\n" + 
			"Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus.\n" + 
			"\n" + 
			"Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo.\n" + 
			"\n" + 
			"Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna.\n" + 
			"\n" + 
			"Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero.\n" + 
			"\n" + 
			"Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus.\n" + 
			"\n" + 
			"Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla.\n" + 
			"\n" + 
			"Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia.\n" + 
			"\n" + 
			"Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum.\n" + 
			"\n" + 
			"Sed aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris.\n" + 
			"\n" + 
			"Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus.\n" + 
			"\n" + 
			"Vestibulum volutpat pretium libero. Cras id dui. Aenean ut eros et nisl sagittis vestibulum.\n" + 
			"\n" + 
			"Nullam nulla eros, ultricies sit amet, nonummy id, imperdiet feugiat, pede. Sed lectus.\n" + 
			"\n" + 
			"Donec mollis hendrerit risus. Phasellus nec sem in justo pellentesque facilisis.\n" + 
			"\n" + 
			"Etiam imperdiet imperdiet orci. Nunc nec neque.\n" + 
			"\n" + 
			"Phasellus leo dolor, tempus non, auctor et, hendrerit quis, nisi.\n" + 
			"\n" + 
			"Curabitur ligula sapien, tincidunt non, euismod vitae, posuere imperdiet, leo. Maecenas malesuada.\n" + 
			"\n" + 
			"Praesent congue erat at massa. Sed cursus turpis vitae tortor. Donec posuere vulputate arcu.\n" + 
			"\n" + 
			"Phasellus accumsan cursus velit.\n" + 
			"\n" + 
			"Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed aliquam, nisi quis porttitor congue, elit erat euismod orci, ac placerat dolor lectus quis orci.\n" + 
			"\n" + 
			"Phasellus consectetuer vestibulum elit. Aenean tellus metus, bibendum sed, posuere ac, mattis non, nunc.\n" + 
			"\n" + 
			"Vestibulum fringilla pede sit amet augue. In turpis. Pellentesque posuere. Praesent turpis.\n" + 
			"\n" + 
			"Aenean posuere, tortor sed cursus feugiat, nunc augue blandit nunc, eu sollicitudin urna dolor sagittis lacus. Donec elit libero, sodales nec, volutpat a, suscipit non, turpis.\n" + 
			"\n" + 
			"Nullam sagittis. Suspendisse pulvinar, augue ac venenatis condimentum, sem libero volutpat nibh, nec pellentesque velit pede quis nunc.\n" + 
			"\n" + 
			"Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce id purus. Ut varius tincidunt libero. Phasellus dolor. Maecenas vestibulum mollis";

	public static void main(String[] args) throws IOException, InterruptedException {
		byte[] bytes = Files.readAllBytes(Paths.get("jpegsystems-home.bmp"));
		
		putHiddenMessageInImage(bytes);
		extractHiddenMessageInImage();
		
	
	}

	private static void extractHiddenMessageInImage() throws IOException {
		byte[] byteswithHidden = Files.readAllBytes(Paths.get("newFile.bmp"));
		
		//Converting new image to BIT representation
		List<String> listNewImageBits = new ArrayList<>();
		for (byte b : byteswithHidden) {
			listNewImageBits.add((b < 0 ? "-" : "")+toBitString(b));
		}
		
		String message = "";
		String temp = "";
		
		//Hidden message start at index 20
		for (int i = POSITION_START_CHANGE_BYTES; i < listNewImageBits.size();i++) {
			
			if(temp.length() == 0 || temp.length()%8 != 0) {
				temp += getTwoLeastSignificantBits(listNewImageBits.get(i), temp);
			}else {
				//converting the 8 bits representation to a Character and append in String
				message += new Character((char)Integer.parseInt(temp,2)).toString();
				
				//Check when the algorithm should stop to look for the message, in this case when found the sequence \q
				if(message.length() >= 2 &&	message.substring(message.length()-2,message.length()).equals("\\q")) {
					System.out.println(message.substring(0, message.length()-2));
					break;					
				}
				
				temp="";
				temp += getTwoLeastSignificantBits(listNewImageBits.get(i), temp);
						
			}
		}
	}

	/**
	 * Hidden message inside image <br>
	 * TODO MAP ALL STEPS TO PUT THE IMAGE
	 * @param bytes
	 * @throws IOException
	 */
	private static void putHiddenMessageInImage(byte[] bytes) throws IOException {
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
		//Write the new file with the message hidden
		File newFile = new File("newFile.bmp");
		FileUtils.writeByteArrayToFile(newFile, newImage);
	}

	/**
	 * Substring to return just the two least significant bits
	 * @param bitRepresentation
	 * @param temp
	 * @return
	 */
	private static String getTwoLeastSignificantBits(String bitRepresentation, String temp) {
		//Get only the lest two bits
		return bitRepresentation.substring(bitRepresentation.length()-2, bitRepresentation.length());
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
		for (int i = POSITION_START_CHANGE_BYTES; i < listImage.size() && countMessage + 1 < listMessage.size();i++) {
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
