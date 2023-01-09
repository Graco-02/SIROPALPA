package Formularios;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Utilidades.Fecha;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Ventanas_S.Date_Time_GUI;

public class busqueda_visita_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fecha_ini,fecha_fin;
	private ResultSetTableModel resultado;
	
	public busqueda_visita_frm(ResultSetTableModel resultado){
		this.resultado = resultado;
		fecha_fin = fecha_fin.valueOf(new Fecha().getFechaSystemaYYMMDD());
		fecha_ini = fecha_ini.valueOf(new Fecha().getFechaSystemaYYMMDD());
		
		
		this.setLayout(new GridLayout(0, 2));
		this.setBackground(Color.DARK_GRAY);
		
		this.add(get_txtFecha_Ini());
		this.add(get_txtFecha_Fin());
	}
	

	private JTextField get_txtFecha_Ini(){
		JTextField txt_fecha_ini = new JTextField(fecha_ini.toString());
		txt_fecha_ini.setEditable(false);
		txt_fecha_ini.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				fecha_ini = new Date_Time_GUI().get_fecha_sql();
				txt_fecha_ini.setText(fecha_ini.toString());
				set_query();
			}
			
		});
		
		return txt_fecha_ini;
	}
	
	private JTextField get_txtFecha_Fin(){
		JTextField txt_fecha_fin = new JTextField(fecha_fin.toString());
		txt_fecha_fin.setEditable(false);
		txt_fecha_fin.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				fecha_fin = new Date_Time_GUI().get_fecha_sql();
				txt_fecha_fin.setText(fecha_fin.toString());
				set_query();
			}
			
		});
		
		return txt_fecha_fin;
	}
	
	private void set_query(){
		try {
			resultado.setQuery(new ManejadorReportes().get_query_visitas(fecha_ini.toString(), fecha_fin.toString()));
		} catch (IllegalStateException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
