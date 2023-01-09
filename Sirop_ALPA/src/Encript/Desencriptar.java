package Encript;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Desencriptar {

	public Desencriptar() {
		// TODO Auto-generated constructor stub
	}
	
	public static String Desencriptar(String textoEncriptado) throws Exception {
		 
        String secretKey = "fgfh02"; //llave para desenciptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        
        }
        
        return base64EncryptedString;
	}
	
	public String[] desencriptarDoc(String[] cadena) throws Exception{
		
		for(int i=0;i<cadena.length;i++){
			cadena[i] = Desencriptar(cadena[i]);
			System.out.println(cadena[i]);
		}
		
		return cadena;
	}

}
