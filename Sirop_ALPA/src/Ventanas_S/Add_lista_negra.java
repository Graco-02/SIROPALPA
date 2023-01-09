/**
 * 
 */
package Ventanas_S;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

import Entidades.bloqueo;
import Entidades.visita;
import Formularios.Add_BL_frm;

/**
 * @author FFerr
 *
 */
public class Add_lista_negra extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Add_BL_frm bl_frm;
	/**
	 * 
	 */
	public Add_lista_negra() {
		bl_frm = new Add_BL_frm();
		
		this.setTitle("Reportar Incidencia");
		this.setSize(700, 500);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_conten());
		this.setVisible(true);
	}
	
	public Add_lista_negra(bloqueo blq) {
		bl_frm = new Add_BL_frm();
		bl_frm.set_betado(blq);
		
		this.setTitle("Reportar Incidencia");
		this.setSize(700, 500);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_conten());
		this.setVisible(true);
	}
	
	public Add_lista_negra(visita visita) {
		bl_frm = new Add_BL_frm(visita);
		
		this.setTitle("Reportar Incidencia");
		this.setSize(700, 500);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_conten());
		this.setVisible(true);
	}
	
	public JPanel get_conten(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(bl_frm,BorderLayout.CENTER);
		return panel;
	}

}
