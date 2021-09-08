import java.util.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class Encoder {
    int maxSize = 512;
    String inputPath = "lzw-file1.txt";
    String outputPath = "output.dat";

    public void encode() {
        try {
            // Read input
            String input = Files.readString(Paths.get(inputPath), StandardCharsets.UTF_8);

            // Initialize output
            ArrayList<Byte> outputList = new ArrayList<Byte>();
            
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
                    outputList.add(dictionary.get(encodedStr).byteValue());
                } else  if (!dictionary.containsKey(encodedStr + input.charAt(i + 1))) {
                    outputList.add(dictionary.get(encodedStr).byteValue());
                    if (dictionary.size() < maxSize) {
                        dictionary.put(encodedStr + input.charAt(i + 1), dictionary.size());
                    }
                    encodedStr = "";
                }
            }

            // Write output
            byte[] output = new byte[outputList.size()];
            for (int i = 0; i < outputList.size(); i++) {
                output[i] = outputList.get(i).byteValue();
            }
            Files.write(Paths.get(outputPath), output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void test() {
        try {
            byte[] input = Files.readAllBytes(Paths.get(outputPath));
            for (int i = 0; i < input.length; i++) {
                System.out.println(input[i]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Encoder encoder = new Encoder();
        encoder.encode();
        encoder.test();
    }
}
