package Utilidades;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

public class Gestor_Imagenes {

	public Gestor_Imagenes() {
		// TODO Auto-generated constructor stub
	}

	
	public Image get_Imagen_Redimencionada(String ruta,int w,int h){
		ImageIcon img = new ImageIcon(ruta);
		 Image image= img.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		 
		 return image;
	}
	
	public Image get_Imagen_Redimencionada(ImageIcon img,int w,int h){
		 Image image= img.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);	 
		 return image;
	}
	
	public boolean valida_extencion(String ruta){
		File file = new File(ruta);
		//if(file.){}
		System.out.println(file.getName());
		return false;
	}
}
