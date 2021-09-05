import java.util.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class Encoder {
    int maxSize = 512;
    String inputPath = "lzw-file1.txt";
    String outputPath = "output.txt";

    public void encode() {
        try {
            // Read input
            String input = Files.readString(Paths.get(inputPath), StandardCharsets.UTF_8);

            // Initialize output
            String output = "";
            FileOutputStream fos = new FileOutputStream(new File(outputPath));
            DataOutputStream dos = new DataOutputStream(fos);
            
            // Initialize ASCII dictionary
            HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
            for (int i = 0; i <= 255; i++) {
                dictionary.put(String.valueOf((char) i), i);
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
            dos.writeBytes(output);
            dos.close();
            fos.close();

            // Convert input to binary to compare with output
            String inputBinary = "";
            char[] inputChars = input.toCharArray();
            for (char c : inputChars) {
                inputBinary += convertBinary(Integer.toBinaryString(c), 7);
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
        encoder.encode();
    }
}
