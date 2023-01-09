package Formularios;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controladores.Foto_control;
import Entidades.Foto;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorFicheros;

public class foto_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel foto_jlb;
	private Foto foto;
	private String ruta_foto;
	
	public foto_frm() {
		this.setLayout(new BorderLayout());
		this.add(get_foto_lbl(),BorderLayout.CENTER);
	}
	
	public JLabel get_foto_lbl(){
		
		foto_jlb = new JLabel( 
				new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/usuarios.png", 200, 200)));
		
		foto_jlb.setBorder(BorderFactory.createTitledBorder(""));
		
		foto_jlb.addMouseListener(new MouseAdapter() {
			

			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser archivo = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JPG & GIF Images", "jpg", "gif", "png");
			    archivo.setFileFilter(filter);
			    
			    int returnVal = archivo.showOpenDialog(null);
			    
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	ruta_foto = archivo.getSelectedFile().toString();
			    	foto_jlb.setIcon(new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada(ruta_foto, 200, 200)));
			    	System.out.println(new ManejadorFicheros().get_file_byte(ruta_foto));
			    	if(foto!=null){
			    		foto.set_ruta_foto(ruta_foto);
			    		foto.setArchivo(new ManejadorFicheros().get_file_byte(ruta_foto));
			    		
			    	}
			    }//compruebe que se haya seleccionado un objeto filtrado 
			}//fin del evento mouse
		});
		
		return foto_jlb;
	}
	
	public Foto get_foto(){
		
		if(foto==null){
			foto = new Foto();
			foto.set_id(new Foto_control().get_NextNum());
		}
		
		if(ruta_foto != null && ruta_foto.length() > 2){
		  foto.set_ruta_foto(ruta_foto);
	 	  foto.setArchivo(new ManejadorFicheros().get_file_byte(ruta_foto));
	 	  System.out.println("Foto seleccionada");
		}else{
			foto.set_ruta_foto(" ");
			System.out.println("Sin foto seleccionada");
		}
		
		return this.foto;
	}
	
	public void set_foto(Foto ft){
		
		this.foto = ft;
		if(this.foto.get_ruta_foto().length() > 2){
			foto_jlb.setIcon(new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada(foto.get_ruta_foto(), 200, 200)));
		}else{
			foto_jlb.setIcon( new ImageIcon(
					new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/usuarios.png", 200, 200)
					) );
		}
		ruta_foto = foto.get_ruta_foto();
	}
	
	public void resize_img(int w,int h){
		foto_jlb.setIcon(new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/usuarios.png", h, w)));
	}
	
	public void set_limpiar(){
		this.foto = null;
		foto_jlb.setIcon(new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/usuarios.png", 200, 200)));
		ruta_foto = "";
	}

}
