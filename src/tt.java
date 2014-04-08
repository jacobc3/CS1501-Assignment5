import static org.junit.Assert.*;

import org.junit.Test;


public class tt {

	@Test
	public void test() {
		//StdOut.println((byte)'a');
		//StdOut.printf(Integer.toBinaryString((byte)'a'));
		
		 String b = Integer.toBinaryString((int)'a');
		if (b.length() < 8) {
            b = "000000000".substring(0, 8 - b.length()).concat(b);
        } else {
            b = b.substring(b.length() - 8);
        }

        System.out.print(b + " ");
		
	}

}
