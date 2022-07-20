package com.paulo.steganography;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SteganographyTest {
    @Test
    void testConvertIntToChar() throws Exception {
        System.out.println("======TEST ONE EXECUTED=======");
        Method method = Steganography.class.getDeclaredMethod("convertIntToChar", int.class);
        method.setAccessible(true);
        Assertions.assertEquals('5', method.invoke(null, 5));
    }

    @Test
    void testReverseArray() throws Exception {
        System.out.println("======TEST TWO EXECUTED=======");
        Method method = Steganography.class.getDeclaredMethod("reverseArray", char[].class, int.class);
        method.setAccessible(true);
        char[] arr = new char[] { '1', '2', '3' };

        Assertions.assertArrayEquals(new char[] { '3', '2', '1' }, (char[]) method.invoke(null, arr, arr.length));
    }

    @Test
    void testFlipBits() throws Exception {
        System.out.println("======TEST THREE EXECUTED=======");
        Method method = Steganography.class.getDeclaredMethod("flipBits", char[].class);
        method.setAccessible(true);
        char[] arr = new char[] { '1', '0', '1', '1' };

        Assertions.assertArrayEquals(new char[] { '0', '1', '0', '0' }, (char[]) method.invoke(null, arr));
    }

    @Test
    void testSumOneToBinary() throws Exception {
        System.out.println("======TEST FOUR EXECUTED=======");
        Method method = Steganography.class.getDeclaredMethod("sumOneToBinary", char[].class);
        method.setAccessible(true);

        char[] arr = new char[] { '0', '1', '1', '0', '0', '1', '1', '1' };
        char[] expectedResult = new char[] { '0', '1', '1', '0', '1', '0', '0', '0' };

        Assertions.assertArrayEquals(expectedResult, (char[]) method.invoke(null, arr));

        char[] arr2 = new char[] { '0', '0', '0', '1', '1', '0', '1', '1' };
        char[] expectedResult2 = new char[] { '0', '0', '0', '1', '1', '1', '0', '0' };
        Assertions.assertArrayEquals(expectedResult2, (char[]) method.invoke(null, arr2));
    }

    @Test
    void testConvertTwoComplementBinaryToUnsignedBinary() throws Exception {
        System.out.println("======TEST FIVE EXECUTED=======");
        Method method = Steganography.class.getDeclaredMethod("convertTwoComplementBinaryToUnsignedBinary",
                String.class);
        method.setAccessible(true);

        String twoComplementBinary = "10011000";
        Assertions.assertEquals("01101000", method.invoke(null, twoComplementBinary));

        String twoComplementBinary2 = "10101011";
        Assertions.assertEquals("01010101", method.invoke(null, twoComplementBinary2));
    }

    @Test
    void testConvertBinaryToByteArray() throws Exception {
        System.out.println("======TEST SIX EXECUTED=======");
        Method method = Steganography.class.getDeclaredMethod("convertBinaryToByteArray", List.class);
        method.setAccessible(true);

        List<String> binaryList = new ArrayList<String>(Arrays.asList("00000001","00101000","00000001"));
        byte[] expectedResult = new byte[]{-1,-40,-1};

        Assertions.assertArrayEquals(expectedResult, (byte[])method.invoke(null, binaryList));
    }

}
