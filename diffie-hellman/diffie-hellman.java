import java.math.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;

public class Apoorva {
	// define static varibale and initialize them
	static String p_value = null;
	static String g_value = null;
	static String y_value = null;

	// define BIGINTEGER type Variable 
	
	private static BigInteger numbig_g;
	
	private static BigInteger numbig_p;

	private static BigInteger numbig_y;

	//starting main class with  Input/Output exception(if any)
	public static void main(String[] args) throws IOException 
	{
		
			//created file type object and passing file name where I put values p,g,q
			// getting input form file(key-value pair) and loading it in static variable and closing file
			File Input_file = new File("sample.properties");
			FileInputStream file_Input = new FileInputStream(Input_file);
			Properties property = new Properties();
			property.load(file_Input);
			file_Input.close(); // closing the file
			
			g_value = property.getProperty("g");// loading value in variables from property object
			p_value = property.getProperty("p");// loading value in variables from property object 
			y_value = property.getProperty("y");// loading value in variables from property object

			
			numbig_g = new BigInteger(g_value);// converting p_value to biginteger value(numbig_g)
			numbig_p = new BigInteger(p_value);// converting p_value to biginteger value(numbig_p) 
			numbig_y = new BigInteger(y_value);// converting p_value to biginteger value(numbig_y)


			System.out.println("value of p == " + " " + numbig_p);// printing values of as biginteger
			System.out.println("value of g == " + " " + numbig_g);// printing values of as biginteger
			System.out.println("value of y == " + " " + numbig_y);// printing values of as biginteger

		
		BigInteger bi_G, bi_P, yc, S1, S2, ys, s; // 's' is shared secert 
		   
		   //Creating client side.
		   BigInteger exponent1 = new BigInteger("250935226");
		   bi_G = numbig_g;
		   bi_P = numbig_p;
		   yc = bi_G.modPow(exponent1, bi_P);
		   String client_public_key = bi_G + "^" + exponent1 + " mod " + bi_P + " is " + yc;
		   System.out.println( "the value of client public key :" + client_public_key );
			
			
			// creating server side.
			BigInteger exponent2 = numbig_y;
		    	S1 = numbig_g;
		    	S2 = numbig_p;
			ys = S2.modPow(exponent2, S2);
		   	String server_public_key = S1 + "^" + exponent2 + " mod " + S2 + " is " + ys;
			System.out.println( "Server public key :" + server_public_key );
			
			
			
			//Generating shared secret.
		
			s = ys.modPow(exponent1, bi_P);
			System.out.println( " shared Secret is (1) " + "         " + s );
			
			//Generating shared secret at server side.
			
			s = yc.modPow(exponent2, S2);
			System.out.println( " shared Secret is (2) " + "         " + s ); 
		
	}
		
}