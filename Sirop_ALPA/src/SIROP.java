import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Controladores.Contacto_control;
import Controladores.Lugar_control;
import Controladores.Usuario_control;
import Entidades.Usuario;
import Entidades.visita;
import Utilidades.DBconeccion;
import Utilidades.Visita_stick;
import Ventanas_P.Adm_Reservas;
import Ventanas_P.Adm_averias;
import Ventanas_P.Adm_contratistas;
import Ventanas_P.Adm_lugares;
import Ventanas_P.Adm_usuarios;
import Ventanas_P.Gestor_Betados;
import Ventanas_P.Gestor_paquetes;
import Ventanas_P.Gestor_visitas;
import Ventanas_P.Loggin;
import Ventanas_S.Add_lista_negra;
import Ventanas_S.Add_visita;
import Ventanas_S.Averias_formulario;
import Ventanas_S.Contratista_formulario;
import Ventanas_S.Usuario_Data;
import Ventanas_S.reserva_formulario;


public class SIROP {

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		// TODO Auto-generated method stub
		colocarSkin();
		//new Adm_averias();
		//new Contratista_formulario();
		//new Adm_contratistas();
		//new Adm_lugares();
		//new Gestor_visitas();
		//new Gestor_paquetes();
		//new Adm_usuarios();
	    // new Loggin();
		//new DBconeccion();
		//new reserva_formulario(null);
		new Adm_Reservas();
		//new Add_lista_negra();
		//new Add_visita();  //OJO hay probllemas aqui
		//new Gestor_Betados();
		//new Usuario_Data();
	}
	
	public static void colocarSkin() throws UnsupportedLookAndFeelException{
		 try{
			  
             JFrame.setDefaultLookAndFeelDecorated(true);
             JDialog.setDefaultLookAndFeelDecorated(true);
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
           //  UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
           UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
	  }
}
