package Ventanas_S;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Utilidades.Gestor_Imagenes;

public class Inicio extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel etiqueta_img;

	public Inicio() {
		this.setTitle("SIROP ALPA");
		this.setSize(600, 300);
		this.setModal(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_panel());
		this.setVisible(true);
	}

	
	private JPanel get_panel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(get_Etiqueta_Img(),BorderLayout.CENTER);
		return panel;
	}
	
	private JLabel get_Etiqueta_Img(){
		etiqueta_img = new JLabel();
		etiqueta_img.setIcon(new ImageIcon(
				new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/acceso.png", 300, 250)));
		return etiqueta_img;
	}
}
