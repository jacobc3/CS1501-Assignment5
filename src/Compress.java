import java.io.IOException;

public class Compress{
   public static void main(String[] args) throws IOException{     
         //your program goes here
	   boolean stdin = true;
	   Out out = new Out("statistics.txt");
	   out.print();
	   AdaptiveHuffmanTree aht = new AdaptiveHuffmanTree();
	   int count = 0;	
		//StdOut.println("Origin String: " + );		
		//out.print(new In(filename).);		
	   if(!stdin){
			//String filename = "o1.bin";
			String filename = "class2.bin";
			//String filename = "abcdefg.in";			
			In in = new In(filename);
			out.println(new In(filename).readAll());	
			while (in.hasNextChar()) {
				char c = in.readChar();
				aht.update(c);
				count++;
			}
		} else {
			while (StdIn.hasNextChar()) {
				char c = StdIn.readChar();
				aht.update(c);
				count++;
			}
		}		
		//StdOut.println("\n"+aht);
		//StdOut.println(aht.output);
		int read = count*8;
		int transmitted = aht.output.length();
		float ratio = (float) (read-transmitted)/read;
		//StdOut.print("\t"+(float)(read-transmitted)/read);
		out.println("bits read = "+read);
		out.println("bits transmitted = "+transmitted);
		out.printf("compression ratio = %.1f",ratio*100);
   }
}