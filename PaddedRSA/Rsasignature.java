package rsa;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

public class Rsasignature {


private static String valueofn;
private static String valueofd;
private static String valueofe;

public static void main(String[] args) throws  InvalidKeyException, SignatureException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, IOException, NoSuchProviderException   {

	
	
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    
    
    // taking data from file sample1.properties
    File file_input = new File("sample1.properties");
    FileInputStream Input_file = new FileInputStream(file_input);
	Properties properties = new Properties();
	properties.load(Input_file);
	Input_file.close();// Closing file after taking i/p values

	//taking values of n,d,e from sample1.properties
	valueofn = properties.getProperty("n");
	valueofd = properties.getProperty("d");
	valueofe = properties.getProperty("e");

	// printing values of n,d,e which we have taken from sample1.properties file through key
	System.out.println(" value of n " + " =  "+ valueofn);
	System.out.println("value of d " + " = " + valueofd);
	System.out.println("value of e" + " =  " + valueofe);
    
	// converting values of n, d, e into biginteger
	BigInteger n_value = new BigInteger(valueofn);
	BigInteger d_value = new BigInteger(valueofd);
	BigInteger e_value = new BigInteger(valueofe);

	 // creating the file name for which we are creating the signature
           String file_name = "Apoorva_sample.txt";
	
	// creating keyspace and passing values to get the private key, that we are using to sign the file
	KeySpec KEYS_private= new RSAPrivateKeySpec(n_value, d_value);
	PrivateKey private_key = KeyFactory.getInstance("RSA").generatePrivate(KEYS_private);
	
	// creating keyspace and passing values to get the public key, that we are using to sign the file
	KeySpec KEYS_public = new RSAPublicKeySpec(n_value, e_value);
	PublicKey public_key = KeyFactory.getInstance("RSA").generatePublic(KEYS_public);
 			    
            // now we are signing Signature for the above filename that we have taken

			Signature signature_instance = Signature.getInstance("SHA256withRSA","BC");
			signature_instance.initSign(private_key);///
			signature_instance.update((file_name).getBytes());
			byte[] signature = signature_instance.sign();

            // creating the hash for the filname for signing the signature 
			MessageDigest digest = MessageDigest.getInstance("SHA256", "BC");
			byte[] hash_array = digest.digest((file_name).getBytes());
			
			// now we are changing the signature to the form where we can get padding, ASN header, signature
			Cipher cipher = Cipher.getInstance("RSA","BC");
			cipher.init(Cipher.DECRYPT_MODE, public_key);
			byte[] cipher_Text = cipher.doFinal(signature);




// coverting the hash value of the filename to the hexadecimal value 
String hexvalueof_hash = DatatypeConverter.printHexBinary(hash_array);
String hexvalueof_signature = DatatypeConverter.printHexBinary(signature);
String hexvalueof_cipher = DatatypeConverter.printHexBinary(cipher_Text);

// printing the value of the all the variable such as file_name, hexvalueof_hash, hashvalueof_signature, hexvalueof_cipher 
System.out.println("File name which is being signed : " + " " + file_name);
System.out.println(" Hash of the file name : " + " " + hexvalueof_hash);
System.out.println("RSA Signature of the given file name:"+" "+ hexvalueof_signature);
System.out.println(" sig ^ e mod n: " + " " + hexvalueof_cipher);




}
	
}