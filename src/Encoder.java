import java.util.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class Encoder {
    public void encode(String inputPath, int maxSize) {
        try {
        	//create start time
        	long startTime = System.nanoTime();
            // Read input
            String input = Files.readString(Paths.get(inputPath + ".txt"), StandardCharsets.UTF_8);

            // Initialize output
            String output = "";
            
            // Initialize ASCII dictionary
            HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
            for (int i = 0; i <= 255; i++) {
                dictionary.put(String.valueOf((char) i), i);
            //create end time
            long endTime = System.nanoTime();
            //return total duration of encode() method in milliseconds
            System.out.println("duration: "+((endTime - startTime)/1000000)+" milliseconds");
            }

            // Encode output
            
            String encodedStr = "";
            for (int i = 0; i < input.length(); i++) {
                encodedStr += input.charAt(i);
                if (i == input.length() - 1) {
                    output += convertBinary(Integer.toBinaryString(dictionary.get(encodedStr)), Integer.toBinaryString(maxSize - 1).length());
                } else  if (!dictionary.containsKey(encodedStr + input.charAt(i + 1))) {
                    output += convertBinary(Integer.toBinaryString(dictionary.get(encodedStr)), Integer.toBinaryString(maxSize - 1).length());
                    if (dictionary.size() < maxSize) {
                        dictionary.put(encodedStr + input.charAt(i + 1), dictionary.size());
                    }
                    encodedStr = "";
                }
            }
            

            // Write output
            BinaryOut out = new BinaryOut(inputPath + ".dat");
            for (int i = 0; i < output.length(); i++) {
                if (output.charAt(i) == '0') {
                    out.write(false);
                } else {
                    out.write(true);
                }
            }
            out.flush();

            // Convert input to binary to compare with output
            String inputBinary = "";
            char[] inputChars = input.toCharArray();
            for (char c : inputChars) {
                inputBinary += convertBinary(Integer.toBinaryString(c), 8);
            }
            System.out.println("\n" + "Input: " + inputBinary + "\n");
            System.out.println("Output: " + output + "\n");
            System.out.println("Input size: "+inputBinary.length()+"" + "\n");
            System.out.println("Output size: "+output.length()+"" + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String convertBinary(String binary, int bit) {
        String convertedBinary = binary;
        for (int j = 0; j < (bit - binary.length()); j++) {
            convertedBinary = "0" + convertedBinary;
        }
        return convertedBinary;
    }

    public static void main(String[] args) {
        Encoder encoder = new Encoder();
        encoder.encode("lzw-file1", 512);
        encoder.encode("lzw-file2", 512);
        encoder.encode("lzw-file3", 512);
    }
}
