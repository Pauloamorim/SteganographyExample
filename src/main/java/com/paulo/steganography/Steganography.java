package com.paulo.steganography;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

// Article https://www.ripublication.com/ijaer18/ijaerv13n1_60.pdf

public class Steganography {

	// byte position in the image where I start to add the message
	private static final int POSITION_GET_BYTES = 50;
	private static final String ENCODE = "e";
	private static final String DECODE = "d";

	public static void main(String[] args) {

		String option = args[0];
		try {
			switch (option) {
				case ENCODE:
					encodeImage(args);
					break;
				case DECODE:
					decodeImage(args);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args[0] type of operation(e for Encode, d for Decode)
	 * @param args[1] path of Image
	 * @param args[2] Message to encode
	 * @param args[3] Path to output file
	 * @throws IOException
	 */
	private static void encodeImage(String[] args) throws Exception {
		String pathImage = args[1];
		validateImageFile(pathImage);

		List<String> binary = getBinaryList(pathImage);

		// code to identify when the message ends
		args[2] = args[2].concat(" \\q");
		String binaryMessage = prettyBinary(convertMessageToBinary(args[2]), 8, " ");

		List<String> updatedBinaryImage = changeLSB(binary, binaryMessage);
		byte[] byteArrWithMessage = convertBinaryToByteArray(updatedBinaryImage);

		writeFile(args[3], "_".concat(FilenameUtils.getName(pathImage)), byteArrWithMessage);

	}

	/**
	 * @param args[0] type of operation(e for Encode, d for Decoded
	 * @param args[1] path of Image
	 * @param args[2] Path to output file
	 * @throws IOException
	 */
	private static void decodeImage(String[] args) throws Exception {
		String pathImage = args[1];
		validateImageFile(pathImage);

		List<String> binary = getBinaryList(pathImage);

		String message = getHiddenMessage(binary);

		extractMessageOrFileHidden(args[2], message);

	}

	/**
	 * get the hiden message, if just text returns in an txt file
	 * if it's a file, return the file with the name "decoded.{extension}"
	 * @param args
	 * @param message
	 * @throws IOException
	 * @throws Exception
	 */
	private static void extractMessageOrFileHidden(String pathOutput, String message) throws IOException, Exception {
		// Check if the message is a file
		byte[] messageToReturn = null;
		String fileExtension = null;
		String fileName = message.trim();
		if (new File(fileName).exists()) {
			messageToReturn = Files.readAllBytes(new File(fileName).toPath());
			fileExtension = FilenameUtils.getExtension(fileName);
		} else {
			messageToReturn = message.getBytes();
			fileExtension = "txt";
		}

		// write txt or file with message
		writeFile(pathOutput, "decoded.".concat(fileExtension), messageToReturn);
	}

	/**
	 * @param basePath
	 * @param fileName
	 * @param data
	 * @throws Exception
	 */
	private static void writeFile(String basePath, String fileName, byte[] data) throws Exception {
		basePath = Optional.ofNullable(basePath).orElse("");
		File f = new File(basePath.concat(fileName));
		FileUtils.writeByteArrayToFile(f, data);
	}

	/**
	 * Function to go over the binary of the image, get only the LSB,
	 * convert to byte and convert back to Character
	 * Function will stop read when find the \q character
	 * 
	 * @param binaryList
	 * @return
	 */
	private static String getHiddenMessage(List<String> binaryList) {
		String tmp = "";
		String msg = "";
		for (int i = POSITION_GET_BYTES; i < binaryList.size(); i++) {
			String bImg = binaryList.get(i);
			// if less then 8 charactes, keep creating the binary
			if (tmp.length() == 0 || tmp.length() % 8 != 0) {
				tmp = tmp.concat(bImg.substring(bImg.length() - 1, bImg.length()));
			} else {
				// parse to byte and then to character
				msg = msg.concat(Character.toString((char) Integer.parseInt(tmp, 2)));
				// if for check when I Should Quit
				if (msg.length() >= 2 && msg.substring(msg.length() - 2, msg.length()).equals("\\q")) {
					msg = msg.substring(0, msg.length() - 2);
					break;
				}
				tmp = "";
				tmp = tmp.concat(bImg.substring(bImg.length() - 1, bImg.length()));
			}
		}
		return msg;
	}

	/**
	 * get image bytes, and for each byte create the respective two complement
	 * binary
	 * 
	 * @param pathImage
	 * @return
	 * @throws IOException
	 */
	private static List<String> getBinaryList(String pathImage) throws IOException {
		List<String> binary = new ArrayList<>();
		byte[] fileContent = Files.readAllBytes(new File(pathImage).toPath());
		for (byte b : fileContent) {
			binary.add(convertTwoComplementBinaryToUnsignedBinary(Integer.toBinaryString(b & 0xff)));
		}
		return binary;
	}

	/**
	 * get binary of image and message
	 * iterate over message binary and get each position of the binary and add in
	 * the
	 * Least Signifacant bit(LSB) of the image binaries
	 * 
	 * @param twoComplementBinary
	 * @param binaryMessage
	 * @return
	 */
	private static List<String> changeLSB(List<String> twoComplementBinary, String binaryMessage) {
		int indexList = POSITION_GET_BYTES;
		String[] bMessage = binaryMessage.split(" ");
		// Iterate over all binaries of the message
		for (int i = 0; i < bMessage.length; i++) {
			char[] chars = bMessage[i].toCharArray();
			// iterate over the 8 positions in each binary
			for (int j = 0; j < chars.length; j++) {
				char messageChar = chars[j];
				String binaryImg = twoComplementBinary.get(indexList);

				// Chaning LSB to the character of the composition of the message binary
				binaryImg = binaryImg.substring(0, binaryImg.length() - 1)
						.concat(Character.toString(messageChar));
				twoComplementBinary.set(indexList, binaryImg);
				indexList++;
			}
		}
		return twoComplementBinary;
	}

	/**
	 * get Text received and convert the message String to a Binary String
	 * 
	 * @param message
	 * @return
	 */
	private static String convertMessageToBinary(String message) {
		byte[] input = message.getBytes();
		StringBuilder result = new StringBuilder();
		for (byte b : input) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				result.append((val & 128) == 0 ? 0 : 1); // 128 = 1000 0000
				val <<= 1;
			}
		}
		return result.toString();
	}

	/**
	 * Add spaces and format binary
	 * 
	 * @param binary
	 * @param blockSize
	 * @param separator
	 * @return
	 */
	private static String prettyBinary(String binary, int blockSize, String separator) {

		List<String> result = new ArrayList<>();
		int index = 0;
		while (index < binary.length()) {
			result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
			index += blockSize;
		}

		return result.stream().collect(Collectors.joining(separator));
	}

	/**
	 * Validate if the path is a valid JPEG Image
	 * 
	 * @param pathImage
	 * @throws Exception
	 */
	private static void validateImageFile(String pathImage) throws Exception {
		if (!new File(pathImage).exists() ||
				!FilenameUtils.getExtension(pathImage).equals("jpeg")) {
			throw new Exception("File is not valid or does not exist.");
		}
	}

	/**
	 * Convert two complement binary to a byte array
	 * 
	 * @param binaryList
	 * @return
	 */
	private static byte[] convertBinaryToByteArray(List<String> binaryList) {
		byte[] imageBytes = new byte[binaryList.size()];

		for (int i = 0; i < binaryList.size(); i++) {
			String binary = binaryList.get(i);
			char[] chars = binary.toCharArray();
			if (chars[0] == 0 || chars.length < 8) {
				imageBytes[i] = Byte.parseByte(binary, 2);
			} else {
				// signed binary, using Integer since we could have +128
				int val = Integer.parseInt(new String(binary), 2);
				imageBytes[i] = (byte) (val * -1);
			}

		}
		return imageBytes;
	}

	/**
	 * Convert an two complement binary to a decimal representation
	 * 
	 * @param binary
	 * @return
	 */
	private static String convertTwoComplementBinaryToUnsignedBinary(String binary) {
		char[] chars = binary.toCharArray();
		if (chars[0] == 0 || chars.length < 8) {
			return binary;
		} else {
			// flip all bits
			char[] newBinary = flipBits(chars);
			newBinary = sumOneToBinary(newBinary);
			return new String(newBinary);
		}
	}

	/**
	 * Process to sum +1 to an binary, this is a required step to convert an
	 * two complement binary to a decimal value
	 * 
	 * @param newBinary
	 * @return
	 */
	private static char[] sumOneToBinary(char[] newBinary) {
		char[] reversedBinary = reverseArray(newBinary, newBinary.length);

		int remaining = 0;
		for (int i = 0; i < reversedBinary.length; i++) {
			int num = Integer.parseInt("" + reversedBinary[i]);
			if (i == 0) {
				// first value from the binary, add 1 to the value
				int sum = num + 1;
				reversedBinary[i] = sum == 2 ? '0' : convertIntToChar(sum);
				remaining = sum == 2 ? 1 : 0;
			} else {
				int sumValue = num + remaining;
				boolean sumEqualsTwo = sumValue == 2;
				reversedBinary[i] = sumEqualsTwo ? '0' : convertIntToChar(num + remaining);
				remaining = sumEqualsTwo ? 1 : 0;
			}
		}
		newBinary = reverseArray(reversedBinary, reversedBinary.length);
		return newBinary;
	}

	static char convertIntToChar(int val) {
		return String.valueOf(val).charAt(0);
	}

	static char[] reverseArray(char char_array[], int n) {
		char[] dest_array = new char[n];
		int j = n;
		for (int i = 0; i < n; i++) {
			dest_array[j - 1] = char_array[i];
			j = j - 1;
		}
		return dest_array;
	}

	/**
	 * For Two's Complement where the value is negative, we need to start flipping
	 * all
	 * values
	 * 
	 * @param binaryChars
	 * @return flipped binary value
	 */
	private static char[] flipBits(char[] binaryChars) {
		char[] newBinary = new char[binaryChars.length];
		for (int i = 0; i < binaryChars.length; i++) {
			char flipped = binaryChars[i] == '1' ? '0' : '1';
			newBinary[i] = flipped;
		}
		return newBinary;
	}
}