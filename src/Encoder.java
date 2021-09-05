import java.util.HashMap;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Encoder {
    int maxSize = 512;

    public void encode() {
        try {
            HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
            Path path = Paths.get("lzw-file1.txt");
            String input = Files.readString(path, StandardCharsets.UTF_8);
            String output = "";
            FileOutputStream fos = new FileOutputStream(new File("output.dat"));
            DataOutputStream dos = new DataOutputStream(fos);
            
            for (int i = 0; i <= 255; i++) {
                dictionary.put(String.valueOf((char) i), i);
            }
            input = "abcabcabcabcabcabcabcabcabcabcabcabc";
            
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
            
            dos.writeBytes(output);
            dos.close();
            fos.close();

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
