package Utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;

import Entidades.Reservas;

public class Imp_reserva {

	private File file;

	public Imp_reserva(Reservas reserva) {
                  
			  
	     byte[] bytes = reserva.getLista_invitados();
		
	     file = new File("lista.txt");
		 file.setExecutable(true);
		 file.setReadable(true);
		 file.setWritable(true);
		 
		 
		 try {
		 
		    OutputStream os = new FileOutputStream(file);
		    os.write(bytes);
		    os.close();
		    
		    
		    String[] texto = leer_archivo();
		    new Reserva_impresa(reserva,texto);
		 } catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		    e.printStackTrace();
		 }
	}
	
	private String[] leer_archivo()
		  {
		    String[] listainvitados = new String[20];
		    String[] listainvitados2 = null;
		    File archivo = null;
		    FileReader fr = null;
		    BufferedReader br = null;
		  
		    try
		    {
		      File archivo1 = new File("lista.txt");
		      fr = new FileReader(archivo1);
		      BufferedReader br1 = new BufferedReader(fr);
		      int indx = 0;
		      String linea;
		      while ((linea = br1.readLine()) != null)
		      {
		    	  listainvitados[(indx++)] = linea;
		      }
		       listainvitados2 = new String[indx];
		       
		       for(int i = 0;i<listainvitados2.length;i++){
		    	   listainvitados2[i] = listainvitados[i];
		       }
		       
		    }
		    catch (Exception e)
		    {
		      e.printStackTrace();
		    }
		    try
		    {
		      if (fr != null) {
		        fr.close();
		      }
		    }
		    catch (Exception e2)
		    {
		      e2.printStackTrace();
		    }
		    try
		    {
		      if (fr != null) {
		        fr.close();
		      }
		    }
		    catch (Exception e2)
		    {
		      e2.printStackTrace();
		    }
		    try
		    {
		      if (fr != null) {
		        fr.close();
		      }
		    }
		    catch (Exception e2)
		    {
		      e2.printStackTrace();
		    }
		    return listainvitados2;
		  }
	}

