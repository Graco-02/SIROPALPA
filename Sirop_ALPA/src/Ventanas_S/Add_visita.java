package Ventanas_S;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

import Utilidades.ResultSetTableModel;
import Entidades.visita;
import Formularios.Add_Visita_frm;

public class Add_visita extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Add_Visita_frm visita_frm;
	
	public Add_visita(ResultSetTableModel resultado) {
		visita_frm = new Add_Visita_frm(resultado);
		
		this.setTitle("Generar Visita");
		this.setSize(700, 700);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_conten());
		this.setVisible(true);
	}
	
	public Add_visita(visita visita,ResultSetTableModel resultado) {
		visita_frm = new Add_Visita_frm(resultado);
		visita_frm.set_visita(visita);
		
		this.setTitle("Generar Visita");
		this.setSize(700, 700);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_conten());
		this.setVisible(true);
	}

	public JPanel get_conten(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(visita_frm,BorderLayout.CENTER);
		return panel;
	}
}
