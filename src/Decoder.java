import java.util.*;
import java.io.*;

public class Decoder {

	public static void decode(String inputFile, String outputFile) throws IOException
	{
		FileInputStream in = new FileInputStream(inputFile);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
		StringBuilder binary = new StringBuilder();
		byte[] bits = in.readAllBytes();
		if(bits.length==0)
		{
			out.println("Nothing in the file");
			out.close();
			return;
		}
		for(int i =0;i<bits.length;i++)
		{
			binary.append(toBinary(bits[i]));
		}
		
		System.out.println(binary);
		/*for(int i = 0 ;i<binary.length()-9;i+=9)
		{
			System.out.print(binary.substring(i,i+9)+" ");
		}System.out.println();
		*/
		
		ArrayList<Integer> binVals = new ArrayList<Integer>();
		
		for(int i = 0 ;i<=binary.length()-9;i+=9)
		{
			//System.out.print(binaryValue(binary.substring(i,i+9))+" ");
			binVals.add(binaryValue(binary.substring(i,i+9)));
		}
		System.out.println(binVals);
		
		HashMap<Integer, String> dict = new HashMap<Integer,String>();
		for(int i = 0;i<256;i++)
			dict.put(i, ""+ (char)i);
		
		int nextVal = 256;
		int old = binVals.get(0);
		String s = dict.get(old);
		String c = ""+s.charAt(0);
		out.print(s);
		for(int i = 1;i<binVals.size();i++)
		{
			int next = binVals.get(i);
			if(!dict.containsKey(next))
			{
				s = dict.get(old);
				s = s+c;
			}
			else
			{
				s = dict.get(next);
			}
			out.print(s);
			c = ""+s.charAt(0);
			dict.put(nextVal, dict.get(old)+c);
			nextVal++;
			old = next;
		}
		
		out.println();
		
		out.close();
	}
	public static int binaryValue(String a)
	{
		int ans = 0;
		for(int i = 0;i<9;i++)
		{
			if(a.charAt(i)=='1')
			{
				ans+=(1<<(8-i));
			}
		}
		return ans;
	}
	public static String toBinary(int a)
	{
		String cur =Integer.toBinaryString(a);
		StringBuilder ans = new StringBuilder();
		while(cur.length()+ans.length()<8)
		{
			ans.append("0");
		}
		if(cur.length()>8)
		{
			cur = cur.substring(cur.length()-8);
		}
		ans.append(cur);
		return ans.toString();
	}

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		//decode("lzw-file1.dat","lzw-decoded1.txt");
		//decode("lzw-file2.dat","lzw-decoded2.txt");
		decode("lzw-file3.dat","lzw-decoded3.txt");
		
		
	}

}
