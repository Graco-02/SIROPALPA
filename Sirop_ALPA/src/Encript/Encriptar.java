package Encript;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encriptar {

	/*
	 * Esta clase esta encargada de recivir un texto o archivo de texto y luego lo encripta
	 * */
	
	public Encriptar(){
		
	}
	
	public String Encriptar_texto(String texto) {
		
		 String secretKey = "fgfh02"; //llave para encriptar datos
	        String base64EncryptedString = "";
	 
	        try {
	 
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
	            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
	 
	            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
	            Cipher cipher = Cipher.getInstance("DESede");
	            cipher.init(Cipher.ENCRYPT_MODE, key);
	 
	            byte[] plainTextBytes = texto.getBytes("utf-8");
	            byte[] buf = cipher.doFinal(plainTextBytes);
	            byte[] base64Bytes = Base64.encodeBase64(buf);
	            base64EncryptedString = new String(base64Bytes);
	 
	        } catch (Exception ex) {
	        	
	        }
	       
	        return base64EncryptedString;
	}
	
	public String[] encriptarDoc(String[] cadena){
		
		for(int i=0;i<cadena.length;i++){
			cadena[i] = Encriptar_texto(cadena[i]);
		}
		
		return cadena;
	}

}
