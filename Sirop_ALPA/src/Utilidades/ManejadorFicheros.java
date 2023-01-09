/**
 * 
 */
package Utilidades;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

/**
 * @author Graco
 *
 */
public class ManejadorFicheros {

	/**
	 * 
	 */
	private File archivo;
	private BufferedWriter bw;
	private FileReader fr = null;
    private BufferedReader br = null;
    private byte[] file_byte;
	
	public ManejadorFicheros(String titulo) {
		archivo = new File(titulo);
	}
	
	public ManejadorFicheros() {
	
	}
	
	public void crear_fichero(String[] texto) throws IOException{
		if(archivo.exists()) {
			bw = new BufferedWriter(new FileWriter(archivo));
		    bw.write("El fichero de texto ya estaba creado.");
		} else {
			 bw = new BufferedWriter(new FileWriter(archivo));
		     if(texto.length > 0){
		    	 
		    	 for(int i = 0;i < texto.length; i++){
		    		 bw.write(texto[i]);// escribo el documento linea a linea
		    		 bw.newLine();//agrego una nueba linea
		    	 }
		    	 
		     }else{
		    	 bw.write("Acabo de crear el fichero de texto.");
		     }//fin if
			 
		}//fin if
		
		bw.close();
	}//fin de crear
	
	public String[] leerFichero() throws IOException {
		String[] cadena = new String[100];
		int cont=0;
		 try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
			 
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         while((linea=br.readLine())!=null){
	            cadena[cont++] = linea;
	            System.out.println(linea);
	         }
	         
		 }catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
		 
		 return cadena;
	   
	}//fin de leer
	
	public void escribe_Fichero(String[] texto){
		FileWriter fichero = null;
        PrintWriter pw = null;
        
        try
        {
            fichero = new FileWriter(archivo);
            
            pw = new PrintWriter(fichero);

            for (int i = 0; i < texto.length; i++){
                pw.println(texto[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}//fin d escribe
	      
	
	public byte[] get_file_byte(String ruta){
		File archivo = new File(ruta);
		byte[] archivoBytes = null;

		long tamanoArch = archivo.length(); //Nos quedamos con el tamaño del archivo.

		//Con esta condicional controlamos si el archivo es demasiado grande
		if (tamanoArch > Integer.MAX_VALUE){
			JOptionPane.showMessageDialog(null, "El archivo es muy grande!");
		}else{
			archivoBytes = new byte[(int) tamanoArch]; //Le damos al array el tamaño del archivo.

			try{
				//Nos creamos esta variable para poder leer el archivo.
				FileInputStream docu = new FileInputStream(archivo);

				//Leemos los bytes del archivo y a la vez se van insertando en el array de bytes creado.
				int numBytes = docu.read(archivoBytes);

				docu.close(); //Muy importante cerrar tras la lectura.
			}catch (FileNotFoundException e){
				JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo.");
			}catch (IOException e){
				JOptionPane.showMessageDialog(null,"No se ha podido leer el archivo.");
			} 
		}
       
		return archivoBytes;
	}
	
	
	public File get_file_from_byte(byte[] file) throws IOException{
		File file2 = new File("c:\\demo.txt");
		 
		BufferedOutputStream bos = null;
	      
	    try {
	      FileOutputStream fos = new FileOutputStream(file2);
	      bos = new BufferedOutputStream(fos); 
	      bos.write(file);
	    }finally {
	      if(bos != null) {
	        try  {
	          //flush and close the BufferedOutputStream
	          bos.flush();
	          bos.close();
	        } catch(Exception e){}
	      }
	    }
	    
	    return file2;
	}///
	
}//final de la clase
	 


